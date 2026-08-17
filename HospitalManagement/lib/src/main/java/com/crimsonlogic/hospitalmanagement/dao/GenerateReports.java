package com.crimsonlogic.hospitalmanagement.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.crimsonlogic.hospitalmanagement.enums.AdmissionStatus;
import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.model.*;
import com.crimsonlogic.hospitalmanagement.services.*;

/**
 * All 35 Stream API reports in one file.
 *
 * The reports are written for the current hospital-management models.
 * Where the current model does not contain a field from the original
 * task list, the report uses the closest data actually available instead
 * of inventing a getter that does not exist.
 */
public class GenerateReports {

    // =========================================================
    // ALL ServiceImplS IN ONE FILE
    // =========================================================

    private final PatientServiceImpl patientServiceImpl = new PatientServiceImpl();
    private final DoctorServiceImpl doctorServiceImpl = new DoctorServiceImpl();
    private final AppointmentServiceImpl appointmentServiceImpl = new AppointmentServiceImpl();
    private final MedicineServiceImpl medicineServiceImpl = new MedicineServiceImpl();
    private final PrescriptionServiceImpl prescriptionServiceImpl = new PrescriptionServiceImpl();
    private final BillServiceImpl billServiceImpl = new BillServiceImpl();
    private final AdmissionServiceImpl admissionServiceImpl = new AdmissionServiceImpl();
    private final BedServiceImpl bedServiceImpl = new BedServiceImpl();
    private final PatientTestServiceImpl patientTestServiceImpl = new PatientTestServiceImpl();
    private final LaboratoryTestServiceImpl laboratoryTestServiceImpl = new LaboratoryTestServiceImpl();

    private final Scanner scanner = new Scanner(System.in);

    // =========================================================
    // REPORT MENU
    // =========================================================

    public void generateReports() {

        while (true) {

            System.out.println("\n========================================================");
            System.out.println("                    HOSPITAL REPORTS");
            System.out.println("========================================================");

            System.out.println(" 1. List admitted patients");
            System.out.println(" 2. List available doctors");
            System.out.println(" 3. Filter patients by department");
            System.out.println(" 4. Filter pending appointments");
            System.out.println(" 5. Sort patients by age");
            System.out.println(" 6. Sort doctors by experience");
            System.out.println(" 7. Top 5 expensive bills");
            System.out.println(" 8. Highest consultation fee");
            System.out.println(" 9. Lowest consultation fee");
            System.out.println("10. Total hospital revenue");
            System.out.println("11. Average bill amount");
            System.out.println("12. Total pharmacy sales");
            System.out.println("13. Group patients by department");
            System.out.println("14. Group doctors by specialization");
            System.out.println("15. Group appointments by status");
            System.out.println("16. Count appointments per doctor");
            System.out.println("17. Count patients per ward");
            System.out.println("18. Most consulted doctor");
            System.out.println("19. Most prescribed medicine");
            System.out.println("20. Distinct specializations");
            System.out.println("21. Distinct medicine categories");
            System.out.println("22. Earliest appointment");
            System.out.println("23. Latest discharge");
            System.out.println("24. Find overdue bills");
            System.out.println("25. Find out-of-stock medicines");
            System.out.println("26. Partition paid/unpaid bills");
            System.out.println("27. Join patient names");
            System.out.println("28. Summary statistics for bills");
            System.out.println("29. Any ICU beds available?");
            System.out.println("30. All test reports delivered?");
            System.out.println("31. FlatMap prescriptions into medicines");
            System.out.println("32. Collect immutable patient list");
            System.out.println("33. Optional lookup for patient ID");
            System.out.println("34. Custom collector for department revenue");
            System.out.println("35. Sequential vs parallel streams");
            System.out.println(" 0. Back");
            System.out.println("========================================================");
            System.out.print("Enter Choice : ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1: report1(); break;
                    case 2: report2(); break;
                    case 3: report3(); break;
                    case 4: report4(); break;
                    case 5: report5(); break;
                    case 6: report6(); break;
                    case 7: report7(); break;
                    case 8: report8(); break;
                    case 9: report9(); break;
                    case 10: report10(); break;
                    case 11: report11(); break;
                    case 12: report12(); break;
                    case 13: report13(); break;
                    case 14: report14(); break;
                    case 15: report15(); break;
                    case 16: report16(); break;
                    case 17: report17(); break;
                    case 18: report18(); break;
                   // case 19: report19(); break;
                    case 20: report20(); break;
                    case 21: report21(); break;
                    case 22: report22(); break;
                    case 23: report23(); break;
                    case 24: report24(); break;
                    case 25: report25(); break;
                    case 26: report26(); break;
                    case 27: report27(); break;
                    case 28: report28(); break;
                    case 29: report29(); break;
                    case 30: report30(); break;
                   // case 31: report31(); break;
                    case 32: report32(); break;
                    case 33: report33(); break;
                    case 34: report34(); break;
                    case 35: report35(); break;
                    case 0: return;
                    default: System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Report Error : " + e.getMessage());
            }
        }
    }

