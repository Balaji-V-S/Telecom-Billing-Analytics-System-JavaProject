package com.tabs.dao;

import com.tabs.models.Subscription;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private static Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

    @Override
    public void addSubscription(Subscription subscription) {
        if (subscription != null && subscription.getSubscriptionId() != null) {
            subscriptions.put(subscription.getSubscriptionId(), subscription);
        }
    }

    @Override
    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptions.get(subscriptionId);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomer(String custId) {
        if (custId == null) {
            return new ArrayList<>();
        }
        return subscriptions.values().stream()
                .filter(sub -> custId.equals(sub.getCustId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return new ArrayList<>(subscriptions.values());
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        if (subscription != null && subscription.getSubscriptionId() != null) {
            subscriptions.put(subscription.getSubscriptionId(), subscription);
        }
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        subscriptions.remove(subscriptionId);
    }

    @Override
    public List<Subscription> findByFamilyId(String familyId) {
        if (familyId == null || familyId.isEmpty()) {
            return new ArrayList<>();
        }
        return subscriptions.values().stream()
                .filter(sub -> familyId.equals(sub.getFamilyId()))
                .collect(Collectors.toList());
    }
}