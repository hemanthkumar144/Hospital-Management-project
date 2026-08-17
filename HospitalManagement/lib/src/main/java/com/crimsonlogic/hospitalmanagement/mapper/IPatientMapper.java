package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.PatientNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Address;
import com.crimsonlogic.hospitalmanagement.model.Patient;

public interface IPatientMapper {

    void addPatient(Patient patient)
            throws ValidationException;

    Patient getPatientById(String patientId)
            throws ValidationException,
            PatientNotFoundException;

    Patient getPatientByUserId(String userId);

    List<Patient> getAllPatients();

    void addAddress(Address address);

    void updatePatient(Patient patient)
            throws ValidationException,
            PatientNotFoundException;

    void deletePatient(String patientId)
            throws PatientNotFoundException, ValidationException;

    Integer getMaxPatientNumber();
}