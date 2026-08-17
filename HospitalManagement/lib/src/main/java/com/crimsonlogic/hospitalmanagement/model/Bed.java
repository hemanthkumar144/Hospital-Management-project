package com.crimsonlogic.hospitalmanagement.model;

/**
 * Represents a hospital bed.
 *
 * Each bed belongs to exactly one ward.
 * A ward can contain multiple beds.
 *
 * A bed can either be AVAILABLE or OCCUPIED.
 * When a bed is OCCUPIED, it must have a patient assigned.
 */
public class Bed {

    private String bedId;
    private Ward ward;
    private String availability;
    private Patient patient;
    private boolean active;


    /**
     * Default constructor.
     */
    public Bed() {
    }


    /**
     * Parameterized constructor.
     *
     * @param bedId        unique bed ID
     * @param ward         ward to which the bed belongs
     * @param availability current availability of the bed
     * @param patient      patient assigned to the bed
     * @param active       whether the bed is active
     */
    public Bed(String bedId,
               Ward ward,
               String availability,
               Patient patient,
               boolean active) {

        this.bedId = bedId;
        this.ward = ward;
        this.availability = availability;
        this.patient = patient;
        this.active = active;
    }


    public String getBedId() {
        return bedId;
    }


    public void setBedId(String bedId) {
        this.bedId = bedId;
    }


    public Ward getWard() {
        return ward;
    }


    public void setWard(Ward ward) {
        this.ward = ward;
    }


    public String getAvailability() {
        return availability;
    }


    public void setAvailability(String availability) {
        this.availability = availability;
    }


    public Patient getPatient() {
        return patient;
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {

        return "Bed [bedId=" + bedId
                + ", ward=" + ward
                + ", availability=" + availability
                + ", patient=" + patient
                + ", active=" + active
                + "]";
    }
}