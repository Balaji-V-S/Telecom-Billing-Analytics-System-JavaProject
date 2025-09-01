package com.tabs.services;

import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.Customer;
import com.tabs.models.Plan;
import com.tabs.models.Subscription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tabs.utility.PlanConfig.SYSTEM_PLAN;
import static com.tabs.utility.PlanConfig.SYSTEM_PLAN_LITE;

public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionDAO subscriptionDAO;
    private CustomerDAO customerDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO, CustomerDAO customerDAO) {
        this.subscriptionDAO = subscriptionDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public Subscription addSubscription(String customerId, String phoneNumber, int planNumber, LocalDateTime startDate) throws CustomerNotFoundException{
        List<Plan> plans = new ArrayList<Plan>();
        plans.add(SYSTEM_PLAN);
        plans.add(SYSTEM_PLAN_LITE);
        Plan planToAdd = planNumber == 1 ? plans.get(0): plans.get(1);
        String planName = planToAdd.getPlanName();
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Cannot add subscription. Customer with ID '" + customerId + "' not found.");
        }

        Subscription sub = new Subscription();
        sub.setSubscriptionId(UUID.randomUUID().toString().substring(0, 8));
        sub.setCustId(customerId);
        sub.setPhoneNumber(phoneNumber);
        sub.setSubsStartDate(startDate);
        sub.setPlanId(planName);

        subscriptionDAO.addSubscription(sub);
        customer.getPhoneNumbers().add(phoneNumber);
        customerDAO.updateCustomer(customer);

        return sub;
    }

    @Override
    public Subscription addSubscription(String customerId, String phoneNumber, int planNumber) throws CustomerNotFoundException{
        List<Plan> plans = new ArrayList<Plan>();
        plans.add(SYSTEM_PLAN);
        plans.add(SYSTEM_PLAN_LITE);
        Plan planToAdd = planNumber == 1 ? plans.get(0): plans.get(1);
        String planName = planToAdd.getPlanName();
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Cannot add subscription. Customer with ID '" + customerId + "' not found.");
        }

        Subscription sub = new Subscription();
        sub.setSubscriptionId(UUID.randomUUID().toString().substring(0, 8));
        sub.setCustId(customerId);
        sub.setPhoneNumber(phoneNumber);
        sub.setSubsStartDate(LocalDateTime.now());
        sub.setPlanId(planName);

        subscriptionDAO.addSubscription(sub);
        customer.getPhoneNumbers().add(phoneNumber);
        customerDAO.updateCustomer(customer);

        return sub;
    }

    @Override
    public Subscription addSubscription(String customerId, String phoneNumber) throws CustomerNotFoundException {
        return null;
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