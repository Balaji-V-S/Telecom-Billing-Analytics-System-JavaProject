package com.tabs.dao;
import com.tabs.models.Invoice;
import java.util.List;

public interface BillingDAO {
    void addInvoice(Invoice invoice);
    Invoice getInvoiceById(String invoiceId);
    List<Invoice> getInvoicesByCustomer(String custId);
    List<Invoice> getInvoicesByPhoneNumber(String phoneNumber);
    List<Invoice> getAllInvoices();
    void updateInvoice(Invoice invoice);
    void deleteInvoice(String invoiceId);
}
