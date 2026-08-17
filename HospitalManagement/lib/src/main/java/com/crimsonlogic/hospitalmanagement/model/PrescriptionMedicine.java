package com.crimsonlogic.hospitalmanagement.model;

public class PrescriptionMedicine {

    private int prescriptionMedicineId;
    private String prescriptionId;
    private String medicineId;
    private String dosage;
    private int quantity;

    public PrescriptionMedicine() {
    }

    public PrescriptionMedicine(
            int prescriptionMedicineId,
            String prescriptionId,
            String medicineId,
            String dosage,
            int quantity) {

        this.prescriptionMedicineId = prescriptionMedicineId;
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.dosage = dosage;
        this.quantity = quantity;
    }

    public int getPrescriptionMedicineId() {
        return prescriptionMedicineId;
    }

    public void setPrescriptionMedicineId(
            int prescriptionMedicineId) {
        this.prescriptionMedicineId =
                prescriptionMedicineId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(
            String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(
            String medicineId) {
        this.medicineId = medicineId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(
            String dosage) {
        this.dosage = dosage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(
            int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {

        return "PrescriptionMedicine ["
                + "prescriptionMedicineId="
                + prescriptionMedicineId
                + ", prescriptionId="
                + prescriptionId
                + ", medicineId="
                + medicineId
                + ", dosage="
                + dosage
                + ", quantity="
                + quantity
                + "]";
    }
}