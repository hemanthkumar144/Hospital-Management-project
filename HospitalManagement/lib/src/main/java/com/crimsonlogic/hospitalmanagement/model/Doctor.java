package com.crimsonlogic.hospitalmanagement.model;

// =========================================================
// DOCTOR MODEL
// =========================================================
// Doctor extends Staff and contains doctor-specific details.
// =========================================================

public class Doctor extends Staff {

    private String specialization;
    private int experience;
    private double consultationFee;
    private boolean active;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Doctor() {
        super();
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public Doctor(String staffId,
                  String name,
                  int age,
                  String gender,
                  String phone,
                  double salary,
                  Department department,
                  String specialization,
                  int experience,
                  double consultationFee,
                  boolean active) {

        super(staffId,
              name,
              age,
              gender,
              phone,
              salary,
              department);

        this.specialization = specialization;
        this.experience = experience;
        this.consultationFee = consultationFee;
        this.active = active;
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    // =========================================================
    // DOCTOR DUTY
    // =========================================================

    @Override
    public void performDuty() {

        System.out.println(
                "Doctor is treating patients");
    }


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {

        return "Doctor [staffId=" + getStaffId()
                + ", name=" + getName()
                + ", age=" + getAge()
                + ", gender=" + getGender()
                + ", phone=" + getPhone()
                + ", salary=" + getSalary()
                + ", department=" + getDepartment()
                + ", specialization=" + specialization
                + ", experience=" + experience
                + ", consultationFee=" + consultationFee
                + "]";
    }
}