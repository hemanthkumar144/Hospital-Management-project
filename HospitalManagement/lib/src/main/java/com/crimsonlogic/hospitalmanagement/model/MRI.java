package com.crimsonlogic.hospitalmanagement.model;

/**
 * Represents an MRI laboratory test.
 *
 * Extends LaboratoryTest and adds attributes
 * specific to MRI examinations.
 */
public class MRI extends LaboratoryTest {

    /*
     * Body part being scanned.
     */
    private String bodyPart;

    /*
     * Indicates whether contrast material
     * is used during the scan.
     */
    private boolean contrastUsed;

    /*
     * Duration of the MRI scan in minutes.
     */
    private int scanDuration;


    /**
     * Default constructor.
     */
    public MRI() {
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
     * @param bodyPart body part scanned
     * @param contrastUsed contrast usage
     * @param scanDuration scan duration
     */
    public MRI(
            String testId,
            String testName,
            String testDescription,
            double testCharge,
            boolean active,
            String bodyPart,
            boolean contrastUsed,
            int scanDuration) {

        super(
                testId,
                testName,
                testDescription,
                testCharge,
                active);

        this.testType = "MRI";

        this.bodyPart = bodyPart;
        this.contrastUsed = contrastUsed;
        this.scanDuration = scanDuration;
    }


    public String getBodyPart() {
        return bodyPart;
    }


    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }


    public boolean isContrastUsed() {
        return contrastUsed;
    }


    public void setContrastUsed(
            boolean contrastUsed) {

        this.contrastUsed = contrastUsed;
    }


    public int getScanDuration() {
        return scanDuration;
    }


    public void setScanDuration(
            int scanDuration) {

        this.scanDuration = scanDuration;
    }


    /**
     * Performs the MRI examination.
     */
    @Override
    public void performTest() {

        System.out.println(
                "Performing MRI scan of "
                        + bodyPart
                        + ".");
    }


    @Override
    public String toString() {

        return "MRI{" +
                "testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                ", testCharge=" + testCharge +
                ", bodyPart='" + bodyPart + '\'' +
                ", contrastUsed=" +
                    contrastUsed +
                ", scanDuration=" +
                    scanDuration +
                ", active=" + active +
                '}';
    }
}