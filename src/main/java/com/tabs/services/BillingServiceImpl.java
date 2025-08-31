package com.tabs.services;


import com.tabs.dao.BillingDAO;
import com.tabs.dao.BillingDAOImpl;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.CustomerDAOImpl;
import com.tabs.models.Invoice;
import java.util.List;

public class BillingServiceImpl implements BillingService {
    BillingDAO billingDAO=new BillingDAOImpl();

    @Override
    public void addInvoice(Invoice invoice) {
        billingDAO.addInvoice(invoice);
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
    }

    @Override
    public void deleteInvoice(String invoiceId) {
        billingDAO.deleteInvoice(invoiceId);
    }
}

