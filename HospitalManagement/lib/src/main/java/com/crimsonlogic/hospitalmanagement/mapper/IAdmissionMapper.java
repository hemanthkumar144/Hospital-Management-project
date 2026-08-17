package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Bed;

/**
 * MyBatis mapper for Admission database operations.
 */
public interface IAdmissionMapper {

    /**
     * Inserts a new admission.
     *
     * @param admission admission to insert
     */
    void addAdmission(Admission admission);


    Admission admitPatient(
            String patientId,
            WardType wardType,
            String selectedBedId)
            throws ValidationException;

    /**
     * Retrieves an admission by ID.
     *
     * @param admissionId admission ID
     * @return admission
     */
    Admission getAdmissionById(String admissionId) throws ValidationException;


    /**
     * Retrieves the active admission of a patient.
     *
     * @param patientId patient ID
     * @return active admission, or null
     */
    Admission getActiveAdmissionByPatient(
            String patientId) throws ValidationException;


    /**
     * Retrieves the active admission associated
     * with a bed.
     *
     * @param bedId bed ID
     * @return active admission, or null
     */
    Admission getActiveAdmissionByBed(
            String bedId) throws ValidationException;


    /**
     * Retrieves all active admissions.
     *
     * @return active admissions
     */
    List<Admission> getAllAdmissions();


    /**
     * Updates an admission.
     *
     * @param admission admission to update
     */
    void updateAdmission(
            Admission admission);


    /**
     * Discharges an admission.
     *
     * @param admission admission to discharge
     */
    void dischargeAdmission(
            Admission admission);


    /**
     * Finds the first available active bed
     * belonging to an active ward of the
     * requested ward type.
     *
     * @param wardType requested ward type
     * @return available bed, or null
     */
    Bed findAvailableBedByWardType(
            WardType wardType);

    void dischargeAdmission(
            String admissionId)
            throws ValidationException;
    
    List<Admission> getAdmissionsByPatientId(
            String patientId) throws ValidationException;
}