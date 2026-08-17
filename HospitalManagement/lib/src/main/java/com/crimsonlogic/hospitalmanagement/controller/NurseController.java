package com.crimsonlogic.hospitalmanagement.controller;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.*;
import com.crimsonlogic.hospitalmanagement.services.*;

@Controller
public class NurseController {

    private final NurseServiceImpl nurseService =
            new NurseServiceImpl();

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final PrescriptionServiceImpl prescriptionService =
            new PrescriptionServiceImpl();

    private final AdmissionServiceImpl admissionService =
            new AdmissionServiceImpl();

    private final BedServiceImpl bedService =
            new BedServiceImpl();


    // =========================================================
    // NURSE DASHBOARD
    // =========================================================

    @GetMapping("/nurse")
    public String nurseDashboard(
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Nurse nurse =
                nurseService.getNurseByUserId(userId);

        if (nurse == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "nurse",
                nurse);

        return "nurse/nurse-dashboard";
    }


    // =========================================================
    // VIEW ALL APPOINTMENTS
    // =========================================================

    @GetMapping("/nurse/appointments")
    public String viewAppointments(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        model.addAttribute(
                "appointments",
                appointments);

        return "nurse/nurse-appointments";
    }


    // =========================================================
    // VIEW PATIENT
    // =========================================================

    @GetMapping("/nurse/patient/{patientId}")
    public String viewPatient(
            @PathVariable String patientId,
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService
                        .getPatientById(patientId);

        model.addAttribute(
                "patient",
                patient);

        return "nurse/nurse-patient";
    }


    // =========================================================
    // LIST ALL PATIENTS
    // =========================================================

    @GetMapping("/nurse/patients")
    public String listPatients(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Patient> patients =
                patientService
                        .getAllPatients();

        model.addAttribute(
                "patients",
                patients);

        return "nurse/nurse-patients";
    }


    // =========================================================
    // VIEW ALL PRESCRIPTIONS
    // =========================================================

    @GetMapping("/nurse/prescriptions")
    public String viewPrescriptions(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Prescription> prescriptions =
                prescriptionService
                        .getAllPrescriptions();

        model.addAttribute(
                "prescriptions",
                prescriptions);

        return "nurse/nurse-prescriptions";
    }


    // =========================================================
    // ADMIT PATIENT PAGE
    // =========================================================

    @GetMapping("/nurse/admission/add")
    public String admissionPage(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "wardTypes",
                WardType.values());

        return "nurse/nurse-admit-patient";
    }


    // =========================================================
    // FIND AVAILABLE BED
    // =========================================================

    @GetMapping("/nurse/admission/available-bed")
    public String findAvailableBed(
            @RequestParam String patientId,
            @RequestParam WardType wardType,
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService
                        .getPatientById(patientId);

        Bed bed =
                admissionService
                        .findAvailableBed(
                                patientId,
                                wardType);

        model.addAttribute(
                "patient",
                patient);

        model.addAttribute(
                "bed",
                bed);

        model.addAttribute(
                "wardType",
                wardType);

        return "nurse/nurse-confirm-admission";
    }


    // =========================================================
    // ADMIT PATIENT
    // =========================================================

    @PostMapping("/nurse/admission/add")
    public String admitPatient(
            @RequestParam String patientId,
            @RequestParam WardType wardType,
            @RequestParam String bedId,
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Admission admission =
                admissionService
                        .admitPatient(
                                patientId,
                                wardType,
                                bedId);

        model.addAttribute(
                "admission",
                admission);

        return "nurse/nurse-admission-success";
    }


    // =========================================================
    // VIEW ADMISSION
    // =========================================================

    @GetMapping("/nurse/admission/{admissionId}")
    public String viewAdmission(
            @PathVariable String admissionId,
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Admission admission =
                admissionService
                        .getAdmissionById(
                                admissionId);

        model.addAttribute(
                "admission",
                admission);

        return "nurse/nurse-admission-details";
    }


    // =========================================================
    // DISCHARGE PATIENT
    // =========================================================

    @PostMapping(
            "/nurse/admission/{admissionId}/discharge")
    public String dischargePatient(
            @PathVariable String admissionId,
            HttpSession session,
            Model model)
            throws ValidationException {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        admissionService
                .dischargeAdmission(
                        admissionId);

        return "redirect:/nurse/admissions";
    }


    // =========================================================
    // VIEW ADMISSIONS
    // =========================================================

    @GetMapping("/nurse/admissions")
    public String viewAdmissions(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Admission> admissions =
                admissionService
                        .getAllAdmissions();

        model.addAttribute(
                "admissions",
                admissions);

        return "nurse/nurse-admissions";
    }


    // =========================================================
    // MANAGE BEDS
    // =========================================================

    @GetMapping("/nurse/beds")
    public String manageBeds(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Bed> beds =
                bedService.getAllBeds();

        model.addAttribute(
                "beds",
                beds);

        return "nurse/nurse-beds";
    }


    // =========================================================
    // VIEW BED
    // =========================================================

    @GetMapping("/nurse/bed/{bedId}")
    public String viewBed(
            @PathVariable String bedId,
            HttpSession session,
            Model model)
            throws Exception {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Bed bed =
                bedService
                        .getBedById(bedId);

        model.addAttribute(
                "bed",
                bed);

        return "nurse/nurse-bed-details";
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @GetMapping("/nurse/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}