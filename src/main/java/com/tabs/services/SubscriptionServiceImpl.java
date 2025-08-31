package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.BillingDAOImpl;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.dao.SubscriptionDAOImpl;
import com.tabs.models.Subscription;
import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {


    SubscriptionDAO subscriptionDAO=new SubscriptionDAOImpl();

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptionDAO.addSubscription(subscription);
    }

    @Override
    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptionDAO.getSubscriptionById(subscriptionId);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomer(String custId) {
        return subscriptionDAO.getSubscriptionsByCustomer(custId);
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

