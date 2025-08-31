package com.tabs.dao;

import com.tabs.models.Invoice;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BillingDAOImpl implements BillingDAO {

    private Map<String, Invoice> invoices = new ConcurrentHashMap<>();

    @Override
    public void addInvoice(Invoice invoice) {
        if (invoice != null && invoice.getInvoiceId() != null) {
            invoices.put(invoice.getInvoiceId(), invoice);
        }
    }

    @Override
    public Invoice getInvoiceById(String invoiceId) {
        return invoices.get(invoiceId);
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(String custId) {
        if (custId == null) {
            return new ArrayList<>();
        }
        return invoices.values().stream()
                .filter(inv -> custId.equals(inv.getCustId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        if (invoice != null && invoice.getInvoiceId() != null) {
            invoices.put(invoice.getInvoiceId(), invoice);
        }
    }

    @Override
    public List<Invoice> getInvoicesBySubscriptionAndPeriod(String subscriptionId, YearMonth period) {
        if (subscriptionId == null || period == null) {
            return new ArrayList<>();
        }
        return invoices.values().stream()
                .filter(inv -> subscriptionId.equals(inv.getSubscriptionId()) &&
                        period.equals(YearMonth.from(inv.getBillingDate())))
                .collect(Collectors.toList());
    }
}