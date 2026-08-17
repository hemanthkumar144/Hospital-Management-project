package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.exceptions.WardNotFoundException;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.services.*;


/**
 * DAO/menu class responsible for user interaction
 * with Ward operations.
 *
 * All user input is validated field by field before
 * the Ward is sent to the ServiceImpl layer.
 */
public class WardDAO {

    private static Scanner sc =
            new Scanner(System.in);

    private static WardServiceImpl ServiceImpl =
            new WardServiceImpl();


    /**
     * Displays the Ward management menu.
     *
     * This method is parameterless as required.
     */
    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");
            System.out.println(
                    "          WARD MANAGEMENT");
            System.out.println(
                    "========================================");
            System.out.println(
                    "ADD WARD");
            System.out.println(
                    "VIEW WARD");
            System.out.println(
                    "LIST WARDS");
            System.out.println(
                    "UPDATE WARD");
            System.out.println(
                    "DEACTIVATE WARD");
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

                case "ADD WARD":
                    addWard();
                    break;

                case "VIEW WARD":
                    viewWard();
                    break;

                case "LIST WARDS":
                    listWards();
                    break;

                case "UPDATE WARD":
                    updateWard();
                    break;

                case "DEACTIVATE WARD":
                    deleteWard();
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
    // ADD WARD
    // =========================================================

    private static void addWard() {

        String wardName =
                readWardName();

        WardType wardType =
                readWardType();

        double bedCharge =
                readBedCharge();


        Ward ward =
                new Ward(
                        null,
                        wardName,
                        wardType,
                        bedCharge,
                        true);


        try {

            ServiceImpl.addWard(ward);

            System.out.println();
            System.out.println(
                    "Ward added successfully.");

            System.out.println(
                    "Generated Ward ID : "
                            + ward.getWardId());

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // WARD NAME
    // =========================================================

    private static String readWardName() {

        while (true) {

            System.out.print(
                    "Ward Name : ");

            String wardName =
                    sc.nextLine().trim();

            if (wardName.isEmpty()) {

                System.out.println(
                        "Ward name cannot be empty.");

                continue;
            }

            if (!wardName.matches(
                    "^[A-Za-z][A-Za-z ]{1,49}$")) {

                System.out.println(
                        "Ward name must contain only "
                        + "letters and spaces.");

                continue;
            }

            if (hasThreeConsecutiveSameCharacters(
                    wardName)) {

                System.out.println(
                        "The same character cannot be "
                        + "repeated more than 2 times "
                        + "consecutively.");

                continue;
            }

            return wardName;
        }
    }


    // =========================================================
    // WARD TYPE
    // =========================================================

    private static WardType readWardType() {

        while (true) {

            System.out.println();
            System.out.println("Available Ward Types:");

            WardType[] types = WardType.values();

            for (int i = 0; i < types.length; i++) {

                System.out.println(
                        (i + 1) + ". " + types[i]);
            }

            System.out.print(
                    "Enter Ward Type : ");

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Ward type cannot be empty.");

                continue;
            }

            try {

                int choice =
                        Integer.parseInt(input);

                if (choice < 1
                        || choice > types.length) {

                    System.out.println(
                            "Invalid ward type choice.");

                    continue;
                }

                return types[choice - 1];

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid "
                        + "ward type number.");
            }
        }
    }

    // =========================================================
    // BED CHARGE
    // =========================================================

