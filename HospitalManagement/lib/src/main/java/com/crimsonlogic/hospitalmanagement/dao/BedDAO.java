package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.BedIdNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.services.*;

/**
 * DAO/menu class responsible for user interaction
 * with Bed operations.
 *
 * Input is validated field-by-field before being
 * passed to the ServiceImpl layer.
 */
public class BedDAO {

    private static Scanner sc =
            new Scanner(System.in);

    private static BedServiceImpl ServiceImpl =
            new BedServiceImpl();


    /**
     * Displays the Bed management menu.

     */
    public static void showMenu()  {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "           BED MANAGEMENT");
            System.out.println(
                    "========================================");
            System.out.println(
                    "ADD BED");
            System.out.println(
                    "VIEW BED");
            System.out.println(
                    "LIST BEDS");
            System.out.println(
                    "UPDATE BED");
            System.out.println(
                    "DEACTIVATE BED");
            System.out.println(
                    "LOGOUT");
            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();

            switch (choice) {

                case "ADD BED":
                    addBed();
                    break;

                case "VIEW BED":
                    viewBed();
                    break;

                case "LIST BEDS":
                    listBeds();
                    break;

                case "UPDATE BED":
                    updateBed();
                    break;

                case "DEACTIVATE BED":
                    deleteBed();
                    break;

                case "LOGOUT":
                    return;

                default:

                    System.out.println(
                            "Invalid choice. "
                            + "Please try again.");
            }
        }
    }


    // =========================================================
    // ADD BED
    // =========================================================

    private static void addBed() {

        Ward ward =
                readWard();

        String availability =
                readAvailability();

        Patient patient = null;

        if ("OCCUPIED".equals(
                availability)) {

            patient =
                    readPatient();
        }


        Bed bed =
                new Bed(
                        null,
                        ward,
                        availability,
                        patient,
                        true);


        try {

            ServiceImpl.addBed(bed);

            System.out.println();
            System.out.println(
                    "Bed added successfully.");

            System.out.println(
                    "Generated Bed ID : "
                            + bed.getBedId());

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());

        } catch (RuntimeException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // READ WARD
    // =========================================================

    private static Ward readWard() {

        while (true) {

            System.out.print(
                    "Ward ID : ");

            String wardId =
                    sc.nextLine().trim();

            if (wardId.isEmpty()) {

                System.out.println(
                        "Ward ID cannot be empty.");

                continue;
            }

            try {

                /*
                 * We only need the ID here.
                 * BedServiceImpl will verify the Ward.
                 */
                Ward ward =
                        new Ward();

                ward.setWardId(
                        wardId);

                return ward;

            } catch (Exception e) {

                System.out.println(
                        "Invalid Ward ID.");
            }
        }
    }


    // =========================================================
    // AVAILABILITY
    // =========================================================

    private static String readAvailability() {

        while (true) {

            System.out.println();
            System.out.println(
                    "1. AVAILABLE");
            System.out.println(
                    "2. OCCUPIED");

            System.out.print(
                    "Enter Availability : ");

            String input =
                    sc.nextLine().trim();

            if (input.equals("1")) {

                return "AVAILABLE";
            }

            if (input.equals("2")) {

                return "OCCUPIED";
            }

            System.out.println(
                    "Invalid availability choice.");
        }
    }


    // =========================================================
    // READ PATIENT
    // =========================================================

    private static Patient readPatient() {

        while (true) {

            System.out.print(
                    "Patient ID : ");

            String patientId =
                    sc.nextLine().trim();

            if (patientId.isEmpty()) {

                System.out.println(
                        "Patient ID cannot be empty.");

                continue;
            }

            Patient patient =
                    new Patient();

            patient.setPatientId(
                    patientId);

            return patient;
        }
    }


    // =========================================================
    // VIEW BED
    // =========================================================

    private static void viewBed() {

        while (true) {

            System.out.print(
                    "Enter Bed ID : ");

            String bedId =
                    sc.nextLine().trim();

            if (bedId.isEmpty()) {

                System.out.println(
                        "Bed ID cannot be empty.");

                continue;
            }

            try {

                Bed bed =
                        ServiceImpl.getBedById(
                                bedId);

                displayBed(bed);

                return;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (BedIdNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the Bed ID again.");
            }
        }
    }


    // =========================================================
    // LIST BEDS
    // =========================================================

    private static void listBeds() {

        List<Bed> beds =
                ServiceImpl.getAllBeds();

        if (beds == null
                || beds.isEmpty()) {

            System.out.println();
            System.out.println(
                    "No active beds found.");

            return;
        }

        System.out.println();

        System.out.println(
                "============================== "
                + "BEDS ==============================");

        System.out.printf(
                "%-15s %-15s %-20s %-15s %-15s%n",
                "BED ID",
                "WARD ID",
                "WARD NAME",
                "AVAILABILITY",
                "PATIENT ID");

        System.out.println(
                "--------------------------------------------------------------------------");

        for (Bed bed : beds) {

            String wardId =
                    bed.getWard() == null
                            ? "-"
                            : bed.getWard()
                                    .getWardId();

            String wardName =
                    bed.getWard() == null
                            ? "-"
                            : bed.getWard()
                                    .getWardName();

            String patientId =
                    bed.getPatient() == null
                            ? "-"
                            : bed.getPatient()
                                    .getPatientId();

            System.out.printf(
                    "%-15s %-15s %-20s %-15s %-15s%n",

                    bed.getBedId(),

                    wardId,

                    wardName,

                    bed.getAvailability(),

                    patientId);
        }

        System.out.println(
                "==========================================================================");
    }


    // =========================================================
    // DISPLAY BED
    // =========================================================

    private static void displayBed(
            Bed bed) {

        System.out.println();

        System.out.println(
                "=============== BED ===============");

        System.out.println(
                "Bed ID       : "
                        + bed.getBedId());

        if (bed.getWard() != null) {

            System.out.println(
                    "Ward ID      : "
                            + bed.getWard()
                                    .getWardId());

            System.out.println(
                    "Ward Name    : "
                            + bed.getWard()
                                    .getWardName());

            System.out.println(
                    "Ward Type    : "
                            + bed.getWard()
                                    .getWardType());

            System.out.println(
                    "Bed Charge   : "
                            + bed.getWard()
                                    .getBedCharge());
        }

        System.out.println(
                "Availability : "
                        + bed.getAvailability());

        if (bed.getPatient() != null) {

            System.out.println(
                    "Patient ID   : "
                            + bed.getPatient()
                                    .getPatientId());

            System.out.println(
                    "Patient Name : "
                            + bed.getPatient()
                                    .getPatientName());
        } else {

            System.out.println(
                    "Patient      : None");
        }

        System.out.println(
                "Active       : "
                        + bed.isActive());

        System.out.println(
                "====================================");
    }


    // =========================================================
    // UPDATE BED
    // =========================================================

    private static void updateBed()  {

        Bed existing =
                readExistingBed();

        Ward ward =
                readWard();

        String availability =
                readAvailability();

        Patient patient = null;

        if ("OCCUPIED".equals(
                availability)) {

            patient =
                    readPatient();
        }


        Bed bed =
                new Bed(
                        existing.getBedId(),
                        ward,
                        availability,
                        patient,
                        existing.isActive());


        try {

            ServiceImpl.updateBed(bed);

            System.out.println();
            System.out.println(
                    "Bed updated successfully.");

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());

        } catch (BedIdNotFoundException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // READ EXISTING BED
    // =========================================================

    private static Bed readExistingBed() {

        while (true) {

            System.out.print(
                    "Enter Bed ID : ");

            String bedId =
                    sc.nextLine().trim();

            if (bedId.isEmpty()) {

                System.out.println(
                        "Bed ID cannot be empty.");

                continue;
            }

            try {

                return ServiceImpl.getBedById(
                        bedId);

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (BedIdNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the Bed ID again.");
            }
        }
    }


    // =========================================================
    // DELETE BED
    // =========================================================

    private static void deleteBed() {

        while (true) {

            System.out.print(
                    "Enter Bed ID : ");

            String bedId =
                    sc.nextLine().trim();

            if (bedId.isEmpty()) {

                System.out.println(
                        "Bed ID cannot be empty.");

                continue;
            }

            try {

                ServiceImpl.deleteBed(
                        bedId);

                System.out.println();
                System.out.println(
                        "Bed deactivated successfully.");

                return;

            } catch (ValidationException e) {

                System.out.println(
                        "Operation failed : "
                                + e.getMessage());

            } catch (BedIdNotFoundException e) {

                System.out.println(
                        "Operation failed : "
                                + e.getMessage());
            }
        }
    }
}