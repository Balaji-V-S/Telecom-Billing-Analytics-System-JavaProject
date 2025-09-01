package com.tabs.models;

public class Plan {
    private String planId;
    private String planName;
    private Double monthlyRental;
    private Double dataAllowanceGB;
    private Double voiceAllowedMins;
    private Integer smsAllowed;
    private Double dataOverageRatePerGB;
    private Double voiceOverageRatePerMin;
    private Double smsOveragePerSMS;
    private Double weekendFreeMinutes;
    private boolean isFamilyShared;

    public Plan() {
        this.isFamilyShared=false;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getMonthlyRental() {
        return monthlyRental;
    }

    public void setMonthlyRental(Double monthlyRental) {
        this.monthlyRental = monthlyRental;
    }

    public Double getDataAllowanceGB() {
        return dataAllowanceGB;
    }

    public void setDataAllowanceGB(Double dataAllowanceGB) {
        this.dataAllowanceGB = dataAllowanceGB;
    }

    public Double getVoiceAllowedMins() {
        return voiceAllowedMins;
    }

    public void setVoiceAllowedMins(Double voiceAllowedMins) {
        this.voiceAllowedMins = voiceAllowedMins;
    }

    public Integer getSmsAllowed() {
        return smsAllowed;
    }

    public void setSmsAllowed(Integer smsAllowed) {
        this.smsAllowed = smsAllowed;
    }

    public Double getDataOverageRatePerGB() {
        return dataOverageRatePerGB;
    }

    public void setDataOverageRatePerGB(Double dataOverageRatePerGB) {
        this.dataOverageRatePerGB = dataOverageRatePerGB;
    }

    public Double getVoiceOverageRatePerMin() {
        return voiceOverageRatePerMin;
    }

    public void setVoiceOverageRatePerMin(Double voiceOverageRatePerMin) {
        this.voiceOverageRatePerMin = voiceOverageRatePerMin;
    }

    public Double getSmsOveragePerSMS() {
        return smsOveragePerSMS;
    }

    public void setSmsOveragePerSMS(Double smsOveragePerSMS) {
        this.smsOveragePerSMS = smsOveragePerSMS;
    }

    public Double getWeekendFreeMinutes() {
        return weekendFreeMinutes;
    }

    public void setWeekendFreeMinutes(Double weekendFreeMinutes) {
        this.weekendFreeMinutes = weekendFreeMinutes;
    }

    public boolean isFamilyShared() {
        return isFamilyShared;
    }

    public void setFamilyShared(boolean familyShared) {
        isFamilyShared = familyShared;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planId='" + planId + '\'' +
                ", planName='" + planName + '\'' +
                ", monthlyRental=" + monthlyRental +
                ", dataAllowanceGB=" + dataAllowanceGB +
                ", voiceAllowedMins=" + voiceAllowedMins +
                ", smsAllowed=" + smsAllowed +
                ", dataOverageRatePerGB=" + dataOverageRatePerGB +
                ", voiceOverageRatePerMin=" + voiceOverageRatePerMin +
                ", smsOveragePerSMS=" + smsOveragePerSMS +
                ", weekendFreeMinutes=" + weekendFreeMinutes +
                ", isFamilyShared=" + isFamilyShared +
                '}';
    }
}