package com.crimsonlogic.hospitalmanagement.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.AppointmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.services.*;
import com.crimsonlogic.hospitalmanagement.services.AppointmentServiceImpl;


public class AppointmentDAO {

    private static Scanner sc =
            new Scanner(System.in);

    private static AppointmentServiceImpl service =
            new AppointmentServiceImpl();

    private static PatientServiceImpl patientService =
            new PatientServiceImpl();

    private static DoctorServiceImpl doctorService =
            new DoctorServiceImpl();


    // =========================================================
    // MENU
    // =========================================================

    public static  void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "        APPOINTMENT MANAGEMENT");
            System.out.println(
                    "========================================");
            System.out.println(
                    "ADD APPOINTMENT");
            System.out.println(
                    "VIEW APPOINTMENT");
            System.out.println(
                    "LIST APPOINTMENTS");
            System.out.println(
                    "UPDATE APPOINTMENT");
            System.out.println(
                    "DELETE APPOINTMENT");
            System.out.println(
                    "LOGOUT");
            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();

            switch (choice) {

                case "ADD APPOINTMENT":
                	AppointmentDAO.addAppointment();
                    break;

                case "VIEW APPOINTMENT":
                    viewAppointment();
                    break;

                case "LIST APPOINTMENTS":
                    listAppointments();
                    break;

                case "UPDATE APPOINTMENT":
                    updateAppointment();
                    break;

                case "DELETE APPOINTMENT":
                    deleteAppointment();
                    break;

                case "LOGOUT":
                    System.out.println(
                            "Returning to previous menu...");
                    return;

                default:
                    System.out.println(
                            "Invalid choice. "
                            + "Please try again.");
            }
        }
    }


    // =========================================================
    // ADD APPOINTMENT
    // =========================================================

    public static void addAppointment() {

        LocalDate date =
                readAppointmentDate();

        LocalTime time =
                readAppointmentTime(date);

        Patient patient =
                readPatient();
        
        List<Doctor> doctors =
                doctorService.getAllDoctors();

        System.out.println();
        System.out.println(
                "========== AVAILABLE DOCTORS ==========");

        System.out.printf(
                "%-12s %-20s %-20s %-20s %-12s%n",
                "DOCTOR ID",
                "NAME",
                "DEPARTMENT",
                "SPECIALIZATION",
                "FEE");

        System.out.println(
                "--------------------------------------------------------------------------");

        for (Doctor doctor : doctors) {

            System.out.printf(
                    "%-12s %-20s %-20s %-20s ₹%-10.2f%n",

                    doctor.getStaffId(),

                    doctor.getName(),

                    doctor.getDepartment()
                            .getDepartmentName(),

                    doctor.getSpecialization(),

                    doctor.getConsultationFee()
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------");

        Doctor doctor = readDoctor();


        Appointment appointment =
                new Appointment(
                        null,
                        date,
                        time,
                        patient,
                        doctor,
                        true);


        try {

            service.addAppointment(
                    appointment);

            System.out.println();
            System.out.println(
                    "Appointment Added Successfully.");

            System.out.println(
                    "Appointment ID : "
                            + appointment
                                    .getAppointmentId());

        } catch (ValidationException e) {

            /*
             * This should normally not occur because
             * the DAO already performs field validation.
             *
             * Service validation is the final safety layer.
             */
            System.out.println();
            System.out.println(
                    "Unable to add appointment : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // READ APPOINTMENT DATE
    // =========================================================

    private static LocalDate
    readAppointmentDate() {

        while (true) {

            System.out.print(
                    "Appointment Date (yyyy-MM-dd) : ");

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Appointment date cannot be empty.");

                continue;
            }

            try {

                LocalDate date =
                        LocalDate.parse(input);

                if (date.isBefore(
                        LocalDate.now())) {

                    System.out.println(
                            "Appointment date cannot "
                            + "be in the past.");

                    System.out.println(
                            "Please enter today's date "
                            + "or a future date.");

                    continue;
                }

                return date;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date.");

                System.out.println(
                        "Please use yyyy-MM-dd format.");
            }
        }
    }


    // =========================================================
    // READ APPOINTMENT TIME
    // =========================================================

    private static LocalTime
    readAppointmentTime(
            LocalDate date) {

        while (true) {

            System.out.print(
                    "Appointment Time (HH:mm) : ");

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Appointment time cannot be empty.");

                continue;
            }

            try {

                LocalTime time =
                        LocalTime.parse(input);

                /*
                 * If appointment is today,
                 * time must be in the future.
                 */
                if (date.equals(
                        LocalDate.now())
                        && !time.isAfter(
                                LocalTime.now())) {

                    System.out.println(
                            "Appointment time must "
                            + "be in the future.");

                    continue;
                }

                return time;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid time.");

                System.out.println(
                        "Please use HH:mm format.");
            }
        }
    }


    // =========================================================
    // READ PATIENT
    // =========================================================

    private static Patient readPatient() {

        while (true) {

            System.out.print(
                    "Patient ID : ");

            String patientId =
                    sc.nextLine().trim();

            if (patientId.isEmpty()) {

                System.out.println(
                        "Patient ID cannot be empty.");

                continue;
            }

            try {

                Patient patient =
                        patientService
                                .getPatientById(
                                        patientId);

                return patient;

            } catch (Exception e) {

                System.out.println(
                        "Invalid Patient ID : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the "
                        + "Patient ID again.");
            }
        }
    }


    // =========================================================
    // READ DOCTOR
    // =========================================================

    private static Doctor readDoctor() {

        while (true) {

            System.out.print(
                    "Doctor ID : ");

            String doctorId =
                    sc.nextLine().trim();

            if (doctorId.isEmpty()) {

                System.out.println(
                        "Doctor ID cannot be empty.");

                continue;
            }

            try {

                Doctor doctor =
                        doctorService
                                .getDoctorById(
                                        doctorId);

                return doctor;

            } catch (Exception e) {

                System.out.println(
                        "Invalid Doctor ID : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the "
                        + "Doctor ID again.");
            }
        }
    }


    // =========================================================
    // VIEW APPOINTMENT
    // =========================================================

    private static void viewAppointment() {

        while (true) {

            System.out.print(
                    "Enter Appointment ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Appointment ID cannot be empty.");

                continue;
            }

            try {

                Appointment appointment =
                        service.getAppointmentById(
                                id);

                System.out.println();
                System.out.println(
                        "=============== APPOINTMENT ===============");

                System.out.println(
                        "Appointment ID : "
                                + appointment
                                        .getAppointmentId());

                System.out.println(
                        "Date           : "
                                + appointment
                                        .getAppointmentDate());

                System.out.println(
                        "Time           : "
                                + appointment
                                        .getAppointmentTime());

                System.out.println(
                        "Patient ID     : "
                                + appointment
                                        .getPatient()
                                        .getPatientId());

                System.out.println(
                        "Patient Name   : "
                                + appointment
                                        .getPatient()
                                        .getPatientName());

                System.out.println(
                        "Doctor ID      : "
                                + appointment
                                        .getDoctor()
                                        .getStaffId());

                System.out.println(
                        "Doctor Name    : "
                                + appointment
                                        .getDoctor()
                                        .getName());

                System.out.println(
                        "============================================");

                return;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (AppointmentNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the "
                        + "Appointment ID again.");
            }
        }
    }


    // =========================================================
    // LIST APPOINTMENTS
    // =========================================================

    private static void listAppointments() {

        List<Appointment> appointments =
                service.getAllAppointments();

        if (appointments == null
                || appointments.isEmpty()) {

            System.out.println();
            System.out.println(
                    "No appointments found.");

            return;
        }

        System.out.println();

        System.out.println(
                "============================ "
                + "APPOINTMENTS "
                + "============================");

        System.out.printf(
                "%-12s %-12s %-8s %-15s %-20s %-15s %-20s%n",
                "APP ID",
                "DATE",
                "TIME",
                "PATIENT ID",
                "PATIENT NAME",
                "DOCTOR ID",
                "DOCTOR NAME"
        );

        System.out.println(
                "---------------------------------------------------------------------------------------------");

        for (Appointment appointment :
                appointments) {

            System.out.printf(
                    "%-12s %-12s %-8s %-15s %-20s %-15s %-20s%n",

                    appointment.getAppointmentId(),

                    appointment.getAppointmentDate(),

                    appointment.getAppointmentTime(),

                    appointment.getPatient()
                            .getPatientId(),

                    appointment.getPatient()
                            .getPatientName(),

                    appointment.getDoctor()
                            .getStaffId(),

                    appointment.getDoctor()
                            .getName()
            );
        }

        System.out.println(
                "=============================================================================================");
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    private static void updateAppointment() {

        Appointment existing =
                readExistingAppointment();


        LocalDate date =
                readAppointmentDate();

        LocalTime time =
                readAppointmentTime(date);

        Patient patient =
                readPatient();

        Doctor doctor =
                readDoctor();


        Appointment appointment =
                new Appointment(
                        existing
                                .getAppointmentId(),

                        date,

                        time,

                        patient,

                        doctor,

                        existing.isActive()
                );


        try {

            service.updateAppointment(
                    appointment);

            System.out.println();
            System.out.println(
                    "Appointment Updated Successfully.");

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Unable to update appointment : "
                            + e.getMessage());

        } catch (AppointmentNotFoundException e) {

            System.out.println(
                    e.getMessage());
        }
    }


    // =========================================================
    // READ EXISTING APPOINTMENT
    // =========================================================

    private static Appointment
    readExistingAppointment() {

        while (true) {

            System.out.print(
                    "Appointment ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Appointment ID cannot be empty.");

                continue;
            }

            try {

                return service
                        .getAppointmentById(id);

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (AppointmentNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the "
                        + "Appointment ID again.");
            }
        }
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    public static void deleteAppointment() {

        while (true) {

            System.out.print(
                    "Enter Appointment ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Appointment ID cannot be empty.");

                continue;
            }

            try {

                service.deleteAppointment(id);

                System.out.println();
                System.out.println(
                        "Appointment Deleted Successfully.");

                return;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (AppointmentNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the "
                        + "Appointment ID again.");
            }
        }
    }
}