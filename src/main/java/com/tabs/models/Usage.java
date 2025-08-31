package com.tabs.models;

import java.time.LocalDateTime;

public class Usage {
    private String subscriptionId;
    private LocalDateTime usageStartTime;
    private Double dataUsedGB;
    private Double voiceUsedMins;
    private Integer smsUsed;
    private boolean isRoaming;
    private boolean isInternational;

    public Usage(String subscriptionId, LocalDateTime usageStartTime, Double dataUsedGB, Double voiceUsedMins, Integer smsUsed, boolean isRoaming, boolean isInternational) {
        this.subscriptionId = subscriptionId;
        this.usageStartTime = usageStartTime;
        this.dataUsedGB = dataUsedGB;
        this.voiceUsedMins = voiceUsedMins;
        this.smsUsed = smsUsed;
        this.isRoaming = isRoaming;
        this.isInternational = isInternational;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDateTime getUsageStartTime() {
        return usageStartTime;
    }

    public void setUsageStartTime(LocalDateTime usageStartTime) {
        this.usageStartTime = usageStartTime;
    }

    public Double getDataUsedGB() {
        return dataUsedGB;
    }

    public void setDataUsedGB(Double dataUsedGB) {
        this.dataUsedGB = dataUsedGB;
    }

    public Double getVoiceUsedMins() {
        return voiceUsedMins;
    }

    public void setVoiceUsedMins(Double voiceUsedMins) {
        this.voiceUsedMins = voiceUsedMins;
    }

    public Integer getSmsUsed() {
        return smsUsed;
    }

    public void setSmsUsed(Integer smsUsed) {
        this.smsUsed = smsUsed;
    }

    public boolean isRoaming() {
        return isRoaming;
    }

    public void setRoaming(boolean roaming) {
        isRoaming = roaming;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }

    @Override
    public String toString() {
        return "Usage{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", usageStartTime=" + usageStartTime +
                ", dataUsedGB=" + dataUsedGB +
                ", voiceUsedMins=" + voiceUsedMins +
                ", smsUsed=" + smsUsed +
                ", isRoaming=" + isRoaming +
                ", isInternational=" + isInternational +
                '}';
    }
}