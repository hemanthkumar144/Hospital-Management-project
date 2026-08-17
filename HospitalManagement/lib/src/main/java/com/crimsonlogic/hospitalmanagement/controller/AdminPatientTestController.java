package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.enums.TestStatus;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;
import com.crimsonlogic.hospitalmanagement.services.LaboratoryTestServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientTestServiceImpl;

@Controller
@RequestMapping("/admin/patient-tests")
public class AdminPatientTestController {

    private final PatientTestServiceImpl patientTestService =
            new PatientTestServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final LaboratoryTestServiceImpl laboratoryService =
            new LaboratoryTestServiceImpl();


    // =========================================================
    // VIEW ALL PATIENT TESTS
    // =========================================================

    @GetMapping
    public String getAllPatientTests(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patientTests",
                patientTestService.getAllPatientTests());

        return "admin/patient-tests/patient-test-list";
    }


    // =========================================================
    // SHOW ADD TEST FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddTestForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patients",
                patientService.getAllPatients());

        model.addAttribute(
                "tests",
                laboratoryService.getAllTests());

        return "admin/patient-tests/patient-test-add";
    }


    // =========================================================
    // ASSIGN TEST TO PATIENT
    // =========================================================

    @PostMapping("/add")
    public String addPatientTest(
            @RequestParam String patientId,
            @RequestParam String testId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            patientTestService.addPatientTest(
                    patientId,
                    testId);

            return "redirect:/admin/patient-tests";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            model.addAttribute(
                    "tests",
                    laboratoryService.getAllTests());

            return "admin/patient-tests/patient-test-add";
        }
    }


    // =========================================================
    // VIEW PATIENT TEST
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewPatientTest(
            @PathVariable("id") String patientTestId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            PatientTest patientTest =
                    patientTestService
                            .getValidatedPatientTestById(
                                    patientTestId);

            model.addAttribute(
                    "patientTest",
                    patientTest);

            return "admin/patient-tests/patient-test-details";

        } catch (ValidationException e) {

            return "redirect:/admin/patient-tests";
        }
    }


    // =========================================================
    // SHOW STATUS UPDATE FORM
    // =========================================================

    @GetMapping("/status/{id}")
    public String showStatusForm(
            @PathVariable("id") String patientTestId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            PatientTest patientTest =
                    patientTestService
                            .getValidatedPatientTestById(
                                    patientTestId);

            model.addAttribute(
                    "patientTest",
                    patientTest);

            model.addAttribute(
                    "statuses",
                    TestStatus.values());

            return "admin/patient-tests/patient-test-status";

        } catch (ValidationException e) {

            return "redirect:/admin/patient-tests";
        }
    }


    // =========================================================
    // UPDATE STATUS
    // =========================================================

    @PostMapping("/status")
    public String updateStatus(
            @RequestParam String patientTestId,
            @RequestParam String status,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            TestStatus testStatus =
                    TestStatus.valueOf(
                            status.toUpperCase());

            patientTestService
                    .updateValidatedTestStatus(
                            patientTestId,
                            testStatus);

            return "redirect:/admin/patient-tests";

        } catch (IllegalArgumentException
                | ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            try {

                model.addAttribute(
                        "patientTest",
                        patientTestService
                                .getValidatedPatientTestById(
                                        patientTestId));

            } catch (ValidationException ignored) {
            }

            model.addAttribute(
                    "statuses",
                    TestStatus.values());

            return "admin/patient-tests/patient-test-status";
        }
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