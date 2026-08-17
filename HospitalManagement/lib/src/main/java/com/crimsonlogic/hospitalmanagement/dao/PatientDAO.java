package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Address;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

public class PatientDAO {

    private static final Scanner sc =
            new Scanner(System.in);

    private static final PatientServiceImpl ServiceImpl =
            new PatientServiceImpl();


    // =========================================================
    // PATIENT MENU
    // =========================================================

    public static void showMenu()
            throws ValidationException {

        while (true) {

            System.out.println();
            System.out.println(
                    "========== PATIENT MANAGEMENT ==========");

            System.out.println("ADD PATIENT");
            System.out.println("VIEW PATIENT BY ID");
            System.out.println("VIEW ALL PATIENTS");
            System.out.println("UPDATE PATIENT");
            System.out.println("DELETE PATIENT");
            System.out.println("BACK");

            System.out.println(
                    "========================================");

            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();


            switch (choice) {

                case "ADD PATIENT":

                    addPatient(null);

                    break;


                case "VIEW PATIENT BY ID":

                    viewPatient();

                    break;


                case "VIEW ALL PATIENTS":

                    listPatients();

                    break;


                case "UPDATE PATIENT":

                    updatePatient();

                    break;


                case "DELETE PATIENT":

                    deletePatient();

                    break;


                case "BACK":

                    return;


                default:

                    System.out.println(
                            "Invalid Choice. "
                            + "Please try again.");
            }
        }
    }


    // =========================================================
    // ADD PATIENT
    // REUSABLE METHOD
    // =========================================================

    public static Patient addPatient(
            UserAccount user)
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== ADD PATIENT ==========");


        // -----------------------------------------------------
        // PATIENT DETAILS
        // -----------------------------------------------------

        System.out.print("Patient Name : ");

        String patientName =
                sc.nextLine().trim();


        System.out.print("Age : ");

        int age;

        try {

            age = Integer.parseInt(
                    sc.nextLine().trim());

        } catch (NumberFormatException e) {

            throw new ValidationException(
                    "Age must be a valid number.");
        }


        System.out.print("Gender : ");

        String gender =
                sc.nextLine().trim();


        System.out.print("Phone : ");

        String phone =
                sc.nextLine().trim();


        // -----------------------------------------------------
        // ADDRESS
        // -----------------------------------------------------

        System.out.println();
        System.out.println(
                "========== ADDRESS ==========");


        System.out.print("Street : ");

        String street =
                sc.nextLine().trim();


        System.out.print("City : ");

        String city =
                sc.nextLine().trim();


        System.out.print("State : ");

        String state =
                sc.nextLine().trim();


        System.out.print("Pincode : ");

        String pincode =
                sc.nextLine().trim();


        // -----------------------------------------------------
        // CREATE ADDRESS OBJECT
        // -----------------------------------------------------

        Address address =
                new Address();

        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPincode(pincode);


        // -----------------------------------------------------
        // CREATE PATIENT OBJECT
        // -----------------------------------------------------

        Patient patient =
                new Patient();

        patient.setPatientName(
                patientName);

        patient.setAge(age);

        patient.setGender(
                gender);

        patient.setPhone(phone);

        patient.setAddress(
                address);


        // -----------------------------------------------------
        // IF PATIENT IS REGISTERING THROUGH LOGIN
        // LINK USER ACCOUNT
        // -----------------------------------------------------

        if (user != null) {

            patient.setUserId(
                    user.getUserId());
        }


        // -----------------------------------------------------
        // ServiceImpl
        // -----------------------------------------------------

        ServiceImpl.addPatient(patient);


        // -----------------------------------------------------
        // SUCCESS
        // -----------------------------------------------------

        System.out.println();

        System.out.println(
                "Patient Added Successfully.");

        System.out.println(
                "Generated Patient ID : "
                        + patient.getPatientId());


