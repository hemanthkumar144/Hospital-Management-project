package com.crimsonlogic.hospitalmanagement.model;

/**
 * Abstract base class representing a laboratory test.
 *
 * Contains the attributes and behavior common to
 * all types of laboratory tests.
 *
 * Subclasses such as BloodTest, XRay and MRI
 * can provide their own implementation of
 * performTest().
 */
public  class LaboratoryTest {

    /*
     * Unique identifier of the laboratory test.
     */
    protected String testId;

    /*
     * Name of the laboratory test.
     */
    protected String testName;

    /*
     * Description of the laboratory test.
     */
    protected String testDescription;

    /*
     * Charge for performing the test.
     */
    protected double testCharge;

    /*
     * Indicates whether the test is currently active.
     */
    protected boolean active;
    
    protected String testType;


    /**
     * Default constructor.
     */
    public LaboratoryTest() {
    }


    /**
     * Parameterized constructor.
     *
     * @param testId unique test ID
     * @param testName test name
     * @param testDescription test description
     * @param testCharge test charge
     * @param active test active status
     */
    public LaboratoryTest(
            String testId,
            String testName,
            String testDescription,
            double testCharge,
            boolean active) {

        this.testId = testId;
        this.testName = testName;
        this.testDescription = testDescription;
        this.testCharge = testCharge;
        this.active = active;
    }


    /**
     * Returns the test ID.
     *
     * @return test ID
     */
    public String getTestId() {
        return testId;
    }


    /**
     * Sets the test ID.
     *
     * @param testId test ID
     */
    public void setTestId(String testId) {
        this.testId = testId;
    }


    /**
     * Returns the test name.
     *
     * @return test name
     */
    public String getTestName() {
        return testName;
    }


    /**
     * Sets the test name.
     *
     * @param testName test name
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }


    /**
     * Returns the test description.
     *
     * @return test description
     */
    public String getTestDescription() {
        return testDescription;
    }


    /**
     * Sets the test description.
     *
     * @param testDescription test description
     */
    public void setTestDescription(
            String testDescription) {

        this.testDescription = testDescription;
    }


    /**
     * Returns the test charge.
     *
     * @return test charge
     */
    public double getTestCharge() {
        return testCharge;
    }


    /**
     * Sets the test charge.
     *
     * @param testCharge test charge
     */
    public void setTestCharge(
            double testCharge) {

        this.testCharge = testCharge;
    }


    /**
     * Returns whether the test is active.
     *
     * @return true if active, otherwise false
     */
    public boolean isActive() {
        return active;
    }


    /**
     * Sets the active status.
     *
     * @param active active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }


    /**
     * Performs the laboratory test.
     *
     * Each type of laboratory test must provide
     * its own implementation.
     */
  
    public void performTest() {
        System.out.println(
                "Performing laboratory test: "
                        + testName);
    }

    /**
     * Returns the laboratory test information.
     *
     * @return test information
     */
    @Override
    public String toString() {

        return "LaboratoryTest{" +
                "testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                ", testDescription='" +
                    testDescription + '\'' +
                ", testCharge=" + testCharge +
                ", testType='" + testType + '\'' +
                ", active=" + active +
                '}';
    }
}