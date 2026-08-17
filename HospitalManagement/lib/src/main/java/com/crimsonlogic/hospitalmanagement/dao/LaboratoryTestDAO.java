package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.services.*;

public class LaboratoryTestDAO {

    private static final Scanner sc =
            new Scanner(System.in);

    private static final LaboratoryTestServiceImpl ServiceImpl =
            new LaboratoryTestServiceImpl();


    // =========================================================
    // MAIN MENU
    // =========================================================

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================");

            System.out.println(
                    "       LABORATORY TEST MANAGEMENT");

            System.out.println(
                    "========================================");

            System.out.println(
                    "1. Add Laboratory Test");

            System.out.println(
                    "2. View Laboratory Test By ID");

            System.out.println(
                    "3. View All Laboratory Tests");

            System.out.println(
                    "4. Update Laboratory Test");

            System.out.println(
                    "5. Delete Laboratory Test");

            System.out.println(
                    "0. Back");

            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");


            String choice =
                    sc.nextLine()
                            .trim();


            try {

                switch (choice) {

                    case "1":

                        addTest();

                        break;


                    case "2":

                        viewTestById();

                        break;


                    case "3":

                        viewAllTests();

                        break;


                    case "4":

                        updateTest();

                        break;


                    case "5":

                        deleteTest();

                        break;


                    case "0":

                        return;


                    default:

                        System.out.println(
                                "Invalid choice. "
                                + "Please enter 0 to 5.");
                }

            } catch (ValidationException e) {

                System.out.println();
                System.out.println(
                        "Validation Error : "
                                + e.getMessage());

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Error : "
                                + e.getMessage());
            }
        }
    }


    // =========================================================
    // ADD TEST
    // =========================================================

    private static void addTest()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== ADD LABORATORY TEST ==========");


        String testName =
                readRequiredText(
                        "Test Name : ",
                        "Test name");


        String description =
                readRequiredText(
                        "Test Description : ",
                        "Test description");


        double charge =
                readPositiveDouble(
                        "Test Charge : ");


        String testType =
                readTestType();


        LaboratoryTest test =
                new LaboratoryTest();


        test.setTestName(
                testName);

        test.setTestDescription(
                description);

        test.setTestCharge(
                charge);

        test.setTestType(
                testType);


        ServiceImpl.addTest(test);


        System.out.println();
        System.out.println(
                "Laboratory test added successfully.");

        System.out.println(
                "Generated Test ID : "
                        + test.getTestId());
    }


    // =========================================================
    // VIEW TEST BY ID
    // =========================================================

    private static void viewTestById()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== VIEW LABORATORY TEST ==========");


        String testId =
                readRequiredText(
                        "Test ID : ",
                        "Test ID");


        LaboratoryTest test =
                ServiceImpl.getTestById(
                        testId);


        System.out.println();
        System.out.println(
                "----------------------------------------");

        System.out.println(
                "Test ID          : "
                        + test.getTestId());

        System.out.println(
                "Test Name        : "
                        + test.getTestName());

        System.out.println(
                "Description      : "
                        + test.getTestDescription());

        System.out.println(
                "Test Charge      : ₹"
                        + String.format(
                                "%.2f",
                                test.getTestCharge()));

        System.out.println(
                "Test Type        : "
                        + test.getTestType());

        System.out.println(
                "Active           : "
                        + test.isActive());

        System.out.println(
                "----------------------------------------");
    }


    // =========================================================
    // VIEW ALL TESTS
    // =========================================================

    private static void viewAllTests() {

        System.out.println();
        System.out.println(
                "========== ALL LABORATORY TESTS ==========");


        List<LaboratoryTest> tests =
                ServiceImpl.getAllTests();


        if (tests == null
                || tests.isEmpty()) {

            System.out.println(
                    "No laboratory tests found.");

            return;
        }


        System.out.println(
                "--------------------------------------------------------------------------");

        System.out.printf(
                "%-15s %-25s %-12s %-12s %-10s%n",
                "TEST ID",
                "TEST NAME",
                "TYPE",
                "CHARGE",
                "STATUS");

        System.out.println(
                "--------------------------------------------------------------------------");


        for (LaboratoryTest test : tests) {

            System.out.printf(
                    "%-15s %-25s %-12s ₹%-11.2f %-10s%n",

                    test.getTestId(),

                    truncate(
                            test.getTestName(),
                            25),

                    test.getTestType(),

                    test.getTestCharge(),

                    test.isActive()
                            ? "ACTIVE"
                            : "INACTIVE");
        }


        System.out.println(
                "--------------------------------------------------------------------------");
    }


    // =========================================================
    // UPDATE TEST
    // =========================================================

    private static void updateTest()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== UPDATE LABORATORY TEST ==========");


        String testId =
                readRequiredText(
                        "Test ID : ",
                        "Test ID");


        /*
         * First check whether the test exists.
         */

        LaboratoryTest existing =
                ServiceImpl.getTestById(
                        testId);


        System.out.println();
        System.out.println(
                "Current Test Details :");

        System.out.println(
                existing);


        System.out.println();
        System.out.println(
                "Enter New Details");


        String testName =
                readRequiredText(
                        "Test Name : ",
                        "Test name");


        String description =
                readRequiredText(
                        "Test Description : ",
                        "Test description");


        double charge =
                readPositiveDouble(
                        "Test Charge : ");


        String testType =
                readTestType();


        LaboratoryTest test =
                new LaboratoryTest();


        test.setTestId(
                testId);

        test.setTestName(
                testName);

        test.setTestDescription(
                description);

        test.setTestCharge(
                charge);

        test.setTestType(
                testType);

        test.setActive(
                existing.isActive());


        ServiceImpl.updateTest(
                test);


        System.out.println();
        System.out.println(
                "Laboratory test updated successfully.");
    }


    // =========================================================
    // DELETE TEST
    // =========================================================

    private static void deleteTest()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== DELETE LABORATORY TEST ==========");


        String testId =
                readRequiredText(
                        "Test ID : ",
                        "Test ID");


        /*
         * Verify that the test exists before deleting.
         */

        LaboratoryTest test =
                ServiceImpl.getTestById(
                        testId);


        System.out.println();
        System.out.println(
                "Test selected : "
                        + test.getTestName());


        System.out.print(
                "Are you sure you want to delete it? (YES/NO) : ");


        String confirmation =
                sc.nextLine()
                        .trim()
                        .toUpperCase();


        if (!confirmation.equals("YES")) {

            System.out.println(
                    "Delete operation cancelled.");

            return;
        }


        ServiceImpl.deactivateTest(
                testId);


        System.out.println();
        System.out.println(
                "Laboratory test deleted successfully.");
    }


    // =========================================================
    // READ REQUIRED TEXT
    // =========================================================

    private static String readRequiredText(
            String message,
            String fieldName)
            throws ValidationException {

        System.out.print(message);

        String value =
                sc.nextLine()
                        .trim();


        if (value.isEmpty()) {

            throw new ValidationException(
                    fieldName
                            + " cannot be empty");
        }


        return value;
    }


    // =========================================================
    // READ POSITIVE DOUBLE
    // =========================================================

    private static double readPositiveDouble(
            String message)
            throws ValidationException {

        System.out.print(message);

        String input =
                sc.nextLine()
                        .trim();


        if (input.isEmpty()) {

            throw new ValidationException(
                    "Test charge cannot be empty");
        }


        try {

            double value =
                    Double.parseDouble(input);


            if (Double.isNaN(value)
                    || Double.isInfinite(value)) {

                throw new ValidationException(
                        "Invalid test charge");
            }


            if (value <= 0) {

                throw new ValidationException(
                        "Test charge must be greater than 0");
            }


            if (value > 1000000) {

                throw new ValidationException(
                        "Test charge is too high");
            }


            return value;

        } catch (NumberFormatException e) {

            throw new ValidationException(
                    "Test charge must be a valid number");
        }
    }


    // =========================================================
    // READ TEST TYPE
    // =========================================================

    private static String readTestType()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "Test Types:");

        System.out.println(
                "1. BLOOD");

        System.out.println(
                "2. XRAY");

        System.out.println(
                "3. MRI");

        System.out.println(
                "4. URINE");

        System.out.println(
                "5. CT");


        System.out.print(
                "Enter Test Type : ");


        String choice =
                sc.nextLine()
                        .trim();


        switch (choice) {

            case "1":
                return "BLOOD";

            case "2":
                return "XRAY";

            case "3":
                return "MRI";

            case "4":
                return "URINE";

            case "5":
                return "CT";

            default:

                throw new ValidationException(
                        "Invalid laboratory test type. "
                        + "Choose BLOOD, XRAY, MRI, URINE or CT.");
        }
    }


    // =========================================================
    // TRUNCATE LONG TEXT FOR TABLE
    // =========================================================

    private static String truncate(
            String value,
            int maxLength) {

        if (value == null) {

            return "";
        }


        if (value.length() <= maxLength) {

            return value;
        }


        return value.substring(
                0,
                maxLength - 3)
                + "...";
    }
}