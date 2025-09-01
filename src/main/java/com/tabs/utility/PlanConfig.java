package com.tabs.utility;

import com.tabs.models.Plan;

public class PlanConfig {
    // single plan for all users, no complication
    public static final Plan SYSTEM_PLAN;
    public static final Plan SYSTEM_PLAN_LITE;

    static {
        SYSTEM_PLAN = new Plan();  // Non-parameterized constructor
        SYSTEM_PLAN.setPlanId("UNIV-01");
        SYSTEM_PLAN.setPlanName("Universal Plan 799");
        SYSTEM_PLAN.setMonthlyRental(799.0);
        SYSTEM_PLAN.setVoiceAllowedMins(100.0);
        SYSTEM_PLAN.setDataAllowanceGB(5000.0);
        SYSTEM_PLAN.setSmsAllowed(100);
        SYSTEM_PLAN.setVoiceOverageRatePerMin(9.0);
        SYSTEM_PLAN.setDataOverageRatePerGB(0.9);
        SYSTEM_PLAN.setSmsOveragePerSMS(0.5);
        SYSTEM_PLAN.setWeekendFreeMinutes(500.0);
        SYSTEM_PLAN.setFamilyShared(true);
    }

    static {
        SYSTEM_PLAN_LITE = new Plan();  // Non-parameterized constructor
        SYSTEM_PLAN_LITE.setPlanId("LITE-01");
        SYSTEM_PLAN_LITE.setPlanName("Lite Plan 599");
        SYSTEM_PLAN_LITE.setMonthlyRental(599.0);
        SYSTEM_PLAN_LITE.setVoiceAllowedMins(70.0);
        SYSTEM_PLAN_LITE.setDataAllowanceGB(3000.0);
        SYSTEM_PLAN_LITE.setSmsAllowed(50);
        SYSTEM_PLAN_LITE.setVoiceOverageRatePerMin(9.0);
        SYSTEM_PLAN_LITE.setDataOverageRatePerGB(0.9);
        SYSTEM_PLAN_LITE.setSmsOveragePerSMS(0.5);
        SYSTEM_PLAN_LITE.setWeekendFreeMinutes(500.0);
        SYSTEM_PLAN_LITE.setFamilyShared(true);
    }


    // Constants for invoicing and rates
    public static final double GST_RATE = 0.18;
    public static final double REFERRAL_DISCOUNT_RATE = 0.05;
    public static final double NIGHT_TIME_DATA_DISCOUNT_WEIGHT = 0.5;
    public static final double DOMESTIC_ROAMING_SURCHARGE_RATE = 0.15;
    public static final double FAMILY_FAIRNESS_USAGE_THRESHOLD = 0.60;
    public static final double FAMILY_FAIRNESS_SURCHARGE = 250.0;
    public static final double DATA_ROLLOVER_CAP_PERCENT = 0.50;
    public static final int CREDIT_CONTROL_DAYS_THRESHOLD = 60;
}