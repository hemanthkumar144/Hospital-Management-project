package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.services.BillServiceImpl;


// =========================================================
// BILL DAO
// =========================================================
// Handles console-based Bill Management.
//
// IMPORTANT:
// BillDAO does NOT calculate charges.
//
// BillServiceImpl automatically calculates:
// 1. Consultation charges
// 2. Medicine charges
// 3. Laboratory charges
// 4. Bed charges
//
// BillDAO only takes user input and displays information.
// =========================================================

public class BillDAO {

    private static Scanner sc =
            new Scanner(System.in);

    private static BillServiceImpl ServiceImpl =
            new BillServiceImpl();


    // =========================================================
    // BILL MANAGEMENT MENU
    // =========================================================

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "          BILL MANAGEMENT");
            System.out.println(
                    "========================================");

            System.out.println(
                    "1. Generate Bill");

            System.out.println(
                    "2. View Bill By ID");

            System.out.println(
                    "3. View All Bills");

            System.out.println(
                    "4. Mark Bill As Paid");

            System.out.println(
                    "5. Cancel Bill");

            System.out.println(
                    "6. Delete Bill");

            System.out.println(
                    "7. Back");

            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");


            String choice =
                    sc.nextLine()
                            .trim();


            switch (choice) {

                case "1":
                    generateBill();
                    break;

                case "2":
                    viewBill();
                    break;

                case "3":
                    viewAllBills();
                    break;

                case "4":
                    markBillAsPaid();
                    break;

                case "5":
                    cancelBill();
                    break;

                case "6":
                    deleteBill();
                    break;

                case "7":
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }


    // =========================================================
    // GENERATE BILL
    // =========================================================
    // User enters ONLY Patient ID.
    //
    // All charges are calculated automatically by BillServiceImpl.
    // =========================================================

    private static void generateBill() {

        try {

            System.out.println();
            System.out.println(
                    "========== GENERATE BILL ==========");


            System.out.print(
                    "Patient ID : ");

            String patientId =
                    sc.nextLine()
                            .trim();


            if (patientId.isEmpty()) {

                System.out.println(
                        "Patient ID cannot be empty.");

                return;
            }


            // -------------------------------------------------
            // Generate complete bill automatically
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "Calculating charges...");


            Bill bill =
                    ServiceImpl.generateBill(
                            patientId);


            // -------------------------------------------------
            // Display calculated bill
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "             BILL SUMMARY");
            System.out.println(
                    "========================================");


            System.out.println(
                    "Bill ID              : "
                            + bill.getBillId());


            System.out.println(
                    "Patient ID           : "
                            + bill.getPatient()
                                    .getPatientId());


            System.out.println(
                    "Patient Name         : "
                            + bill.getPatient()
                                    .getPatientName());


            System.out.println(
                    "----------------------------------------");


            System.out.printf(
                    "Consultation Charges : ₹%.2f%n",
                    bill.getConsultationFee());


            System.out.printf(
                    "Medicine Charges     : ₹%.2f%n",
                    bill.getMedicineCharges());


            System.out.printf(
                    "Laboratory Charges   : ₹%.2f%n",
                    bill.getLaboratoryCharges());


            System.out.printf(
                    "Bed Charges          : ₹%.2f%n",
                    bill.getBedCharges());


            System.out.println(
                    "----------------------------------------");


            System.out.printf(
                    "TOTAL AMOUNT         : ₹%.2f%n",
                    bill.getTotalAmount());


            System.out.println(
                    "Bill Date            : "
                            + bill.getBillDate());


            System.out.println(
                    "Status               : "
                            + bill.getStatus());


            System.out.println(
                    "========================================");


            System.out.println(
                    "Bill Generated Successfully.");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // VIEW BILL BY ID
    // =========================================================

    private static void viewBill() {

        try {

            System.out.println();
            System.out.println(
                    "========== VIEW BILL ==========");


            System.out.print(
                    "Bill ID : ");

            String billId =
                    sc.nextLine()
                            .trim();


            if (billId.isEmpty()) {

                System.out.println(
                        "Bill ID cannot be empty.");

                return;
            }


            Bill bill =
                    ServiceImpl.getBillById(
                            billId);


            displayBill(bill);


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // VIEW ALL BILLS
    // =========================================================

 // =========================================================
 // VIEW ALL BILLS
 // =========================================================
 // Displays all bills in a table format.
 // =========================================================

 private static void viewAllBills() {

     try {

         List<Bill> bills =
                 ServiceImpl.getAllBills();


         if (bills == null || bills.isEmpty()) {

             System.out.println();
             System.out.println(
                     "No bills found.");

             return;
         }


         System.out.println();
         System.out.println(
                 "==============================================================================================");
         System.out.println(
                 "                                      ALL BILLS");
         System.out.println(
                 "==============================================================================================");


         // -------------------------------------------------
         // Table Header
         // -------------------------------------------------

         System.out.printf(
                 "%-12s %-14s %-14s %-14s %-14s %-14s %-12s%n",

                 "BILL ID",
                 "PATIENT ID",
                 "CONSULTATION",
                 "MEDICINE",
                 "LABORATORY",
                 "TOTAL",
                 "STATUS"
         );


         System.out.println(
                 "----------------------------------------------------------------------------------------------");


         // -------------------------------------------------
         // Table Rows
         // -------------------------------------------------

         for (Bill bill : bills) {

             String billId =
                     bill.getBillId();

             String patientId =
                     bill.getPatient() != null
                             ? bill.getPatient()
                                     .getPatientId()
                             : "N/A";


             System.out.printf(
                     "%-12s %-14s ₹%-13.2f ₹%-13.2f ₹%-13.2f ₹%-13.2f %-12s%n",

                     billId,

                     patientId,

                     bill.getConsultationFee(),

                     bill.getMedicineCharges(),

                     bill.getLaboratoryCharges(),

                     bill.getTotalAmount(),

                     bill.getStatus()
             );
         }


         System.out.println(
                 "==============================================================================================");


     } catch (Exception e) {

         System.out.println(
                 "Error : "
                         + e.getMessage());
     }
 }

    // =========================================================
    // MARK BILL AS PAID
    // =========================================================

    private static void markBillAsPaid() {

        try {

            System.out.println();
            System.out.println(
                    "========== MARK BILL AS PAID ==========");


            System.out.print(
                    "Bill ID : ");

            String billId =
                    sc.nextLine()
                            .trim();


            if (billId.isEmpty()) {

                System.out.println(
                        "Bill ID cannot be empty.");

                return;
            }


            ServiceImpl.markBillAsPaid(
                    billId);


            System.out.println();
            System.out.println(
                    "Bill marked as PAID successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // CANCEL BILL
    // =========================================================

    private static void cancelBill() {

        try {

            System.out.println();
            System.out.println(
                    "========== CANCEL BILL ==========");


            System.out.print(
                    "Bill ID : ");

            String billId =
                    sc.nextLine()
                            .trim();


            if (billId.isEmpty()) {

                System.out.println(
                        "Bill ID cannot be empty.");

                return;
            }


            // -------------------------------------------------
            // Confirmation
            // -------------------------------------------------

            System.out.print(
                    "Are you sure you want to cancel "
                    + "this bill? (Y/N) : ");


            String confirm =
                    sc.nextLine()
                            .trim();


            if (!confirm.equalsIgnoreCase("Y")) {

                System.out.println(
                        "Cancellation aborted.");

                return;
            }


            ServiceImpl.cancelBill(
                    billId);


            System.out.println();
            System.out.println(
                    "Bill cancelled successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // DELETE BILL
    // =========================================================

    private static void deleteBill() {

        try {

            System.out.println();
            System.out.println(
                    "========== DELETE BILL ==========");


            System.out.print(
                    "Bill ID : ");

            String billId =
                    sc.nextLine()
                            .trim();


            if (billId.isEmpty()) {

                System.out.println(
                        "Bill ID cannot be empty.");

                return;
            }


            // -------------------------------------------------
            // Confirmation
            // -------------------------------------------------

            System.out.print(
                    "Are you sure you want to delete "
                    + "this bill? (Y/N) : ");


            String confirm =
                    sc.nextLine()
                            .trim();


            if (!confirm.equalsIgnoreCase("Y")) {

                System.out.println(
                        "Deletion aborted.");

                return;
            }


            ServiceImpl.deleteBill(
                    billId);


            System.out.println();
            System.out.println(
                    "Bill deleted successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // DISPLAY BILL
    // =========================================================
    // Common method used when displaying one bill.
    // =========================================================

    private static void displayBill(
            Bill bill) {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "              BILL DETAILS");
        System.out.println(
                "========================================");


        System.out.println(
                "Bill ID              : "
                        + bill.getBillId());


        if (bill.getPatient() != null) {

            System.out.println(
                    "Patient ID           : "
                            + bill.getPatient()
                                    .getPatientId());


            System.out.println(
                    "Patient Name         : "
                            + bill.getPatient()
                                    .getPatientName());
        }


        System.out.println(
                "----------------------------------------");


        System.out.printf(
                "Consultation Charges : ₹%.2f%n",
                bill.getConsultationFee());


        System.out.printf(
                "Medicine Charges     : ₹%.2f%n",
                bill.getMedicineCharges());


        System.out.printf(
                "Laboratory Charges   : ₹%.2f%n",
                bill.getLaboratoryCharges());


        System.out.printf(
                "Bed Charges          : ₹%.2f%n",
                bill.getBedCharges());


        System.out.println(
                "----------------------------------------");


        System.out.printf(
                "TOTAL AMOUNT         : ₹%.2f%n",
                bill.getTotalAmount());


        System.out.println(
                "Bill Date            : "
                        + bill.getBillDate());


        System.out.println(
                "Status               : "
                        + bill.getStatus());


        System.out.println(
                "========================================");
    }
}