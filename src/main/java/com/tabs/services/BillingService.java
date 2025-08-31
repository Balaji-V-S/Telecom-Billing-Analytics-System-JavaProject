package com.tabs.services;


import com.tabs.models.Invoice;
import java.util.*;

public interface BillingService {
    void addInvoice(Invoice invoice);
    Invoice getInvoiceById(String invoiceId);
    List<Invoice> getInvoicesByCustomer(String custId);
    List<Invoice> getInvoicesByPhoneNumber(String phoneNumber);
    List<Invoice> getAllInvoices();
    void updateInvoice(Invoice invoice);
    void deleteInvoice(String invoiceId);
}

