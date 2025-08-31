package com.tabs.services;

import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.models.Customer;
import java.util.List;

/**
 * Service interface for managing customer-related business logic,
 * including lifecycle and credit control.
 */
public interface CustomerService {

    /**
     * Adds a new customer to the system.
     * @param customer The customer object to add.
     */
    void addCustomer(Customer customer);

    /**
     * Retrieves a customer by their unique ID.
     * @param custId The ID of the customer to find.
     * @return The found Customer object.
     */
    Customer getCustomerById(String custId) throws CustomerNotFoundException;

    /**
     * Retrieves a list of all customers in the system.
     * @return A list of all customers.
     */
    List<Customer> getAllCustomers();

    /**
     * Updates an existing customer's details.
     * @param customer The customer object with updated information.
     */
    void updateCustomer(Customer customer);

    /**
     * Deletes a customer and handles all cascading logic,
     * such as removing their subscriptions and cleaning up referrals.
     * @param custId The ID of the customer to delete.
     */
    void deleteCustomer(String custId) throws CustomerNotFoundException;

    /**
     * Scans all customers and their invoices to automatically block services
     * for those with payments overdue by a configured number of days.
     */
    void runCreditControlCheck();
}