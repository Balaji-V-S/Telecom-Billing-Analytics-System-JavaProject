package com.tabs.dao;

import com.tabs.models.Customer;
import com.tabs.models.Usage;
import com.tabs.models.Invoice;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {

    private Map<String, Customer> customers = new HashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustId(), customer);
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
        customers.put(customer.getCustId(), customer);
    }

    @Override
    public void deleteCustomer(String custId) {
        customers.remove(custId);
    }

    @Override
    public void addUsage(String custId, String phoneNumber, Usage usage) {
        Customer customer = customers.get(custId);
        if (customer != null) {
            customer.getUsageByNumber()
                    .computeIfAbsent(phoneNumber, k -> new ArrayList<>())
                    .add(usage);
        }
    }

    @Override
    public List<Usage> getUsageByNumber(String custId, String phoneNumber) {
        Customer customer = customers.get(custId);
        if (customer != null) {
            return customer.getUsageByNumber().getOrDefault(phoneNumber, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public void addInvoice(String custId, String phoneNumber, Invoice invoice) {
        Customer customer = customers.get(custId);
        if (customer != null) {
            customer.getInvoicesByNumber()
                    .computeIfAbsent(phoneNumber, k -> new ArrayList<>())
                    .add(invoice);
        }
    }

    @Override
    public List<Invoice> getInvoicesByNumber(String custId, String phoneNumber) {
        Customer customer = customers.get(custId);
        if (customer != null) {
            return customer.getInvoicesByNumber().getOrDefault(phoneNumber, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
