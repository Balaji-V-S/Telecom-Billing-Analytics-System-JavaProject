package com.tabs.services;

import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.Customer;
import com.tabs.models.Subscription;
import java.time.LocalDateTime;
import java.util.UUID;

public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionDAO subscriptionDAO;
    private CustomerDAO customerDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO, CustomerDAO customerDAO) {
        this.subscriptionDAO = subscriptionDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public Subscription addSubscription(String customerId, String phoneNumber) throws CustomerNotFoundException{
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Cannot add subscription. Customer with ID '" + customerId + "' not found.");
        }

        Subscription sub = new Subscription();
        sub.setSubscriptionId(UUID.randomUUID().toString().substring(0, 8));
        sub.setCustId(customerId);
        sub.setPhoneNumber(phoneNumber);
        sub.setSubsStartDate(LocalDateTime.now());

        subscriptionDAO.addSubscription(sub);
        customer.getPhoneNumbers().add(phoneNumber);
        customerDAO.updateCustomer(customer);

        return sub;
    }

    @Override
    public void updateMnpStatus(String subscriptionId, boolean mnpStatus) throws SubscriptionNotFoundException {
        Subscription sub = subscriptionDAO.getSubscriptionById(subscriptionId);
        if (sub == null) {
            throw new SubscriptionNotFoundException("Subscription with ID '" + subscriptionId + "' not found.");
        }
        sub.setMnpStatus(mnpStatus);
        subscriptionDAO.updateSubscription(sub);
        System.out.println("MNP status for subscription " + subscriptionId + " updated to: " + mnpStatus);
    }
}