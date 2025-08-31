package com.tabs.services;

import com.tabs.models.Customer;
import com.tabs.models.Usage;
import com.tabs.models.Invoice;
import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    Customer getCustomerById(String custId);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(String custId);

    // Usage Operations
    void addUsage(String custId, String phoneNumber, Usage usage);
    List<Usage> getUsageByNumber(String custId, String phoneNumber);

    //Invoice Operations
    void addInvoice(String custId, String phoneNumber, Invoice invoice);
    List<Invoice> getInvoicesByNumber(String custId, String phoneNumber);

    // Credit / Blocking
    boolean isCustomerCreditBlocked(String customerId);
    void blockCustomer(String customerId);
    void unblockCustomer(String customerId);
}
