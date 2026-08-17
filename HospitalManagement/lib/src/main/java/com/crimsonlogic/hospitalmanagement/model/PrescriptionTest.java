package com.crimsonlogic.hospitalmanagement.model;

public class PrescriptionTest {

    private int prescriptionTestId;
    private String prescriptionId;
    private String testId;

    public PrescriptionTest() {
    }

    public PrescriptionTest(
            int prescriptionTestId,
            String prescriptionId,
            String testId) {

        this.prescriptionTestId =
                prescriptionTestId;

        this.prescriptionId =
                prescriptionId;

        this.testId =
                testId;
    }

    public int getPrescriptionTestId() {
        return prescriptionTestId;
    }

    public void setPrescriptionTestId(
            int prescriptionTestId) {

        this.prescriptionTestId =
                prescriptionTestId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(
            String prescriptionId) {

        this.prescriptionId =
                prescriptionId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(
            String testId) {

        this.testId = testId;
    }

    @Override
    public String toString() {

        return "PrescriptionTest ["
                + "prescriptionTestId="
                + prescriptionTestId
                + ", prescriptionId="
                + prescriptionId
                + ", testId="
                + testId
                + "]";
    }
}