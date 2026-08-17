package com.crimsonlogic.hospitalmanagement.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.crimsonlogic.hospitalmanagement.exceptions.PrescriptionNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.Prescription;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionMedicine;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionTest;
import com.crimsonlogic.hospitalmanagement.services.*;


// =========================================================
// PRESCRIPTION DAO
// =========================================================
// Console interface for Prescription Management.
//
// A prescription can contain:
// 1. Multiple medicines
// 2. Multiple laboratory tests
//
// The prescription date is generated automatically.
// =========================================================

public class PrescriptionDAO {

    private static final Scanner sc =
            new Scanner(System.in);


    private static final PrescriptionServiceImpl
            prescriptionServiceImpl =
            new PrescriptionServiceImpl();


    private static final PatientServiceImpl
            patientServiceImpl =
            new PatientServiceImpl();


    private static final DoctorServiceImpl
            doctorServiceImpl =
            new DoctorServiceImpl();


    private static final MedicineServiceImpl
            medicineServiceImpl =
            new MedicineServiceImpl();


    private static final LaboratoryTestServiceImpl
            laboratoryTestServiceImpl =
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
                    "       PRESCRIPTION MANAGEMENT");

            System.out.println(
                    "========================================");

            System.out.println(
                    "1. Add Prescription");

            System.out.println(
                    "2. View Prescription By ID");

            System.out.println(
                    "3. View All Prescriptions");

            System.out.println(
                    "4. Update Prescription");

            System.out.println(
                    "5. Delete Prescription");

            System.out.println(
                    "6. Back");

            System.out.println(
                    "========================================");

            System.out.print(
                    "Enter Choice : ");


            String choice =
                    sc.nextLine().trim();


            switch (choice) {

                case "1":
                    addPrescription();
                    break;

                case "2":
                    viewPrescriptionById();
                    break;

                case "3":
                    viewAllPrescriptions();
                    break;

                case "4":
                    updatePrescription();
                    break;

                case "5":
                    deletePrescription();
                    break;

                case "6":
                    return;

                default:

                    System.out.println(
                            "Invalid choice. "
                            + "Please enter 1-6.");
            }
        }
    }


    // =========================================================
    // ADD PRESCRIPTION
    // =========================================================

    private static void addPrescription() {

        try {

            System.out.println();
            System.out.println(
                    "========== ADD PRESCRIPTION ==========");


            // -------------------------------------------------
            // Patient
            // -------------------------------------------------

            Patient patient =
                    readPatient();


            // -------------------------------------------------
            // Doctor
            // -------------------------------------------------

            Doctor doctor =
                    readDoctor();


            // -------------------------------------------------
            // Instructions
            // -------------------------------------------------

            String instructions =
                    readInstructions();


            // -------------------------------------------------
            // Date is automatic
            // -------------------------------------------------

            LocalDate prescriptionDate =
                    LocalDate.now();


            // -------------------------------------------------
            // Medicines
            // -------------------------------------------------

            List<PrescriptionMedicine> medicines =
                    readMedicines();


            // -------------------------------------------------
            // Laboratory tests
            // -------------------------------------------------

            List<PrescriptionTest> tests =
                    readTests();


            // -------------------------------------------------
            // At least one medicine or test
            // -------------------------------------------------

            if (medicines.isEmpty()
                    && tests.isEmpty()) {

                System.out.println(
                        "Prescription must contain "
                        + "at least one medicine or test.");

                return;
            }


            // =================================================
            // CREATE PRESCRIPTION OBJECT
            // =================================================

            Prescription prescription =
                    new Prescription();


            prescription.setPatient(
                    patient);

            prescription.setDoctor(
                    doctor);

            prescription.setPrescriptionDate(
                    prescriptionDate);

            prescription.setInstructions(
                    instructions);

            prescription.setMedicines(
                    medicines);

            prescription.setTests(
                    tests);


            // =================================================
            // SAVE
            // =================================================

            prescriptionServiceImpl
                    .addPrescription(
                            prescription);


            System.out.println();
            System.out.println(
                    "Prescription Added Successfully.");

            System.out.println(
                    "Prescription ID : "
                    + prescription
                            .getPrescriptionId());

            System.out.println(
                    "Prescription Date : "
                    + prescription
                            .getPrescriptionDate());


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Prescription Failed : "
                    + e.getMessage());
        }
    }


    // =========================================================
    // READ PATIENT
    // =========================================================

    private static Patient readPatient()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Patient ID : ");

            String patientId =
                    sc.nextLine().trim();


            if (patientId.isEmpty()) {

                System.out.println(
                        "Patient ID cannot be empty.");

                continue;
            }


            try {

                Patient patient =
                        patientServiceImpl
                                .getPatientById(
                                        patientId);


                if (patient == null) {

                    System.out.println(
                            "Patient not found.");

                    continue;
                }


                if (!patient.isActive()) {

                    System.out.println(
                            "Patient is inactive.");

                    continue;
                }


                return patient;


            } catch (Exception e) {

                System.out.println(
                        "Invalid Patient ID : "
                        + e.getMessage());
            }
        }
    }


    // =========================================================
    // READ DOCTOR
    // =========================================================

    private static Doctor readDoctor()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Doctor ID : ");

            String doctorId =
                    sc.nextLine().trim();


            if (doctorId.isEmpty()) {

                System.out.println(
                        "Doctor ID cannot be empty.");

                continue;
            }


            try {

                Doctor doctor =
                        doctorServiceImpl
                                .getDoctorById(
                                        doctorId);


                if (doctor == null) {

                    System.out.println(
                            "Doctor not found.");

                    continue;
                }


                return doctor;


            } catch (Exception e) {

                System.out.println(
                        "Invalid Doctor ID : "
                        + e.getMessage());
            }
        }
    }


    // =========================================================
    // READ INSTRUCTIONS
    // =========================================================

    private static String readInstructions()
            throws ValidationException {

        while (true) {

            System.out.print(
                    "Instructions : ");

            String instructions =
                    sc.nextLine().trim();


            if (instructions.isEmpty()) {

                System.out.println(
                        "Instructions cannot be empty.");

                continue;
            }


            if (instructions.length() > 255) {

                System.out.println(
                        "Instructions cannot exceed "
                        + "255 characters.");

                continue;
            }


            return instructions;
        }
    }


    // =========================================================
    // DISPLAY AVAILABLE MEDICINES
    // =========================================================

    private static List<Medicine>
    getAvailableMedicines() {

        List<Medicine> medicines =
                medicineServiceImpl
                        .getAllMedicines();


        if (medicines == null) {

            return new ArrayList<>();
        }


        return medicines;
    }


    // =========================================================
    // READ MULTIPLE MEDICINES
    // =========================================================

    private static List<PrescriptionMedicine>
    readMedicines()
            throws ValidationException {

        List<PrescriptionMedicine> selected =
                new ArrayList<>();


        List<Medicine> available =
                getAvailableMedicines();


        System.out.println();
        System.out.println(
                "========== AVAILABLE MEDICINES ==========");


        if (available.isEmpty()) {

            System.out.println(
                    "No active medicines available.");

            return selected;
        }


        System.out.printf(
                "%-15s %-25s %-15s%n",
                "MEDICINE ID",
                "MEDICINE NAME",
                "PRICE");

        System.out.println(
                "--------------------------------------------------------");


        for (Medicine medicine : available) {

            System.out.printf(
                    "%-15s %-25s ₹%-14.2f%n",
                    medicine.getMedicineId(),
                    medicine.getMedicineName(),
                    medicine.getPrice());
        }


        System.out.println(
                "--------------------------------------------------------");


        int count =
                readNonNegativeInteger(
                        "How many medicines? : ");


        if (count == 0) {

            return selected;
        }


        Set<String> selectedIds =
                new HashSet<>();


        for (int i = 1; i <= count; i++) {

            System.out.println();
            System.out.println(
                    "----- Medicine "
                    + i
                    + " -----");


            Medicine medicine;


            while (true) {

                System.out.print(
                        "Select Medicine ID : ");

                String medicineId =
                        sc.nextLine().trim();


                if (medicineId.isEmpty()) {

                    System.out.println(
                            "Medicine ID cannot be empty.");

                    continue;
                }


                try {

                    medicine =
                            medicineServiceImpl
                                    .getMedicineById(
                                            medicineId);


                    if (medicine == null) {

                        System.out.println(
                                "Medicine not found.");

                        continue;
                    }


                    if (!medicine.isActive()) {

                        System.out.println(
                                "Medicine is inactive.");

                        continue;
                    }


                    if (selectedIds.contains(
                            medicine.getMedicineId())) {

                        System.out.println(
                                "Medicine already selected. "
                                + "Choose another medicine.");

                        continue;
                    }


                    break;


                } catch (Exception e) {

                    System.out.println(
                            "Invalid Medicine ID : "
                            + e.getMessage());
                }
            }


            String dosage;


            while (true) {

                System.out.print(
                        "Dosage : ");

                dosage =
                        sc.nextLine().trim();


                if (dosage.isEmpty()) {

                    System.out.println(
                            "Dosage cannot be empty.");

                    continue;
                }


                if (dosage.length() > 100) {

                    System.out.println(
                            "Dosage cannot exceed "
                            + "100 characters.");

                    continue;
                }


                break;
            }


            int quantity =
                    readPositiveInteger(
                            "Quantity : ");


            PrescriptionMedicine
                    prescriptionMedicine =
                    new PrescriptionMedicine();


            prescriptionMedicine.setMedicineId(
                    medicine.getMedicineId());

            prescriptionMedicine.setDosage(
                    dosage);

            prescriptionMedicine.setQuantity(
                    quantity);


            selected.add(
                    prescriptionMedicine);


            selectedIds.add(
                    medicine.getMedicineId());
        }


        return selected;
    }


    // =========================================================
    // DISPLAY AVAILABLE TESTS
    // =========================================================

    private static List<LaboratoryTest>
    getAvailableTests() {

        List<LaboratoryTest> tests =
                laboratoryTestServiceImpl
                        .getAllTests();


        if (tests == null) {

            return new ArrayList<>();
        }


        return tests;
    }


    // =========================================================
    // READ MULTIPLE LABORATORY TESTS
    // =========================================================

    private static List<PrescriptionTest>
    readTests()
            throws ValidationException {

        List<PrescriptionTest> selected =
                new ArrayList<>();


        List<LaboratoryTest> available =
                getAvailableTests();


        System.out.println();
        System.out.println(
                "========== AVAILABLE LABORATORY TESTS ==========");


        if (available.isEmpty()) {

            System.out.println(
                    "No active laboratory tests available.");

            return selected;
        }


        System.out.printf(
                "%-15s %-25s %-15s%n",
                "TEST ID",
                "TEST NAME",
                "CHARGE");

        System.out.println(
                "--------------------------------------------------------");


        for (LaboratoryTest test :
                available) {

            System.out.printf(
                    "%-15s %-25s ₹%-14.2f%n",
                    test.getTestId(),
                    test.getTestName(),
                    test.getTestCharge());
        }


        System.out.println(
                "--------------------------------------------------------");


        int count =
                readNonNegativeInteger(
                        "How many laboratory tests? : ");


        if (count == 0) {

            return selected;
        }


        Set<String> selectedIds =
                new HashSet<>();


        for (int i = 1; i <= count; i++) {

            System.out.println();
            System.out.println(
                    "----- Laboratory Test "
                    + i
                    + " -----");


            LaboratoryTest test;


            while (true) {

                System.out.print(
                        "Select Test ID : ");

                String testId =
                        sc.nextLine().trim();


                if (testId.isEmpty()) {

                    System.out.println(
                            "Test ID cannot be empty.");

                    continue;
                }


                try {

                    test =
                            laboratoryTestServiceImpl
                                    .getTestById(
                                            testId);


                    if (test == null) {

                        System.out.println(
                                "Laboratory test not found.");

                        continue;
                    }


                    if (!test.isActive()) {

                        System.out.println(
                                "Laboratory test is inactive.");

                        continue;
                    }


                    if (selectedIds.contains(
                            test.getTestId())) {

                        System.out.println(
                                "Test already selected. "
                                + "Choose another test.");

                        continue;
                    }


                    break;


                } catch (Exception e) {

                    System.out.println(
                            "Invalid Test ID : "
                            + e.getMessage());
                }
            }


            PrescriptionTest
                    prescriptionTest =
                    new PrescriptionTest();


            prescriptionTest.setTestId(
                    test.getTestId());


            selected.add(
                    prescriptionTest);


            selectedIds.add(
                    test.getTestId());
        }


        return selected;
    }


    // =========================================================
    // READ NON-NEGATIVE INTEGER
    // =========================================================

    private static int readNonNegativeInteger(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();


            try {

                int value =
                        Integer.parseInt(input);


                if (value < 0) {

                    System.out.println(
                            "Value cannot be negative.");

                    continue;
                }


                return value;


            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }


    // =========================================================
    // READ POSITIVE INTEGER
    // =========================================================

    private static int readPositiveInteger(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();


            try {

                int value =
                        Integer.parseInt(input);


                if (value <= 0) {

                    System.out.println(
                            "Value must be greater than zero.");

                    continue;
                }


                return value;


            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid positive number.");
            }
        }
    }


    // =========================================================
    // VIEW PRESCRIPTION BY ID
    // =========================================================

    private static void viewPrescriptionById() {

        try {

            System.out.println();
            System.out.println(
                    "========== VIEW PRESCRIPTION ==========");


            System.out.print(
                    "Prescription ID : ");

            String prescriptionId =
                    sc.nextLine().trim();


            Prescription prescription =
                    prescriptionServiceImpl
                            .getPrescriptionById(
                                    prescriptionId);


            displayPrescription(
                    prescription);


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                    + e.getMessage());
        }
    }


    // =========================================================
    // VIEW ALL PRESCRIPTIONS
    // =========================================================

    private static void viewAllPrescriptions() {

        try {

            List<Prescription> prescriptions =
                    prescriptionServiceImpl
                            .getAllPrescriptions();


            if (prescriptions == null
                    || prescriptions.isEmpty()) {

                System.out.println(
                        "No prescriptions found.");

                return;
            }


            for (Prescription prescription :
                    prescriptions) {

                displayPrescription(
                        prescription);
            }


        } catch (Exception e) {

            System.out.println(
                    "Error : "
                    + e.getMessage());
        }
    }


    // =========================================================
    // DISPLAY PRESCRIPTION
    // =========================================================

    private static void displayPrescription(
            Prescription prescription) {

        System.out.println();
        System.out.println(
                "==============================================");

        System.out.println(
                "           PRESCRIPTION DETAILS");

        System.out.println(
                "==============================================");


        System.out.println(
                "Prescription ID : "
                + prescription.getPrescriptionId());


        if (prescription.getPatient() != null) {

            System.out.println(
                    "Patient ID      : "
                    + prescription.getPatient()
                            .getPatientId());
        }


        if (prescription.getDoctor() != null) {

            System.out.println(
                    "Doctor ID       : "
                    + prescription.getDoctor()
                            .getStaffId());
        }


        System.out.println(
                "Prescription Date : "
                + prescription.getPrescriptionDate());


        System.out.println(
                "Instructions      : "
                + prescription.getInstructions());


        // -------------------------------------------------
        // Medicines
        // -------------------------------------------------

        System.out.println();
        System.out.println(
                "MEDICINES");

        System.out.println(
                "------------------------------------------------");

        System.out.printf(
                "%-15s %-25s %-10s%n",
                "MEDICINE ID",
                "DOSAGE",
                "QUANTITY");


        System.out.println(
                "------------------------------------------------");


        if (prescription.getMedicines() == null
                || prescription.getMedicines()
                        .isEmpty()) {

            System.out.println(
                    "No medicines.");
        } else {

            for (PrescriptionMedicine medicine :
                    prescription.getMedicines()) {

                System.out.printf(
                        "%-15s %-25s %-10d%n",

                        medicine.getMedicineId(),

                        medicine.getDosage(),

                        medicine.getQuantity());
            }
        }


        // -------------------------------------------------
        // Laboratory Tests
        // -------------------------------------------------

        System.out.println();
        System.out.println(
                "LABORATORY TESTS");

        System.out.println(
                "------------------------------------------------");

        System.out.printf(
                "%-15s %-25s%n",
                "TEST ID",
                "TEST NAME");


        System.out.println(
                "------------------------------------------------");


        if (prescription.getTests() == null
                || prescription.getTests()
                        .isEmpty()) {

            System.out.println(
                    "No laboratory tests.");

        } else {

            for (PrescriptionTest prescriptionTest :
                    prescription.getTests()) {

                try {

                    LaboratoryTest test =
                            laboratoryTestServiceImpl
                                    .getTestById(
                                            prescriptionTest
                                                    .getTestId());


                    if (test != null) {

                        System.out.printf(
                                "%-15s %-25s%n",

                                test.getTestId(),

                                test.getTestName());

                    } else {

                        System.out.printf(
                                "%-15s %-25s%n",

                                prescriptionTest
                                        .getTestId(),

                                "Unknown Test");
                    }


                } catch (Exception e) {

                    System.out.printf(
                            "%-15s %-25s%n",

                            prescriptionTest
                                    .getTestId(),

                            "Unknown Test");
                }
            }
        }


        System.out.println(
                "==============================================");
    }


    // =========================================================
    // UPDATE PRESCRIPTION
    // =========================================================
    // Date is NOT entered again.
    // The original prescription date is preserved.
    // =========================================================

    private static void updatePrescription() {

        try {

            System.out.println();
            System.out.println(
                    "========== UPDATE PRESCRIPTION ==========");


            System.out.print(
                    "Prescription ID : ");

            String prescriptionId =
                    sc.nextLine().trim();


            Prescription existing =
                    prescriptionServiceImpl
                            .getPrescriptionById(
                                    prescriptionId);


            // -------------------------------------------------
            // Patient
            // -------------------------------------------------

            Patient patient =
                    readPatient();


            // -------------------------------------------------
            // Doctor
            // -------------------------------------------------

            Doctor doctor =
                    readDoctor();


            // -------------------------------------------------
            // Instructions
            // -------------------------------------------------

            String instructions =
                    readInstructions();


            // -------------------------------------------------
            // Read medicines again
            // -------------------------------------------------

            List<PrescriptionMedicine> medicines =
                    readMedicines();


            // -------------------------------------------------
            // Read tests again
            // -------------------------------------------------

            List<PrescriptionTest> tests =
                    readTests();


            if (medicines.isEmpty()
                    && tests.isEmpty()) {

                System.out.println(
                        "Prescription must contain "
                        + "at least one medicine or test.");

                return;
            }


            Prescription updated =
                    new Prescription();


            updated.setPrescriptionId(
                    existing.getPrescriptionId());

            updated.setPatient(
                    patient);

            updated.setDoctor(
                    doctor);

            // Keep original date
            updated.setPrescriptionDate(
                    existing.getPrescriptionDate());

            updated.setInstructions(
                    instructions);

            updated.setMedicines(
                    medicines);

            updated.setTests(
                    tests);

            updated.setActive(
                    existing.isActive());


            prescriptionServiceImpl
                    .updatePrescription(
                            updated);


            System.out.println();
            System.out.println(
                    "Prescription Updated Successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Update Failed : "
                    + e.getMessage());
        }
    }


    // =========================================================
    // DELETE PRESCRIPTION
    // =========================================================

    private static void deletePrescription() {

        try {

            System.out.println();
            System.out.println(
                    "========== DELETE PRESCRIPTION ==========");


            System.out.print(
                    "Prescription ID : ");

            String prescriptionId =
                    sc.nextLine().trim();


            System.out.print(
                    "Are you sure? (Y/N) : ");


            String confirmation =
                    sc.nextLine().trim();


            if (!confirmation.equalsIgnoreCase("Y")) {

                System.out.println(
                        "Deletion cancelled.");

                return;
            }


            prescriptionServiceImpl
                    .deletePrescription(
                            prescriptionId);


            System.out.println();
            System.out.println(
                    "Prescription Deleted Successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Delete Failed : "
                    + e.getMessage());
        }
    }
}