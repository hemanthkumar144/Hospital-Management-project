package com.crimsonlogic.hospitalmanagement.exceptions;
public class DepartmentNotFoundException extends RuntimeException 
{

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}