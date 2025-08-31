package com.tabs.dao;

import com.tabs.models.Invoice;
import java.util.*;

public class BillingDAOImpl implements BillingDAO {

    private Map<String, Invoice> invoices = new HashMap<>();

    @Override
    public void addInvoice(Invoice invoice) {
        invoices.put(invoice.getInvoiceId(), invoice);
    }

    @Override
    public Invoice getInvoiceById(String invoiceId) {
        return invoices.get(invoiceId);
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(String custId) {
        List<Invoice> result = new ArrayList<>();
        for (Invoice inv : invoices.values()) {
            if (inv.getCustId().equals(custId)) {
                result.add(inv);
            }
        }
        return result;
    }

    @Override
    public List<Invoice> getInvoicesByPhoneNumber(String phoneNumber) {
        List<Invoice> result = new ArrayList<>();
        for (Invoice inv : invoices.values()) {
            if (inv.getPhoneNumber().equals(phoneNumber)) {
                result.add(inv);
            }
        }
        return result;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        invoices.put(invoice.getInvoiceId(), invoice);
    }

    @Override
    public void deleteInvoice(String invoiceId) {
        invoices.remove(invoiceId);
    }
}
