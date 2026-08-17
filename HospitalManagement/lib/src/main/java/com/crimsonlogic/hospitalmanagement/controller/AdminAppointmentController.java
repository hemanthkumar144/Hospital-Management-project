package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.AppointmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.services.AppointmentServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.DoctorServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;

@Controller
@RequestMapping("/admin/appointments")
public class AdminAppointmentController {

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // VIEW ALL APPOINTMENTS
    // =========================================================

    @GetMapping
    public String getAllAppointments(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments());

        return "admin/appointments/appointment-list";
    }


    // =========================================================
    // VIEW APPOINTMENT
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewAppointment(
            @PathVariable("id") String appointmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Appointment appointment =
                    appointmentService.getAppointmentById(
                            appointmentId);

            model.addAttribute(
                    "appointment",
                    appointment);

            return "admin/appointments/appointment-details";

        } catch (ValidationException
                | AppointmentNotFoundException e) {

            return "redirect:/admin/appointments";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(
            @PathVariable("id") String appointmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Appointment appointment =
                    appointmentService.getAppointmentById(
                            appointmentId);

            model.addAttribute(
                    "appointment",
                    appointment);

            model.addAttribute(
                    "doctors",
                    doctorService.getAllDoctors());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            return "admin/appointments/appointment-edit";

        } catch (ValidationException
                | AppointmentNotFoundException e) {

            return "redirect:/admin/appointments";
        }
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    @PostMapping("/edit")
    public String updateAppointment(
            @RequestParam String appointmentId,
            @RequestParam String doctorId,
            @RequestParam String patientId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Doctor doctor =
                    doctorService.getDoctorById(
                            doctorId);

            Patient patient =
                    patientService.getPatientById(
                            patientId);

            Appointment appointment =
                    appointmentService.getAppointmentById(
                            appointmentId);

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);

            appointment.setAppointmentDate(
                    java.time.LocalDate.parse(
                            appointmentDate));

            appointment.setAppointmentTime(
                    java.time.LocalTime.parse(
                            appointmentTime));

            appointmentService.updateAppointment(
                    appointment);

            return "redirect:/admin/appointments";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            try {

                model.addAttribute(
                        "appointment",
                        appointmentService.getAppointmentById(
                                appointmentId));

            } catch (Exception ignored) {
            }

            model.addAttribute(
                    "doctors",
                    doctorService.getAllDoctors());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            return "admin/appointments/appointment-edit";
        }
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteAppointment(
            @PathVariable("id") String appointmentId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            appointmentService.deleteAppointment(
                    appointmentId);

        } catch (ValidationException
                | AppointmentNotFoundException e) {

            // Return to list if deletion fails
        }

        return "redirect:/admin/appointments";
    }


    // =========================================================
    // ADMIN CHECK
    // =========================================================

    private boolean isAdmin(
            HttpSession session) {

        String role =
                (String) session.getAttribute("role");

        return role != null
                && "ADMIN".equalsIgnoreCase(role);
    }
}