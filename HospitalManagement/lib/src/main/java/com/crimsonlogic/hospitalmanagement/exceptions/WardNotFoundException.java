package com.crimsonlogic.hospitalmanagement.exceptions;

/**
 * Thrown when a requested ward does not exist
 * or is not active.
 */
public class WardNotFoundException extends Exception {

   

    public WardNotFoundException(String message) {
        super(message);
    }
}