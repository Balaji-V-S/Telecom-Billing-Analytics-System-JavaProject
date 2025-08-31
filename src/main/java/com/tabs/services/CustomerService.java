package com.tabs.services;

import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.models.Customer;
import java.util.List;


public interface CustomerService {
    void addCustomer(Customer customer);
    Customer getCustomerById(String custId) throws CustomerNotFoundException;
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(String custId) throws CustomerNotFoundException;
    void runCreditControlCheck();
}