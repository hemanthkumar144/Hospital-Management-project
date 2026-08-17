package com.crimsonlogic.hospitalmanagement.model;

/**
 * Represents a blood laboratory test.
 *
 * Extends LaboratoryTest and adds attributes
 * specific to blood tests.
 */
public class BloodTest extends LaboratoryTest {

    /*
     * Type of blood sample collected.
     */
    private String sampleType;

    /*
     * Indicates whether fasting is required
     * before performing the test.
     */
    private boolean fastingRequired;

    /*
     * Amount of blood required in millilitres.
     */
    private double bloodVolume;


    /**
     * Default constructor.
     */
    public BloodTest() {
        super();
    }


    /**
     * Parameterized constructor.
     *
     * @param testId test ID
     * @param testName test name
     * @param testDescription test description
     * @param testCharge test charge
     * @param active active status
     * @param sampleType blood sample type
     * @param fastingRequired fasting requirement
     * @param bloodVolume required blood volume
     */
    public BloodTest(
            String testId,
            String testName,
            String testDescription,
            double testCharge,
            boolean active,
            String sampleType,
            boolean fastingRequired,
            double bloodVolume) {

        super(
                testId,
                testName,
                testDescription,
                testCharge,
                active);

        this.testType = "BLOOD";

        this.sampleType = sampleType;
        this.fastingRequired = fastingRequired;
        this.bloodVolume = bloodVolume;
    }

    public String getSampleType() {
        return sampleType;
    }


    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }


    public boolean isFastingRequired() {
        return fastingRequired;
    }


    public void setFastingRequired(
            boolean fastingRequired) {

        this.fastingRequired = fastingRequired;
    }


    public double getBloodVolume() {
        return bloodVolume;
    }


    public void setBloodVolume(
            double bloodVolume) {

        this.bloodVolume = bloodVolume;
    }


    /**
     * Performs the blood test.
     */
    @Override
    public void performTest() {

        System.out.println(
                "Performing blood test using "
                        + sampleType
                        + " sample.");
    }


    @Override
    public String toString() {

        return "BloodTest{" +
                "testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                ", testCharge=" + testCharge +
                ", sampleType='" + sampleType + '\'' +
                ", fastingRequired=" +
                    fastingRequired +
                ", bloodVolume=" +
                    bloodVolume +
                ", active=" + active +
                '}';
    }
}