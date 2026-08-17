package com.crimsonlogic.hospitalmanagement.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Appointment {

	private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Patient patient;
    private Doctor doctor;
    private boolean active;

    public Appointment(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return isActive() == that.isActive() && Objects.equals(getAppointmentId(), that.getAppointmentId()) && Objects.equals(getAppointmentDate(), that.getAppointmentDate()) && Objects.equals(getAppointmentTime(), that.getAppointmentTime()) && Objects.equals(getPatient(), that.getPatient()) && Objects.equals(getDoctor(), that.getDoctor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getAppointmentDate(), getAppointmentTime(), getPatient(), getDoctor(), isActive());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Appointment() {}

    @Override
    public String toString() {
        return "\nAppointment ID : " + appointmentId +
               "\nDate          : " + appointmentDate +
               "\nTime          : " + appointmentTime +
               "\n\nPatient ID    : " + patient.getPatientId() +
               "\nPatient Name  : " + patient.getPatientName() +
               "\nAge           : " + patient.getAge() +
               "\nGender        : " + patient.getGender() +
               "\nPhone         : " + patient.getPhone() +
               "\n\nDoctor ID     : " + doctor.getStaffId() +
               "\nDoctor Name   : " + doctor.getName();
    }
	public Appointment(String appointmentId2, LocalDate appointmentDate,
                       LocalTime appointmentTime,
                       Patient patient, Doctor doctor,boolean active) {
        this.appointmentId = appointmentId2;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.active=active;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}