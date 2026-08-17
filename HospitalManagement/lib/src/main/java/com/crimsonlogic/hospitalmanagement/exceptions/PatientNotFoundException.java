package com.crimsonlogic.hospitalmanagement.exceptions;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }
}