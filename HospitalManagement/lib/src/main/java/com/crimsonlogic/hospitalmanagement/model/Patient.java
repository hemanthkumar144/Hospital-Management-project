package com.crimsonlogic.hospitalmanagement.model;

public class Patient {

    private String patientId;
    private String patientName;
    private int age;
    private String gender;
    private String phone;
    private Address address;
    private boolean active;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Patient() {}

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Patient(String patientId, String patientName, int age,
                   String gender, String phone, Address address,
                   boolean active) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient [patientId=" + patientId
                + ", patientName=" + patientName
                + ", age=" + age
                + ", gender=" + gender
                + ", phone=" + phone
                + ", address=" + address
                + ", active=" + active
                + ", userId=" + userId + "]";
    }
}