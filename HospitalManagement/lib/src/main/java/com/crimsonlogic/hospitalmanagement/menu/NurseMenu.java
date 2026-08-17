package com.crimsonlogic.hospitalmanagement.menu;

import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.model.UserAccount;

public class NurseMenu {

    private static final Scanner sc =
            new Scanner(System.in);

    public void showMenu(UserAccount user) {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              NURSE MENU");
            System.out.println("========================================");

            System.out.println("VIEW APPOINTMENTS");
            System.out.println("VIEW PATIENT");
            System.out.println("LIST PATIENTS");
            System.out.println("VIEW PRESCRIPTIONS");
            System.out.println("ADMIT PATIENT");
            System.out.println("DISCHARGE PATIENT");
            System.out.println("MANAGE BEDS");
            System.out.println("LOGOUT");

            System.out.println("========================================");
            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();

            try {

                switch (choice) {

                    case "VIEW APPOINTMENTS":

                        // Existing Appointment Service / DAO
                        break;


                    case "VIEW PATIENT":

                        // Existing Patient Service / DAO
                        break;


                    case "LIST PATIENTS":

                        // Existing Patient Service / DAO
                        break;


                    case "VIEW PRESCRIPTIONS":

                        // Existing Prescription Service / DAO
                        break;


                    case "ADMIT PATIENT":

                        // Existing Admission Service / DAO
                        break;


                    case "DISCHARGE PATIENT":

                        // Existing Admission Service / DAO
                        break;


                    case "MANAGE BEDS":

                        // Existing Bed Service / DAO
                        break;


                    case "LOGOUT":

                        System.out.println();
                        System.out.println(
                                "Nurse logged out successfully.");

                        return;


                    default:

                        System.out.println();
                        System.out.println(
                                "Invalid choice. Please try again.");
                }

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Operation failed : "
                                + e.getMessage());
            }
        }
    }
}