package com.tabs.services;


import com.tabs.models.Subscription;
import java.util.*;

public interface SubscriptionService {
    void addSubscription(Subscription subscription);
    Subscription getSubscriptionById(String subscriptionId);
    List<Subscription> getSubscriptionsByCustomer(String custId);
    List<Subscription> getAllSubscriptions();
    void updateSubscription(Subscription subscription);
    void deleteSubscription(String subscriptionId);
    Subscription getSubscriptionByPhoneNumber(String phoneNumber);
}

