package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.crimsonlogic.hospitalmanagement.exceptions.AppointmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IAppointmentMapper;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

public class AppointmentServiceImpl {

    private PatientServiceImpl patientService =
            new PatientServiceImpl();

    private DoctorServiceImpl doctorService =
            new DoctorServiceImpl();


    // =========================================================
    // ADD APPOINTMENT
    // =========================================================

    public void addAppointment(
            Appointment appointment)
            throws ValidationException {

        validateAppointment(appointment);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            try {

                String appointmentId =
                        IdGenerator.generateRandomId("APP");

                appointment.setAppointmentId(
                        appointmentId);

                appointment.setActive(true);

                mapper.addAppointment(
                        appointment);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET APPOINTMENT BY ID
    // =========================================================

    public Appointment getAppointmentById(
            String appointmentId)
            throws ValidationException,
                   AppointmentNotFoundException {

        validateId(
                appointmentId,
                "Appointment ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            Appointment appointment =
                    mapper.getAppointmentById(
                            appointmentId.trim());

            if (appointment == null) {

                throw new AppointmentNotFoundException(
                        "Appointment with ID "
                                + appointmentId
                                + " not found");
            }

            return appointment;
        }
    }


    // =========================================================
    // GET ALL APPOINTMENTS
    // =========================================================

    public List<Appointment>
    getAllAppointments() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            return mapper.getAllAppointments();
        }
    }


    // =========================================================
    // GET APPOINTMENTS BY DOCTOR AND DATE RANGE
    // =========================================================

    public List<Appointment>
    getAppointmentsByDoctorAndDateRange(
            String doctorId,
            LocalDate fromDate,
            LocalDate toDate) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            return mapper
                    .getAppointmentsByDoctorAndDateRange(
                            doctorId,
                            fromDate,
                            toDate);
        }
    }


    // =========================================================
    // GET APPOINTMENTS BY PATIENT
    // =========================================================

    public List<Appointment> getAppointmentsByPatientId(
            String patientId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            return mapper.getAppointmentsByPatientId(
                    patientId);
        }
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    public void updateAppointment(
            Appointment appointment)
            throws ValidationException,
                   AppointmentNotFoundException {

        validateAppointment(appointment);

        validateId(
                appointment.getAppointmentId(),
                "Appointment ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            try {

                Appointment existingAppointment =
                        mapper.getAppointmentById(
                                appointment
                                        .getAppointmentId());

                if (existingAppointment == null) {

                    throw new AppointmentNotFoundException(
                            "Appointment with ID "
                                    + appointment
                                            .getAppointmentId()
                                    + " not found");
                }

                mapper.updateAppointment(
                        appointment);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    public void deleteAppointment(
            String appointmentId)
            throws ValidationException,
                   AppointmentNotFoundException {

        validateId(
                appointmentId,
                "Appointment ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAppointmentMapper mapper =
                    session.getMapper(
                            IAppointmentMapper.class);

            try {

                Appointment appointment =
                        mapper.getAppointmentById(
                                appointmentId.trim());

                if (appointment == null) {

                    throw new AppointmentNotFoundException(
                            "Appointment with ID "
                                    + appointmentId
                                    + " not found");
                }

                mapper.deleteAppointment(
                        appointmentId.trim());

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // COMPLETE APPOINTMENT VALIDATION
    // =========================================================

    private void validateAppointment(
            Appointment appointment)
            throws ValidationException {

        if (appointment == null) {

            throw new ValidationException(
                    "Appointment cannot be null");
        }


        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        if (appointment.getAppointmentDate()
                == null) {

            throw new ValidationException(
                    "Appointment date cannot be null");
        }

        LocalDate appointmentDate =
                appointment.getAppointmentDate();

        LocalDate today =
                LocalDate.now();

        if (appointmentDate.isBefore(today)) {

            throw new ValidationException(
                    "Appointment date cannot be in the past");
        }


        // -----------------------------------------------------
        // TIME
        // -----------------------------------------------------

        if (appointment.getAppointmentTime()
                == null) {

            throw new ValidationException(
                    "Appointment time cannot be null");
        }

        LocalTime appointmentTime =
                appointment.getAppointmentTime();

        /*
         * If appointment is today,
         * time must also be in the future.
         */

        if (appointmentDate.equals(today)
                && !appointmentTime.isAfter(
                        LocalTime.now())) {

            throw new ValidationException(
                    "Appointment time must be in the future");
        }


        // -----------------------------------------------------
        // PATIENT
        // -----------------------------------------------------

        if (appointment.getPatient() == null) {

            throw new ValidationException(
                    "Patient is required");
        }

        if (appointment.getPatient()
                .getPatientId() == null
                || appointment.getPatient()
                        .getPatientId()
                        .trim()
                        .isEmpty()) {

            throw new ValidationException(
                    "Patient ID is required");
        }


        // -----------------------------------------------------
        // DOCTOR
        // -----------------------------------------------------

        if (appointment.getDoctor() == null) {

            throw new ValidationException(
                    "Doctor is required");
        }

        if (appointment.getDoctor()
                .getStaffId() == null
                || appointment.getDoctor()
                        .getStaffId()
                        .trim()
                        .isEmpty()) {

            throw new ValidationException(
                    "Doctor ID is required");
        }


        // -----------------------------------------------------
        // VERIFY PATIENT EXISTS
        // -----------------------------------------------------

        patientService.getPatientById(
                appointment.getPatient()
                        .getPatientId()
                        .trim());


        // -----------------------------------------------------
        // VERIFY DOCTOR EXISTS
        // -----------------------------------------------------

        doctorService.getDoctorById(
                appointment.getDoctor()
                        .getStaffId()
                        .trim());
    }


    // =========================================================
    // ID VALIDATION
    // =========================================================

    private void validateId(
            String id,
            String fieldName)
            throws ValidationException {

        if (id == null
                || id.trim().isEmpty()) {

            throw new ValidationException(
                    fieldName
                            + " cannot be empty");
        }
    }
}