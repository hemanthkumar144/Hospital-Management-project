package com.crimsonlogic.hospitalmanagement.model;

import com.crimsonlogic.hospitalmanagement.enums.WardType;

/**
 * Represents a hospital ward.
 *
 * A ward can contain multiple beds.
 * Each bed belongs to one ward.
 */
public class Ward {

    private String wardId;
    private String wardName;
    private WardType wardType;
    private double bedCharge;
    private boolean active;


    /**
     * Default constructor.
     */
    public Ward() {
    }


    /**
     * Parameterized constructor.
     *
     * @param wardId      unique ward ID
     * @param wardName    name of the ward
     * @param wardType    type of the ward
     * @param bedCharge   charge for one bed
     * @param active      whether the ward is active
     */
    public Ward(String wardId,
            String wardName,
            WardType wardType,
            double bedCharge,
            boolean active) {

    this.wardId = wardId;
    this.wardName = wardName;
    this.wardType = wardType;
    this.bedCharge = bedCharge;
    this.active = active;
}


    public String getWardId() {
        return wardId;
    }


    public void setWardId(String wardId) {
        this.wardId = wardId;
    }


    public String getWardName() {
        return wardName;
    }


    public void setWardName(String wardName) {
        this.wardName = wardName;
    }


    public WardType getWardType() {
        return wardType;
    }

    public void setWardType(WardType wardType) {
        this.wardType = wardType;
    }

    public double getBedCharge() {
        return bedCharge;
    }


    public void setBedCharge(double bedCharge) {
        this.bedCharge = bedCharge;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {

        return "Ward [wardId=" + wardId
                + ", wardName=" + wardName
                + ", wardType=" + wardType
                + ", bedCharge=" + bedCharge
                + ", active=" + active
                + "]";
    }
}