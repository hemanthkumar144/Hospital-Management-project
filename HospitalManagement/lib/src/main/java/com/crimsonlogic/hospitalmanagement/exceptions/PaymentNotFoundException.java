package com.crimsonlogic.hospitalmanagement.exceptions;

/**
 * Exception thrown when Payment
 * is not found.
 */
public class PaymentNotFoundException
        extends Exception {

    public PaymentNotFoundException(
            String message) {

        super(message);
    }
}