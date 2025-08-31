package com.tabs.models;

import java.time.LocalDateTime;

public class Subscription {
    private String custId;
    private String subscriptionId;
    private String phoneNumber;
    private boolean mnpStatus;
    private String familyId;
    private LocalDateTime subsStartDate;
    private LocalDateTime subsEndDate;
    private double dataRolloverBalanceGb = 0.0;

    // --- Getters and Setters ---
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

    public boolean isMnpStatus() {
        return mnpStatus;
    }

    public void setMnpStatus(boolean mnpStatus) {
        this.mnpStatus = mnpStatus;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public LocalDateTime getSubsStartDate() {
        return subsStartDate;
    }

    public void setSubsStartDate(LocalDateTime subsStartDate) {
        this.subsStartDate = subsStartDate;
    }

    public LocalDateTime getSubsEndDate() {
        return subsEndDate;
    }

    public void setSubsEndDate(LocalDateTime subsEndDate) {
        this.subsEndDate = subsEndDate;
    }

    public double getDataRolloverBalanceGb() {
        return dataRolloverBalanceGb;
    }

    public void setDataRolloverBalanceGb(double dataRolloverBalanceGb) {
        this.dataRolloverBalanceGb = dataRolloverBalanceGb;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "custId='" + custId + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mnpStatus=" + mnpStatus +
                ", familyId='" + familyId + '\'' +
                ", subsStartDate=" + subsStartDate +
                ", subsEndDate=" + subsEndDate +
                ", dataRolloverBalanceGb=" + dataRolloverBalanceGb +
                '}';
    }
}