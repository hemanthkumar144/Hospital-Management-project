package com.crimsonlogic.hospitalmanagement.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private String prescriptionId;

    private Patient patient;

    private Doctor doctor;

    private LocalDate prescriptionDate;

    private String instructions;

    private boolean active;


    // Multiple medicines
    private List<PrescriptionMedicine>
            medicines = new ArrayList<>();


    // Multiple laboratory tests
    private List<PrescriptionTest>
            tests = new ArrayList<>();


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Prescription() {
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(
            String prescriptionId) {

        this.prescriptionId =
                prescriptionId;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(
            Patient patient) {

        this.patient = patient;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(
            Doctor doctor) {

        this.doctor = doctor;
    }


    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(
            LocalDate prescriptionDate) {

        this.prescriptionDate =
                prescriptionDate;
    }


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(
            String instructions) {

        this.instructions =
                instructions;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(
            boolean active) {

        this.active = active;
    }


    // =========================================================
    // MEDICINES
    // =========================================================

    public List<PrescriptionMedicine>
            getMedicines() {

        return medicines;
    }

    public void setMedicines(
            List<PrescriptionMedicine> medicines) {

        this.medicines = medicines;
    }


    // =========================================================
    // TESTS
    // =========================================================

    public List<PrescriptionTest>
            getTests() {

        return tests;
    }

    public void setTests(
            List<PrescriptionTest> tests) {

        this.tests = tests;
    }


    // =========================================================
    // ADD MEDICINE
    // =========================================================

    public void addMedicine(
            PrescriptionMedicine medicine) {

        this.medicines.add(medicine);
    }


    // =========================================================
    // ADD TEST
    // =========================================================

    public void addTest(
            PrescriptionTest test) {

        this.tests.add(test);
    }


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {

        return "Prescription ["
                + "prescriptionId="
                + prescriptionId
                + ", patient="
                + patient
                + ", doctor="
                + doctor
                + ", prescriptionDate="
                + prescriptionDate
                + ", instructions="
                + instructions
                + ", active="
                + active
                + ", medicines="
                + medicines
                + ", tests="
                + tests
                + "]";
    }
}