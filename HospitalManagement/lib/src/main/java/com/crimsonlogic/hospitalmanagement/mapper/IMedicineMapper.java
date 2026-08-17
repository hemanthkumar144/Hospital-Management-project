package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.MedicineNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Medicine;

public interface IMedicineMapper {

    void addMedicine(
            Medicine medicine)
            throws ValidationException;

    Medicine getMedicineById(
            String medicineId)
            throws ValidationException,
            MedicineNotFoundException;

    List<Medicine> getAllMedicines();

    void updateMedicine(
            Medicine medicine)
            throws ValidationException,
            MedicineNotFoundException;

    void deleteMedicine(
            String medicineId)
            throws ValidationException,
            MedicineNotFoundException;

    Integer getMaxMedicineNumber();
}