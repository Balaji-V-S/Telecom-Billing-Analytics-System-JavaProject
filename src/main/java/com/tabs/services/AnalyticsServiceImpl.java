package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.dao.UsageDAO;
import com.tabs.models.Customer;
import com.tabs.models.Invoice;
import com.tabs.models.Usage;
import com.tabs.utility.PlanConfig;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsServiceImpl implements AnalyticsService {
    private final UsageDAO usageDAO;
    private final BillingDAO billingDAO;
    private final SubscriptionDAO subscriptionDAO;
    private final CustomerDAO customerDAO;

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
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());

        return riskyCustomerIds.stream()
                .map(customerDAO::getCustomerById)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getPlanRecommendations() {
        // In a single-plan system, this recommends subscribing if not already a user, or suggests checking for family plan benefits.
        // This is a simplified implementation for the single-plan context.
        return customerDAO.getAllCustomers().stream()
                .filter(c -> subscriptionDAO.getSubscriptionsByCustomer(c.getCustId()).isEmpty())
                .collect(Collectors.toMap(
                        Customer::getName,
                        c -> "This customer has no active subscriptions. Recommend the Universal Plan."
                ));
    }
}