package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.model.Nurse;
import com.crimsonlogic.hospitalmanagement.services.DepartmentServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.NurseServiceImpl;

@Controller
@RequestMapping("/admin/nurses")
public class AdminNurseController {

    private final NurseServiceImpl nurseService =
            new NurseServiceImpl();

    private final DepartmentServiceImpl departmentService =
            new DepartmentServiceImpl();


    // =========================================================
    // VIEW ALL NURSES
    // =========================================================

    @GetMapping
    public String getAllNurses(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "nurses",
                nurseService.getAllNurses());

        return "admin/nurses/nurse-list";
    }


    // =========================================================
    // SHOW ADD NURSE FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddNurseForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "nurse",
                new Nurse());

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments());

        return "admin/nurses/nurse-add";
    }


    // =========================================================
    // ADD NURSE
    // =========================================================

    @PostMapping("/add")
    public String addNurse(
            @ModelAttribute("nurse") Nurse nurse,
            @RequestParam String departmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Department department =
                    departmentService.getDepartmentById(
                            departmentId);

            nurse.setDepartment(department);

            nurseService.addNurse(nurse);

            return "redirect:/admin/nurses";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/nurses/nurse-add";
        }
    }


    // =========================================================
    // VIEW NURSE
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewNurse(
            @PathVariable("id") String nurseId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Nurse nurse =
                    nurseService.getNurseById(
                            nurseId);

            model.addAttribute(
                    "nurse",
                    nurse);

            return "admin/nurses/nurse-details";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "redirect:/admin/nurses";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditNurseForm(
            @PathVariable("id") String nurseId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Nurse nurse =
                    nurseService.getNurseById(
                            nurseId);

            model.addAttribute(
                    "nurse",
                    nurse);

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/nurses/nurse-edit";

        } catch (ValidationException e) {

            return "redirect:/admin/nurses";
        }
    }


    // =========================================================
    // UPDATE NURSE
    // =========================================================

    @PostMapping("/edit")
    public String updateNurse(
            @ModelAttribute("nurse") Nurse nurse,
            @RequestParam String departmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Department department =
                    departmentService.getDepartmentById(
                            departmentId);

            nurse.setDepartment(department);

            nurseService.updateNurse(nurse);

            return "redirect:/admin/nurses";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/nurses/nurse-edit";
        }
    }


    // =========================================================
    // DEACTIVATE NURSE
    // =========================================================

    @GetMapping("/deactivate/{id}")
    public String deactivateNurse(
            @PathVariable("id") String nurseId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            nurseService.deactivateNurse(
                    nurseId);

            return "redirect:/admin/nurses";

        } catch (ValidationException e) {

            return "redirect:/admin/nurses";
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