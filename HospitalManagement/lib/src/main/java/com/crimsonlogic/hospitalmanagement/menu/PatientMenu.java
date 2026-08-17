package com.crimsonlogic.hospitalmanagement.menu;

import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.dao.AppointmentDAO;
import com.crimsonlogic.hospitalmanagement.dao.PaymentDAO;
import com.crimsonlogic.hospitalmanagement.model.Address;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

public class PatientMenu {

    private static final Scanner sc =
            new Scanner(System.in);

    private final AppointmentServiceImpl appointmentServiceImpl =
            new AppointmentServiceImpl();

    private final PrescriptionServiceImpl prescriptionServiceImpl =
            new PrescriptionServiceImpl();

    private final BillServiceImpl billServiceImpl =
            new BillServiceImpl();

    private final PatientServiceImpl patientServiceImpl =
            new PatientServiceImpl();

    public void showMenu(UserAccount user) {

        if (user == null || user.getUserId() == null) {
            System.out.println("Invalid user session.");
            return;
        }

        // Find the patient profile belonging to this login.
        Patient patient =
                patientServiceImpl.getPatientByUserId(
                        user.getUserId());

        // First login: create the profile only once.
        if (patient == null) {
            patient = createPatientProfile(user);
            if (patient == null) {
                return;
            }
        }

        String patientId = patient.getPatientId();

        while (true) {

            System.out.println();
System.out.println("Patient ID : " + patientId);
            System.out.println(
                    "========================================");
            System.out.println(
                    "             PATIENT MENU");
            System.out.println(
                    "========================================");

            System.out.println(
                    "BOOK APPOINTMENT");

            System.out.println(
                    "VIEW APPOINTMENTS");

            System.out.println(
                    "VIEW PRESCRIPTIONS");

            System.out.println(
                    "MAKE PAYMENT");

            System.out.println(
                    "VIEW BILLS");

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

            try {

                switch (choice) {

                    case "BOOK APPOINTMENT":

                        AppointmentDAO appointment=new AppointmentDAO();
                        appointment.addAppointment();

                        break;

                    case "VIEW APPOINTMENTS":

                        appointmentServiceImpl
                                .getAllAppointments()
                                .stream()
                                .filter(a ->
                                        a.getPatient() != null &&
                                        a.getPatient()
                                         .getPatientId()
                                         .equals(patientId))
                                .forEach(System.out::println);

                        break;

                    case "VIEW PRESCRIPTIONS":

                        prescriptionServiceImpl
                                .getAllPrescriptions()
                                .stream()
                                .filter(p ->
                                        p.getPatient() != null &&
                                        p.getPatient()
                                         .getPatientId()
                                         .equals(patientId))
                                .forEach(System.out::println);

                        break;

                    case "MAKE PAYMENT":

                        PaymentDAO paymentDAO =
                                new PaymentDAO();

                        paymentDAO.showMenu();

                        break;

                    case "VIEW BILLS":

                        billServiceImpl
                                .getAllBills()
                                .stream()
                                .filter(b ->
                                        b.getPatient() != null &&
                                        b.getPatient()
                                         .getPatientId()
                                         .equals(patientId))
                                .forEach(System.out::println);

                        break;

                    case "LOGOUT":

                        System.out.println();
                        System.out.println(
                                "Patient logged out successfully.");

                        return;

                    default:

                        System.out.println();
                        System.out.println(
                                "Invalid choice. "
                                + "Please try again.");
                }

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Operation failed : "
                        + e.getMessage());
            }
        }
    }

    private Patient createPatientProfile(UserAccount user) {

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "       COMPLETE PATIENT PROFILE");
        System.out.println(
                "========================================");

        System.out.println(
                "No patient profile is linked to this account.");
        System.out.println(
                "Please enter your details once.");

        try {

            System.out.print("Patient Name : ");
            String patientName =
                    sc.nextLine().trim();

            System.out.print("Age : ");
            int age =
                    Integer.parseInt(
                            sc.nextLine().trim());

            System.out.print("Gender (Male/Female/Other) : ");
            String gender =
                    sc.nextLine().trim();

            System.out.print("Phone : ");
            String phone =
                    sc.nextLine().trim();

            System.out.println();
            System.out.println("========== ADDRESS ==========");

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

            Address address =
                    new Address();

            address.setStreet(street);
            address.setCity(city);
            address.setState(state);
            address.setPincode(pincode);

            Patient newPatient =
                    new Patient();

            newPatient.setUserId(
                    user.getUserId());

            newPatient.setPatientName(
                    patientName);

            newPatient.setAge(age);

            newPatient.setGender(
                    gender);

            newPatient.setPhone(
                    phone);

            newPatient.setAddress(
                    address);

            patientServiceImpl.addPatient(
                    newPatient);

            System.out.println();
            System.out.println(
                    "Patient profile created successfully.");

            System.out.println(
                    "Patient ID : "
                            + newPatient.getPatientId());

            return newPatient;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid age. Please enter a number.");

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Profile creation failed : "
                            + e.getMessage());
        }

        return null;
    }
}