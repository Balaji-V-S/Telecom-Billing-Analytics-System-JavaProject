package com.tabs.services;

import com.tabs.models.Invoice;
import com.tabs.models.Usage;
import com.tabs.models.Plan;

import java.util.List;
import java.util.Map;

public interface AnalyticsService {

    // 1. Get top N customers
    List<String> getTopNDataUsers(List<Usage> allUsage, int n);

    // 2. ARPU
    Map<String, Double> getArpuByPlan(List<Invoice> allInvoices);

    // 3. Overage analysis
    Map<String, Double> getOverageCharges(List<Invoice> allInvoices);

    // 4. Credit risk detection
    List<String> getCreditRiskCustomers(List<Invoice> allInvoices);

    // 5. Simple plan recommendations
    Map<String, String> recommendPlans(List<Usage> usageRecords, List<Plan> plans);
}


