package com.crimsonlogic.hospitalmanagement.exceptions;

/**
 * Exception thrown when Bill
 * is not found.
 */
public class BillNotFoundException
        extends Exception {

    public BillNotFoundException(
            String message) {

        super(message);
    }
}