    private static double readBedCharge() {

        while (true) {

            System.out.print(
                    "Bed Charge Per Day : ");

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Bed charge cannot be empty.");

                continue;
            }

            try {

                double charge =
                        Double.parseDouble(input);

                if (Double.isNaN(charge)
                        || Double.isInfinite(charge)) {

                    System.out.println(
                            "Invalid bed charge.");

                    continue;
                }

                if (charge <= 0) {

                    System.out.println(
                            "Bed charge must be "
                            + "greater than 0.");

                    continue;
                }

                if (charge > 1000000) {

                    System.out.println(
                            "Bed charge is too high.");

                    continue;
                }

                return charge;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid bed charge.");

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }


    // =========================================================
    // VIEW WARD
    // =========================================================

    private static void viewWard() {

        while (true) {

            System.out.print(
                    "Enter Ward ID : ");

            String wardId =
                    sc.nextLine().trim();

            if (wardId.isEmpty()) {

                System.out.println(
                        "Ward ID cannot be empty.");

                continue;
            }

            try {

                Ward ward =
                        ServiceImpl.getWardById(
                                wardId);

                System.out.println();
                System.out.println(
                        "=============== WARD ===============");

                System.out.println(
                        "Ward ID       : "
                                + ward.getWardId());

                System.out.println(
                        "Ward Name     : "
                                + ward.getWardName());

                System.out.println(
                        "Ward Type     : "
                                + ward.getWardType());

                System.out.println(
                        "Bed Charge    : "
                                + ward.getBedCharge());

                System.out.println(
                        "Active        : "
                                + ward.isActive());

                System.out.println(
                        "====================================");

                return;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (WardNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the Ward ID again.");
            }
        }
    }


    // =========================================================
    // LIST WARDS
    // =========================================================

    private static void listWards() {

        List<Ward> wards =
                ServiceImpl.getAllWards();

        if (wards == null
                || wards.isEmpty()) {

            System.out.println();
            System.out.println(
                    "No active wards found.");

            return;
        }

        System.out.println();
        System.out.println(
                "============================== WARDS "
                + "==============================");

        System.out.printf(
                "%-15s %-25s %-20s %-15s%n",
                "WARD ID",
                "WARD NAME",
                "WARD TYPE",
                "BED CHARGE");

        System.out.println(
                "-----------------------------------------------------------------------");

        for (Ward ward : wards) {

            System.out.printf(
                    "%-15s %-25s %-20s %-15.2f%n",

                    ward.getWardId(),

                    ward.getWardName(),

                    ward.getWardType(),

                    ward.getBedCharge());
        }

        System.out.println(
                "=======================================================================");
    }


    // =========================================================
    // UPDATE WARD
    // =========================================================

    private static void updateWard() {

        Ward existing =
                readExistingWard();

        String wardName =
                readWardName();

        WardType wardType =
                readWardType();

        double bedCharge =
                readBedCharge();


        Ward ward =
                new Ward(
                        existing.getWardId(),
                        wardName,
                        wardType,
                        bedCharge,
                        existing.isActive());


        try {

            ServiceImpl.updateWard(ward);

            System.out.println();
            System.out.println(
                    "Ward updated successfully.");

        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Operation failed : "
                            + e.getMessage());

        } catch (WardNotFoundException e) {

            System.out.println(
                    "Operation failed : "
                            + e.getMessage());
        }
    }


    // =========================================================
    // GET EXISTING WARD
    // =========================================================

    private static Ward readExistingWard() {

        while (true) {

            System.out.print(
                    "Enter Ward ID : ");

            String wardId =
                    sc.nextLine().trim();

            if (wardId.isEmpty()) {

                System.out.println(
                        "Ward ID cannot be empty.");

                continue;
            }

            try {

                return ServiceImpl.getWardById(
                        wardId);

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (WardNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the Ward ID again.");
            }
        }
    }


    // =========================================================
    // DELETE / DEACTIVATE
    // =========================================================

    private static void deleteWard() {

        while (true) {

            System.out.print(
                    "Enter Ward ID : ");

            String wardId =
                    sc.nextLine().trim();

            if (wardId.isEmpty()) {

                System.out.println(
                        "Ward ID cannot be empty.");

                continue;
            }

            try {

                ServiceImpl.deleteWard(
                        wardId);

                System.out.println();
                System.out.println(
                        "Ward deactivated successfully.");

                return;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());

            } catch (WardNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the Ward ID again.");
            }
        }
    }


    // =========================================================
    // REPEATED CHARACTER VALIDATION
    // =========================================================

    private static boolean
    hasThreeConsecutiveSameCharacters(
            String value) {

        for (int i = 0;
             i < value.length() - 2;
             i++) {

            char first =
                    Character.toLowerCase(
                            value.charAt(i));

            char second =
                    Character.toLowerCase(
                            value.charAt(i + 1));

            char third =
                    Character.toLowerCase(
                            value.charAt(i + 2));

            if (first == second
                    && second == third) {

                return true;
            }
        }

        return false;
    }
}