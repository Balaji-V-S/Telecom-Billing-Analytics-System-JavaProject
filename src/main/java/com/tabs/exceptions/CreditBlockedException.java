package com.tabs.exceptions;

public class CreditBlockedException extends RuntimeException{
    public CreditBlockedException(String message) {
        super(message);
    }
}
