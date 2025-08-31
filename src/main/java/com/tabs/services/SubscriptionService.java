package com.tabs.services;

import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.Subscription;
import java.util.List;

public interface SubscriptionService {
    Subscription addSubscription(String customerId, String phoneNumber) throws CustomerNotFoundException;
    void updateMnpStatus(String subscriptionId, boolean mnpStatus) throws SubscriptionNotFoundException;
    // Add other lifecycle methods like changePlan, cancelSubscription - out of scope
}