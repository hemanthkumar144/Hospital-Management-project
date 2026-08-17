package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Doctor;

public interface IDoctorMapper {

    void addDoctor(Doctor doctor) throws ValidationException;

    Doctor getDoctorById(String staffId) throws ValidationException;

    void deleteDoctor(String staffId) throws ValidationException;

    List<Doctor> getAllDoctors();

    void updateDoctor(Doctor doctor) throws ValidationException;

    Doctor getDoctorByUserId(String userId);
}