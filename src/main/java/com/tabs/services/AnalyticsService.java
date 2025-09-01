package com.tabs.services;

import com.tabs.models.Customer;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {
    List<Map.Entry<String, Double>> getTopNDataUsers(int n);
    double getSystemArpu();
    DoubleSummaryStatistics getOverageDistribution();
    List<Customer> getCreditRiskCustomers();
    Map<String, String> getPlanRecommendations();
}