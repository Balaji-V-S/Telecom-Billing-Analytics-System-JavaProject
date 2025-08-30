package com.tabs.models;

import java.time.LocalDateTime;

public class Subscription {
    private String custId;
    private String subscriptionId;
    private String planId;
    private String phoneNumber;
    private boolean mnpStatus;
    private String familyId;
    private LocalDateTime subsStartDate;
    private LocalDateTime subsEndDate;

    public Subscription() {
    }

    public Subscription(String custId, String subscriptionId, String planId, String phoneNumber, boolean mnpStatus, String familyId, LocalDateTime subsStartDate, LocalDateTime subsEndDate) {
        this.custId = custId;
        this.subscriptionId = subscriptionId;
        this.planId = planId;
        this.phoneNumber = phoneNumber;
        this.mnpStatus = mnpStatus;
        this.familyId = familyId;
        this.subsStartDate = subsStartDate;
        this.subsEndDate = subsEndDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public void setSubscriptionId(String subscriptionID) {
        this.subscriptionId = subscriptionID;
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "custId='" + custId + '\'' +
                ", subscriptionID='" + subscriptionId + '\'' +
                ", planId='" + planId + '\'' +
                ", mnpStatus=" + mnpStatus +
                ", familyId='" + familyId + '\'' +
                ", subsStartDate=" + subsStartDate +
                ", subsEndDate=" + subsEndDate +
                '}';
    }
}
