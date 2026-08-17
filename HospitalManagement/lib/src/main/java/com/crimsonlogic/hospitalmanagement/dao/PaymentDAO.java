package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Payment;
import com.crimsonlogic.hospitalmanagement.services.*;


// =========================================================
// PAYMENT DAO
// =========================================================

// Card and UPI details are temporary inputs only.
// They are NOT stored in the database.
//
//
// =========================================================

public class PaymentDAO {

    private final Scanner sc =
            new Scanner(System.in);

    private final PaymentServiceImpl paymentServiceImpl =
            new PaymentServiceImpl();

    private final BillServiceImpl billServiceImpl =
            new BillServiceImpl();


    // =========================================================
    // SHOW PAYMENT MENU
    // =========================================================

    public void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");

            System.out.println(
                    "          PAYMENT MANAGEMENT");

            System.out.println(
                    "========================================");

            System.out.println(
                    "1. Make Payment");

            System.out.println(
                    "2. View Payment By ID");

            System.out.println(
                    "3. View All Payments");

            System.out.println(
                    "4. Back");

            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");


            String choice =
                    sc.nextLine().trim();


            switch (choice) {

                case "1":
                    makePayment();
                    break;

                case "2":
                    viewPaymentById();
                    break;

                case "3":
                    viewAllPayments();
                    break;

                case "4":
                    return;

                default:
                    System.out.println(
                            "Invalid Choice.");
            }
        }
    }


    // =========================================================
    // MAKE PAYMENT
    // =========================================================
    // Step 1:
    // Ask Patient ID.
    //
    // Step 2:
    // Display only pending bills.
    //
    // Step 3:
    // Select bill.
    //
    // Step 4:
    // Select payment type.
    // =========================================================

    private void makePayment() {

        try {

            System.out.println();
            System.out.println(
                    "========== MAKE PAYMENT ==========");


            // -------------------------------------------------
            // PATIENT ID
            // -------------------------------------------------

            System.out.print(
                    "Patient ID : ");

            String patientId =
                    sc.nextLine().trim();


            if (patientId.isEmpty()) {

                System.out.println(
                        "Patient ID cannot be empty.");

                return;
            }


            // -------------------------------------------------
            // GET PENDING BILLS
            // -------------------------------------------------

            List<Bill> pendingBills =
                    billServiceImpl
                            .getPendingBillsByPatientId(
                                    patientId);


            if (pendingBills == null
                    || pendingBills.isEmpty()) {

                System.out.println();
                System.out.println(
                        "No pending bills found "
                        + "for patient "
                        + patientId);

                return;
            }


            // -------------------------------------------------
            // DISPLAY PENDING BILLS
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "============================================================");

            System.out.println(
                    "                    PENDING BILLS");

            System.out.println(
                    "============================================================");


            System.out.printf(
                    "%-5s %-15s %-15s %-15s%n",
                    "NO.",
                    "BILL ID",
                    "BILL DATE",
                    "AMOUNT");


            System.out.println(
                    "------------------------------------------------------------");


            for (int i = 0;
                 i < pendingBills.size();
                 i++) {

                Bill pendingBill =
                        pendingBills.get(i);


                System.out.printf(
                        "%-5d %-15s %-15s ₹%-14.2f%n",

                        i + 1,

                        pendingBill.getBillId(),

                        pendingBill.getBillDate(),

                        pendingBill.getTotalAmount());
            }


            System.out.println(
                    "============================================================");


            // -------------------------------------------------
            // SELECT BILL
            // -------------------------------------------------

            System.out.print(
                    "Select Bill : ");


            int billChoice;


            try {

                billChoice =
                        Integer.parseInt(
                                sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid bill selection.");

                return;
            }


            if (billChoice < 1
                    || billChoice > pendingBills.size()) {

                System.out.println(
                        "Invalid bill selection.");

                return;
            }


            // -------------------------------------------------
            // SELECTED BILL
            // -------------------------------------------------

            Bill selectedBill =
                    pendingBills.get(
                            billChoice - 1);


            System.out.println();
            System.out.println(
                    "Selected Bill : "
                            + selectedBill.getBillId());


            System.out.printf(
                    "Bill Amount   : ₹%.2f%n",
                    selectedBill.getTotalAmount());


            // -------------------------------------------------
            // PAYMENT TYPE
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "Select Payment Type");

            System.out.println(
                    "1. Cash");

            System.out.println(
                    "2. Card");

            System.out.println(
                    "3. UPI");


            System.out.print(
                    "Enter Choice : ");


            String paymentChoice =
                    sc.nextLine().trim();


            switch (paymentChoice) {

                case "1":
                    processCashPayment(
                            selectedBill);
                    break;

                case "2":
                    processCardPayment(
                            selectedBill);
                    break;

                case "3":
                    processUpiPayment(
                            selectedBill);
                    break;

                default:
                    System.out.println(
                            "Invalid payment type.");
            }


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Payment Failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // CASH PAYMENT
    // =========================================================

    private void processCashPayment(
            Bill bill) {

        try {

            System.out.println();
            System.out.println(
                    "---------- CASH PAYMENT ----------");


            System.out.printf(
                    "Bill Amount : ₹%.2f%n",
                    bill.getTotalAmount());


            System.out.print(
                    "Cash Amount : ₹");


            double cashAmount;


            try {

                cashAmount =
                        Double.parseDouble(
                                sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid cash amount.");

                return;
            }


            Payment payment =
                    new Payment();


            payment.setBill(
                    bill);

            payment.setPaymentType(
                    "CASH");

            payment.setAmount(
                    cashAmount);


            paymentServiceImpl.addPayment(
                    payment,
                    null,
                    null,
                    null,
                    null,
                    null);


            System.out.println();
            System.out.println(
                    "Payment Successful.");

            System.out.println(
                    "Payment ID : "
                            + payment.getPaymentId());

            System.out.println(
                    "Bill marked as PAID.");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Payment Failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // CARD PAYMENT
    // =========================================================
    // Card details are used only for validation.
    // They are NOT stored.
    // =========================================================

    private void processCardPayment(
            Bill bill) {

        try {

            System.out.println();
            System.out.println(
                    "---------- CARD PAYMENT ----------");


            System.out.printf(
                    "Bill Amount : ₹%.2f%n",
                    bill.getTotalAmount());


            System.out.print(
                    "Card Number : ");

            String cardNumber =
                    sc.nextLine().trim();


            System.out.print(
                    "Expiry (MM/YY) : ");

            String expiry =
                    sc.nextLine().trim();


            System.out.print(
                    "CVV : ");

            String cvv =
                    sc.nextLine().trim();


            // -------------------------------------------------
            // Create payment
            // -------------------------------------------------

            Payment payment =
                    new Payment();


            payment.setBill(
                    bill);

            payment.setPaymentType(
                    "CARD");

            payment.setAmount(
                    bill.getTotalAmount());


            // -------------------------------------------------
            // Card details are passed only to ServiceImpl.
            // They are NOT stored.
            // -------------------------------------------------

            paymentServiceImpl.addPayment(
                    payment,
                    cardNumber,
                    expiry,
                    cvv,
                    null,
                    null);


            System.out.println();
            System.out.println(
                    "Payment Successful.");

            System.out.println(
                    "Payment ID : "
                            + payment.getPaymentId());

            System.out.println(
                    "Bill marked as PAID.");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Payment Failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // UPI PAYMENT
    // =========================================================
    // UPI details are used only for validation.
    // They are NOT stored.
    // =========================================================

    private void processUpiPayment(
            Bill bill) {

        try {

            System.out.println();
            System.out.println(
                    "---------- UPI PAYMENT ----------");


            System.out.printf(
                    "Bill Amount : ₹%.2f%n",
                    bill.getTotalAmount());


            System.out.print(
                    "UPI ID : ");

            String upiId =
                    sc.nextLine().trim();


            System.out.print(
                    "UPI PIN : ");

            String upiPin =
                    sc.nextLine().trim();


            // -------------------------------------------------
            // Create payment
            // -------------------------------------------------

            Payment payment =
                    new Payment();


            payment.setBill(
                    bill);

            payment.setPaymentType(
                    "UPI");

            payment.setAmount(
                    bill.getTotalAmount());


            // -------------------------------------------------
            // UPI details are passed only to ServiceImpl.
            // They are NOT stored.
            // -------------------------------------------------

            paymentServiceImpl.addPayment(
                    payment,
                    null,
                    null,
                    null,
                    upiId,
                    upiPin);


            System.out.println();
            System.out.println(
                    "Payment Successful.");

            System.out.println(
                    "Payment ID : "
                            + payment.getPaymentId());

            System.out.println(
                    "Bill marked as PAID.");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Payment Failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // VIEW PAYMENT BY ID
    // =========================================================

    private void viewPaymentById() {

        try {

            System.out.println();
            System.out.println(
                    "========== VIEW PAYMENT ==========");


            System.out.print(
                    "Payment ID : ");

            String paymentId =
                    sc.nextLine().trim();


            if (paymentId.isEmpty()) {

                System.out.println(
                        "Payment ID cannot be empty.");

                return;
            }


            Payment payment =
                    paymentServiceImpl
                            .getPaymentById(
                                    paymentId);


            if (payment == null) {

                System.out.println(
                        "Payment not found.");

                return;
            }


            System.out.println();
            System.out.println(
                    "========================================");

            System.out.println(
                    "           PAYMENT DETAILS");

            System.out.println(
                    "========================================");


            System.out.println(
                    "Payment ID   : "
                            + payment.getPaymentId());


            if (payment.getBill() != null) {

                System.out.println(
                        "Bill ID      : "
                                + payment.getBill()
                                        .getBillId());


                if (payment.getBill()
                        .getPatient() != null) {

                    System.out.println(
                            "Patient ID   : "
                                    + payment.getBill()
                                            .getPatient()
                                            .getPatientId());
                }
            }


            System.out.println(
                    "Payment Type : "
                            + payment.getPaymentType());


            System.out.printf(
                    "Amount       : ₹%.2f%n",
                    payment.getAmount());


            System.out.println(
                    "Date         : "
                            + payment.getPaymentDate());


            System.out.println(
                    "Status       : "
                            + payment.getStatus());


            System.out.println(
                    "========================================");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // VIEW ALL PAYMENTS
    // =========================================================
    // Displays payments in table format.
    // Patient ID is obtained through:
    //
    // Payment → Bill → Patient
    // =========================================================

    private void viewAllPayments() {

        try {

            System.out.println();
            System.out.println(
                    "======================================================================");

            System.out.println(
                    "                         ALL PAYMENTS");

            System.out.println(
                    "======================================================================");


            List<Payment> payments =
                    paymentServiceImpl
                            .getAllPayments();


            if (payments == null
                    || payments.isEmpty()) {

                System.out.println(
                        "No payments found.");

                return;
            }


            // -------------------------------------------------
            // TABLE HEADER
            // -------------------------------------------------

            System.out.printf(
                    "%-12s %-15s %-15s %-12s %-14s %-12s%n",

                    "PAYMENT ID",
                    "PATIENT ID",
                    "BILL ID",
                    "TYPE",
                    "AMOUNT",
                    "STATUS");


            System.out.println(
                    "----------------------------------------------------------------------");


            // -------------------------------------------------
            // TABLE ROWS
            // -------------------------------------------------

            for (Payment pay :
                    payments) {


                String paymentId =
                        pay.getPaymentId();


                String patientId =
                        "N/A";


                String paymentBillId =
                        "N/A";


                String paymentType =
                        pay.getPaymentType();


                double amount =
                        pay.getAmount();


                String status =
                        pay.getStatus();


                // -------------------------------------------------
                // Get Bill ID and Patient ID
                // -------------------------------------------------

                if (pay.getBill() != null) {

                    paymentBillId =
                            pay.getBill()
                                    .getBillId();


                    if (pay.getBill()
                            .getPatient() != null) {

                        patientId =
                                pay.getBill()
                                        .getPatient()
                                        .getPatientId();
                    }
                }


                // -------------------------------------------------
                // PRINT ROW
                // -------------------------------------------------

                System.out.printf(
                        "%-12s %-15s %-15s %-12s ₹%-13.2f %-12s%n",

                        paymentId,

                        patientId,

                        paymentBillId,

                        paymentType,

                        amount,

                        status);
            }


            System.out.println(
                    "----------------------------------------------------------------------");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Error : "
                            + e.getMessage());
        }
    }
}