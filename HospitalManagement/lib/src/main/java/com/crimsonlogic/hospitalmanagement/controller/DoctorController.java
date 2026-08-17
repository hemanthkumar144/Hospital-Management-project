package com.crimsonlogic.hospitalmanagement.controller;

import java.time.LocalDate;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.crimsonlogic.hospitalmanagement.exceptions.*;
import com.crimsonlogic.hospitalmanagement.model.*;
import com.crimsonlogic.hospitalmanagement.services.*;

@Controller
public class DoctorController {

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final MedicineServiceImpl medicineService =
            new MedicineServiceImpl();

    private final PrescriptionServiceImpl prescriptionService =
            new PrescriptionServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // DOCTOR DASHBOARD
    // =========================================================

    @GetMapping("/doctor")
    public String doctorDashboard(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "doctor",
                doctor);

        return "doctor/doctor-dashboard";
    }


    // =========================================================
    // VIEW APPOINTMENTS
    // DATE RANGE IS ALSO HANDLED HERE
    // =========================================================

    @GetMapping("/doctor/appointments")
    public String viewAppointments(
            @RequestParam(required = false)
            String fromDate,

            @RequestParam(required = false)
            String toDate,

            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        LocalDate from;
        LocalDate to;

        if (fromDate == null
                || fromDate.trim().isEmpty()
                || toDate == null
                || toDate.trim().isEmpty()) {

            from = LocalDate.of(
                    1000,
                    1,
                    1);

            to = LocalDate.of(
                    9999,
                    12,
                    31);

        } else {

            from = LocalDate.parse(
                    fromDate);

            to = LocalDate.parse(
                    toDate);
        }

        List<Appointment> appointments =
                appointmentService
                        .getAppointmentsByDoctorAndDateRange(
                                doctor.getStaffId(),
                                from,
                                to);

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "appointments",
                appointments);

        model.addAttribute(
                "fromDate",
                fromDate);

        model.addAttribute(
                "toDate",
                toDate);

        return "doctor/doctor-appointments";
    }


    // =========================================================
    // VIEW APPOINTMENT DETAILS
    // =========================================================

    @GetMapping("/doctor/appointment/{appointmentId}")
    public String viewAppointment(
            @PathVariable String appointmentId,
            HttpSession session,
            Model model)
            throws ValidationException,
                   AppointmentNotFoundException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        Appointment appointment =
                appointmentService
                        .getAppointmentById(
                                appointmentId);

        if (appointment == null
                || appointment.getDoctor() == null
                || !doctor.getStaffId()
                        .equals(
                                appointment
                                        .getDoctor()
                                        .getStaffId())) {

            return "redirect:/doctor/appointments";
        }

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "appointment",
                appointment);

        return "doctor/doctor-appointment-details";
    }


    // =========================================================
    // CANCEL APPOINTMENT
    // =========================================================

    @PostMapping(
            "/doctor/appointment/{appointmentId}/cancel")
    public String cancelAppointment(
            @PathVariable String appointmentId,
            HttpSession session)
            throws ValidationException,
                   AppointmentNotFoundException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        Appointment appointment =
                appointmentService
                        .getAppointmentById(
                                appointmentId);

        if (appointment == null
                || appointment.getDoctor() == null
                || !doctor.getStaffId()
                        .equals(
                                appointment
                                        .getDoctor()
                                        .getStaffId())) {

            return "redirect:/doctor/appointments";
        }

        appointmentService.deleteAppointment(
                appointmentId);

        return "redirect:/doctor/appointments";
    }


    // =========================================================
    // LIST PATIENTS
    // =========================================================

    @GetMapping("/doctor/patients")
    public String listPatients(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        List<Patient> patients =
                patientService.getAllPatients();

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "patients",
                patients);

        return "doctor/doctor-patients";
    }


    // =========================================================
    // VIEW PATIENT - SEARCH PAGE
    // =========================================================

    @GetMapping("/doctor/patient")
    public String patientSearchPage(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("doctor", doctor);

        return "doctor/doctor-patient-search";
    }


    // =========================================================
    // VIEW PATIENT - SEARCH RESULT
    // =========================================================

    @GetMapping("/doctor/patient/search")
    public String searchPatient(
            @RequestParam String patientId,
            HttpSession session,
            Model model)
            throws ValidationException,
                   PatientNotFoundException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        Patient patient =
                patientService.getPatientById(patientId);

        model.addAttribute("doctor", doctor);
        model.addAttribute("patient", patient);

        return "doctor/doctor-patient";
    }


    // =========================================================
    // VIEW MEDICINES
    // =========================================================

    @GetMapping("/doctor/medicines")
    public String medicines(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        List<Medicine> medicines =
                medicineService.getAllMedicines();

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "medicines",
                medicines);

        return "doctor/doctor-medicines";
    }


    // =========================================================
    // ADD MEDICINE PAGE
    // =========================================================

    @GetMapping("/doctor/medicines/add")
    public String addMedicinePage(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "medicine",
                new Medicine());

        return "doctor/doctor-add-medicine";
    }


    // =========================================================
    // ADD MEDICINE
    // =========================================================

    @PostMapping("/doctor/medicines/add")
    public String addMedicine(
            Medicine medicine,
            HttpSession session)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        medicineService.addMedicine(
                medicine);

        return "redirect:/doctor/medicines";
    }


    // =========================================================
    // WRITE PRESCRIPTION PAGE
    // =========================================================

    @GetMapping("/doctor/prescription/add")
    public String prescriptionPage(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "doctor",
                doctor);

        model.addAttribute(
                "prescription",
                new Prescription());

        model.addAttribute(
                "medicines",
                medicineService.getAllMedicines());

        return "doctor/doctor-add-prescription";
    }

    // =========================================================
    // ADD PRESCRIPTION
    // =========================================================

    @PostMapping("/doctor/prescription/add")
    public String addPrescription(
            Prescription prescription,
            HttpSession session)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Doctor doctor =
                doctorService.getDoctorByUserId(userId);

        if (doctor == null) {
            return "redirect:/dashboard";
        }

        /*
         * Always use the logged-in doctor.
         */
        prescription.setDoctor(
                doctor);

        prescriptionService.addPrescription(
                prescription);

        return "redirect:/doctor";
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @GetMapping("/doctor/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}