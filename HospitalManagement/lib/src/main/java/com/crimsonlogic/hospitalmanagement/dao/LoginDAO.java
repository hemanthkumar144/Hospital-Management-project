package com.crimsonlogic.hospitalmanagement.dao;

import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

/**
 * Provides the login menu for the Hospital ERP system.
 *
 * <p>The user selects the role they want to log in as,
 * enters their credentials, and is authenticated against
 * the database.</p>
 */
public class LoginDAO {

    /**
     * Displays the login menu and authenticates the user.
     *

     * @return authenticated user account, or null when the user exits
     */
    public static UserAccount showMenu() {
    	Scanner sc=new Scanner(System.in);

        AuthenticationServiceImpl authenticationServiceImpl =
                new AuthenticationServiceImpl();

        while (true) {

            System.out.println();
            System.out.println("====================================");
            System.out.println("        HOSPITAL ERP SYSTEM");
            System.out.println("====================================");
            System.out.println("ADMIN");
            System.out.println("DOCTOR");
            System.out.println("NURSE");
            System.out.println("PATIENT");
            System.out.println("EXIT");
            System.out.println("====================================");

            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                      .trim()
                      .toUpperCase();

            String expectedRole;

            switch (choice) {

                case "ADMIN":
                    expectedRole = "ADMIN";
                    break;

                case "DOCTOR":
                    expectedRole = "DOCTOR";
                    break;

                case "NURSE":
                    expectedRole = "NURSE";
                    break;

                case "PATIENT":
                    expectedRole = "PATIENT";
                    break;

                case "EXIT":
                    System.out.println(
                            "Thank you for using Hospital ERP.");

                    return null;

                default:
                    System.out.println(
                            "Invalid choice. Please enter "
                            + "ADMIN, DOCTOR, NURSE, PATIENT or EXIT.");

                    continue;
            }

            System.out.println();
            System.out.println(
                    "========== "
                    + expectedRole
                    + " LOGIN ==========");

            System.out.print("Username : ");
            String username = sc.nextLine().trim();

            System.out.print("Password : ");
            String password = sc.nextLine();

            try {

                UserAccount user =
                        authenticationServiceImpl.login(
                                username,
                                password,
                                expectedRole);

                System.out.println();
                System.out.println(
                        "Login Successful!");

                System.out.println(
                        "Welcome, "
                        + user.getUsername()
                        + "!");

                return user;

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Login Failed : "
                        + e.getMessage());

                System.out.println(
                        "Please try again.");
            }
        }
    }
}