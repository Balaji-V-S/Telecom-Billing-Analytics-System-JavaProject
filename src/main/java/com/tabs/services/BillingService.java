package com.tabs.services;

import com.tabs.exceptions.InvoiceGenerationException;
import com.tabs.models.Invoice;
import com.tabs.models.Plan;
import java.time.YearMonth;
import java.util.List;

/**
 * Service interface for the core billing engine.
 */
public interface BillingService {
    /**
     * Generates a monthly invoice for a single subscription, applying all business rules.
     * @param subscriptionId The ID of the subscription to bill.
     * @param billingPeriod The month for which to generate the invoice (e.g., 2025-08).
     * @return The newly created and saved Invoice object.
     */
    Invoice generateInvoiceForSubscription(String subscriptionId, YearMonth billingPeriod) throws InvoiceGenerationException;

    List<Invoice> getInvoicesByCustomer(String custId);

    void markInvoiceAsPaid(String invoiceId);

    Plan getSystemPlan();
}