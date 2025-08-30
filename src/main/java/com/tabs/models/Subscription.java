package com.tabs.models;

import java.time.LocalDateTime;

public class Subscription {
    private String custID;
    private String subscriptionID;
    private String planId;
    private boolean mnpStatus;
    private String familyId;
    private LocalDateTime subsStartDate;
    private LocalDateTime subsEndDate;

    public Subscription() {
    }

    public Subscription(String custID, String subscriptionID, String planId, boolean mnpStatus, String familyId, LocalDateTime subsStartDate, LocalDateTime subsEndDate) {
        this.custID = custID;
        this.subscriptionID = subscriptionID;
        this.planId = planId;
        this.mnpStatus = mnpStatus;
        this.familyId = familyId;
        this.subsStartDate = subsStartDate;
        this.subsEndDate = subsEndDate;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
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
                "custID='" + custID + '\'' +
                ", subscriptionID='" + subscriptionID + '\'' +
                ", planId='" + planId + '\'' +
                ", mnpStatus=" + mnpStatus +
                ", familyId='" + familyId + '\'' +
                ", subsStartDate=" + subsStartDate +
                ", subsEndDate=" + subsEndDate +
                '}';
    }
}
