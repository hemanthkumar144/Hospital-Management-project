package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

public class DoctorDAO {

    private static final Scanner sc =
            new Scanner(System.in);

    private static final DoctorServiceImpl ServiceImpl =
            new DoctorServiceImpl();


    // =========================================================
    // DOCTOR MENU
    // =========================================================

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========== DOCTOR MANAGEMENT ==========");

            System.out.println("ADD DOCTOR");
            System.out.println("VIEW DOCTOR BY ID");
            System.out.println("VIEW ALL DOCTORS");
            System.out.println("UPDATE DOCTOR");
            System.out.println("DELETE DOCTOR");
            System.out.println("BACK");

            System.out.println(
                    "========================================");

            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();

            try {

                switch (choice) {

                    case "ADD DOCTOR":

                        addDoctor();

                        break;


                    case "VIEW DOCTOR BY ID":

                        viewDoctor();

                        break;


                    case "VIEW ALL DOCTORS":

                        listDoctors();

                        break;


                    case "UPDATE DOCTOR":

                        updateDoctor();

                        break;


                    case "DELETE DOCTOR":

                        deleteDoctor();

                        break;


                    case "BACK":

                        return;


                    default:

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


    // =========================================================
    // ADD DOCTOR
    // =========================================================

    private static void addDoctor()
            throws ValidationException {

        System.out.println();
        System.out.println(
                "========== ADD DOCTOR ==========");


        String name =
                readName();


        int age =
                readAge();


        String gender =
                readGender();


        String phone =
                readPhone();


        double salary =
                readSalary();


        String specialization =
                readSpecialization();


        int experience =
                readExperience();


        double consultationFee =
                readConsultationFee();


        /*
         * Department ID and Department Name are both
         * validated immediately.
         */
        Department department =
                readDepartment();


        Doctor doctor =
                new Doctor(
                        null,
                        name,
                        age,
                        gender,
                        phone,
                        salary,
                        department,
                        specialization,
                        experience,
                        consultationFee,
                        true);
        System.out.println();
        System.out.println("========== DOCTOR LOGIN ACCOUNT ==========");

        System.out.print("Username : ");
        String username = sc.nextLine().trim();

        System.out.print("Password : ");
        String password = sc.nextLine();

        UserAccount user = new UserAccount();

        user.setUsername(username);
        user.setPasswordHash(password);
        user.setRole("DOCTOR");

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.addUser(user);

        doctor.setUserId(user.getUserId());

        ServiceImpl.addDoctor(doctor); 





        System.out.println();
        System.out.println(
                "Doctor Added Successfully");

        System.out.println(
                "Generated Doctor ID : "
                        + doctor.getStaffId());
    }


    // =========================================================
    // NAME
    // =========================================================

    private static String readName()
            throws ValidationException {

        while (true) {

            System.out.print("Name : ");

            String name =
                    sc.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println(
                        "Name cannot be empty.");

                continue;
            }

            if (!name.matches(
                    "^[A-Za-z ]{2,50}$")) {

                System.out.println(
                        "Name must contain only "
                                + "letters and spaces.");

                continue;
            }

            return name;
        }
    }


    // =========================================================
    // AGE
    // =========================================================

    private static int readAge()
            throws ValidationException {

        while (true) {

            System.out.print("Age : ");

            try {

                int age =
                        Integer.parseInt(
                                sc.nextLine().trim());

                if (age < 21 || age > 80) {

                    System.out.println(
                            "Doctor age must be "
                                    + "between 21 and 80.");

                    continue;
                }

                return age;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Age must be a valid number.");
            }
        }
    }


    // =========================================================
    // GENDER
    // =========================================================

    private static String readGender()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Gender (Male/Female/Other) : ");

            String gender =
                    sc.nextLine().trim();

            if (gender.matches(
                    "(?i)Male|Female|Other")) {

                return gender;
            }

            System.out.println(
                    "Gender must be Male, Female or Other.");
        }
    }


    // =========================================================
    // PHONE
    // =========================================================

    private static String readPhone()
            throws ValidationException {

        while (true) {

            System.out.print("Phone : ");

            String phone =
                    sc.nextLine().trim();

            if (phone.matches(
                    "^[6-9][0-9]{9}$")) {

                return phone;
            }

            System.out.println(
                    "Phone must contain exactly "
                            + "10 digits and start with 6-9.");
        }
    }


    // =========================================================
    // SALARY
    // =========================================================

    private static double readSalary()
            throws ValidationException {

        while (true) {

            System.out.print("Salary : ");

            try {

                double salary =
                        Double.parseDouble(
                                sc.nextLine().trim());

                if (salary <= 0) {

                    System.out.println(
                            "Salary must be greater than zero.");

                    continue;
                }

                if (salary > 1000000) {

                    System.out.println(
                            "Salary cannot exceed 10,00,000.");

                    continue;
                }

                return salary;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Salary must be a valid number.");
            }
        }
    }


    // =========================================================
    // SPECIALIZATION
    // =========================================================

    private static String readSpecialization()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Specialization : ");

            String specialization =
                    sc.nextLine().trim();

            if (specialization.isEmpty()) {

                System.out.println(
                        "Specialization cannot be empty.");

                continue;
            }

            if (!specialization.matches(
                    "^[A-Za-z ]{2,50}$")) {

                System.out.println(
                        "Specialization must contain only "
                                + "letters and spaces.");

                continue;
            }

            return specialization;
        }
    }


    // =========================================================
    // EXPERIENCE
    // =========================================================

    private static int readExperience()
            throws ValidationException {

        while (true) {

            System.out.print("Experience : ");

            try {

                int experience =
                        Integer.parseInt(
                                sc.nextLine().trim());

                if (experience < 0
                        || experience > 60) {

                    System.out.println(
                            "Experience must be "
                                    + "between 0 and 60 years.");

                    continue;
                }

                return experience;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Experience must be a valid number.");
            }
        }
    }


    // =========================================================
    // CONSULTATION FEE
    // =========================================================

    private static double readConsultationFee()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Consultation Fee : ");

            try {

                double fee =
                        Double.parseDouble(
                                sc.nextLine().trim());

                if (fee <= 0) {

                    System.out.println(
                            "Consultation fee must be "
                                    + "greater than zero.");

                    continue;
                }

                if (fee > 100000) {

                    System.out.println(
                            "Consultation fee cannot "
                                    + "exceed 1,00,000.");

                    continue;
                }

                return fee;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Consultation fee must "
                                + "be a valid number.");
            }
        }
    }


    // =========================================================
    // DEPARTMENT
    // =========================================================

    private static Department readDepartment()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Department ID : ");

            String departmentId =
                    sc.nextLine().trim();


            System.out.print(
                    "Department Name : ");

            String departmentName =
                    sc.nextLine().trim();


            try {

                /*
                 * ServiceImpl checks:
                 *
                 * Department ID exists
                 * AND
                 * Department Name matches that ID
                 */
                return ServiceImpl.getValidatedDepartment(
                        departmentId,
                        departmentName);

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Department validation failed : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the department details again.");
                System.out.println();
            }
        }
    }


    // =========================================================
    // VIEW DOCTOR
    // =========================================================

    private static void viewDoctor()
            throws Exception {

        System.out.print(
                "Enter Doctor ID : ");

        String doctorId =
                sc.nextLine().trim();

        Doctor doctor =
                ServiceImpl.getDoctorById(
                        doctorId);

        System.out.println();
        System.out.println(
                "========== DOCTOR DETAILS ==========");

        System.out.println(
                "Doctor ID        : "
                        + doctor.getStaffId());

        System.out.println(
                "Name             : "
                        + doctor.getName());

        System.out.println(
                "Age              : "
                        + doctor.getAge());

        System.out.println(
                "Gender           : "
                        + doctor.getGender());

        System.out.println(
                "Phone            : "
                        + doctor.getPhone());

        System.out.println(
                "Salary           : "
                        + doctor.getSalary());

        System.out.println(
                "Specialization   : "
                        + doctor.getSpecialization());

        System.out.println(
                "Experience       : "
                        + doctor.getExperience());

        System.out.println(
                "Consultation Fee : ₹"
                        + doctor.getConsultationFee());

        System.out.println(
                "Department       : "
                        + doctor.getDepartment()
                                .getDepartmentName());
    }


    // =========================================================
    // LIST DOCTORS
    // =========================================================

    private static void listDoctors() {

        List<Doctor> doctors =
                ServiceImpl.getAllDoctors();

        if (doctors.isEmpty()) {

            System.out.println(
                    "No Doctors Found.");

            return;
        }

        System.out.printf(
                "%-12s %-20s %-5s %-10s %-15s %-20s %-15s%n",
                "ID",
                "NAME",
                "AGE",
                "GENDER",
                "PHONE",
                "SPECIALIZATION",
                "CONSULTATION");

        System.out.println(
                "--------------------------------------------------------------------------------");

        for (Doctor doctor : doctors) {

            System.out.printf(
                    "%-12s %-20s %-5d %-10s %-15s %-20s ₹%-14.2f%n",

                    doctor.getStaffId(),

                    doctor.getName(),

                    doctor.getAge(),

                    doctor.getGender(),

                    doctor.getPhone(),

                    doctor.getSpecialization(),

                    doctor.getConsultationFee());
        }
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    private static void updateDoctor()
            throws Exception {

        System.out.print(
                "Doctor ID : ");

        String doctorId =
                sc.nextLine().trim();

        ServiceImpl.getDoctorById(
                doctorId);

        String name =
                readName();

        int age =
                readAge();

        String gender =
                readGender();

        String phone =
                readPhone();

        double salary =
                readSalary();

        String specialization =
                readSpecialization();

        int experience =
                readExperience();

        double consultationFee =
                readConsultationFee();

        Department department =
                readDepartment();

        Doctor doctor =
                new Doctor(
                        doctorId,
                        name,
                        age,
                        gender,
                        phone,
                        salary,
                        department,
                        specialization,
                        experience,
                        consultationFee,
                        true);

        ServiceImpl.updateDoctor(doctor);

        System.out.println(
                "Doctor Updated Successfully.");
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    private static void deleteDoctor()
            throws Exception {

        System.out.print(
                "Enter Doctor ID : ");

        String doctorId =
                sc.nextLine().trim();

        ServiceImpl.deleteDoctor(
                doctorId);

        System.out.println(
                "Doctor Deleted Successfully.");
    }
}