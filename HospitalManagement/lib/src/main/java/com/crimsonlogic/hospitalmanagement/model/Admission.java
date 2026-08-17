package com.crimsonlogic.hospitalmanagement.model;

import java.time.LocalDateTime;

import com.crimsonlogic.hospitalmanagement.enums.AdmissionStatus;

/**
 * Represents a patient's hospital admission.
 *
 * An admission connects a Patient with a Bed.
 */
public class Admission {

    private String admissionId;

    private Patient patient;

    private Bed bed;

    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    private AdmissionStatus status;

    private boolean active;


    /**
     * Default constructor.
     */
    public Admission() {
    }


    /**
     * Parameterized constructor.
     */
    public Admission(
            String admissionId,
            Patient patient,
            Bed bed,
            LocalDateTime admissionDate,
            LocalDateTime dischargeDate,
            AdmissionStatus status,
            boolean active) {

        this.admissionId = admissionId;
        this.patient = patient;
        this.bed = bed;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.status = status;
        this.active = active;
    }


    public String getAdmissionId() {
        return admissionId;
    }


    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }


    public Patient getPatient() {
        return patient;
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public Bed getBed() {
        return bed;
    }


    public void setBed(Bed bed) {
        this.bed = bed;
    }


    public LocalDateTime getAdmissionDate() {
        return admissionDate;
    }


    public void setAdmissionDate(
            LocalDateTime admissionDate) {

        this.admissionDate = admissionDate;
    }


    public LocalDateTime getDischargeDate() {
        return dischargeDate;
    }


    public void setDischargeDate(
            LocalDateTime dischargeDate) {

        this.dischargeDate = dischargeDate;
    }


    public AdmissionStatus getStatus() {
        return status;
    }


    public void setStatus(
            AdmissionStatus status) {

        this.status = status;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {

        return "Admission [admissionId="
                + admissionId
                + ", patient="
                + patient
                + ", bed="
                + bed
                + ", admissionDate="
                + admissionDate
                + ", dischargeDate="
                + dischargeDate
                + ", status="
                + status
                + ", active="
                + active
                + "]";
    }
}