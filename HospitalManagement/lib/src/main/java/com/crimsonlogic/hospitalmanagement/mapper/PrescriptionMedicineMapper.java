package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crimsonlogic.hospitalmanagement.model.PrescriptionMedicine;

public interface PrescriptionMedicineMapper {

    // Add medicine to a prescription
    void addPrescriptionMedicine(
            PrescriptionMedicine prescriptionMedicine);

    // Get all medicines belonging to a prescription
    List<PrescriptionMedicine>
    getMedicinesByPrescriptionId(
            String prescriptionId);

    // Delete all medicines of a prescription
    void deleteByPrescriptionId(
            String prescriptionId);
}