package com.crimsonlogic.hospitalmanagement.menu;

import java.util.Scanner;


import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.AuthenticationServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.UserServiceImpl;

public class LoginRegisterMenu {

    private static final Scanner sc =
            new Scanner(System.in);

    private static final AuthenticationServiceImpl authenticationService =
            new AuthenticationServiceImpl();

    private static final UserServiceImpl userService =
            new UserServiceImpl();
  


    // =========================================================
    // LOGIN / REGISTER MENU
    // =========================================================

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");

            System.out.println(
                    "       HOSPITAL MANAGEMENT SYSTEM");

            System.out.println(
                    "========================================");

            System.out.println("1. LOGIN");
            System.out.println("2. REGISTER");
            System.out.println("3. EXIT");

            System.out.println(
                    "========================================");

            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                      .trim();

            switch (choice) {

                case "1":

                    UserAccount user = login();

                    if (user != null) {

                        redirectToRoleMenu(user);
                    }

                    break;


                case "2":

                    register();

                    break;


                case "3":

                    System.out.println();
                    System.out.println(
                            "Thank you for using "
                            + "Hospital Management System.");

                    return;


                default:

                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }


    // =========================================================
    // LOGIN
    // =========================================================

    private static UserAccount login() {

        System.out.println();
        System.out.println(
                "========== LOGIN ==========");


        // -----------------------------------------------------
        // ROLE FIRST
        // -----------------------------------------------------

        System.out.println();
        System.out.println("Select Role");

        System.out.println("1. ADMIN");
        System.out.println("2. DOCTOR");
        System.out.println("3. NURSE");
        System.out.println("4. PATIENT");

        System.out.print("Enter Role : ");

        String roleChoice =
                sc.nextLine()
                  .trim();


        String role =
                getLoginRole(roleChoice);


        if (role == null) {

            System.out.println(
                    "Invalid role.");

            return null;
        }


        // -----------------------------------------------------
        // USERNAME
        // -----------------------------------------------------

        System.out.println();

        System.out.print("Username : ");

        String username =
                sc.nextLine()
                  .trim();


        if (username.isEmpty()) {

            System.out.println(
                    "Username cannot be empty.");

            return null;
        }


        // -----------------------------------------------------
        // PASSWORD
        // -----------------------------------------------------

        System.out.print("Password : ");

        String password =
                sc.nextLine();


        if (password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty.");

            return null;
        }


        // -----------------------------------------------------
        // AUTHENTICATION
        // -----------------------------------------------------

        try {

            UserAccount user =
                    authenticationService.login(
                            username,
                            password,
                            role);


            System.out.println();
            System.out.println(
                    "Login Successful.");

            System.out.println(
                    "Welcome, "
                    + user.getUsername()
                    + "!");

            System.out.println(
                    "Role : "
                    + user.getRole());


            return user;


        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Login Failed : "
                    + e.getMessage());

            return null;
        }
    }


    // =========================================================
    // REDIRECT USER ACCORDING TO ROLE
    // =========================================================

    private static void redirectToRoleMenu(UserAccount user) {

        String role =
                user.getRole()
                    .trim()
                    .toUpperCase();

        switch (role) {

            case "ADMIN":

                AdminMenu.showMenu();

                break;


            case "DOCTOR":

                System.out.print("Enter Doctor ID : ");
                String doctorId =
                        sc.nextLine().trim();

                DoctorMenu doctorMenu =
                        new DoctorMenu();

                doctorMenu.showMenu(
                        user,
                        doctorId);

                break;


            case "NURSE":

                NurseMenu nurseMenu =
                        new NurseMenu();

                nurseMenu.showMenu(user);

                break;


            case "PATIENT":

                PatientMenu patientMenu =
                        new PatientMenu();

                patientMenu.showMenu(user);

                break;


            default:

                System.out.println(
                        "Invalid user role.");
        }
    }

    // =========================================================
    // PATIENT REGISTRATION ONLY
    // =========================================================

    private static void register() {

        System.out.println();
        System.out.println(
                "========== PATIENT REGISTRATION ==========");


        System.out.print("Username : ");

        String username =
                sc.nextLine()
                  .trim();


        if (username.isEmpty()) {

            System.out.println(
                    "Username cannot be empty.");

            return;
        }


        System.out.print("Password : ");

        String password =
                sc.nextLine();


        if (password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty.");

            return;
        }


        System.out.print("Confirm Password : ");

        String confirmPassword =
                sc.nextLine();


        if (!password.equals(confirmPassword)) {

            System.out.println(
                    "Passwords do not match.");

            return;
        }


        UserAccount user =
                new UserAccount();


        user.setUsername(username);

        user.setPasswordHash(password);

        // Registration is ONLY for patients
        user.setRole("PATIENT");


        try {

            userService.addUser(user);


            System.out.println();
            System.out.println(
                    "Patient Registration Successful.");

            System.out.println(
                    "Generated User ID : "
                    + user.getUserId());

            System.out.println(
                    "Username : "
                    + user.getUsername());

            System.out.println(
                    "Role : "
                    + user.getRole());


        } catch (ValidationException e) {

            System.out.println();
            System.out.println(
                    "Registration Failed : "
                    + e.getMessage());
        }
    }


    // =========================================================
    // LOGIN ROLE
    // =========================================================

    private static String getLoginRole(
            String choice) {

        switch (choice) {

            case "1":
                return "ADMIN";

            case "2":
                return "DOCTOR";

            case "3":
                return "NURSE";

            case "4":
                return "PATIENT";

            default:
                return null;
        }
    }
}