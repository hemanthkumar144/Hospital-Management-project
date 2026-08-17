package com.crimsonlogic.hospitalmanagement.menu;
import java.util.Scanner;
import com.crimsonlogic.hospitalmanagement.dao.*;

public class AdminMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println("================================================");
            System.out.println("                 ADMIN MENU");
            System.out.println("================================================");

            System.out.println("PATIENT");
            System.out.println("DOCTOR");
            System.out.println("NURSE");
            System.out.println("DEPARTMENT");
            System.out.println("APPOINTMENT");
            System.out.println("PRESCRIPTION");
            System.out.println("MEDICINE");
            System.out.println("LABORATORY");
            System.out.println("PATIENT TEST");
            System.out.println("WARD");
            System.out.println("BED");
            System.out.println("ADMISSION");
            System.out.println("BILL");
            System.out.println("PAYMENT");
            System.out.println("REPORTS");
            System.out.println("LOGOUT");

            System.out.println("================================================");

            System.out.print("Enter Choice : ");

            String choice = sc.nextLine()
                    .trim()
                    .toUpperCase();

            try {

                switch (choice) {

                    case "PATIENT":
                    	
                        PatientDAO.showMenu();

                        break;


                    case "DOCTOR":

                        DoctorDAO.showMenu();

                        break;


                    case "NURSE":

                        NurseDAO.showMenu();

                        break;


                    case "DEPARTMENT":

                        DepartmentDAO.showMenu();

                        break;


                    case "APPOINTMENT":

                        AppointmentDAO.showMenu();

                        break;


                    case "PRESCRIPTION":

                        PrescriptionDAO.showMenu();

                        break;


                    case "MEDICINE":

                        MedicineDAO.showMenu();

                        break;


                    case "LABORATORY":

                        LaboratoryTestDAO.showMenu();

                        break;


                    case "PATIENT TEST":

                        PatientTestDAO.showMenu();

                        break;


                    case "WARD":

                        WardDAO.showMenu();

                        break;


                    case "BED":

                        BedDAO.showMenu();

                        break;


                    case "ADMISSION":

                        AdmissionDAO admission=new AdmissionDAO();
                        admission.showMenu();

                        break;


                    case "BILL":

                        BillDAO.showMenu();

                        break;


                    case "PAYMENT":

                        PaymentDAO paymentDAO =
                                new PaymentDAO();

                        paymentDAO.showMenu();

                        break;


                    case "REPORTS":

                        GenerateReports reports =
                                new GenerateReports();

                        reports.generateReports();

                        break;


                    case "LOGOUT":

                        System.out.println(
                                "Admin logged out successfully.");

                        return;


                    default:

                        System.out.println(
                                "Invalid choice. Please try again.");
                }

            } catch (Exception e) {

                System.out.println(
                        "Operation failed : "
                                + e.getMessage());
            }
        }
    }
}