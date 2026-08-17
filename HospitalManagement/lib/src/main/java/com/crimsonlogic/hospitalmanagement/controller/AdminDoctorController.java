package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.DoctorNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.services.*;
@Controller
@RequestMapping("/admin/doctors")
public class AdminDoctorController {

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final DepartmentServiceImpl departmentService =
            new DepartmentServiceImpl();


    // =========================================================
    // VIEW ALL DOCTORS
    // =========================================================

    @GetMapping
    public String getAllDoctors(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors());

        return "admin/doctors/doctor-list";
    }


    // =========================================================
    // VIEW ADD DOCTOR FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddDoctorForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "doctor",
                new Doctor());

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments());

        return "admin/doctors/doctor-add";
    }


    // =========================================================
    // ADD DOCTOR
    // =========================================================

    @PostMapping("/add")
    public String addDoctor(
            @ModelAttribute("doctor") Doctor doctor,
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

            doctor.setDepartment(department);

            doctorService.addDoctor(doctor);

            return "redirect:/admin/doctors";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/doctors/doctor-add";
        }
    }


    // =========================================================
    // VIEW DOCTOR
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewDoctor(
            @PathVariable("id") String doctorId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Doctor doctor =
                    doctorService.getDoctorById(doctorId);

            model.addAttribute(
                    "doctor",
                    doctor);

            return "admin/doctors/doctor-details";

        } catch (ValidationException
                | DoctorNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/doctors/doctor-list";
        }
    }


    // =========================================================
    // VIEW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditDoctorForm(
            @PathVariable("id") String doctorId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Doctor doctor =
                    doctorService.getDoctorById(
                            doctorId);

            model.addAttribute(
                    "doctor",
                    doctor);

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/doctors/doctor-edit";

        } catch (ValidationException
                | DoctorNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "redirect:/admin/doctors";
        }
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    @PostMapping("/edit")
    public String updateDoctor(
            @ModelAttribute("doctor") Doctor doctor,
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

            doctor.setDepartment(department);

            doctorService.updateDoctor(doctor);

            return "redirect:/admin/doctors";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "departments",
                    departmentService.getAllDepartments());

            return "admin/doctors/doctor-edit";
        }
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteDoctor(
            @PathVariable("id") String doctorId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            doctorService.deleteDoctor(
                    doctorId);

            return "redirect:/admin/doctors";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "redirect:/admin/doctors";
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