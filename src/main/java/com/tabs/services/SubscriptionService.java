package com.tabs.services;

import com.tabs.models.Subscription;
import java.util.List;

public interface SubscriptionService {

    void addSubscription(String customerId, Subscription subscription);

    Subscription getSubscriptionById(String subscriptionId);

    List<Subscription> getSubscriptionsByCustomer(String customerId);

    List<Subscription> getAllSubscriptions();

    void updateSubscription(Subscription subscription);

    void deleteSubscription(String subscriptionId);

    Subscription getSubscriptionByPhoneNumber(String phoneNumber);
}
