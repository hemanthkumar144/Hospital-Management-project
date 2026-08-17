package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.PrescriptionNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Prescription;

public interface IPrescriptionMapper {

    void addPrescription(
            Prescription prescription) throws ValidationException;

    Prescription getPrescriptionById(
            String prescriptionId) throws PrescriptionNotFoundException, ValidationException;

    List<Prescription> getAllPrescriptions();

    void updatePrescription(
            Prescription prescription) throws ValidationException, PrescriptionNotFoundException;

    void deletePrescription(
            String prescriptionId) throws PrescriptionNotFoundException, ValidationException;
}