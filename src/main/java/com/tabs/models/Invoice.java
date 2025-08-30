package com.tabs.models;

import java.time.LocalDate;

public class Invoice {

    public enum PaymentStatus {PENDING,PAID}

    private String invoiceId;
    private String custId;
    private String subscriptionId;
    private LocalDate billingDate;
    private Double baseFare;
    private Double overageFare;
    private Double roamingCharges;
    private Double discount;
    private Double subTotal;
    private Double gst;
    private Double grandTotal;
    private PaymentStatus paymentStatus;

    public Invoice() {
    }

    public Invoice(String invoiceId, String custId, String subscriptionId, LocalDate billingDate, Double baseFare, Double overageFare, Double roamingCharges, Double discount, Double subtotal, Double gst, Double grandTotal, PaymentStatus paymentStatus) {
        this.invoiceId = invoiceId;
        this.custId = custId;
        this.subscriptionId = subscriptionId;
        this.billingDate = billingDate;
        this.baseFare = baseFare;
        this.overageFare = overageFare;
        this.roamingCharges = roamingCharges;
        this.discount = discount;
        subTotal = subtotal;
        this.gst = gst;
        this.grandTotal = grandTotal;
        this.paymentStatus = paymentStatus;
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
                ", subscriptionId='" + subscriptionId + '\'' +
                ", billingDate=" + billingDate +
                ", baseFare=" + baseFare +
                ", overageFare=" + overageFare +
                ", roamingCharges=" + roamingCharges +
                ", discount=" + discount +
                ", subTotal=" + subTotal +
                ", gst=" + gst +
                ", grandTotal=" + grandTotal +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
