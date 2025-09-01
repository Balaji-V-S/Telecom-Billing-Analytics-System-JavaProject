package com.tabs.utility;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdGenerator {
    // This map safely stores the last used ID number for each month.
    private static final Map<YearMonth, Integer> invoiceCounters = new ConcurrentHashMap<>();
    private static final DateTimeFormatter YM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    private IdGenerator(){}
    public static String generateInvoiceId() {
        YearMonth currentMonth = YearMonth.now();
        // Atomically increment the counter for the current month
        int sequence = invoiceCounters.compute(currentMonth, (k, v) -> (v == null) ? 1 : v + 1);

        // Format: INV-YYYYMM-0001
        return String.format("INV-%s-%04d", currentMonth.format(YM_FORMATTER), sequence);
    }
}