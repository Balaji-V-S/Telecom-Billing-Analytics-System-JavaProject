package com.tabs.dao;

import com.tabs.models.Invoice;
import java.time.YearMonth;
import java.util.List;

public interface BillingDAO {
    void addInvoice(Invoice invoice);
    Invoice getInvoiceById(String invoiceId);
    List<Invoice> getInvoicesByCustomer(String custId);
    List<Invoice> getAllInvoices();
    void updateInvoice(Invoice invoice);
    List<Invoice> getInvoicesBySubscriptionAndPeriod(String subscriptionId, YearMonth period);
}