package com.crimsonlogic.hospitalmanagement.model;

import java.time.LocalDateTime;

import com.crimsonlogic.hospitalmanagement.enums.TestStatus;

/**
 * Represents a laboratory test actually performed
 * for a patient.
 *
 * LaboratoryTest:
 *      Defines the laboratory test offered by the hospital.
 *
 * PatientTest:
 *      Records the test actually performed for a patient.
 */
public class PatientTest {

    /*
     * Unique ID of this patient-test record.
     */
    private String patientTestId;

    /*
     * ID of the patient who underwent the test.
     */
    private String patientId;

    /*
     * ID of the laboratory test performed.
     */
    private String testId;

    /*
     * Date and time when the test was performed.
     */
    private LocalDateTime testDate;

    /*
     * Current status of the performed test.
     */
    private TestStatus status;

    /*
     * Actual charge applied when the test was performed.
     */
    private double charge;


    /**
     * Default constructor.
     */
    public PatientTest() {
    }


    /**
     * Parameterized constructor.
     *
     * @param patientTestId unique patient-test ID
     * @param patientId patient ID
     * @param testId laboratory test ID
     * @param testDate test date and time
     * @param status test status
     * @param charge actual test charge
     */
    public PatientTest(
            String patientTestId,
            String patientId,
            String testId,
            LocalDateTime testDate,
            TestStatus status,
            double charge) {

        this.patientTestId = patientTestId;
        this.patientId = patientId;
        this.testId = testId;
        this.testDate = testDate;
        this.status = status;
        this.charge = charge;
    }


    public String getPatientTestId() {
        return patientTestId;
    }


    public void setPatientTestId(
            String patientTestId) {

        this.patientTestId = patientTestId;
    }


    public String getPatientId() {
        return patientId;
    }


    public void setPatientId(
            String patientId) {

        this.patientId = patientId;
    }


    public String getTestId() {
        return testId;
    }


    public void setTestId(String testId) {
        this.testId = testId;
    }


    public LocalDateTime getTestDate() {
        return testDate;
    }


    public void setTestDate(
            LocalDateTime testDate) {

        this.testDate = testDate;
    }


    public TestStatus getStatus() {
        return status;
    }


    public void setStatus(TestStatus status) {
        this.status = status;
    }


    public double getCharge() {
        return charge;
    }


    public void setCharge(double charge) {
        this.charge = charge;
    }


    /**
     * Returns the patient-test information.
     *
     * @return patient-test information
     */
    @Override
    public String toString() {

        return "PatientTest{" +
                "patientTestId='" +
                    patientTestId + '\'' +
                ", patientId='" +
                    patientId + '\'' +
                ", testId='" +
                    testId + '\'' +
                ", testDate=" +
                    testDate +
                ", status=" +
                    status +
                ", charge=" +
                    charge +
                '}';
    }
}