package com.tabs.services;

import com.tabs.exceptions.InvoiceGenerationException;
import com.tabs.models.Invoice;
import com.tabs.models.Plan;
import java.time.YearMonth;
import java.util.List;

public interface BillingService {
    Invoice generateInvoiceForSubscription(String subscriptionId, YearMonth billingPeriod) throws InvoiceGenerationException;

    List<Invoice> getInvoicesByCustomer(String custId);

    void markInvoiceAsPaid(String invoiceId);

    Plan getSystemPlan();
}