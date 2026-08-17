package com.crimsonlogic.hospitalmanagement.model;

/**
 * Represents an X-Ray laboratory test.
 *
 * Extends LaboratoryTest and adds attributes
 * specific to X-Ray examinations.
 */
public class XRay extends LaboratoryTest {

    /*
     * Body part being examined.
     */
    private String bodyPart;

    /*
     * Indicates whether contrast material
     * is used during the examination.
     */
    private boolean contrastUsed;

    /*
     * Radiation dose associated with the X-Ray.
     */
    private double radiationDose;


    /**
     * Default constructor.
     */
    public XRay() {
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
     * @param bodyPart body part examined
     * @param contrastUsed contrast usage
     * @param radiationDose radiation dose
     */
    public XRay(
            String testId,
            String testName,
            String testDescription,
            double testCharge,
            boolean active,
            String bodyPart,
            boolean contrastUsed,
            double radiationDose) {

        super(
                testId,
                testName,
                testDescription,
                testCharge,
                active);

        this.testType = "XRAY";

        this.bodyPart = bodyPart;
        this.contrastUsed = contrastUsed;
        this.radiationDose = radiationDose;
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


    public double getRadiationDose() {
        return radiationDose;
    }


    public void setRadiationDose(
            double radiationDose) {

        this.radiationDose = radiationDose;
    }


    /**
     * Performs the X-Ray examination.
     */
    @Override
    public void performTest() {

        System.out.println(
                "Performing X-Ray of "
                        + bodyPart
                        + ".");
    }


    @Override
    public String toString() {

        return "XRay{" +
                "testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                ", testCharge=" + testCharge +
                ", bodyPart='" + bodyPart + '\'' +
                ", contrastUsed=" +
                    contrastUsed +
                ", radiationDose=" +
                    radiationDose +
                ", active=" + active +
                '}';
    }
}