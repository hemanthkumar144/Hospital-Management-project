package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.model.Nurse;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

public class NurseDAO {

    private static Scanner sc = new Scanner(System.in);

    private static NurseServiceImpl nurseServiceImpl =
            new NurseServiceImpl();

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              NURSE MENU");
            System.out.println("========================================");
            System.out.println("ADD NURSE");
            System.out.println("VIEW NURSE");
            System.out.println("LIST NURSES");
            System.out.println("UPDATE NURSE");
            System.out.println("DEACTIVATE NURSE");
            System.out.println("LOGOUT");
            System.out.println("========================================");

            System.out.print("Enter Choice : ");

            String choice = sc.nextLine()
                    .trim()
                    .toUpperCase();

            try {

                switch (choice) {

                    case "ADD NURSE":
                        addNurse();
                        break;

                    case "VIEW NURSE":
                        viewNurse();
                        break;

                    case "LIST NURSES":
                        listNurses();
                        break;

                    case "UPDATE NURSE":
                        updateNurse();
                        break;

                    case "DEACTIVATE NURSE":
                        deactivateNurse();
                        break;

                    case "LOGOUT":
                        return;

                    default:
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


    private static void addNurse() throws ValidationException {

        Nurse nurse = new Nurse();

        while (true) {

            try {

                System.out.print("Enter Nurse Name : ");
                String name = sc.nextLine();

                nurseServiceImpl.validateName(name);

                nurse.setName(name);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print("Enter Age : ");

                int age = Integer.parseInt(
                        sc.nextLine());

                nurseServiceImpl.validateAge(age);

                nurse.setAge(age);
                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Age must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Gender : ");

                String gender =
                        sc.nextLine();

                nurseServiceImpl.validateGender(
                        gender);

                nurse.setGender(gender);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Phone : ");

                String phone =
                        sc.nextLine();

                nurseServiceImpl.validatePhone(
                        phone);

                nurse.setPhone(phone);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Salary : ");

                double salary =
                        Double.parseDouble(
                                sc.nextLine());

                nurseServiceImpl.validateSalary(
                        salary);

                nurse.setSalary(salary);
                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Salary must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Department ID : ");

                String departmentId =
                        sc.nextLine();

                nurseServiceImpl.validateDepartment(
                        departmentId);

                Department department =
                        new Department();

                department.setDepartmentId(
                        departmentId);

                nurse.setDepartment(
                        department);

                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Shift : ");

                String shift =
                        sc.nextLine();

                nurseServiceImpl.validateShift(
                        shift);

                nurse.setShift(shift);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        System.out.println("========== NURSE LOGIN ACCOUNT ==========");

        System.out.print("Username : ");
        String username = sc.nextLine().trim();

        System.out.print("Password : ");
        String password = sc.nextLine();

        UserAccount user = new UserAccount();

        user.setUsername(username);
        user.setPasswordHash(password);
        user.setRole("NURSE");

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.addUser(user);

        nurse.setUserId(user.getUserId());

        nurseServiceImpl.addNurse(nurse);

      

        System.out.println();
        System.out.println(
                "Nurse added successfully.");

        System.out.println(
                "Generated Nurse ID : "
                        + nurse.getStaffId());
    }


    private static void viewNurse()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Enter Nurse ID : ");

            String nurseId =
                    sc.nextLine().trim();

            if (nurseId.isEmpty()) {

                System.out.println(
                        "Nurse ID cannot be empty.");

                continue;
            }

            Nurse nurse =
                    nurseServiceImpl.getNurseById(
                            nurseId);

            if (nurse == null) {

                System.out.println(
                        "Nurse not found.");

                continue;
            }

            System.out.println();
            System.out.println(nurse);

            break;
        }
    }


    private static void listNurses() {

        List<Nurse> nurses =
                nurseServiceImpl.getAllNurses();

        if (nurses == null || nurses.isEmpty()) {

            System.out.println();
            System.out.println("No nurses found.");
            return;
        }

        System.out.println();
        System.out.println(
                "============================== NURSES ==============================");

        System.out.printf(
                "%-12s %-18s %-5s %-8s %-15s %-12s %-15s %-12s%n",
                "STAFF ID",
                "NAME",
                "AGE",
                "GENDER",
                "PHONE",
                "SALARY",
                "DEPARTMENT",
                "SHIFT"
        );

        System.out.println(
                "---------------------------------------------------------------------"
              + "----------------");

        for (Nurse nurse : nurses) {

            String departmentName = "N/A";

            if (nurse.getDepartment() != null) {

                departmentName =
                        nurse.getDepartment().getDepartmentName();
            }

            System.out.printf(
                    "%-12s %-18s %-5d %-8s %-15s %-12.2f %-15s %-12s%n",
                    nurse.getStaffId(),
                    nurse.getName(),
                    nurse.getAge(),
                    nurse.getGender(),
                    nurse.getPhone(),
                    nurse.getSalary(),
                    departmentName,
                    nurse.getShift()
            );
        }

        System.out.println(
                "====================================================================="
              + "================");
    }


    private static void updateNurse()
            throws ValidationException {

        Nurse nurse;

        while (true) {

            System.out.print(
                    "Enter Nurse ID : ");

            String nurseId =
                    sc.nextLine().trim();

            if (nurseId.isEmpty()) {

                System.out.println(
                        "Nurse ID cannot be empty.");

                continue;
            }

            nurse =
                    nurseServiceImpl.getNurseById(
                            nurseId);

            if (nurse == null) {

                System.out.println(
                        "Nurse not found. Enter ID again.");

                continue;
            }

            break;
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Nurse Name : ");

                String name =
                        sc.nextLine();

                nurseServiceImpl.validateName(name);

                nurse.setName(name);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Age : ");

                int age =
                        Integer.parseInt(
                                sc.nextLine());

                nurseServiceImpl.validateAge(age);

                nurse.setAge(age);
                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Age must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Gender : ");

                String gender =
                        sc.nextLine();

                nurseServiceImpl.validateGender(
                        gender);

                nurse.setGender(gender);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Phone : ");

                String phone =
                        sc.nextLine();

                nurseServiceImpl.validatePhone(
                        phone);

                nurse.setPhone(phone);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Salary : ");

                double salary =
                        Double.parseDouble(
                                sc.nextLine());

                nurseServiceImpl.validateSalary(
                        salary);

                nurse.setSalary(salary);
                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Salary must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Department ID : ");

                String departmentId =
                        sc.nextLine();

                nurseServiceImpl.validateDepartment(
                        departmentId);

                Department department =
                        new Department();

                department.setDepartmentId(
                        departmentId);

                nurse.setDepartment(
                        department);

                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Enter Shift : ");

                String shift =
                        sc.nextLine();

                nurseServiceImpl.validateShift(
                        shift);

                nurse.setShift(shift);
                break;

            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }


        nurseServiceImpl.updateNurse(nurse);

        System.out.println();
        System.out.println(
                "Nurse updated successfully.");
    }


    private static void deactivateNurse()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Enter Nurse ID : ");

            String nurseId =
                    sc.nextLine().trim();

            if (nurseId.isEmpty()) {

                System.out.println(
                        "Nurse ID cannot be empty.");

                continue;
            }

            Nurse nurse =
                    nurseServiceImpl.getNurseById(
                            nurseId);

            if (nurse == null) {

                System.out.println(
                        "Nurse not found.");

                continue;
            }

            nurseServiceImpl.deactivateNurse(
                    nurseId);

            System.out.println(
                    "Nurse deactivated successfully.");

            break;
        }
    }
}