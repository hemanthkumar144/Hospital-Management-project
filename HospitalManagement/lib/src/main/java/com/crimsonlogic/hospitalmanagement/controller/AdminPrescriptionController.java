package com.crimsonlogic.hospitalmanagement.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.PrescriptionNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.Prescription;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionMedicine;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionTest;
import com.crimsonlogic.hospitalmanagement.services.DoctorServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.LaboratoryTestServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.MedicineServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PrescriptionServiceImpl;

@Controller
@RequestMapping("/admin/prescriptions")
public class AdminPrescriptionController {

    private final PrescriptionServiceImpl prescriptionService =
            new PrescriptionServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final MedicineServiceImpl medicineService =
            new MedicineServiceImpl();

    private final LaboratoryTestServiceImpl laboratoryTestService =
            new LaboratoryTestServiceImpl();


    // =========================================================
    // VIEW ALL
    // =========================================================

    @GetMapping
    public String getAllPrescriptions(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "prescriptions",
                prescriptionService.getAllPrescriptions());

        return "admin/prescriptions/prescription-list";
    }


    // =========================================================
    // VIEW PRESCRIPTION
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewPrescription(
            @PathVariable("id") String prescriptionId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Prescription prescription =
                    prescriptionService.getPrescriptionById(
                            prescriptionId);

            model.addAttribute(
                    "prescription",
                    prescription);

            return "admin/prescriptions/prescription-details";

        } catch (ValidationException
                | PrescriptionNotFoundException e) {

            return "redirect:/admin/prescriptions";
        }
    }


    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditPrescriptionForm(
            @PathVariable("id") String prescriptionId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Prescription prescription =
                    prescriptionService.getPrescriptionById(
                            prescriptionId);

            loadFormData(model);

            model.addAttribute(
                    "prescription",
                    prescription);

            return "admin/prescriptions/prescription-edit";

        } catch (ValidationException
                | PrescriptionNotFoundException e) {

            return "redirect:/admin/prescriptions";
        }
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @PostMapping("/edit")
    public String updatePrescription(
            @RequestParam String prescriptionId,
            @RequestParam String patientId,
            @RequestParam String doctorId,
            @RequestParam String prescriptionDate,
            @RequestParam String instructions,

            @RequestParam(required = false)
            List<String> medicineId,

            @RequestParam(required = false)
            List<String> dosage,

            @RequestParam(required = false)
            List<Integer> quantity,

            @RequestParam(required = false)
            List<String> testId,

            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Prescription prescription =
                    prescriptionService.getPrescriptionById(
                            prescriptionId);

            Patient patient =
                    patientService.getPatientById(
                            patientId);

            Doctor doctor =
                    doctorService.getDoctorById(
                            doctorId);

            prescription.setPatient(patient);
            prescription.setDoctor(doctor);

            prescription.setPrescriptionDate(
                    LocalDate.parse(prescriptionDate));

            prescription.setInstructions(
                    instructions);


            // -------------------------------------------------
            // MEDICINES
            // -------------------------------------------------

            List<PrescriptionMedicine> medicines =
                    new ArrayList<>();

            if (medicineId != null) {

                for (int i = 0;
                     i < medicineId.size();
                     i++) {

                    if (medicineId.get(i) == null
                            || medicineId.get(i).trim().isEmpty()) {
                        continue;
                    }

                    PrescriptionMedicine medicine =
                            new PrescriptionMedicine();

                    medicine.setMedicineId(
                            medicineId.get(i));

                    medicine.setDosage(
                            dosage.get(i));

                    medicine.setQuantity(
                            quantity.get(i));

                    medicines.add(medicine);
                }
            }

            prescription.setMedicines(medicines);


            // -------------------------------------------------
            // TESTS
            // -------------------------------------------------

            List<PrescriptionTest> tests =
                    new ArrayList<>();

            if (testId != null) {

                for (String id : testId) {

                    if (id == null
                            || id.trim().isEmpty()) {
                        continue;
                    }

                    PrescriptionTest test =
                            new PrescriptionTest();

                    test.setTestId(id);

                    tests.add(test);
                }
            }

            prescription.setTests(tests);


            prescriptionService.updatePrescription(
                    prescription);

            return "redirect:/admin/prescriptions";


        } catch (ValidationException
                | PrescriptionNotFoundException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            try {

                model.addAttribute(
                        "prescription",
                        prescriptionService
                                .getPrescriptionById(
                                        prescriptionId));

            } catch (Exception ignored) {
            }

            loadFormData(model);

            return "admin/prescriptions/prescription-edit";
        }
    }


    // =========================================================
    // DELETE
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deletePrescription(
            @PathVariable("id") String prescriptionId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            prescriptionService.deletePrescription(
                    prescriptionId);

        } catch (ValidationException
                | PrescriptionNotFoundException e) {

            // Return to list
        }

        return "redirect:/admin/prescriptions";
    }


    // =========================================================
    // LOAD FORM DATA
    // =========================================================

    private void loadFormData(Model model) {

        model.addAttribute(
                "patients",
                patientService.getAllPatients());

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors());

        model.addAttribute(
                "medicines",
                medicineService.getAllMedicines());

        model.addAttribute(
                "tests",
                laboratoryTestService.getAllTests());
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