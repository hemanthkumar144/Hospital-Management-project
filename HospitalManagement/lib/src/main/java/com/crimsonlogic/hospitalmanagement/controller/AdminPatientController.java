package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.PatientNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;

@Controller
@RequestMapping("/admin/patients")
public class AdminPatientController {

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // VIEW ALL PATIENTS
    // =========================================================

    @GetMapping
    public String getAllPatients(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patients",
                patientService.getAllPatients());

        return "admin/patients/patient-list";
    }


    // =========================================================
    // VIEW PATIENT
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewPatient(
            @PathVariable("id") String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Patient patient =
                    patientService.getPatientById(
                            patientId);

            model.addAttribute(
                    "patient",
                    patient);

            return "admin/patients/patient-details";

        } catch (ValidationException
                | PatientNotFoundException e) {

            return "redirect:/admin/patients";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditPatientForm(
            @PathVariable("id") String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Patient patient =
                    patientService.getPatientById(
                            patientId);

            model.addAttribute(
                    "patient",
                    patient);

            return "admin/patients/patient-edit";

        } catch (ValidationException
                | PatientNotFoundException e) {

            return "redirect:/admin/patients";
        }
    }


    // =========================================================
    // UPDATE PATIENT
    // =========================================================

    @PostMapping("/edit")
    public String updatePatient(
            @ModelAttribute("patient") Patient patient,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            patientService.updatePatient(
                    patient);

            return "redirect:/admin/patients";

        } catch (ValidationException
                | PatientNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/patients/patient-edit";
        }
    }


    // =========================================================
    // DELETE / DEACTIVATE PATIENT
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deletePatient(
            @PathVariable("id") String patientId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            patientService.deletePatient(
                    patientId);

        } catch (ValidationException
                | PatientNotFoundException e) {

            // Return to list if deletion fails
        }

        return "redirect:/admin/patients";
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