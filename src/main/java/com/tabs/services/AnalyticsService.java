package com.tabs.services;

import com.tabs.models.Customer;
import com.tabs.models.Plan;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

/**
 * Service interface for running analytical reports on billing data.
 */
public interface AnalyticsService {
    List<Map.Entry<String, Double>> getTopNDataUsers(int n);
    double getSystemArpu();
    DoubleSummaryStatistics getOverageDistribution();
    List<Customer> getCreditRiskCustomers();
    Map<String, String> getPlanRecommendations();
}