package com.tabs.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String custId;
    private String name;
    private List<String> phoneNumbers = new ArrayList<>();
    private String email;
    private String familyId;
    private String referredBy;
    private boolean isCreditBlocked;

    public Customer() {
    }

    public Customer(String custId, String name, String email) {
        this.custId = custId;
        this.name = name;
        this.email = email;
        this.isCreditBlocked = false; // Customers are not blocked by default
    }

    // --- Getters and Setters ---
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public boolean isCreditBlocked() {
        return isCreditBlocked;
    }

    public void setCreditBlocked(boolean creditBlocked) {
        isCreditBlocked = creditBlocked;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId='" + custId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", email='" + email + '\'' +
                ", familyId='" + familyId + '\'' +
                ", referredBy='" + referredBy + '\'' +
                ", isCreditBlocked=" + isCreditBlocked +
                '}';
    }
}