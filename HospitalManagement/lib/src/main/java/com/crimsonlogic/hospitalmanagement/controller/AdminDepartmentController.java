package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.DepartmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.services.DepartmentServiceImpl;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private final DepartmentServiceImpl departmentService =
            new DepartmentServiceImpl();


    // =========================================================
    // VIEW ALL DEPARTMENTS
    // =========================================================

    @GetMapping
    public String getAllDepartments(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments());

        return "admin/departments/department-list";
    }


    // =========================================================
    // SHOW ADD FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddDepartmentForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "department",
                new Department());

        return "admin/departments/department-add";
    }


    // =========================================================
    // ADD DEPARTMENT
    // =========================================================

    @PostMapping("/add")
    public String addDepartment(
            @ModelAttribute("department")
            Department department,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            departmentService.addDepartment(
                    department);

            return "redirect:/admin/departments";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/departments/department-add";
        }
    }


    // =========================================================
    // VIEW DEPARTMENT
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewDepartment(
            @PathVariable("id") String departmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Department department =
                    departmentService.getDepartmentById(
                            departmentId);

            model.addAttribute(
                    "department",
                    department);

            return "admin/departments/department-details";

        } catch (ValidationException
                | DepartmentNotFoundException e) {

            return "redirect:/admin/departments";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditDepartmentForm(
            @PathVariable("id") String departmentId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Department department =
                    departmentService.getDepartmentById(
                            departmentId);

            model.addAttribute(
                    "department",
                    department);

            return "admin/departments/department-edit";

        } catch (ValidationException
                | DepartmentNotFoundException e) {

            return "redirect:/admin/departments";
        }
    }


    // =========================================================
    // UPDATE DEPARTMENT
    // =========================================================

    @PostMapping("/edit")
    public String updateDepartment(
            @ModelAttribute("department")
            Department department,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            departmentService.updateDepartment(
                    department);

            return "redirect:/admin/departments";

        } catch (ValidationException
                | DepartmentNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/departments/department-edit";
        }
    }


    // =========================================================
    // DELETE DEPARTMENT
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteDepartment(
            @PathVariable("id") String departmentId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            departmentService.deleteDepartment(
                    departmentId);

        } catch (ValidationException
                | DepartmentNotFoundException e) {

            // Return to list if deletion fails
        }

        return "redirect:/admin/departments";
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