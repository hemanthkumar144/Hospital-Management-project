package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.services.DepartmentServiceImpl;

public class DepartmentDAO {

    public static void showMenu() throws ValidationException {

        Scanner sc = new Scanner(System.in);
        DepartmentServiceImpl ServiceImpl = new DepartmentServiceImpl();

        while (true) {

            System.out.println();
            System.out.println("========== DEPARTMENT MANAGEMENT ==========");
            System.out.println("1. Add Department");
            System.out.println("2. View Department By ID");
            System.out.println("3. View All Departments");
            System.out.println("4. Update Department");
            System.out.println("5. Delete Department");
            System.out.println("6. Back");
            System.out.println("===========================================");

            System.out.print("Enter Choice : ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":

                    System.out.print("Enter Department Name : ");
                    String name = sc.nextLine().trim();

                    System.out.print("Enter Location : ");
                    String location = sc.nextLine().trim();

                    Department dept =
                            new Department();

                    dept.setDepartmentName(name);
                    dept.setLocation(location);

                    ServiceImpl.addDepartment(dept);

                    System.out.println(
                            "Department Added Successfully");

                    System.out.println(
                            "Generated Department ID : "
                                    + dept.getDepartmentId());

                    break;


                case "2":

                    System.out.print(
                            "Enter Department ID : ");

                    String deptId =
                            sc.nextLine().trim();

                    Department department =
                            ServiceImpl.getDepartmentById(deptId);

                    if (department != null) {
                        System.out.println(department);
                    } else {
                        System.out.println(
                                "Department Not Found");
                    }

                    break;


                case "3":

                    List<Department> departments =
                            ServiceImpl.getAllDepartments();

                    if (departments.isEmpty()) {

                        System.out.println(
                                "No Departments Found.");

                        break;
                    }

                    System.out.println();
                    System.out.printf(
                            "%-15s %-25s %-20s%n",
                            "ID",
                            "Department Name",
                            "Location");

                    System.out.println(
                            "------------------------------------------------------------");

                    for (Department d : departments) {

                        System.out.printf(
                                "%-15s %-25s %-20s%n",
                                d.getDepartmentId(),
                                d.getDepartmentName(),
                                d.getLocation());
                    }

                    break;


                case "4":

                    System.out.print(
                            "Enter Department ID : ");

                    String updateId =
                            sc.nextLine().trim();

                    Department updateDept =
                            ServiceImpl.getDepartmentById(
                                    updateId);

                    if (updateDept == null) {

                        System.out.println(
                                "Department Not Found.");

                        break;
                    }

                    System.out.print(
                            "Enter New Department Name : ");

                    String updateName =
                            sc.nextLine().trim();

                    System.out.print(
                            "Enter New Location : ");

                    String updateLocation =
                            sc.nextLine().trim();

                    updateDept.setDepartmentName(
                            updateName);

                    updateDept.setLocation(
                            updateLocation);

                    ServiceImpl.updateDepartment(
                            updateDept);

                    System.out.println(
                            "Department Updated Successfully");

                    break;


                case "5":

                    System.out.print(
                            "Enter Department ID : ");

                    String deleteId =
                            sc.nextLine().trim();

                    Department deleteDept =
                            ServiceImpl.getDepartmentById(
                                    deleteId);

                    if (deleteDept == null) {

                        System.out.println(
                                "Department Not Found.");

                        break;
                    }

                    ServiceImpl.deleteDepartment(deleteId);

                    System.out.println(
                            "Department Deleted Successfully");

                    break;


                case "6":

                    return;


                default:

                    System.out.println(
                            "Invalid Choice. Please try again.");
            }
        }
    }
}