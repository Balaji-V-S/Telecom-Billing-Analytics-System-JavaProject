package com.tabs.exceptions;

public class InvoiceGenerationException extends RuntimeException {
    public InvoiceGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}