    // =========================================================
    // 1. LIST ADMITTED PATIENTS
    // =========================================================

    private void report1() {
        System.out.println("\n========== ADMITTED PATIENTS ==========");

        admissionServiceImpl.getAllAdmissions()
                .stream()
                .filter(a -> a.getStatus() == AdmissionStatus.ADMITTED)
                .map(Admission::getPatient)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

    // =========================================================
    // 2. LIST AVAILABLE DOCTORS
    // =========================================================

    private void report2() {
        System.out.println("\n========== AVAILABLE DOCTORS ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .filter(Doctor::isActive)
                .forEach(System.out::println);
    }

    // =========================================================
    // 3. FILTER PATIENTS BY DEPARTMENT
    // =========================================================

    private void report3() {
        System.out.print("\nEnter Department Name : ");
        String department = scanner.nextLine().trim();

        if (department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        // Patient has no direct department field.
        // Patient -> Appointment -> Doctor -> Department.
        Set<String> patientIds = appointmentServiceImpl.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getDoctor() != null)
                .filter(a -> a.getDoctor().getDepartment() != null)
                .filter(a -> a.getDoctor().getDepartment().getDepartmentName()
                        .equalsIgnoreCase(department))
                .map(a -> a.getPatient().getPatientId())
                .collect(Collectors.toSet());

        patientServiceImpl.getAllPatients()
                .stream()
                .filter(p -> patientIds.contains(p.getPatientId()))
                .forEach(System.out::println);
    }

    // =========================================================
    // 4. FILTER PENDING APPOINTMENTS
    // =========================================================

    private void report4() {
        System.out.println("\n========== PENDING APPOINTMENTS ==========");

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // Current Appointment model has no status field.
        // Future active appointments are treated as pending.
        appointmentServiceImpl.getAllAppointments()
                .stream()
                .filter(Appointment::isActive)
                .filter(a -> a.getAppointmentDate() != null)
                .filter(a -> a.getAppointmentDate().isAfter(today)
                        || (a.getAppointmentDate().isEqual(today)
                        && a.getAppointmentTime() != null
                        && a.getAppointmentTime().isAfter(now)))
                .forEach(System.out::println);
    }

    // =========================================================
    // 5. SORT PATIENTS BY AGE
    // =========================================================

    private void report5() {
        System.out.println("\n========== PATIENTS SORTED BY AGE ==========");

        patientServiceImpl.getAllPatients()
                .stream()
                .sorted(Comparator.comparingInt(Patient::getAge))
                .forEach(System.out::println);
    }

    // =========================================================
    // 6. SORT DOCTORS BY EXPERIENCE
    // =========================================================

    private void report6() {
        System.out.println("\n========== DOCTORS BY EXPERIENCE ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .sorted(Comparator.comparingInt(Doctor::getExperience).reversed())
                .forEach(System.out::println);
    }

    // =========================================================
    // 7. TOP 5 EXPENSIVE BILLS
    // =========================================================

    private void report7() {
        System.out.println("\n========== TOP 5 EXPENSIVE BILLS ==========");

        billServiceImpl.getAllBills()
                .stream()
                .sorted(Comparator.comparingDouble(Bill::getTotalAmount).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    // =========================================================
    // 8. HIGHEST CONSULTATION FEE
    // =========================================================

    private void report8() {
        System.out.println("\n========== HIGHEST CONSULTATION FEE ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .max(Comparator.comparingDouble(Doctor::getConsultationFee))
                .ifPresent(d -> System.out.println(
                        d.getName() + " -> ₹" + d.getConsultationFee()));
    }

    // =========================================================
    // 9. LOWEST CONSULTATION FEE
    // =========================================================

    private void report9() {
        System.out.println("\n========== LOWEST CONSULTATION FEE ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .min(Comparator.comparingDouble(Doctor::getConsultationFee))
                .ifPresent(d -> System.out.println(
                        d.getName() + " -> ₹" + d.getConsultationFee()));
    }

    // =========================================================
    // 10. TOTAL HOSPITAL REVENUE
    // =========================================================

    private void report10() {
        double revenue = billServiceImpl.getAllBills()
                .stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();

        System.out.println("\nTotal Hospital Revenue : ₹" + revenue);
    }

    // =========================================================
    // 11. AVERAGE BILL AMOUNT
    // =========================================================

    private void report11() {
        double average = billServiceImpl.getAllBills()
                .stream()
                .mapToDouble(Bill::getTotalAmount)
                .average()
                .orElse(0.0);

        System.out.println("\nAverage Bill Amount : ₹" + average);
    }

    // =========================================================
    // 12. TOTAL PHARMACY SALES
    // =========================================================

    private void report12() {
        double sales = billServiceImpl.getAllBills()
                .stream()
                .mapToDouble(Bill::getMedicineCharges)
                .sum();

        System.out.println("\nTotal Pharmacy Sales : ₹" + sales);
    }

    // =========================================================
    // 13. GROUP PATIENTS BY DEPARTMENT
    // =========================================================

    private void report13() {
        System.out.println("\n========== PATIENTS BY DEPARTMENT ==========");

        Map<String, Set<String>> grouped =
                appointmentServiceImpl.getAllAppointments()
                        .stream()
                        .filter(a -> a.getPatient() != null)
                        .filter(a -> a.getDoctor() != null)
                        .filter(a -> a.getDoctor().getDepartment() != null)
                        .collect(Collectors.groupingBy(
                                a -> a.getDoctor().getDepartment().getDepartmentName(),
                                Collectors.mapping(
                                        a -> a.getPatient().getPatientId(),
                                        Collectors.toCollection(LinkedHashSet::new))));

        grouped.forEach((department, patients) -> {
            System.out.println("\nDepartment : " + department);
            patients.forEach(System.out::println);
        });
    }

    // =========================================================
    // 14. GROUP DOCTORS BY SPECIALIZATION
    // =========================================================

    private void report14() {
        System.out.println("\n========== DOCTORS BY SPECIALIZATION ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .filter(d -> d.getSpecialization() != null)
                .collect(Collectors.groupingBy(Doctor::getSpecialization))
                .forEach((specialization, doctors) -> {
                    System.out.println("\nSpecialization : " + specialization);
                    doctors.forEach(System.out::println);
                });
    }

    // =========================================================
    // 15. GROUP APPOINTMENTS BY STATUS
    // =========================================================

    private void report15() {
        System.out.println("\n========== APPOINTMENTS BY STATUS ==========");

        // Current Appointment has no status property.
        // Its existing active flag is used as the current state.
        appointmentServiceImpl.getAllAppointments()
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.isActive() ? "ACTIVE" : "INACTIVE"))
                .forEach((status, appointments) -> {
                    System.out.println("\nStatus : " + status);
                    appointments.forEach(System.out::println);
                });
    }

    // =========================================================
    // 16. COUNT APPOINTMENTS PER DOCTOR
    // =========================================================

    private void report16() {
        System.out.println("\n========== APPOINTMENTS PER DOCTOR ==========");

        appointmentServiceImpl.getAllAppointments()
                .stream()
                .filter(a -> a.getDoctor() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getDoctor().getName(),
                        Collectors.counting()))
                .forEach((doctor, count) ->
                        System.out.println(doctor + " -> " + count));
    }

    // =========================================================
    // 17. COUNT PATIENTS PER WARD
    // =========================================================

    private void report17() {
        System.out.println("\n========== PATIENTS PER WARD ==========");

        admissionServiceImpl.getAllAdmissions()
                .stream()
                .filter(a -> a.getBed() != null)
                .filter(a -> a.getBed().getWard() != null)
                .filter(a -> a.getPatient() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getBed().getWard().getWardName(),
                        Collectors.mapping(
                                a -> a.getPatient().getPatientId(),
                                Collectors.toCollection(LinkedHashSet::new))))
                .forEach((ward, patients) ->
                        System.out.println(
                                ward + " -> " + patients.size() + " patient(s)"));
    }

    // =========================================================
    // 18. MOST CONSULTED DOCTOR
    // =========================================================

    private void report18() {
        System.out.println("\n========== MOST CONSULTED DOCTOR ==========");

        appointmentServiceImpl.getAllAppointments()
                .stream()
                .filter(a -> a.getDoctor() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getDoctor().getName(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e ->
                        System.out.println(
                                e.getKey() + " -> " + e.getValue()
                                        + " appointments"));
    }

    // =========================================================
    // 19. MOST PRESCRIBED MEDICINE
    // =========================================================

//    private void report19() {
//        System.out.println("\n========== MOST PRESCRIBED MEDICINE ==========");
//
//        prescriptionServiceImpl.getAllPrescriptions()
//                .stream()
//                .filter(p -> p.getMedicine() != null)
//                .collect(Collectors.groupingBy(
//                        p -> p.getMedicine().getMedicineName(),
//                        Collectors.counting()))
//                .entrySet()
//                .stream()
//                .max(Map.Entry.comparingByValue())
//                .ifPresent(e ->
//                        System.out.println(
//                                e.getKey() + " -> " + e.getValue()
//                                        + " prescription(s)"));
//    }

    // =========================================================
    // 20. DISTINCT SPECIALIZATIONS
    // =========================================================

    private void report20() {
        System.out.println("\n========== DISTINCT SPECIALIZATIONS ==========");

        doctorServiceImpl.getAllDoctors()
                .stream()
                .map(Doctor::getSpecialization)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

    // =========================================================
    // 21. DISTINCT MEDICINE CATEGORIES
    // =========================================================

    private void report21() {
        System.out.println("\n========== DISTINCT MEDICINE MANUFACTURERS ==========");

        // Medicine currently has no category field.
        medicineServiceImpl.getAllMedicines()
                .stream()
                .map(Medicine::getManufacturer)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

    // =========================================================
    // 22. EARLIEST APPOINTMENT
    // =========================================================

    private void report22() {
        System.out.println("\n========== EARLIEST APPOINTMENT ==========");

        appointmentServiceImpl.getAllAppointments()
                .stream()
                .filter(a -> a.getAppointmentDate() != null)
                .min(Comparator.comparing(Appointment::getAppointmentDate)
                        .thenComparing(a -> a.getAppointmentTime() == null
                                ? LocalTime.MAX
                                : a.getAppointmentTime()))
                .ifPresent(System.out::println);
    }

    // =========================================================
    // 23. LATEST DISCHARGE
    // =========================================================

    private void report23() {
        System.out.println("\n========== LATEST DISCHARGE ==========");

        admissionServiceImpl.getAllAdmissions()
                .stream()
                .filter(a -> a.getDischargeDate() != null)
                .max(Comparator.comparing(Admission::getDischargeDate))
                .ifPresent(a ->
                        System.out.println(
                                "Patient : " + a.getPatient()
                                        + "\nDischarge : " + a.getDischargeDate()));
    }

    // =========================================================
    // 24. OVERDUE BILLS
    // =========================================================

    private void report24() {
        System.out.println("\n========== OVERDUE BILLS ==========");

        LocalDate cutoff = LocalDate.now().minusDays(30);

        billServiceImpl.getAllBills()
                .stream()
                .filter(b -> b.getBillDate() != null)
                .filter(b -> b.getStatus() == null
                        || !"PAID".equalsIgnoreCase(b.getStatus()))
                .filter(b -> b.getBillDate().isBefore(cutoff))
                .forEach(System.out::println);
    }

    // =========================================================
    // 25. OUT OF STOCK MEDICINES
    // =========================================================

    private void report25() {
        System.out.println("\n========== MEDICINE AVAILABILITY ==========");

        // No stock quantity exists in the current Medicine model.
        // Inactive medicines are therefore shown as unavailable.
        medicineServiceImpl.getAllMedicines()
                .stream()
                .filter(m -> !m.isActive())
                .forEach(System.out::println);

        System.out.println(
                "\nNote: stock quantity is not stored in the current Medicine model.");
    }

    // =========================================================
    // 26. PARTITION PAID / UNPAID BILLS
    // =========================================================

    private void report26() {
        System.out.println("\n========== PAID / UNPAID BILLS ==========");

        Map<Boolean, List<Bill>> partition =
                billServiceImpl.getAllBills()
                        .stream()
                        .collect(Collectors.partitioningBy(
                                b -> "PAID".equalsIgnoreCase(b.getStatus())));

        System.out.println("\nPAID BILLS");
        partition.get(true).forEach(System.out::println);

        System.out.println("\nUNPAID BILLS");
        partition.get(false).forEach(System.out::println);
    }

    // =========================================================
    // 27. JOIN PATIENT NAMES
    // =========================================================

    private void report27() {
        System.out.println("\n========== PATIENT NAMES ==========");

        String names = patientServiceImpl.getAllPatients()
                .stream()
                .map(Patient::getPatientName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        System.out.println(names);
    }

    // =========================================================
    // 28. BILL SUMMARY STATISTICS
    // =========================================================

    private void report28() {
        System.out.println("\n========== BILL SUMMARY STATISTICS ==========");

        DoubleSummaryStatistics statistics =
                billServiceImpl.getAllBills()
                        .stream()
                        .collect(Collectors.summarizingDouble(
                                Bill::getTotalAmount));

        System.out.println("Number of Bills : " + statistics.getCount());
        System.out.println("Total           : ₹" + statistics.getSum());
        System.out.println("Average         : ₹" + statistics.getAverage());
        System.out.println("Highest         : ₹" + statistics.getMax());
        System.out.println("Lowest          : ₹" + statistics.getMin());
    }

    // =========================================================
    // 29. ANY ICU BEDS AVAILABLE?
    // =========================================================

    private void report29() {
        System.out.println("\n========== ICU BED AVAILABILITY ==========");

        boolean available = bedServiceImpl.getAllBeds()
                .stream()
                .filter(Bed::isActive)
                .filter(b -> b.getWard() != null)
                .anyMatch(b ->
                        b.getWard().getWardType() == WardType.ICU
                                && "AVAILABLE".equalsIgnoreCase(
                                        b.getAvailability()));

        System.out.println("ICU Bed Available : " + available);
    }

    // =========================================================
    // 30. ALL TEST REPORTS DELIVERED?
    // =========================================================

    private void report30() {
        System.out.println("\n========== LABORATORY TEST REPORT STATUS ==========");

        List<PatientTest> tests = patientTestServiceImpl.getAllPatientTests();

        if (tests == null || tests.isEmpty()) {
            System.out.println("No patient laboratory tests found.");
            return;
        }

        boolean allCompleted = tests.stream()
                .allMatch(t -> t.getStatus() != null
                        && t.getStatus().toString()
                                .equalsIgnoreCase("COMPLETED"));

        System.out.println(
                "All test reports delivered : " + allCompleted);

        tests.stream()
                .filter(t -> t.getStatus() == null
                        || !t.getStatus().toString()
                                .equalsIgnoreCase("COMPLETED"))
                .forEach(System.out::println);
    }

    // =========================================================
    // 31. FLATMAP PRESCRIPTIONS INTO MEDICINES
    // =========================================================

 // =========================================================
 // 31. FLATTEN PRESCRIPTIONS INTO MEDICINES
 // =========================================================

// private void report31() {
//
//     System.out.println(
//             "\n========== PRESCRIPTION -> MEDICINE ==========");

//     prescriptionServiceImpl
//             .getAllPrescriptions()
//             .stream()
//             .filter(p -> p != null)
//             .filter(p -> p.getMedicine() != null)
//             .flatMap(
//                     p -> java.util.stream.Stream.of(
//                             p.getMedicine()))
//             .map(
//                     medicine ->
//                             medicine.getMedicineName())
//             .filter(
//                     name -> name != null)
//             .forEach(
//                     System.out::println);
// }

    // =========================================================
    // 32. COLLECT IMMUTABLE PATIENT LIST
    // =========================================================

    private void report32() {
        System.out.println("\n========== IMMUTABLE PATIENT LIST ==========");

        List<Patient> immutablePatients =
                patientServiceImpl.getAllPatients()
                        .stream()
                        .toList();

        immutablePatients.forEach(System.out::println);

        System.out.println("\nList created using Stream.toList().");
    }

    // =========================================================
    // 33. OPTIONAL LOOKUP FOR PATIENT ID
    // =========================================================

    private void report33() {
        System.out.print("\nEnter Patient ID : ");
        String patientId = scanner.nextLine().trim();

        Optional<Patient> result = patientServiceImpl.getAllPatients()
                .stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst();

        result.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Patient Not Found."));
    }

    // =========================================================
    // 34. CUSTOM COLLECTOR FOR DEPARTMENT REVENUE
    // =========================================================

    private void report34() {
        System.out.println("\n========== DEPARTMENT REVENUE ==========");

        // Patient has no direct Department field.
        // Build patient -> department using appointments.
        Map<String, String> departmentByPatient =
                appointmentServiceImpl.getAllAppointments()
                        .stream()
                        .filter(a -> a.getPatient() != null)
                        .filter(a -> a.getDoctor() != null)
                        .filter(a -> a.getDoctor().getDepartment() != null)
                        .collect(Collectors.toMap(
                                a -> a.getPatient().getPatientId(),
                                a -> a.getDoctor().getDepartment().getDepartmentName(),
                                (oldValue, newValue) -> oldValue));

        // Actual custom Collector.
        Collector<Bill, Map<String, Double>, Map<String, Double>> collector =
                Collector.of(
                        HashMap::new,
                        (map, bill) -> {
                            if (bill.getPatient() == null) {
                                return;
                            }

                            String department = departmentByPatient.get(
                                    bill.getPatient().getPatientId());

                            if (department == null) {
                                department = "UNKNOWN";
                            }

                            map.merge(
                                    department,
                                    bill.getTotalAmount(),
                                    Double::sum);
                        },
                        (map1, map2) -> {
                            map2.forEach((department, amount) ->
                                    map1.merge(
                                            department,
                                            amount,
                                            Double::sum));
                            return map1;
                        });

        billServiceImpl.getAllBills()
                .stream()
                .collect(collector)
                .forEach((department, amount) ->
                        System.out.println(
                                department + " -> ₹" + amount));
    }

    // =========================================================
    // 35. SEQUENTIAL VS PARALLEL STREAM
    // =========================================================

    private void report35() {
        System.out.println("\n========== SEQUENTIAL VS PARALLEL ==========");

        List<Patient> patients = patientServiceImpl.getAllPatients();

        long sequentialStart = System.nanoTime();

        long sequentialCount = patients.stream()
                .filter(p -> p.getAge() >= 18)
                .count();

        long sequentialEnd = System.nanoTime();

        long parallelStart = System.nanoTime();

        long parallelCount = patients.parallelStream()
                .filter(p -> p.getAge() >= 18)
                .count();

        long parallelEnd = System.nanoTime();

        System.out.println("Sequential Count : " + sequentialCount);
        System.out.println("Parallel Count   : " + parallelCount);
        System.out.println("Sequential Time  : "
                + (sequentialEnd - sequentialStart) + " ns");
        System.out.println("Parallel Time    : "
                + (parallelEnd - parallelStart) + " ns");
    }
}
