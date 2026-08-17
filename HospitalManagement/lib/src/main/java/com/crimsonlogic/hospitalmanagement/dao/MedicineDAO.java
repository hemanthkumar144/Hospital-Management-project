package com.crimsonlogic.hospitalmanagement.dao;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.hospitalmanagement.exceptions.MedicineNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Medicine;

import com.crimsonlogic.hospitalmanagement.services.MedicineServiceImpl;

public class MedicineDAO {

    private static Scanner sc = new Scanner(System.in);

    private static MedicineServiceImpl service =
            new MedicineServiceImpl();

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("             MEDICINE MENU");
            System.out.println("========================================");
            System.out.println("ADD MEDICINE");
            System.out.println("VIEW MEDICINE");
            System.out.println("LIST MEDICINES");
            System.out.println("UPDATE MEDICINE");
            System.out.println("DELETE MEDICINE");
            System.out.println("LOGOUT");
            System.out.println("========================================");

            System.out.print("Enter Choice : ");

            String choice =
                    sc.nextLine()
                            .trim()
                            .toUpperCase();

            try {

                switch (choice) {

                    case "ADD MEDICINE":
                        addMedicine();
                        break;

                    case "VIEW MEDICINE":
                        viewMedicine();
                        break;

                    case "LIST MEDICINES":
                        listMedicines();
                        break;

                    case "UPDATE MEDICINE":
                        updateMedicine();
                        break;

                    case "DELETE MEDICINE":
                        deleteMedicine();
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


    private static void addMedicine()
            throws ValidationException {

        Medicine medicine = new Medicine();


        // ================= MEDICINE NAME =================

        while (true) {

            try {

                System.out.print(
                        "Medicine Name : ");

                String name =
                        sc.nextLine().trim();

                service.validateMedicineName(name);

                medicine.setMedicineName(name);

                break;

            } catch (ValidationException e) {

                System.out.println(
                        "Invalid Medicine Name : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the Medicine Name again.");
            }
        }


        // ================= MANUFACTURER =================

        while (true) {

            try {

                System.out.print(
                        "Manufacturer : ");

                String manufacturer =
                        sc.nextLine().trim();

                service.validateManufacturer(
                        manufacturer);

                medicine.setManufacturer(
                        manufacturer);

                break;

            } catch (ValidationException e) {

                System.out.println(
                        "Invalid Manufacturer : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the Manufacturer again.");
            }
        }


        // ================= PRICE =================

        while (true) {

            try {

                System.out.print(
                        "Price : ");

                double price =
                        Double.parseDouble(
                                sc.nextLine().trim());

                service.validatePrice(price);

                medicine.setPrice(price);

                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Price must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(
                        "Invalid Price : "
                                + e.getMessage());

                System.out.println(
                        "Please enter the Price again.");
            }
        }


        // ================= SAVE =================

        service.addMedicine(medicine);

        System.out.println();
        System.out.println(
                "Medicine added successfully.");

        System.out.println(
                "Generated Medicine ID : "
                        + medicine.getMedicineId());
    }

    private static void viewMedicine()
            throws ValidationException,
                   MedicineNotFoundException {

        while (true) {

            System.out.print(
                    "Enter Medicine ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Medicine ID cannot be empty.");

                continue;
            }

            Medicine medicine =
                    service.getMedicineById(id);

            System.out.println();
            System.out.println(medicine);

            break;
        }
    }


    private static void listMedicines() {

        List<Medicine> medicines =
                service.getAllMedicines();

        if (medicines == null ||
                medicines.isEmpty()) {

            System.out.println(
                    "No medicines found.");

            return;
        }

        System.out.println();
        System.out.printf(
                "%-15s %-25s %-25s %-12s%n",
                "Medicine ID",
                "Medicine Name",
                "Manufacturer",
                "Price");

        System.out.println(
                "--------------------------------------------------------------------------");

        for (Medicine medicine : medicines) {

            System.out.printf(
                    "%-15s %-25s %-25s %-12.2f%n",
                    medicine.getMedicineId(),
                    medicine.getMedicineName(),
                    medicine.getManufacturer(),
                    medicine.getPrice());
        }
    }


    private static void updateMedicine()
            throws ValidationException,
                   MedicineNotFoundException {

        Medicine medicine;

        while (true) {

            System.out.print(
                    "Enter Medicine ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Medicine ID cannot be empty.");

                continue;
            }

            try {

                medicine =
                        service.getMedicineById(id);

                break;

            } catch (MedicineNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the ID again.");
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Medicine Name : ");

                String name =
                        sc.nextLine().trim();

                if (!name.matches(
                        "^[A-Za-z0-9][A-Za-z0-9 .-]{1,49}$")) {

                    throw new ValidationException(
                            "Medicine name must contain "
                            + "2 to 50 valid characters");
                }

                medicine.setMedicineName(name);

                break;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Manufacturer : ");

                String manufacturer =
                        sc.nextLine().trim();

                if (!manufacturer.matches(
                        "^[A-Za-z0-9][A-Za-z0-9 .&-]{1,49}$")) {

                    throw new ValidationException(
                            "Manufacturer must contain "
                            + "2 to 50 valid characters");
                }

                medicine.setManufacturer(
                        manufacturer);

                break;

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());
            }
        }


        while (true) {

            try {

                System.out.print(
                        "Price : ");

                double price =
                        Double.parseDouble(
                                sc.nextLine());

                if (price <= 0) {

                    throw new ValidationException(
                            "Price must be greater than zero");
                }

                medicine.setPrice(price);

                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Price must be a valid number.");

            } catch (ValidationException e) {

                System.out.println(
                        e.getMessage());
            }
        }


        service.updateMedicine(medicine);

        System.out.println();
        System.out.println(
                "Medicine updated successfully.");
    }


    private static void deleteMedicine()
            throws ValidationException,
                   MedicineNotFoundException {

        while (true) {

            System.out.print(
                    "Enter Medicine ID : ");

            String id =
                    sc.nextLine().trim();

            if (id.isEmpty()) {

                System.out.println(
                        "Medicine ID cannot be empty.");

                continue;
            }

            try {

                service.deleteMedicine(id);

                System.out.println(
                        "Medicine deleted successfully.");

                break;

            } catch (MedicineNotFoundException e) {

                System.out.println(
                        e.getMessage());

                System.out.println(
                        "Please enter the ID again.");
            }
        }
    }
}