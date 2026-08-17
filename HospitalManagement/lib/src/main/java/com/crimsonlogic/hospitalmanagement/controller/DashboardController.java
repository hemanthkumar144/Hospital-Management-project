package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        switch (role.toUpperCase()) {

            case "ADMIN":
                return "redirect:/admin/dashboard";

            case "DOCTOR":
                return "doctor/doctor-dashboard";

            case "NURSE":
                return "nurse/nurse-dashboard";

            case "PATIENT":
                return "patient/patient-dashboard";

            default:
                session.invalidate();
                return "redirect:/login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(role)) {
            return "redirect:/dashboard";
        }

        return "admin-dashboard";
    }
}