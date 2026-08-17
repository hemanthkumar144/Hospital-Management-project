package com.crimsonlogic.hospitalmanagement.dao;

import java.util.Scanner;
import java.util.List;

import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.services.AdmissionServiceImpl;


/**
 * DAO layer responsible for Admission console operations.
 */
public class AdmissionDAO {

    private final Scanner scanner;

    private final AdmissionServiceImpl admissionService;


    /**
     * Creates AdmissionDAO.
     */
    public AdmissionDAO() {

        scanner = new Scanner(System.in);

        admissionService =
                new AdmissionServiceImpl();
    }


    /**
     * Displays the Admission menu.
     */
    public  void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "          ADMISSION MENU");
            System.out.println(
                    "========================================");

            System.out.println("1. Admit Patient");
            System.out.println("2. View Admission");
            System.out.println("3. List Admissions");
            System.out.println("4. Discharge Patient");
            System.out.println("5. Exit");

            System.out.print("Enter Choice : ");

            String choice =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();

            switch (choice) {

                case "1":
                    admitPatient();
                    break;

                case "2":
                    viewAdmission();
                    break;

                case "3":
                    listAdmissions();
                    break;

                case "4":
                    dischargePatient();
                    break;

                case "5":
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }


    // =========================================================
    // ADMIT PATIENT
    // =========================================================

    /**
     * Handles the patient admission process.
     */
    
    private void admitPatient() {

        try {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "          ADMIT PATIENT");
            System.out.println(
                    "========================================");


            // -------------------------------------------------
            // PATIENT ID
            // -------------------------------------------------

            System.out.print(
                    "Patient ID : ");

            String patientId =
                    scanner.nextLine().trim();


            // -------------------------------------------------
            // WARD TYPE
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "Ward Type:");

            System.out.println(
                    "1. General");

            System.out.println(
                    "2. ICU");

            System.out.println(
                    "3. Pediatric");

            System.out.println(
                    "4. Private");

            System.out.print(
                    "Enter Ward Type : ");

            String wardChoice =
                    scanner.nextLine().trim();


            WardType wardType =
                    getWardType(wardChoice);


            // -------------------------------------------------
            // FIND AVAILABLE BED
            // -------------------------------------------------

            Bed bed =
                    admissionService.findAvailableBed(
                            patientId,
                            wardType);


            System.out.println();
            System.out.println(
                    "Available bed found : "
                            + bed.getBedId());


            // -------------------------------------------------
            // DISPLAY WARD INFORMATION
            // -------------------------------------------------


            if (bed.getWard() != null) {

                System.out.println(
                        "Ward Name           : "
                                + bed.getWard()
                                        .getWardName());

                System.out.println(
                        "Ward Type           : "
                                + bed.getWard()
                                        .getWardType());
            }


            // -------------------------------------------------
            // CONFIRM BED
            // -------------------------------------------------

            System.out.print(
                    "Admit patient to "
                            + bed.getBedId()
                            + "? (Y/N) : ");

            String confirmation =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();


            if (!confirmation.equals("Y")) {

                System.out.println();
                System.out.println(
                        "Admission cancelled.");

                return;
            }


            // -------------------------------------------------
            // CREATE ADMISSION
            // -------------------------------------------------

            Admission admission =
                    admissionService.admitPatient(
                            patientId,
                            wardType,
                            bed.getBedId());


            System.out.println();
            System.out.println(
                    "Admission successful.");

            System.out.println(
                    "Admission ID : "
                            + admission
                                    .getAdmissionId());

            System.out.println(
                    "Bed Assigned : "
                            + admission
                                    .getBed()
                                    .getBedId());

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    e.getMessage());

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unexpected error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // VIEW ADMISSION
    // =========================================================

    /**
     * Displays a single admission.
     */
    private void viewAdmission() {

        try {

            System.out.println();
            System.out.print(
                    "Enter Admission ID : ");

            String admissionId =
                    scanner.nextLine().trim();


            Admission admission =
                    admissionService
                            .getAdmissionById(
                                    admissionId);


            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "          ADMISSION DETAILS");
            System.out.println(
                    "========================================");

            System.out.println(
                    "Admission ID : "
                            + admission
                                    .getAdmissionId());

            System.out.println(
                    "Patient ID   : "
                            + admission
                                    .getPatient()
                                    .getPatientId());

            System.out.println(
                    "Patient Name : "
                            + admission
                                    .getPatient()
                                    .getPatientName());

            System.out.println(
                    "Bed ID       : "
                            + admission
                                    .getBed()
                                    .getBedId());

            System.out.println(
                    "Ward         : "
                            + admission
                                    .getBed()
                                    .getWard()
                                    .getWardName());

            System.out.println(
                    "Ward Type    : "
                            + admission
                                    .getBed()
                                    .getWard()
                                    .getWardType());

            System.out.println(
                    "Admission Date : "
                            + admission
                                    .getAdmissionDate());

            System.out.println(
                    "Discharge Date : "
                            + admission
                                    .getDischargeDate());

            System.out.println(
                    "Status         : "
                            + admission
                                    .getStatus());

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    e.getMessage());

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unexpected error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // LIST ADMISSIONS
    // =========================================================

    /**
     * Displays all active admissions.
     */
    /**
     * Displays all active admissions in table format.
     */
    private void listAdmissions() {

        try {

            List<Admission> admissions =
                    admissionService.getAllAdmissions();

            if (admissions == null
                    || admissions.isEmpty()) {

                System.out.println();
                System.out.println(
                        "No active admissions found.");

                return;
            }

            System.out.println();
            System.out.println(
                    "====================================================================================================================");

            System.out.printf(
                    "%-15s %-15s %-20s %-15s %-18s %-20s %-12s%n",
                    "Admission ID",
                    "Patient ID",
                    "Patient Name",
                    "Bed ID",
                    "Ward",
                    "Admission Date",
                    "Status"
            );

            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------------");

            for (Admission admission : admissions) {

                String admissionId =
                        admission.getAdmissionId();

                String patientId =
                        admission.getPatient()
                                .getPatientId();

                String patientName =
                        admission.getPatient()
                                .getPatientName();

                String bedId =
                        admission.getBed()
                                .getBedId();

                String wardName =
                        admission.getBed()
                                .getWard()
                                .getWardName();

                String admissionDate =
                        admission.getAdmissionDate()
                                .toString();

                String status =
                        admission.getStatus()
                                .toString();

                System.out.printf(
                        "%-15s %-15s %-20s %-15s %-18s %-20s %-12s%n",
                        admissionId,
                        patientId,
                        patientName,
                        bedId,
                        wardName,
                        admissionDate,
                        status
                );
            }

            System.out.println(
                    "==========================================================================================================");

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unexpected error : "
                            + e.getMessage());
        }
    }

    // =========================================================
    // DISCHARGE PATIENT
    // =========================================================

    /**
     * Discharges a patient from an active admission.
     */
    private void dischargePatient() {

        try {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "        DISCHARGE PATIENT");
            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Admission ID : ");

            String admissionId =
                    scanner.nextLine().trim();


            admissionService
                    .dischargeAdmission(
                            admissionId);


            System.out.println();
            System.out.println(
                    "Patient discharged successfully.");

            System.out.println(
                    "Bed is now available.");

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    e.getMessage());

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unexpected error : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // WARD TYPE CONVERSION
    // =========================================================

    /**
     * Converts the user's menu selection
     * into a WardType enum.
     */
    private WardType getWardType(
            String choice)
            throws ValidationException {

        switch (choice) {

            case "1":
                return WardType.GENERAL;

            case "2":
                return WardType.ICU;

            case "3":
                return WardType.PEDIATRIC;

            case "4":
                return WardType.PRIVATE;

            default:
                throw new ValidationException(
                        "Invalid ward type.");
        }
    }
}