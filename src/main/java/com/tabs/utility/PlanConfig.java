package com.tabs.utility;

import com.tabs.models.Plan;

public class PlanConfig {
    // single plan for all users, no complication
    public static final Plan SYSTEM_PLAN = new Plan(
            "UNIV-01", "Universal Plan 799", 799.0, 100.0, 5000.0,
            100, 9.0, 0.9, 0.5, 500.0, true
    );
    public static final Plan SYSTEM_PLAN_LITE = new Plan(
            "UNIV-02", "LITE Plan 599", 599.0, 50.0, 3000.0,
            100, 9.0, 0.9, 0.5, 500.0, true
    );


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