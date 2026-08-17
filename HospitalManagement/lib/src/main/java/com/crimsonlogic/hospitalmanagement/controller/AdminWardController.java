package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.exceptions.WardNotFoundException;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.services.WardServiceImpl;

@Controller
@RequestMapping("/admin/wards")
public class AdminWardController {

    private final WardServiceImpl wardService =
            new WardServiceImpl();


    // =========================================================
    // VIEW ALL WARDS
    // =========================================================

    @GetMapping
    public String getAllWards(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "wards",
                wardService.getAllWards());

        return "admin/wards/ward-list";
    }


    // =========================================================
    // SHOW ADD FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddWardForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "ward",
                new Ward());

        return "admin/wards/ward-add";
    }


    // =========================================================
    // ADD WARD
    // =========================================================

    @PostMapping("/add")
    public String addWard(
            @ModelAttribute("ward") Ward ward,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            wardService.addWard(ward);

            return "redirect:/admin/wards";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/wards/ward-add";
        }
    }


    // =========================================================
    // VIEW WARD
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewWard(
            @PathVariable("id") String wardId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Ward ward =
                    wardService.getWardById(wardId);

            model.addAttribute(
                    "ward",
                    ward);

            return "admin/wards/ward-details";

        } catch (ValidationException
                | WardNotFoundException e) {

            return "redirect:/admin/wards";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditWardForm(
            @PathVariable("id") String wardId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Ward ward =
                    wardService.getWardById(wardId);

            model.addAttribute(
                    "ward",
                    ward);

            return "admin/wards/ward-edit";

        } catch (ValidationException
                | WardNotFoundException e) {

            return "redirect:/admin/wards";
        }
    }


    // =========================================================
    // UPDATE WARD
    // =========================================================

    @PostMapping("/edit")
    public String updateWard(
            @ModelAttribute("ward") Ward ward,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            wardService.updateWard(ward);

            return "redirect:/admin/wards";

        } catch (ValidationException
                | WardNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/wards/ward-edit";
        }
    }


    // =========================================================
    // DELETE WARD
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteWard(
            @PathVariable("id") String wardId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            wardService.deleteWard(wardId);

        } catch (ValidationException
                | WardNotFoundException e) {

            // Return to list
        }

        return "redirect:/admin/wards";
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