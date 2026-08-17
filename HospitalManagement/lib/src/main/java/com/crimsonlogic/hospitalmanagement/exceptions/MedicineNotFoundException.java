package com.crimsonlogic.hospitalmanagement.exceptions;


public class MedicineNotFoundException extends RuntimeException {

    public MedicineNotFoundException(String message) {
        super(message);
    }
}