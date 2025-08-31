package com.tabs.services;

import com.tabs.exceptions.CustomerNotFoundException;
import com.tabs.exceptions.SubscriptionNotFoundException;
import com.tabs.models.Usage;
import com.tabs.exceptions.CreditBlockedException;

public interface UsageService {
    void addUsage(Usage usage) throws CreditBlockedException, SubscriptionNotFoundException, CustomerNotFoundException;
}