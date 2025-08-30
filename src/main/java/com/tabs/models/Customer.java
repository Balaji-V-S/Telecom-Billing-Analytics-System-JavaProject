package com.tabs.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private String custId;
    private String name;
    private List<String> phoneNumbers = new ArrayList<>();
    private String email;
    private String familyId;        // for family plan/grouping
    private String referredBy;      // custId of the person who referred
    private boolean isCreditBlocked;  // true if services are blocked due to non-payment
    private Map<String, List<Usage>> usageByNumber = new HashMap<>();  ///session level usage records per phone number
    private Map<String, List<Invoice>> invoicesByNumber = new HashMap<>();  // invoices per phone number

    public Customer() {
    }

    public Customer(String custId, String name, List<String> phoneNumbers, String email, String familyId, String referredBy, boolean creditBlocked, Map<String, List<Usage>> usageByNumber, Map<String, List<Invoice>> invoicesByNumber) {
        this.custId = custId;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.familyId = familyId;
        this.referredBy = referredBy;
        this.isCreditBlocked = creditBlocked;
        this.usageByNumber = usageByNumber;
        this.invoicesByNumber = invoicesByNumber;
    }

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
        this.isCreditBlocked = creditBlocked;
    }

    public Map<String, List<Usage>> getUsageByNumber() {
        return usageByNumber;
    }

    public void setUsageByNumber(Map<String, List<Usage>> usageByNumber) {
        this.usageByNumber = usageByNumber;
    }

    public Map<String, List<Invoice>> getInvoicesByNumber() {
        return invoicesByNumber;
    }

    public void setInvoicesByNumber(Map<String, List<Invoice>> invoicesByNumber) {
        this.invoicesByNumber = invoicesByNumber;
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
                ", usageByNumber=" + usageByNumber +
                ", invoicesByNumber=" + invoicesByNumber +
                '}';
    }
}
