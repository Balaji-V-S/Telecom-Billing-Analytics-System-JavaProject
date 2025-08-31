package com.tabs.services;

import com.tabs.dao.*;
import com.tabs.exceptions.CreditBlockedException;
import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.Customer;
import com.tabs.models.Subscription;
import com.tabs.models.Usage;

public class UsageServiceImpl implements UsageService {

    private UsageDAO usageDAO=new UsageDAOImpl();
    private SubscriptionDAO subscriptionDAO=new SubscriptionDAOImpl();
    private CustomerDAO customerDAO=new CustomerDAOImpl();

    public UsageServiceImpl(UsageDAO usageDAO, SubscriptionDAO subscriptionDAO, CustomerDAO customerDAO) {
        this.usageDAO = usageDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public void addUsage(Usage usage) throws SubscriptionNotFoundException, CustomerNotFoundException, CreditBlockedException {
        if (usage == null || usage.getSubscriptionId() == null) {
            // This is a programming error, so a generic exception is acceptable here.
            throw new IllegalArgumentException("Usage record or subscription ID cannot be null.");
        }

        Subscription subscription = subscriptionDAO.getSubscriptionById(usage.getSubscriptionId());
        if (subscription == null) {
            throw new SubscriptionNotFoundException("Cannot add usage. Subscription with ID '" + usage.getSubscriptionId() + "' not found.");
        }

        Customer customer = customerDAO.getCustomerById(subscription.getCustId());
        if (customer == null) {
            throw new CustomerNotFoundException("Cannot add usage. Customer with ID '" + subscription.getCustId() + "' not found.");
        }

        if (customer.isCreditBlocked()) {
            throw new CreditBlockedException("Cannot add usage. Customer '" + customer.getName() + "' is credit-blocked.");
        }

        // If all checks pass, finally add the usage
        usageDAO.addUsage(usage);
    }
}