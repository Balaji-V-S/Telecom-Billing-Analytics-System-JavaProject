package com.tabs.services;

import com.tabs.models.Invoice;
import com.tabs.models.Usage;
import com.tabs.models.Plan;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public List<String> getTopNDataUsers(List<Usage> allUsage, int n) {
        // Aggregate usage by subscription
        Map<String, Double> dataUsageBySub = new HashMap<>();
        for (Usage u : allUsage) {
            dataUsageBySub.merge(u.getSubscriptionId(), u.getDataUsedGB(), Double::sum);
        }
        // Sort and return top N subscriptionId
        return dataUsageBySub.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getArpuByPlan(List<Invoice> allInvoices) {
        Map<String, List<Invoice>> invoicesByPlan = allInvoices.stream()
                .collect(Collectors.groupingBy(Invoice::getSubscriptionId));

        Map<String, Double> arpuByPlan = new HashMap<>();
        for (Map.Entry<String, List<Invoice>> entry : invoicesByPlan.entrySet()) {
            String planId = entry.getKey();
            List<Invoice> invoices = entry.getValue();
            double avgRevenue = invoices.stream()
                    .mapToDouble(Invoice::getGrandTotal)
                    .average()
                    .orElse(0.0);
            arpuByPlan.put(planId, avgRevenue);
        }
        return arpuByPlan;
    }

    @Override
    public Map<String, Double> getOverageCharges(List<Invoice> allInvoices) {
        Map<String, Double> overageByCust = new HashMap<>();
        for (Invoice inv : allInvoices) {
            double overage = (inv.getOverageFare() == null ? 0.0 : inv.getOverageFare())
                    + (inv.getRoamingCharges() == null ? 0.0 : inv.getRoamingCharges());
            overageByCust.merge(inv.getCustId(), overage, Double::sum);
        }
        return overageByCust;
    }

    @Override
    public List<String> getCreditRiskCustomers(List<Invoice> allInvoices) {
        return allInvoices.stream()
                .filter(inv -> inv.getPaymentStatus() == Invoice.PaymentStatus.PENDING)
                .map(Invoice::getCustId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> recommendPlans(List<Usage> usageRecords, List<Plan> plans) {
        Map<String, Double> totalDataBySub = new HashMap<>();
        for (Usage usage : usageRecords) {
            totalDataBySub.merge(usage.getSubscriptionId(), usage.getDataUsedGB(), Double::sum);
        }

        Map<String, String> recommendations = new HashMap<>();
        for (Map.Entry<String, Double> entry : totalDataBySub.entrySet()) {
            String subscriptionId = entry.getKey();
            Double totalData = entry.getValue();

            // Pick the cheapest plan that can satisfy this usage
            Plan recommended = plans.stream()
                    .filter(p -> p.getDataAllowanceGB() >= totalData)
                    .min(Comparator.comparing(Plan::getMonthlyRental))
                    .orElse(null);

            if (recommended != null) {
                recommendations.put(subscriptionId, recommended.getPlanName());
            }
        }
        return recommendations;
    }
}



