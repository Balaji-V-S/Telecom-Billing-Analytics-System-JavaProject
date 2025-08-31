package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.BillingDAOImpl;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.CustomerDAOImpl;
import com.tabs.models.Customer;
import com.tabs.models.Invoice;

import java.util.ArrayList;
import java.util.List;

public class BillingServiceImpl implements BillingService {

    private BillingDAO billingDAO = new BillingDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void addInvoice(Invoice invoice) {
        // Step 1: Add to BillingDAO store
        billingDAO.addInvoice(invoice);

        // Step 2: Also attach invoice to the right Customer in CustomerDAO
        Customer customer = customerDAO.getCustomerById(invoice.getCustId());
        if (customer != null) {
            customer.getInvoicesByNumber(invoice.getCustId(),invoice.getPhoneNumber())
                    .computeIfAbsent(invoice.getPhoneNumber(), k -> new ArrayList<>())
                    .add(invoice);
        }
    }

    @Override
    public Invoice getInvoiceById(String invoiceId) {
        return billingDAO.getInvoiceById(invoiceId);
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(String custId) {
        return billingDAO.getInvoicesByCustomer(custId);
    }

    @Override
    public List<Invoice> getInvoicesByPhoneNumber(String phoneNumber) {
        return billingDAO.getInvoicesByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return billingDAO.getAllInvoices();
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        billingDAO.updateInvoice(invoice);

        // sync CustomerDAO side
        Customer customer = customerDAO.getCustomerById(invoice.getCustId());
        if (customer != null) {
            List<Invoice> invoiceList = customer.getInvoices().get(invoice.getPhoneNumber());
            if (invoiceList != null) {
                invoiceList.removeIf(inv -> inv.getInvoiceId().equals(invoice.getInvoiceId()));
                invoiceList.add(invoice);
            }
        }
    }

    @Override
    public void deleteInvoice(String invoiceId) {
        Invoice invoice = billingDAO.getInvoiceById(invoiceId);
        if (invoice != null) {
            // Deleting invoices from BillingDAO
            billingDAO.deleteInvoice(invoiceId);

            // Deleting Invoices from CustomerDAO’s invoices map
            Customer customer = customerDAO.getCustomerById(invoice.getCustId());
            if (customer != null) {
                List<Invoice> invoiceList = customer.getInvoices().get(invoice.getPhoneNumber());
                if (invoiceList != null) {
                    invoiceList.removeIf(inv -> inv.getInvoiceId().equals(invoiceId));
                }
            }
        }
    }
}
