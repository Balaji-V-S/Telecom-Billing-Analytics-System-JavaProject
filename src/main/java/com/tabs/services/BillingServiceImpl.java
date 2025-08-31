package com.tabs.services;

import com.tabs.dao.BillingDAO;
import com.tabs.dao.CustomerDAO;
import com.tabs.dao.SubscriptionDAO;
import com.tabs.dao.UsageDAO;
import com.tabs.exceptions.InvoiceGenerationException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.*;
import com.tabs.utility.PlanConfig;
import com.tabs.utility.IdGenerator;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

public class BillingServiceImpl implements BillingService {
    private final UsageDAO usageDAO;
    private final SubscriptionDAO subscriptionDAO;
    private final CustomerDAO customerDAO;
    private final BillingDAO billingDAO;

    public BillingServiceImpl(UsageDAO usageDAO, SubscriptionDAO subscriptionDAO, CustomerDAO customerDAO, BillingDAO billingDAO) {
        this.usageDAO = usageDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.customerDAO = customerDAO;
        this.billingDAO = billingDAO;
    }

    @Override
    public Plan getSystemPlan() {
        return PlanConfig.SYSTEM_PLAN;
    }

    @Override
    public Invoice generateInvoiceForSubscription(String subscriptionId, YearMonth billingPeriod) throws InvoiceGenerationException{
        try {
            List<Invoice> existingInvoices = billingDAO.getInvoicesBySubscriptionAndPeriod(subscriptionId, billingPeriod);
            if (!existingInvoices.isEmpty()) {
                System.out.println("  INFO: Invoice already exists for subscription " + subscriptionId + " for " + billingPeriod + ". Skipping.");
                return existingInvoices.get(0);
            }

            Subscription sub = subscriptionDAO.getSubscriptionById(subscriptionId);
            if (sub == null) {
                throw new SubscriptionNotFoundException("Subscription with ID '" + subscriptionId + "' not found.");
            }

            if (sub.getSubsStartDate().toLocalDate().isAfter(billingPeriod.atEndOfMonth())) {
                System.out.println("  INFO: Subscription " + subscriptionId + " was not active during " + billingPeriod + ". Skipping.");
                return null;
            }

            Plan plan = PlanConfig.SYSTEM_PLAN;
            Customer customer = customerDAO.getCustomerById(sub.getCustId());
            List<Usage> usageRecords = usageDAO.getUsageBySubscriptionIdAndPeriod(subscriptionId, billingPeriod);

            double totalDataUsed = 0, totalVoiceUsed = 0, roamingCharges = 0;
            int totalSmsUsed = 0;

            for (Usage usage : usageRecords) {
                double dataWeight = (usage.getUsageStartTime().toLocalTime().isAfter(LocalTime.MIDNIGHT) && usage.getUsageStartTime().toLocalTime().isBefore(LocalTime.of(6, 0))) ? PlanConfig.NIGHT_TIME_DATA_DISCOUNT_WEIGHT : 1.0;
                totalDataUsed += usage.getDataUsedGB() * dataWeight;

                DayOfWeek day = usage.getUsageStartTime().getDayOfWeek();
                boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
                if (!isWeekend || plan.getWeekendFreeMinutes() <= 0) {
                    totalVoiceUsed += usage.getVoiceUsedMins();
                }

                totalSmsUsed += usage.getSmsUsed();

                if (usage.isRoaming()){
                    double dataCost = usage.getDataUsedGB() * plan.getDataOverageRatePerGB();
                    double voiceCost = usage.getVoiceUsedMins() * plan.getVoiceOverageRatePerMin();
                    roamingCharges += (dataCost + voiceCost) * PlanConfig.DOMESTIC_ROAMING_SURCHARGE_RATE;
                }
            }

            double availableDataAllowance = plan.getDataAllowanceGB() + sub.getDataRolloverBalanceGb();
            double dataOverage = Math.max(0, totalDataUsed - availableDataAllowance);
            double voiceOverage = Math.max(0, totalVoiceUsed - plan.getVoiceAllowedMins());
            int smsOverage = Math.max(0, totalSmsUsed - plan.getSmsAllowed());
            double overageFare = (dataOverage * plan.getDataOverageRatePerGB()) + (voiceOverage * plan.getVoiceOverageRatePerMin()) + (smsOverage * plan.getSmsOveragePerSMS());

            double unusedData = Math.max(0, availableDataAllowance - totalDataUsed);
            double newRolloverBalance = Math.min(unusedData, plan.getDataAllowanceGB() * PlanConfig.DATA_ROLLOVER_CAP_PERCENT);
            sub.setDataRolloverBalanceGb(newRolloverBalance);
            subscriptionDAO.updateSubscription(sub);

            double baseFare = plan.getMonthlyRental();
            double subTotal = baseFare + overageFare + roamingCharges;
            double familySurcharge = 0;

            if (plan.isFamilyShared()) {
                List<Subscription> familySubs = subscriptionDAO.findByFamilyId(sub.getFamilyId());
                double totalFamilyDataPool = familySubs.size() * plan.getDataAllowanceGB();
                if(totalDataUsed > totalFamilyDataPool * PlanConfig.FAMILY_FAIRNESS_USAGE_THRESHOLD){
                    familySurcharge = PlanConfig.FAMILY_FAIRNESS_SURCHARGE;
                    subTotal += familySurcharge;
                }
            }

            double discount = 0;
            if (customer.getReferredBy() != null) {
                discount = subTotal * PlanConfig.REFERRAL_DISCOUNT_RATE;
                subTotal -= discount;
            }

            double gst = subTotal * PlanConfig.GST_RATE;
            double grandTotal = subTotal + gst;

            Invoice invoice = new Invoice();
            invoice.setInvoiceId(IdGenerator.generateInvoiceId());
            invoice.setCustId(customer.getCustId());
            invoice.setSubscriptionId(subscriptionId);
            invoice.setPhoneNumber(sub.getPhoneNumber());
            invoice.setBillingDate(billingPeriod.atDay(1));
            invoice.setBaseFare(baseFare);
            invoice.setOverageFare(overageFare);
            invoice.setRoamingCharges(roamingCharges);
            invoice.setFamilyFairnessSurcharge(familySurcharge);
            invoice.setDiscount(discount);
            invoice.setSubTotal(subTotal);
            invoice.setGst(gst);
            invoice.setGrandTotal(grandTotal);
            invoice.setPaymentStatus(Invoice.PaymentStatus.PENDING);

            billingDAO.addInvoice(invoice);
            return invoice;

        } catch (Exception e) {
            throw new InvoiceGenerationException("Failed to generate invoice for subscription " + subscriptionId + " for " + billingPeriod, e);
        }
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(String custId) { return billingDAO.getInvoicesByCustomer(custId); }

    @Override
    public void markInvoiceAsPaid(String invoiceId) {
        Invoice invoice = billingDAO.getInvoiceById(invoiceId);
        if (invoice != null) {
            invoice.setPaymentStatus(Invoice.PaymentStatus.PAID);
            billingDAO.updateInvoice(invoice);
        }
    }
}