package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.models.Customer;
import com.tabs.models.Invoice;
import com.tabs.models.Subscription;
import com.tabs.utility.PlanConfig;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;
    private SubscriptionDAO subscriptionDAO;
    private BillingDAO billingDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO, SubscriptionDAO subscriptionDAO, BillingDAO billingDAO) {
        this.customerDAO = customerDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.billingDAO = billingDAO;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(String custId) throws CustomerNotFoundException{
        Customer customer = customerDAO.getCustomerById(custId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID '" + custId + "' not found.");
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(String custId) throws CustomerNotFoundException {
        Customer customerToDelete = getCustomerById(custId); // Uses the exception-throwing getter

        // Step 1: Delete all associated subscriptions
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByCustomer(custId);
        for (Subscription sub : subscriptions) {
            subscriptionDAO.deleteSubscription(sub.getSubscriptionId());
        }
        System.out.println("  - Deleted " + subscriptions.size() + " associated subscription(s).");

        // Step 2: Nullify any referrals pointing to this customer
        for (Customer otherCustomer : customerDAO.getAllCustomers()) {
            if (custId.equals(otherCustomer.getReferredBy())) {
                otherCustomer.setReferredBy(null);
                customerDAO.updateCustomer(otherCustomer);
                System.out.println("  - Cleared referral from customer: " + otherCustomer.getName());
            }
        }

        // Step 3: Finally, delete the customer
        customerDAO.deleteCustomer(custId);
        System.out.println("  - Successfully deleted customer: " + customerToDelete.getName());
    }

    @Override
    public void runCreditControlCheck() {
        System.out.println("Running credit control check for date: " + LocalDate.now());
        for (Customer customer : customerDAO.getAllCustomers()) {
            List<Invoice> invoices = billingDAO.getInvoicesByCustomer(customer.getCustId());
            boolean hasOverdueInvoice = invoices.stream()
                    .anyMatch(inv -> inv.getPaymentStatus() == Invoice.PaymentStatus.PENDING &&
                            ChronoUnit.DAYS.between(inv.getBillingDate(), LocalDate.now()) > PlanConfig.CREDIT_CONTROL_DAYS_THRESHOLD);

            if (hasOverdueInvoice && !customer.isCreditBlocked()) {
                customer.setCreditBlocked(true);
                customerDAO.updateCustomer(customer);
                System.out.println("  - Credit BLOCKED for customer: " + customer.getName() + " due to unpaid invoices.");
            } else if (!hasOverdueInvoice && customer.isCreditBlocked()) {
                customer.setCreditBlocked(false);
                customerDAO.updateCustomer(customer);
                System.out.println("  - Credit UNBLOCKED for customer: " + customer.getName() + " as all overdue invoices are cleared.");
            }
        }
    }
}