package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.services.LaboratoryTestServiceImpl;

@Controller
@RequestMapping("/admin/laboratory")
public class AdminLaboratoryController {

    private final LaboratoryTestServiceImpl laboratoryService =
            new LaboratoryTestServiceImpl();


    // =========================================================
    // VIEW ALL TESTS
    // =========================================================

    @GetMapping
    public String getAllTests(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "tests",
                laboratoryService.getAllTests());

        return "admin/laboratory/laboratory-list";
    }


    // =========================================================
    // SHOW ADD FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddTestForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "test",
                new LaboratoryTest());

        return "admin/laboratory/laboratory-add";
    }


    // =========================================================
    // ADD TEST
    // =========================================================

    @PostMapping("/add")
    public String addTest(
            @ModelAttribute("test")
            LaboratoryTest test,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            laboratoryService.addTest(test);

            return "redirect:/admin/laboratory";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/laboratory/laboratory-add";
        }
    }


    // =========================================================
    // VIEW TEST
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewTest(
            @PathVariable("id") String testId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            LaboratoryTest test =
                    laboratoryService.getTestById(testId);

            model.addAttribute(
                    "test",
                    test);

            return "admin/laboratory/laboratory-details";

        } catch (ValidationException e) {

            return "redirect:/admin/laboratory";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditTestForm(
            @PathVariable("id") String testId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            LaboratoryTest test =
                    laboratoryService.getTestById(testId);

            model.addAttribute(
                    "test",
                    test);

            return "admin/laboratory/laboratory-edit";

        } catch (ValidationException e) {

            return "redirect:/admin/laboratory";
        }
    }


    // =========================================================
    // UPDATE TEST
    // =========================================================

    @PostMapping("/edit")
    public String updateTest(
            @ModelAttribute("test")
            LaboratoryTest test,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            laboratoryService.updateTest(test);

            return "redirect:/admin/laboratory";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/laboratory/laboratory-edit";
        }
    }


    // =========================================================
    // DEACTIVATE TEST
    // =========================================================

    @GetMapping("/deactivate/{id}")
    public String deactivateTest(
            @PathVariable("id") String testId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            laboratoryService.deactivateTest(testId);

        } catch (ValidationException e) {

            // Return to list
        }

        return "redirect:/admin/laboratory";
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