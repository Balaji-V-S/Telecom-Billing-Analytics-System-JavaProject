package com.tabs.dao;

import com.tabs.models.Subscription;
import java.util.*;

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptions.put(subscription.getSubscriptionId(), subscription);
    }

    @Override
    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptions.get(subscriptionId);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomer(String custId) {
        List<Subscription> result = new ArrayList<>();
        for (Subscription sub : subscriptions.values()) {
            if (sub.getCustId().equals(custId)) {
                result.add(sub);
            }
        }
        return result;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return new ArrayList<>(subscriptions.values());
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptions.put(subscription.getSubscriptionId(), subscription);
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        subscriptions.remove(subscriptionId);
    }

    @Override
    public Subscription getSubscriptionByPhoneNumber(String phoneNumber) {
        for (Subscription sub : subscriptions.values()) {
            if (sub.getPhoneNumber().equals(phoneNumber)) {
                return sub;
            }
        }
        return null;
    }
}
