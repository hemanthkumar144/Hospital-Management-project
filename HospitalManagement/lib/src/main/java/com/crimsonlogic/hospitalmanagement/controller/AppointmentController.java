package com.crimsonlogic.hospitalmanagement.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.services.*;

@Controller
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();
    
    private final ReportServiceImpl reportService=new ReportServiceImpl();


    @GetMapping("/patient/book-appointment")
    public String showBookingForm(
            HttpSession session,
            Model model) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        List<Doctor> doctors =
                doctorService.getAllDoctors();

        model.addAttribute("doctors", doctors);

        if ("ADMIN".equalsIgnoreCase(role)) {

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            return "admin-book-appointment";
        }

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        return "book-appointment";
    }


    @PostMapping("/patient/book-appointment")
    public String bookAppointment(
            @RequestParam String doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam(required = false) String patientId,
            HttpSession session,
            Model model) {

        try {

            String role =
                    (String) session.getAttribute("role");

            if (role == null) {
                return "redirect:/login";
            }

            Patient patient;

            if ("ADMIN".equalsIgnoreCase(role)) {

                patient =
                        patientService.getPatientById(
                                patientId);

            } else {

                String userId =
                        (String) session.getAttribute("userId");

                if (userId == null) {
                    return "redirect:/login";
                }

                patient =
                        patientService.getPatientByUserId(
                                userId);
            }

            if (patient == null) {
                throw new RuntimeException(
                        "Patient not found");
            }

            Doctor doctor =
                    doctorService.getDoctorById(
                            doctorId);

            Appointment appointment =
                    new Appointment();

            appointment.setPatient(patient);
            appointment.setDoctor(doctor);

            appointment.setAppointmentDate(
                    LocalDate.parse(
                            appointmentDate));

            appointment.setAppointmentTime(
                    LocalTime.parse(
                            appointmentTime));

            appointmentService.addAppointment(
                    appointment);

            if ("ADMIN".equalsIgnoreCase(role)) {
                return "redirect:/admin/dashboard";
            }

            return "redirect:/patient/appointments";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "doctors",
                    doctorService.getAllDoctors());

            String role =
                    (String) session.getAttribute("role");

            if ("ADMIN".equalsIgnoreCase(role)) {

                model.addAttribute(
                        "patients",
                        patientService.getAllPatients());

                return "admin-book-appointment";
            }

            return "book-appointment";
        }
    }


    @GetMapping("/patient/appointments")
    public String myAppointments(
            HttpSession session,
            Model model) {

        String role =
                (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        if ("ADMIN".equalsIgnoreCase(role)) {

            List<Appointment> appointments =
                    appointmentService.getAllAppointments();

            model.addAttribute(
                    "appointments",
                    appointments);

            return "patient-appointments";
        }

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        List<Appointment> appointments =
                appointmentService
                        .getAppointmentsByPatientId(
                                patient.getPatientId());

        model.addAttribute(
                "appointments",
                appointments);

        return "patient-appointments";
    }
    @GetMapping("/appointments-by-status")
    public String appointmentsByStatus(Model model) {

        Map<String, List<Appointment>> groupedAppointments =
                reportService.getAppointmentsByStatus();

        model.addAttribute(
                "groupedAppointments",
                groupedAppointments);

        return "admin/reports/appointments-by-status";
    }
}