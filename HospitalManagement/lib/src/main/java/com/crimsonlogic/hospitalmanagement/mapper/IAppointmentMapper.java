package com.crimsonlogic.hospitalmanagement.mapper;

import java.time.LocalDate;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.crimsonlogic.hospitalmanagement.model.Appointment;

public interface IAppointmentMapper {

    void addAppointment(Appointment appointment) throws ValidationException;

    Appointment getAppointmentById(String appointmentId) throws ValidationException;

    List<Appointment> getAllAppointments();

    void updateAppointment(Appointment appointment) throws ValidationException;

    void deleteAppointment(String appointmentId) throws ValidationException;

    List<Appointment> getAppointmentsByDoctorAndDateRange(
            @Param("doctorId") String doctorId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
    List<Appointment> getAppointmentsByPatientId(String patientId);
}