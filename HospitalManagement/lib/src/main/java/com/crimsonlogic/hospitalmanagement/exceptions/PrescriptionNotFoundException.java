package com.crimsonlogic.hospitalmanagement.exceptions;

/**
 * Exception thrown when Prescription
 * is not found.
 */
public class PrescriptionNotFoundException
        extends Exception {

    public PrescriptionNotFoundException(
            String message) {

        super(message);
    }
}