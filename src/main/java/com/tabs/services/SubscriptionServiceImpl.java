package com.tabs.services;

import com.tabs.dao.SubscriptionDAO;
import com.tabs.dao.SubscriptionDAOImpl;
import com.tabs.models.Plan;
import com.tabs.models.Subscription;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();

    @Override
    public void addSubscription(String customerId, Subscription subscription) {
        // Attach default plan (singleton or pre-configured instance)
        Plan defaultPlan = Plan.getDefaultPlan();
        subscription.setPlan(defaultPlan);

        subscriptionDAO.addSubscription(customerId, subscription);
    }

    @Override
    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptionDAO.getSubscriptionById(subscriptionId);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomer(String customerId) {
        return subscriptionDAO.getSubscriptionsByCustomer(customerId);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionDAO.getAllSubscriptions();
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        subscriptionDAO.deleteSubscription(subscriptionId);
    }

    @Override
    public Subscription getSubscriptionByPhoneNumber(String phoneNumber) {
        return subscriptionDAO.getSubscriptionByPhoneNumber(phoneNumber);
    }
}
