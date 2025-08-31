package com.tabs.dao;

import com.tabs.models.Usage;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface UsageDAO {
    void addUsage(Usage usage);
    List<Usage> getUsageBySubscriptionId(String subscriptionId);
    List<Usage> getUsageBySubscriptionIdAndPeriod(String subscriptionId, YearMonth period);
    Map<String, List<Usage>> getAllUsageBySubscription();
}