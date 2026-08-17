package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.hospitalmanagement.exceptions.BillNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.services.BillServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;

@Controller
@RequestMapping("/admin/bills")
public class AdminBillController {

    private final BillServiceImpl billService =
            new BillServiceImpl();

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // VIEW ALL BILLS
    // =========================================================

    @GetMapping
    public String getAllBills(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "bills",
                billService.getAllBills());

        return "admin/bills/bill-list";
    }


    // =========================================================
    // SHOW GENERATE BILL FORM
    // =========================================================

    @GetMapping("/add")
    public String showGenerateBillForm(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "patients",
                patientService.getAllPatients());

        return "admin/bills/bill-add";
    }


    // =========================================================
    // GENERATE BILL
    // =========================================================

    @PostMapping("/add")
    public String generateBill(
            @RequestParam String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Bill bill =
                    billService.generateBill(
                            patientId);

            return "redirect:/admin/bills/view/"
                    + bill.getBillId();

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients());

            return "admin/bills/bill-add";
        }
    }


    // =========================================================
    // VIEW BILL
    // =========================================================

    @GetMapping("/view/{id}")
    public String viewBill(
            @PathVariable("id") String billId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            Bill bill =
                    billService.getBillByIdService(
                            billId);

            model.addAttribute(
                    "bill",
                    bill);

            return "admin/bills/bill-details";

        } catch (BillNotFoundException
                | ValidationException e) {

            return "redirect:/admin/bills";
        }
    }


    // =========================================================
    // MARK BILL AS PAID
    // =========================================================

    @GetMapping("/pay/{id}")
    public String markBillAsPaid(
            @PathVariable("id") String billId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            billService.markBillAsPaid(
                    billId);

        } catch (BillNotFoundException
                | ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/admin/bills";
    }


    // =========================================================
    // CANCEL BILL
    // =========================================================

    @GetMapping("/cancel/{id}")
    public String cancelBill(
            @PathVariable("id") String billId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            billService.cancelBill(
                    billId);

        } catch (BillNotFoundException
                | ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/admin/bills";
    }


    // =========================================================
    // DELETE BILL
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteBill(
            @PathVariable("id") String billId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            billService.deleteBillService(
                    billId);

        } catch (BillNotFoundException
                | ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/admin/bills";
    }


    // =========================================================
    // PENDING BILLS BY PATIENT
    // =========================================================

    @GetMapping("/pending")
    public String pendingBills(
            @RequestParam String patientId,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {

            model.addAttribute(
                    "bills",
                    billService
                            .getPendingBillsByPatientIdService(
                                    patientId));

            model.addAttribute(
                    "patientId",
                    patientId);

            return "admin/bills/bill-pending";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "admin/bills/bill-pending";
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