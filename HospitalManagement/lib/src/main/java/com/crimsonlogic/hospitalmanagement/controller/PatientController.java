package com.crimsonlogic.hospitalmanagement.controller;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.crimsonlogic.hospitalmanagement.model.*;
import com.crimsonlogic.hospitalmanagement.services.*;

@Controller
public class PatientController {

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final PrescriptionServiceImpl prescriptionService =
            new PrescriptionServiceImpl();

    private final BillServiceImpl billService =
            new BillServiceImpl();

    private final PaymentServiceImpl paymentService =
            new PaymentServiceImpl();


    // =========================================================
    // PATIENT DASHBOARD
    // =========================================================

    @GetMapping("/patient")
    public String patientDashboard(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patient",
                patient);

        return "patient-dashboard";
    }


    // =========================================================
    // PATIENT PROFILE
    // =========================================================

    @GetMapping("/patient/profile")
    public String viewProfile(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patient",
                patient);

        return "patient-profile";
    }


    // =========================================================
    // VIEW PRESCRIPTIONS
    // =========================================================

    @GetMapping("/patient/prescriptions")
    public String myPrescriptions(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        List<Prescription> prescriptions =
                prescriptionService
                        .getAllPrescriptions()
                        .stream()
                        .filter(p ->
                                p != null
                                && p.getPatient() != null
                                && patient.getPatientId()
                                    .equals(
                                        p.getPatient()
                                            .getPatientId()))
                        .collect(
                                java.util.stream.Collectors
                                        .toList());

        model.addAttribute(
                "prescriptions",
                prescriptions);

        model.addAttribute(
                "patient",
                patient);

        return "patient-prescriptions";
    }


    // =========================================================
    // VIEW BILLS
    // =========================================================

    @GetMapping("/patient/bills")
    public String myBills(
            HttpSession session,
            Model model) {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        List<Bill> bills =
                billService
                        .getAllBills()
                        .stream()
                        .filter(b ->
                                b != null
                                && b.getPatient() != null
                                && patient.getPatientId()
                                    .equals(
                                        b.getPatient()
                                            .getPatientId()))
                        .collect(
                                java.util.stream.Collectors
                                        .toList());

        model.addAttribute(
                "bills",
                bills);

        model.addAttribute(
                "patient",
                patient);

        return "patient-bills";
    }


    // =========================================================
    // PAYMENT PAGE
    // =========================================================

    @GetMapping("/patient/payment")
    public String paymentPage(
            @RequestParam String billId,
            HttpSession session,
            Model model)
            throws Exception {

        String userId =
                (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Patient patient =
                patientService.getPatientByUserId(
                        userId);

        if (patient == null) {
            return "redirect:/dashboard";
        }

        Bill bill =
                billService.getBillById(
                        billId);

        if (bill == null) {
            model.addAttribute(
                    "error",
                    "Bill not found");

            return "patient-bills";
        }

        /*
         * Security check:
         * The patient can pay only his/her own bill.
         */
        if (bill.getPatient() == null
                || !patient.getPatientId()
                        .equals(
                            bill.getPatient()
                                .getPatientId())) {

            model.addAttribute(
                    "error",
                    "You are not authorized "
                    + "to pay this bill");

            return "patient-bills";
        }

        model.addAttribute(
                "bill",
                bill);

        model.addAttribute(
                "patient",
                patient);

        return "patient-payment";
    }


    // =========================================================
    // MAKE PAYMENT
    // =========================================================

    @PostMapping("/patient/payment")
    public String makePayment(
            @RequestParam String billId,
            @RequestParam String paymentType,
            HttpSession session,
            Model model) {

        try {

            String userId =
                    (String) session.getAttribute(
                            "userId");

            if (userId == null) {
                return "redirect:/login";
            }

            Patient patient =
                    patientService
                            .getPatientByUserId(
                                    userId);

            if (patient == null) {
                return "redirect:/dashboard";
            }

            Bill bill =
                    billService.getBillById(
                            billId);

            if (bill == null) {
                throw new Exception(
                        "Bill not found");
            }

            /*
             * Important:
             * Patient can pay only his/her own bill.
             */
            if (bill.getPatient() == null
                    || !patient.getPatientId()
                            .equals(
                                bill.getPatient()
                                    .getPatientId())) {

                throw new Exception(
                        "You are not authorized "
                        + "to pay this bill");
            }

            Payment payment =
                    new Payment();

            payment.setBill(
                    bill);

            payment.setPaymentType(
                    paymentType);

            paymentService.addPayment(
                    payment);

            return "redirect:/patient/bills";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "patient-payment";
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @GetMapping("/patient/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}