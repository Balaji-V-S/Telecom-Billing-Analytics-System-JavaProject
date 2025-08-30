package com.tabs.services;

import com.tabs.dao.CustomerDAO;
import com.tabs.dao.CustomerDAOImpl;
import com.tabs.models.Customer;
import com.tabs.models.Invoice;
import com.tabs.models.Usage;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO=new CustomerDAOImpl();

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(String custId) {
        return customerDAO.getCustomerById(custId);
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
    public void deleteCustomer(String custId) {
        customerDAO.deleteCustomer(custId);
    }

    @Override
    public void addUsage(String custId, String phoneNumber, Usage usage) {
        customerDAO.addUsage(custId, phoneNumber, usage);
    }

    @Override
    public List<Usage> getUsageByNumber(String customerId, String phoneNumber) {
        return customerDAO.getUsageByNumber(customerId, phoneNumber);
    }

    @Override
    public void addInvoice(String customerId, String phoneNumber, Invoice invoice) {
        customerDAO.addInvoice(customerId, phoneNumber, invoice);
    }

    @Override
    public List<Invoice> getInvoicesByNumber(String customerId, String phoneNumber) {
        return customerDAO.getInvoicesByNumber(customerId, phoneNumber);
    }

    @Override
    public boolean isCustomerCreditBlocked(String customerId) {
        return customerDAO.isCustomerCreditBlocked(customerId);
    }

    @Override
    public void blockCustomer(String customerId) {
        customerDAO.blockCustomer(customerId);
    }

    @Override
    public void unblockCustomer(String customerId) {
        customerDAO.unblockCustomer(customerId);
    }
}
