package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.BedIdNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.services.BedServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.WardServiceImpl;

@Controller
@RequestMapping("/admin/beds")
public class AdminBedController {

    private final BedServiceImpl bedService =
            new BedServiceImpl();

    private final WardServiceImpl wardService =
            new WardServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // VIEW ALL BEDS
    // =========================================================

    @GetMapping
    public String getAllBeds(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "beds",
                bedService.getAllBeds());

        return "admin/beds/bed-list";
    }


    // =========================================================
    // SHOW ADD FORM
    // =========================================================

    @GetMapping("/add")
    public String showAddBedForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        loadFormData(model);

        return "admin/beds/bed-add";
    }


    // =========================================================
    // ADD BED
    // =========================================================

    @PostMapping("/add")
    public String addBed(
            @RequestParam String wardId,
            @RequestParam String availability,
            @RequestParam(required = false) String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Ward ward =
                    wardService.getWardById(
                            wardId);

            Bed bed =
                    new Bed();

            bed.setWard(ward);

            bed.setAvailability(
                    availability);

            /*
             * AVAILABLE bed must not have a patient.
             *
             * OCCUPIED bed requires a patient.
             */
            if ("OCCUPIED".equalsIgnoreCase(
                    availability)) {

                Patient patient =
                        patientService.getPatientById(
                                patientId);

                bed.setPatient(patient);
            }

            bedService.addBed(bed);

            return "redirect:/admin/beds";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            loadFormData(model);

            return "admin/beds/bed-add";
        }
    }


    // =========================================================
    // VIEW BED
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewBed(
            @PathVariable("id") String bedId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Bed bed =
                    bedService.getBedById(
                            bedId);

            model.addAttribute(
                    "bed",
                    bed);

            return "admin/beds/bed-details";

        } catch (ValidationException
                | BedIdNotFoundException e) {

            return "redirect:/admin/beds";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditBedForm(
            @PathVariable("id") String bedId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Bed bed =
                    bedService.getBedById(
                            bedId);

            model.addAttribute(
                    "bed",
                    bed);

            loadFormData(model);

            return "admin/beds/bed-edit";

        } catch (ValidationException
                | BedIdNotFoundException e) {

            return "redirect:/admin/beds";
        }
    }


    // =========================================================
    // UPDATE BED
    // =========================================================

    @PostMapping("/edit")
    public String updateBed(
            @RequestParam String bedId,
            @RequestParam String wardId,
            @RequestParam String availability,
            @RequestParam(required = false) String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Ward ward =
                    wardService.getWardById(
                            wardId);

            Bed bed =
                    bedService.getBedById(
                            bedId);

            bed.setWard(ward);

            bed.setAvailability(
                    availability);

            if ("AVAILABLE".equalsIgnoreCase(
                    availability)) {

                bed.setPatient(null);

            } else {

                Patient patient =
                        patientService.getPatientById(
                                patientId);

                bed.setPatient(patient);
            }

            bedService.updateBed(bed);

            return "redirect:/admin/beds";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            try {

                model.addAttribute(
                        "bed",
                        bedService.getBedById(
                                bedId));

            } catch (Exception ignored) {
            }

            loadFormData(model);

            return "admin/beds/bed-edit";
        }
    }


    // =========================================================
    // DELETE / DEACTIVATE BED
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteBed(
            @PathVariable("id") String bedId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            bedService.deleteBed(
                    bedId);

        } catch (ValidationException
                | BedIdNotFoundException e) {

            // Return to list
        }

        return "redirect:/admin/beds";
    }


    // =========================================================
    // LOAD WARDS + PATIENTS
    // =========================================================

    private void loadFormData(
            Model model) {

        model.addAttribute(
                "wards",
                wardService.getAllWards());

        model.addAttribute(
                "patients",
                patientService.getAllPatients());
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