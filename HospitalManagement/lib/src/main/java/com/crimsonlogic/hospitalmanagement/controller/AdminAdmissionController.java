package com.crimsonlogic.hospitalmanagement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.services.AdmissionServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;

@Controller
@RequestMapping("/admin/admissions")
public class AdminAdmissionController {

    private final AdmissionServiceImpl admissionService =
            new AdmissionServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // VIEW ALL ADMISSIONS
    // =========================================================

    @GetMapping
    public String getAllAdmissions(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "admissions",
                admissionService.getAllAdmissions());

        return "admin/admissions/admission-list";
    }


    // =========================================================
    // SHOW ADMIT PATIENT FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddAdmissionForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patients",
                patientService.getAllPatients());

        model.addAttribute(
                "wardTypes",
                WardType.values());

        return "admin/admissions/admission-add";
    }


    // =========================================================
    // FIND AVAILABLE BEDS
    // =========================================================

    @GetMapping("/available-beds")
    public String getAvailableBeds(
            @RequestParam String patientId,
            @RequestParam String wardType,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            WardType type =
                    WardType.valueOf(
                            wardType.toUpperCase());

            Bed bed =
                    admissionService.findAvailableBed(
                            patientId,
                            type);

            model.addAttribute(
                    "availableBed",
                    bed);

            model.addAttribute(
                    "patientId",
                    patientId);

            model.addAttribute(
                    "wardType",
                    type);

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            model.addAttribute(
                    "wardTypes",
                    WardType.values());

            return "admin/admissions/admission-add";

        } catch (ValidationException
                | IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            model.addAttribute(
                    "wardTypes",
                    WardType.values());

            return "admin/admissions/admission-add";
        }
    }


    // =========================================================
    // ADMIT PATIENT
    // =========================================================

    @PostMapping("/add")
    public String admitPatient(
            @RequestParam String patientId,
            @RequestParam String wardType,
            @RequestParam String selectedBedId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            WardType type =
                    WardType.valueOf(
                            wardType.toUpperCase());

            admissionService.admitPatient(
                    patientId,
                    type,
                    selectedBedId);

            return "redirect:/admin/admissions";

        } catch (ValidationException
                | IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            model.addAttribute(
                    "wardTypes",
                    WardType.values());

            return "admin/admissions/admission-add";
        }
    }


    // =========================================================
    // VIEW ADMISSION
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewAdmission(
            @PathVariable("id") String admissionId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Admission admission =
                    admissionService.getAdmissionById(
                            admissionId);

            model.addAttribute(
                    "admission",
                    admission);

            return "admin/admissions/admission-details";

        } catch (ValidationException e) {

            return "redirect:/admin/admissions";
        }
    }


    // =========================================================
    // DISCHARGE PATIENT
    // =========================================================

    @GetMapping("/discharge/{id}")
    public String dischargePatient(
            @PathVariable("id") String admissionId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            admissionService.dischargeAdmission(
                    admissionId);

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/admin/admissions";
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