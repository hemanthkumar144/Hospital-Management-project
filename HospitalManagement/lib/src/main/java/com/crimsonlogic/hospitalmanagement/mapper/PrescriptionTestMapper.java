package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.model.PrescriptionTest;

public interface PrescriptionTestMapper {

    // Add test to a prescription
    void addPrescriptionTest(
            PrescriptionTest prescriptionTest);

    // Get all tests belonging to a prescription
    List<PrescriptionTest>
    getTestsByPrescriptionId(
            String prescriptionId);

    // Delete all tests of a prescription
    void deleteByPrescriptionId(
            String prescriptionId);
}