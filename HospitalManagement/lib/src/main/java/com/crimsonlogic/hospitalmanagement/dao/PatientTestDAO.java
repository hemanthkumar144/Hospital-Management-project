package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.enums.TestStatus;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;
import com.crimsonlogic.hospitalmanagement.services.*;

public class PatientTestDAO {

    public static void showMenu() {

        Scanner sc = new Scanner(System.in);

        PatientTestServiceImpl patientTestServiceImpl =
                new PatientTestServiceImpl();

        LaboratoryTestServiceImpl laboratoryTestServiceImpl =
                new LaboratoryTestServiceImpl();

        while (true) {

            System.out.println(
                    "\n========== PATIENT TEST MANAGEMENT ==========");

            System.out.println("1. Perform Laboratory Test");
            System.out.println("2. View Patient Test By ID");
            System.out.println("3. View Tests By Patient ID");
            System.out.println("4. View All Patient Tests");
            System.out.println("5. Update Test Status");
            System.out.println("6. Back");

            System.out.print("Enter Choice : ");

            int choice;

            try {

                choice = Integer.parseInt(
                        sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Invalid Choice");
                continue;
            }


            switch (choice) {


                // =================================================
                // 1. PERFORM LABORATORY TEST
                // =================================================

                case 1:

                    try {

                        System.out.print(
                                "Patient ID : ");

                        String patientId =
                                sc.nextLine().trim();


                        System.out.println(
                                "\n========== AVAILABLE LABORATORY TESTS ==========");


                        List<LaboratoryTest> tests =
                                laboratoryTestServiceImpl
                                        .getAllTests();


                        if (tests.isEmpty()) {

                            System.out.println(
                                    "No Laboratory Tests Found");

                            break;
                        }


                        System.out.printf(
                                "%-12s %-20s %-15s %-12s%n",
                                "TEST ID",
                                "TEST NAME",
                                "TEST TYPE",
                                "CHARGE");

                        System.out.println(
                                "------------------------------------------------------------");


                        for (LaboratoryTest test : tests) {

                            System.out.printf(
                                    "%-12s %-20s %-15s ₹%-10.2f%n",
                                    test.getTestId(),
                                    test.getTestName(),
                                    test.getTestType(),
                                    test.getTestCharge());
                        }


                        System.out.println(
                                "------------------------------------------------------------");


                        System.out.print(
                                "Enter Test ID : ");

                        String testId =
                                sc.nextLine().trim();


                        LaboratoryTest selectedTest =
                                laboratoryTestServiceImpl
                                        .getTestById(testId);


                        System.out.println();

                        System.out.println(
                                "Test Selected : "
                                        + selectedTest.getTestName());

                        System.out.println(
                                "Test Type     : "
                                        + selectedTest.getTestType());

                        System.out.println(
                                "Charge        : ₹"
                                        + selectedTest.getTestCharge());


                        // =================================================
                        // TAKE TEST-SPECIFIC INPUT
                        // THESE VALUES ARE NOT STORED
                        // =================================================

                        String testType =
                                selectedTest.getTestType();


                        if (testType.equalsIgnoreCase("BLOOD")) {

                            System.out.println();
                            System.out.println(
                                    "---------- BLOOD TEST ----------");

                            System.out.print(
                                    "Hemoglobin (g/dL) : ");

                            String hemoglobin =
                                    sc.nextLine().trim();

                            System.out.print(
                                    "WBC (/µL) : ");

                            String wbc =
                                    sc.nextLine().trim();

                            System.out.print(
                                    "Platelets (/µL) : ");

                            String platelets =
                                    sc.nextLine().trim();


                            System.out.println();
                            System.out.println(
                                    "Blood Test Values Entered");

                            System.out.println(
                                    "Hemoglobin : "
                                            + hemoglobin
                                            + " g/dL");

                            System.out.println(
                                    "WBC        : "
                                            + wbc
                                            + " /µL");

                            System.out.println(
                                    "Platelets  : "
                                            + platelets
                                            + " /µL");


                        } else if (
                                testType.equalsIgnoreCase("X-RAY")) {

                            System.out.println();
                            System.out.println(
                                    "---------- X-RAY TEST ----------");

                            System.out.print(
                                    "Body Part : ");

                            String bodyPart =
                                    sc.nextLine().trim();

                            System.out.print(
                                    "Finding : ");

                            String finding =
                                    sc.nextLine().trim();


                            System.out.println();
                            System.out.println(
                                    "X-Ray Details Entered");

                            System.out.println(
                                    "Body Part : "
                                            + bodyPart);

                            System.out.println(
                                    "Finding   : "
                                            + finding);


                        } else if (
                                testType.equalsIgnoreCase("MRI")) {

                            System.out.println();
                            System.out.println(
                                    "---------- MRI TEST ----------");

                            System.out.print(
                                    "Body Part : ");

                            String bodyPart =
                                    sc.nextLine().trim();

                            System.out.print(
                                    "Finding : ");

                            String finding =
                                    sc.nextLine().trim();

                            System.out.print(
                                    "Remarks : ");

                            String remarks =
                                    sc.nextLine().trim();


                            System.out.println();
                            System.out.println(
                                    "MRI Details Entered");

                            System.out.println(
                                    "Body Part : "
                                            + bodyPart);

                            System.out.println(
                                    "Finding   : "
                                            + finding);

                            System.out.println(
                                    "Remarks   : "
                                            + remarks);


                        } else {

                            System.out.println();

                            System.out.print(
                                    "Enter Test Observation : ");

                            String observation =
                                    sc.nextLine().trim();


                            System.out.println(
                                    "Observation : "
                                            + observation);
                        }


                        // =================================================
                        // CONFIRM TEST
                        // =================================================

                        System.out.println();

                        System.out.print(
                                "Perform this test? (Y/N) : ");

                        String confirm =
                                sc.nextLine().trim();


                        if (!confirm.equalsIgnoreCase("Y")) {

                            System.out.println(
                                    "Test Cancelled");

                            break;
                        }


                        // =================================================
                        // SAVE PATIENT TEST
                        //
                        // Test input values above are NOT passed
                        // to the ServiceImpl and are NOT stored.
                        // =================================================

                        PatientTest patientTest =
                                patientTestServiceImpl
                                        .addPatientTest(
                                                patientId,
                                                testId);


                        System.out.println();

                        System.out.println(
                                "Laboratory Test Completed Successfully");

                        System.out.println(
                                "Patient Test ID : "
                                        + patientTest
                                                .getPatientTestId());

                        System.out.println(
                                "Patient ID      : "
                                        + patientTest
                                                .getPatientId());

                        System.out.println(
                                "Test ID         : "
                                        + patientTest
                                                .getTestId());

                        System.out.println(
                                "Status          : "
                                        + patientTest
                                                .getStatus());

                        System.out.println(
                                "Charge          : ₹"
                                        + patientTest
                                                .getCharge());


                    } catch (Exception e) {

                        System.out.println(
                                "Error : "
                                        + e.getMessage());
                    }

                    break;


                // =================================================
                // 2. VIEW PATIENT TEST BY ID
                // =================================================

                case 2:

                    try {

                        System.out.print(
                                "Enter Patient Test ID : ");

                        String patientTestId =
                                sc.nextLine().trim();


                        PatientTest patientTest =
                                patientTestServiceImpl
                                        .getPatientTestById(
                                                patientTestId);


                        System.out.println(
                                "\n========== PATIENT TEST DETAILS ==========");

                        System.out.println(
                                "Patient Test ID : "
                                        + patientTest
                                                .getPatientTestId());

                        System.out.println(
                                "Patient ID      : "
                                        + patientTest
                                                .getPatientId());

                        System.out.println(
                                "Test ID         : "
                                        + patientTest
                                                .getTestId());

                        System.out.println(
                                "Test Date       : "
                                        + patientTest
                                                .getTestDate());

                        System.out.println(
                                "Status          : "
                                        + patientTest
                                                .getStatus());

                        System.out.println(
                                "Charge          : ₹"
                                        + patientTest
                                                .getCharge());


                    } catch (Exception e) {

                        System.out.println(
                                "Error : "
                                        + e.getMessage());
                    }

                    break;


                // =================================================
                // 3. VIEW TESTS BY PATIENT ID
                // =================================================

                case 3:

                    try {

                        System.out.print(
                                "Enter Patient ID : ");

                        String patientId =
                                sc.nextLine().trim();


                        List<PatientTest> tests =
                                patientTestServiceImpl
                                        .getPatientTestsByPatientId(
                                                patientId);


                        if (tests.isEmpty()) {

                            System.out.println(
                                    "No Tests Found For This Patient");

                            break;
                        }


                        System.out.println(
                                "\n========== PATIENT TESTS ==========");


                        System.out.printf(
                                "%-15s %-15s %-15s %-12s %-12s%n",
                                "PATIENT TEST ID",
                                "PATIENT ID",
                                "TEST ID",
                                "STATUS",
                                "CHARGE");


                        System.out.println(
                                "----------------------------------------------------------------");


                        for (PatientTest test : tests) {

                            System.out.printf(
                                    "%-15s %-15s %-15s %-12s ₹%-10.2f%n",
                                    test.getPatientTestId(),
                                    test.getPatientId(),
                                    test.getTestId(),
                                    test.getStatus(),
                                    test.getCharge());
                        }


                    } catch (Exception e) {

                        System.out.println(
                                "Error : "
                                        + e.getMessage());
                    }

                    break;


                // =================================================
                // 4. VIEW ALL PATIENT TESTS
                // =================================================

                case 4:

                    try {

                        List<PatientTest> tests =
                                patientTestServiceImpl
                                        .getAllPatientTests();


                        if (tests.isEmpty()) {

                            System.out.println(
                                    "No Patient Tests Found");

                            break;
                        }


                        System.out.println(
                                "\n========== ALL PATIENT TESTS ==========");


                        System.out.printf(
                                "%-15s %-15s %-15s %-12s %-12s%n",
                                "PATIENT TEST ID",
                                "PATIENT ID",
                                "TEST ID",
                                "STATUS",
                                "CHARGE");


                        System.out.println(
                                "----------------------------------------------------------------");


                        for (PatientTest test : tests) {

                            System.out.printf(
                                    "%-15s %-15s %-15s %-12s ₹%-10.2f%n",
                                    test.getPatientTestId(),
                                    test.getPatientId(),
                                    test.getTestId(),
                                    test.getStatus(),
                                    test.getCharge());
                        }


                    } catch (Exception e) {

                        System.out.println(
                                "Error : "
                                        + e.getMessage());
                    }

                    break;


                // =================================================
                // 5. UPDATE TEST STATUS
                // =================================================

                case 5:

                    try {

                        System.out.print(
                                "Patient Test ID : ");

                        String patientTestId =
                                sc.nextLine().trim();


                        System.out.println();
                        System.out.println(
                                "1. COMPLETED");

                        System.out.println(
                                "2. CANCELLED");


                        System.out.print(
                                "Enter Status : ");

                        String statusChoice =
                                sc.nextLine().trim();


                        TestStatus status;


                        if (statusChoice.equals("1")) {

                            status =
                                    TestStatus.COMPLETED;

                        } else if (
                                statusChoice.equals("2")) {

                            status =
                                    TestStatus.CANCELLED;

                        } else {

                            System.out.println(
                                    "Invalid Status");

                            break;
                        }


                        patientTestServiceImpl
                                .updateTestStatus(
                                        patientTestId,
                                        statusChoice);


                        System.out.println(
                                "Test Status Updated Successfully");


                    } catch (Exception e) {

                        System.out.println(
                                "Error : "
                                        + e.getMessage());
                    }

                    break;


                // =================================================
                // 6. BACK
                // =================================================

                case 6:

                    return;


                default:

                    System.out.println(
                            "Invalid Choice");
            }
        }
    }
}