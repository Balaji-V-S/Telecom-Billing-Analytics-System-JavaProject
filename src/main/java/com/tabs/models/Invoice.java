package com.tabs.models;

import java.time.LocalDate;

public class Invoice {

    public enum PaymentStatus {PENDING, PAID}

    private String invoiceId;
    private String custId;
    private String subscriptionId;
    private String phoneNumber;
    private LocalDate billingDate;
    private Double baseFare;
    private Double overageFare;
    private Double roamingCharges;
    private Double familyFairnessSurcharge;
    private Double discount;
    private Double subTotal;
    private Double gst;
    private Double grandTotal;
    private PaymentStatus paymentStatus;

    public Invoice() {
    }

    public Invoice(String invoiceId, String custId, String subscriptionId, String phoneNumber, LocalDate billingDate, Double baseFare, Double overageFare, Double roamingCharges, Double familyFairnessSurcharge, Double discount, Double subTotal, Double gst, Double grandTotal, PaymentStatus paymentStatus) {
        this.invoiceId = invoiceId;
        this.custId = custId;
        this.subscriptionId = subscriptionId;
        this.phoneNumber = phoneNumber;
        this.billingDate = billingDate;
        this.baseFare = 0.0;
        this.overageFare = 0.0;
        this.roamingCharges = 0.0;
        this.familyFairnessSurcharge = 0.0;
        this.discount = 0.0;
        this.subTotal = 0.0;
        this.gst = 0.0;
        this.grandTotal = 0.0;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }

    public Double getOverageFare() {
        return overageFare;
    }

    public void setOverageFare(Double overageFare) {
        this.overageFare = overageFare;
    }

    public Double getRoamingCharges() {
        return roamingCharges;
    }

    public void setRoamingCharges(Double roamingCharges) {
        this.roamingCharges = roamingCharges;
    }

    public Double getFamilyFairnessSurcharge() {
        return familyFairnessSurcharge;
    }

    public void setFamilyFairnessSurcharge(Double familyFairnessSurcharge) {
        this.familyFairnessSurcharge = familyFairnessSurcharge;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", custId='" + custId + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", billingDate=" + billingDate +
                ", baseFare=" + baseFare +
                ", overageFare=" + overageFare +
                ", roamingCharges=" + roamingCharges +
                ", familyFairnessSurcharge=" + familyFairnessSurcharge +
                ", discount=" + discount +
                ", subTotal=" + subTotal +
                ", gst=" + gst +
                ", grandTotal=" + grandTotal +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}