        return patient;
    }


    // =========================================================
    // VIEW PATIENT
    // =========================================================

    private static void viewPatient()
            throws ValidationException {

        System.out.print(
                "Enter Patient ID : ");

        String id =
                sc.nextLine().trim();


        Patient p =
                ServiceImpl.getPatientById(id);


        if (p == null) {

            System.out.println(
                    "Patient Not Found.");

            return;
        }


        System.out.println();
        System.out.println(
                "========================================");

        System.out.println(
                "           PATIENT DETAILS");

        System.out.println(
                "========================================");


        System.out.printf(
                "%-18s : %s%n",
                "Patient ID",
                p.getPatientId());


        System.out.printf(
                "%-18s : %s%n",
                "Patient Name",
                p.getPatientName());


        System.out.printf(
                "%-18s : %d%n",
                "Age",
                p.getAge());


        System.out.printf(
                "%-18s : %s%n",
                "Gender",
                p.getGender());


        System.out.printf(
                "%-18s : %s%n",
                "Phone",
                p.getPhone());


        System.out.println(
                "----------------------------------------");

        System.out.println("Address");


        System.out.printf(
                "  %-16s : %s%n",
                "Address ID",
                p.getAddress().getAddressId());


        System.out.printf(
                "  %-16s : %s%n",
                "Street",
                p.getAddress().getStreet());


        System.out.printf(
                "  %-16s : %s%n",
                "City",
                p.getAddress().getCity());


        System.out.printf(
                "  %-16s : %s%n",
                "State",
                p.getAddress().getState());


        System.out.printf(
                "  %-16s : %s%n",
                "Pincode",
                p.getAddress().getPincode());


        System.out.println(
                "----------------------------------------");


        System.out.printf(
                "%-18s : %s%n",
                "Status",
                p.isActive()
                        ? "ACTIVE"
                        : "INACTIVE");


        System.out.println(
                "========================================");
    }


    // =========================================================
    // LIST PATIENTS
    // =========================================================

    private static void listPatients() {

        List<Patient> patients =
                ServiceImpl.getAllPatients();


        if (patients.isEmpty()) {

            System.out.println(
                    "No Patients Found.");

            return;
        }


        System.out.println();

        System.out.printf(
                "%-15s %-20s %-5s %-10s %-15s%n",
                "ID",
                "Name",
                "Age",
                "Gender",
                "Phone");


        System.out.println(
                "----------------------------------------------------------------");


        for (Patient patient : patients) {

            System.out.printf(
                    "%-15s %-20s %-5d %-10s %-15s%n",

                    patient.getPatientId(),

                    patient.getPatientName(),

                    patient.getAge(),

                    patient.getGender(),

                    patient.getPhone());
        }
    }


    // =========================================================
    // UPDATE PATIENT
    // =========================================================

    private static void updatePatient()
            throws ValidationException {

        System.out.print(
                "Enter Patient ID : ");

        String updateId =
                sc.nextLine().trim();


        Patient patient =
                ServiceImpl.getPatientById(
                        updateId);


        if (patient == null) {

            System.out.println(
                    "Patient Not Found.");

            return;
        }


        System.out.print(
                "Patient Name : ");

        String name =
                sc.nextLine().trim();


        System.out.print(
                "Age : ");

        int age;

        try {

            age = Integer.parseInt(
                    sc.nextLine().trim());

        } catch (NumberFormatException e) {

            throw new ValidationException(
                    "Invalid Age.");
        }


        System.out.print(
                "Gender : ");

        String gender =
                sc.nextLine().trim();


        System.out.print(
                "Phone : ");

        String phone =
                sc.nextLine().trim();


        /*
         * For now we keep the existing address ID
         * during update.
         */
        Address address =
                patient.getAddress();


        patient.setPatientName(name);

        patient.setAge(age);

        patient.setGender(gender);

        patient.setPhone(phone);

        patient.setAddress(address);


        ServiceImpl.updatePatient(
                patient);


        System.out.println(
                "Patient Updated Successfully.");
    }


    // =========================================================
    // DELETE PATIENT
    // =========================================================

    private static void deletePatient() {

        System.out.print(
                "Enter Patient ID : ");

        String deleteId =
                sc.nextLine().trim();


        try {

            ServiceImpl.getPatientById(
                    deleteId);

            ServiceImpl.deletePatient(
                    deleteId);


            System.out.println(
                    "Patient Deleted Successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Unable to delete patient : "
                            + e.getMessage());
        }
    }
}