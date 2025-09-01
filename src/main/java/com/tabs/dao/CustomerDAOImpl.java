package com.tabs.dao;

import com.tabs.models.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerDAOImpl implements CustomerDAO {

    private static Map<String, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        if (customer != null && customer.getCustId() != null) {
            customers.put(customer.getCustId(), customer);
        }
    }

    @Override
    public Customer getCustomerById(String custId) {
        return customers.get(custId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer != null && customer.getCustId() != null) {
            customers.put(customer.getCustId(), customer);
        }
    }

    @Override
    public void deleteCustomer(String custId) {
        customers.remove(custId);
    }
}