package com.tabs.dao;

import com.tabs.models.Usage;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UsageDAOImpl implements UsageDAO {
    private static Map<String, List<Usage>> usageBySubscriptionId = new ConcurrentHashMap<>();

    @Override
    public void addUsage(Usage usage) {
        if (usage != null && usage.getSubscriptionId() != null) {
            usageBySubscriptionId.computeIfAbsent(usage.getSubscriptionId(), k -> new ArrayList<>()).add(usage);
        }
    }

    @Override
    public List<Usage> getUsageBySubscriptionId(String subscriptionId) {
        return usageBySubscriptionId.getOrDefault(subscriptionId, new ArrayList<>());
    }

    @Override
    public List<Usage> getUsageBySubscriptionIdAndPeriod(String subscriptionId, YearMonth period) {
        return getUsageBySubscriptionId(subscriptionId).stream()
                .filter(usage -> YearMonth.from(usage.getUsageStartTime()).equals(period))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Usage>> getAllUsageBySubscription() {
        // Returning a new copy map to prevent external modification
        return new ConcurrentHashMap<>(usageBySubscriptionId);
    }
}