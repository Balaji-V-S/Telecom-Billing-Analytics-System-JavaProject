package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.dao.UsageDAO;
import com.tabs.models.*;
import com.tabs.utility.PlanConfig;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsServiceImpl implements AnalyticsService {
    private UsageDAO usageDAO;
    private BillingDAO billingDAO;
    private SubscriptionDAO subscriptionDAO;
    private CustomerDAO customerDAO;

    public AnalyticsServiceImpl(UsageDAO usageDAO, BillingDAO billingDAO, SubscriptionDAO subscriptionDAO, CustomerDAO customerDAO) {
        this.usageDAO = usageDAO;
        this.billingDAO = billingDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Map.Entry<String, Double>> getTopNDataUsers(int n) {
        return usageDAO.getAllUsageBySubscription().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToDouble(Usage::getDataUsedGB).sum()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .toList();
    }

    @Override
    public double getSystemArpu() {
        return billingDAO.getAllInvoices().stream()
                .mapToDouble(Invoice::getGrandTotal)
                .average()
                .orElse(0.0);
    }

    @Override
    public DoubleSummaryStatistics getOverageDistribution() {
        return billingDAO.getAllInvoices().stream()
                // This filter adds extra safety, in case an invoice has a null fare
                .filter(inv -> inv.getOverageFare() != null)
                // This collector does all the work and handles the empty case automatically
                .collect(Collectors.summarizingDouble(Invoice::getOverageFare));
    }

    @Override
    public List<Customer> getCreditRiskCustomers() {
        List<String> riskyCustomerIds = billingDAO.getAllInvoices().stream()
                .filter(inv -> inv.getPaymentStatus() == Invoice.PaymentStatus.PENDING &&
                        ChronoUnit.DAYS.between(inv.getBillingDate(), LocalDate.now()) > PlanConfig.CREDIT_CONTROL_DAYS_THRESHOLD)
                .map(Invoice::getCustId)
                .distinct()
                .toList();

        return riskyCustomerIds.stream()
                .map(customerDAO::getCustomerById)
                .toList();
    }

    @Override
    public Map<String, String> getPlanRecommendations() {
        Map<String, String> recommendations = new HashMap<>();
        List<Plan> allPlans = List.of(PlanConfig.SYSTEM_PLAN, PlanConfig.SYSTEM_PLAN_LITE); // Using plans from PlanConfig
        YearMonth previousMonth = YearMonth.now().minusMonths(1);

        for (Customer customer : customerDAO.getAllCustomers()) {
            List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByCustomer(customer.getCustId());
            double totalDataGb = 0.0;
            double totalVoiceMins = 0.0;
            int totalSMS = 0;

            for (Subscription sub : subscriptions) {
                List<Usage> usages = usageDAO.getUsageBySubscriptionIdAndPeriod(sub.getSubscriptionId(), previousMonth);
                for (Usage usage : usages) {
                    totalDataGb += usage.getDataUsedGB() != null ? usage.getDataUsedGB() : 0.0;
                    totalVoiceMins += usage.getVoiceUsedMins() != null ? usage.getVoiceUsedMins() : 0.0;
                    totalSMS += usage.getSmsUsed() != null ? usage.getSmsUsed() : 0;
                }
            }

            if (allPlans.isEmpty()) {
                recommendations.put(customer.getName(), "No plans are available for recommendation.");
                continue;
            }

            double bestEstimate = Double.MAX_VALUE;
            Plan bestPlan = null;
            for (Plan plan : allPlans) {
                double allowedData = plan.getDataAllowanceGB() != null ? plan.getDataAllowanceGB() : 0.0;
                double allowedVoice = plan.getVoiceAllowedMins() != null ? plan.getVoiceAllowedMins() : 0.0;
                int allowedSMS = plan.getSmsAllowed() != null ? plan.getSmsAllowed() : 0;

                double dataOverage = Math.max(0.0, totalDataGb - allowedData);
                double voiceOverage = Math.max(0.0, totalVoiceMins - allowedVoice);
                int smsOverage = Math.max(0, totalSMS - allowedSMS);

                double estimatedBill = (plan.getMonthlyRental() != null ? plan.getMonthlyRental() : 0.0)
                        + dataOverage * (plan.getDataOverageRatePerGB() != null ? plan.getDataOverageRatePerGB() : 0.0)
                        + voiceOverage * (plan.getVoiceOverageRatePerMin() != null ? plan.getVoiceOverageRatePerMin() : 0.0)
                        + smsOverage * (plan.getSmsOveragePerSMS() != null ? plan.getSmsOveragePerSMS() : 0.0);

                estimatedBill += estimatedBill * PlanConfig.GST_RATE; // GST

                if (estimatedBill < bestEstimate) {
                    bestEstimate = estimatedBill;
                    bestPlan = plan;
                }
            }

            if (bestPlan == null) {
                recommendations.put(customer.getName(), "No suitable plan found.");
            } else {
                recommendations.put(
                        customer.getName(),
                        String.format("Recommended Plan: %s (Monthly Rental: Rs. %.2f, Estimated Bill: Rs. %.2f)",
                                bestPlan.getPlanName(),
                                bestPlan.getMonthlyRental(),
                                bestEstimate)
                );
            }
        }
        return recommendations;
    }

}