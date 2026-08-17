package com.crimsonlogic.hospitalmanagement.menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import com.crimsonlogic.hospitalmanagement.dao.MedicineDAO;
import com.crimsonlogic.hospitalmanagement.dao.PatientDAO;
import com.crimsonlogic.hospitalmanagement.dao.PrescriptionDAO;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.services.AppointmentServiceImpl;
import com.crimsonlogic.hospitalmanagement.services.PatientServiceImpl;

public class DoctorMenu {

    private static final Scanner sc =
            new Scanner(System.in);
    private static final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();
    private static final PatientServiceImpl patientService =
            new PatientServiceImpl();

    public void showMenu(UserAccount user,String doctorId) {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              DOCTOR MENU");
            System.out.println("========================================");

            System.out.println("VIEW APPOINTMENTS");
            System.out.println("VIEW APPOINTMENTS BY DATE RANGE");
            System.out.println("CANCEL APPOINTMENT");
            System.out.println("VIEW PATIENT");
            System.out.println("LIST OF PATIENTS");
            System.out.println("WRITE PRESCRIPTION");
            System.out.println("MEDICINE MANAGEMENT");
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

                    	 appointmentService.getAllAppointments()
                         .stream()
                         .filter(a ->
                                 a.getDoctor() != null &&
                                 a.getDoctor()
                                  .getStaffId()
                                  .equals(doctorId))
                         .forEach(System.out::println);
                    	 break;

                    case "VIEW APPOINTMENTS BY DATE RANGE":

                        System.out.print(
                                "From Date (yyyy-MM-dd) : ");

                        LocalDate fromDate =
                                LocalDate.parse(
                                        sc.nextLine().trim());

                        System.out.print(
                                "To Date (yyyy-MM-dd) : ");

                        LocalDate toDate =
                                LocalDate.parse(
                                        sc.nextLine().trim());

                        List<Appointment> appointments =
                                appointmentService
                                        .getAppointmentsByDoctorAndDateRange(
                                                doctorId,
                                                fromDate,
                                                toDate);

                        appointments.forEach(
                                System.out::println);

                        break;

                    case "CANCEL APPOINTMENT":

                    	 System.out.println();
                    	    System.out.println("========== CANCEL APPOINTMENT ==========");

                    	    System.out.print("Appointment ID : ");

                    	    String appointmentId =
                    	            sc.nextLine().trim();

                    	    appointmentService.deleteAppointment(
                    	            appointmentId);

                    	    System.out.println(
                    	            "Appointment cancelled successfully.");


                    case "VIEW PATIENT":

                   

                        System.out.println();
                        System.out.println("========== VIEW PATIENT ==========");

                        System.out.print("Patient ID : ");

                        String patientId =
                                sc.nextLine().trim();

                        Patient patient =
                                patientService.getPatientById(patientId);

                        System.out.println();
                        System.out.println(patient);

                        break;

                       


                    case "LIST OF PATIENTS":

                        PatientDAO.showMenu();

                        break;


                    case "WRITE PRESCRIPTION":

                        PrescriptionDAO.showMenu();

                        break;


                    case "MEDICINE MANAGEMENT":

                        MedicineDAO.showMenu();

                        break;


                    case "LOGOUT":

                        System.out.println(
                                "Doctor logged out successfully.");

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