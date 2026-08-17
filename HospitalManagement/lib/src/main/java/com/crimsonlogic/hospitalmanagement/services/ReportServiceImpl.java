package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.enums.AdmissionStatus;
import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.mapper.IReportService;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;

@Service
public class ReportServiceImpl implements IReportService {

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final AdmissionServiceImpl admissionService =
            new AdmissionServiceImpl();

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final BillServiceImpl billService =
            new BillServiceImpl();

    private final MedicineServiceImpl medicineService =
            new MedicineServiceImpl();

    private final BedServiceImpl bedService =
            new BedServiceImpl();

    private final PatientTestServiceImpl patientTestService =
            new PatientTestServiceImpl();


    // =========================================================
    // 1. ADMITTED PATIENTS
    // =========================================================

    @Override
    public List<Patient> getAdmittedPatients() {

        return admissionService.getAllAdmissions()
                .stream()
                .filter(Objects::nonNull)
                .filter(admission ->
                        admission.getStatus()
                                == AdmissionStatus.ADMITTED)
                .map(Admission::getPatient)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }


    // =========================================================
    // 2. PATIENTS BY DEPARTMENT
    // =========================================================

    @Override
    public List<Patient> getPatientsByDepartment(
            String departmentName) {

        if (departmentName == null
                || departmentName.trim().isEmpty()) {

            return List.of();
        }

        String department = departmentName.trim();

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getDoctor() != null)
                .filter(a ->
                        a.getDoctor().getDepartment() != null)
                .filter(a ->
                        a.getDoctor()
                                .getDepartment()
                                .getDepartmentName() != null)
                .filter(a ->
                        department.equalsIgnoreCase(
                                a.getDoctor()
                                        .getDepartment()
                                        .getDepartmentName()))
                .map(Appointment::getPatient)
                .filter(Objects::nonNull)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Patient::getPatientId,
                                        patient -> patient,
                                        (existing, replacement)
                                                -> existing),
                                map ->
                                        map.values()
                                                .stream()
                                                .collect(
                                                        Collectors.toList())));
    }


    // =========================================================
    // 3. PATIENTS BY AGE
    // =========================================================

    @Override
    public List<Patient> getPatientsByAge() {

        return patientService
                .getAllPatients()
                .stream()
                .filter(Objects::nonNull)
                .sorted(
                        Comparator.comparingInt(
                                Patient::getAge))
                .collect(Collectors.toList());
    }


    // =========================================================
    // 4. PATIENTS BY WARD
    // =========================================================

    @Override
    public List<Patient> getPatientsByWard(
            String wardName) {

        if (wardName == null
                || wardName.trim().isEmpty()) {

            return List.of();
        }

        String ward = wardName.trim();

        return admissionService
                .getAllAdmissions()
                .stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getBed() != null)
                .filter(a ->
                        a.getBed().getWard() != null)
                .filter(a ->
                        a.getBed()
                                .getWard()
                                .getWardName() != null)
                .filter(a ->
                        ward.equalsIgnoreCase(
                                a.getBed()
                                        .getWard()
                                        .getWardName()))
                .map(Admission::getPatient)
                .filter(Objects::nonNull)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Patient::getPatientId,
                                        patient -> patient,
                                        (existing, replacement)
                                                -> existing),
                                map ->
                                        map.values()
                                                .stream()
                                                .collect(
                                                        Collectors.toList())));
    }


    // =========================================================
    // 5. AVAILABLE DOCTORS
    // =========================================================

    @Override
    public List<Doctor> getAvailableDoctors() {

        return doctorService
                .getAllDoctors()
                .stream()
                .filter(Doctor::isActive)
                .collect(Collectors.toList());
    }


    // =========================================================
    // 6. DOCTORS BY EXPERIENCE
    // =========================================================

    @Override
    public List<Doctor> getDoctorsByExperience() {

        return doctorService
                .getAllDoctors()
                .stream()
                .sorted(
                        Comparator.comparingInt(
                                Doctor::getExperience)
                                .reversed())
                .collect(Collectors.toList());
    }


    // =========================================================
    // 7. HIGHEST CONSULTATION FEE
    // =========================================================

    @Override
    public Doctor getHighestConsultationFeeDoctor() {

        return doctorService
                .getAllDoctors()
                .stream()
                .max(
                        Comparator.comparingDouble(
                                Doctor::getConsultationFee))
                .orElse(null);
    }


    // =========================================================
    // 8. LOWEST CONSULTATION FEE
    // =========================================================

    @Override
    public Doctor getLowestConsultationFeeDoctor() {

        return doctorService
                .getAllDoctors()
                .stream()
                .min(
                        Comparator.comparingDouble(
                                Doctor::getConsultationFee))
                .orElse(null);
    }


    // =========================================================
    // 9. DOCTORS BY SPECIALIZATION
    // =========================================================

    @Override
    public Map<String, List<Doctor>>
    getDoctorsBySpecialization() {

        return doctorService
                .getAllDoctors()
                .stream()
                .filter(doctor ->
                        doctor.getSpecialization() != null)
                .collect(
                        Collectors.groupingBy(
                                Doctor::getSpecialization));
    }


    // =========================================================
    // 10. APPOINTMENTS PER DOCTOR
    // =========================================================

    @Override
    public Map<String, Long>
    getAppointmentsPerDoctor() {

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(a -> a.getDoctor() != null)
                .collect(
                        Collectors.groupingBy(
                                a -> a.getDoctor().getName(),
                                Collectors.counting()));
    }


    // =========================================================
    // 11. MOST CONSULTED DOCTOR
    // =========================================================

    @Override
    public Map.Entry<String, Long>
    getMostConsultedDoctor() {

        return getAppointmentsPerDoctor()
                .entrySet()
                .stream()
                .max(
                        Map.Entry.comparingByValue())
                .orElse(null);
    }


    // =========================================================
    // 12. PENDING APPOINTMENTS
    // =========================================================

    @Override
    public List<Appointment> getPendingAppointments() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(Objects::nonNull)
                .filter(Appointment::isActive)
                .filter(a ->
                        a.getAppointmentDate() != null)
                .filter(a ->
                        a.getAppointmentDate()
                                .isAfter(today)
                        ||
                        (
                                a.getAppointmentDate()
                                        .isEqual(today)
                                &&
                                a.getAppointmentTime() != null
                                &&
                                a.getAppointmentTime()
                                        .isAfter(now)
                        )
                )
                .collect(Collectors.toList());
    }


    // =========================================================
    // 13. APPOINTMENTS BY STATUS
    // =========================================================

    @Override
    public Map<String, List<Appointment>>
    getAppointmentsByStatus() {

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(
                                a -> a.isActive()
                                        ? "ACTIVE"
                                        : "INACTIVE"));
    }


    // =========================================================
    // 14. EARLIEST APPOINTMENT
    // =========================================================

    @Override
    public Appointment getEarliestAppointment() {

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(Objects::nonNull)
                .filter(a ->
                        a.getAppointmentDate() != null)
                .min(
                        Comparator.comparing(
                                Appointment::getAppointmentDate)
                                .thenComparing(
                                        Appointment::getAppointmentTime,
                                        Comparator.nullsLast(
                                                Comparator.naturalOrder())))
                .orElse(null);
    }


    // =========================================================
    // 15. LATEST DISCHARGE
    // =========================================================

    @Override
    public Admission getLatestDischarge() {

        return admissionService
                .getAllAdmissions()
                .stream()
                .filter(Objects::nonNull)
                .filter(a ->
                        a.getDischargeDate() != null)
                .max(
                        Comparator.comparing(
                                Admission::getDischargeDate))
                .orElse(null);
    }


    // =========================================================
    // 16. TOP 5 EXPENSIVE BILLS
    // =========================================================

    @Override
    public List<Bill> getTop5ExpensiveBills() {

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .sorted(
                        Comparator.comparingDouble(
                                Bill::getTotalAmount)
                                .reversed())
                .limit(5)
                .collect(Collectors.toList());
    }


    // =========================================================
    // 17. TOTAL HOSPITAL REVENUE
    // =========================================================

    @Override
    public double getTotalHospitalRevenue() {

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(
                        Bill::getTotalAmount)
                .sum();
    }


    // =========================================================
    // 18. AVERAGE BILL AMOUNT
    // =========================================================

    @Override
    public double getAverageBillAmount() {

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(
                        Bill::getTotalAmount)
                .average()
                .orElse(0.0);
    }


    // =========================================================
    // 19. TOTAL PHARMACY SALES
    // =========================================================

    @Override
    public double getTotalPharmacySales() {

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(
                        Bill::getMedicineCharges)
                .sum();
    }


    // =========================================================
    // 20. GROUP PATIENTS BY DEPARTMENT
    // =========================================================

    @Override
    public Map<String, Set<String>>
    getPatientsByDepartmentGrouped() {

        return appointmentService
                .getAllAppointments()
                .stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getDoctor() != null)
                .filter(a ->
                        a.getDoctor().getDepartment() != null)
                .filter(a ->
                        a.getDoctor()
                                .getDepartment()
                                .getDepartmentName() != null)
                .collect(
                        Collectors.groupingBy(
                                a -> a.getDoctor()
                                        .getDepartment()
                                        .getDepartmentName(),
                                Collectors.mapping(
                                        a -> a.getPatient()
                                                .getPatientId(),
                                        Collectors.toSet())));
    }


    // =========================================================
    // 21. PATIENTS PER WARD
    // =========================================================

    @Override
    public Map<String, Set<String>>
    getPatientsPerWard() {

        return admissionService
                .getAllAdmissions()
                .stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getBed() != null)
                .filter(a ->
                        a.getBed().getWard() != null)
                .filter(a -> a.getPatient() != null)
                .collect(
                        Collectors.groupingBy(
                                a -> a.getBed()
                                        .getWard()
                                        .getWardName(),
                                Collectors.mapping(
                                        a -> a.getPatient()
                                                .getPatientId(),
                                        Collectors.toSet())));
    }


    // =========================================================
    // 22. DISTINCT SPECIALIZATIONS
    // =========================================================

    @Override
    public Set<String>
    getDistinctSpecializations() {

        return doctorService
                .getAllDoctors()
                .stream()
                .map(Doctor::getSpecialization)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


    // =========================================================
    // 23. DISTINCT MEDICINE MANUFACTURERS
    // =========================================================

    @Override
    public Set<String>
    getDistinctMedicineManufacturers() {

        return medicineService
                .getAllMedicines()
                .stream()
                .map(Medicine::getManufacturer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


    // =========================================================
    // 24. OVERDUE BILLS
    // =========================================================

    @Override
    public List<Bill> getOverdueBills() {

        LocalDate cutoff =
                LocalDate.now().minusDays(30);

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .filter(b ->
                        b.getBillDate() != null)
                .filter(b ->
                        b.getStatus() == null
                        ||
                        !"PAID".equalsIgnoreCase(
                                b.getStatus()))
                .filter(b ->
                        b.getBillDate()
                                .isBefore(cutoff))
                .collect(Collectors.toList());
    }


    // =========================================================
    // 25. UNAVAILABLE MEDICINES
    // =========================================================

    @Override
    public List<Medicine>
    getUnavailableMedicines() {

        return medicineService
                .getAllMedicines()
                .stream()
                .filter(Objects::nonNull)
                .filter(m -> !m.isActive())
                .collect(Collectors.toList());
    }


    // =========================================================
    // 26. PAID / UNPAID BILLS
    // =========================================================

    @Override
    public Map<Boolean, List<Bill>>
    getPaidUnpaidBills() {

        return billService
                .getAllBills()
                .stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.partitioningBy(
                                b ->
                                        "PAID".equalsIgnoreCase(
                                                b.getStatus())));
    }


    // =========================================================
    // 27. JOIN PATIENT NAMES
    // =========================================================

    @Override
    public String getJoinedPatientNames() {

        return patientService
                .getAllPatients()
                .stream()
                .filter(Objects::nonNull)
                .map(Patient::getPatientName)
                .filter(Objects::nonNull)
                .collect(
                        Collectors.joining(", "));
    }


    // =========================================================
    // 28. BILL SUMMARY STATISTICS
    // =========================================================

    @Override
    public Map<String, Double>
    getBillSummaryStatistics() {

        double count =
                billService.getAllBills()
                        .stream()
                        .filter(Objects::nonNull)
                        .count();

        double total =
                billService.getAllBills()
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToDouble(
                                Bill::getTotalAmount)
                        .sum();

        double average =
                billService.getAllBills()
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToDouble(
                                Bill::getTotalAmount)
                        .average()
                        .orElse(0.0);

        double highest =
                billService.getAllBills()
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToDouble(
                                Bill::getTotalAmount)
                        .max()
                        .orElse(0.0);

        double lowest =
                billService.getAllBills()
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToDouble(
                                Bill::getTotalAmount)
                        .min()
                        .orElse(0.0);

        Map<String, Double> result =
                new LinkedHashMap<>();

        result.put("count", count);
        result.put("total", total);
        result.put("average", average);
        result.put("highest", highest);
        result.put("lowest", lowest);

        return result;
    }


    // =========================================================
    // 29. ICU BED AVAILABLE
    // =========================================================

    @Override
    public boolean isICUBedAvailable() {

        return bedService
                .getAllBeds()
                .stream()
                .filter(Objects::nonNull)
                .filter(Bed::isActive)
                .filter(b ->
                        b.getWard() != null)
                .anyMatch(b ->
                        b.getWard()
                                .getWardType()
                                == WardType.ICU
                        &&
                        "AVAILABLE".equalsIgnoreCase(
                                b.getAvailability()));
    }


    // =========================================================
    // 30. ALL TEST REPORTS DELIVERED
    // =========================================================

    @Override
    public boolean areAllTestReportsDelivered() {

        List<PatientTest> tests =
                patientTestService
                        .getAllPatientTests();

        if (tests == null || tests.isEmpty()) {
            return false;
        }

        return tests.stream()
                .allMatch(t ->
                        t.getStatus() != null
                        &&
                        t.getStatus()
                                .toString()
                                .equalsIgnoreCase(
                                        "COMPLETED"));
    }


    // =========================================================
    // 31. INCOMPLETE TEST REPORTS
    // =========================================================

    @Override
    public List<PatientTest>
    getIncompleteTestReports() {

        return patientTestService
                .getAllPatientTests()
                .stream()
                .filter(Objects::nonNull)
                .filter(t ->
                        t.getStatus() == null
                        ||
                        !t.getStatus()
                                .toString()
                                .equalsIgnoreCase(
                                        "COMPLETED"))
                .collect(Collectors.toList());
    }


    // =========================================================
    // 32. IMMUTABLE PATIENT LIST
    // =========================================================

    @Override
    public List<Patient>
    getImmutablePatientList() {

        return patientService
                .getAllPatients()
                .stream()
                .toList();
    }


    // =========================================================
    // 33. OPTIONAL PATIENT LOOKUP
    // =========================================================

    @Override
    public Optional<Patient>
    findPatientById(String patientId) {

        if (patientId == null
                || patientId.trim().isEmpty()) {

            return Optional.empty();
        }

        return patientService
                .getAllPatients()
                .stream()
                .filter(Objects::nonNull)
                .filter(patient ->
                        patientId.equals(
                                patient.getPatientId()))
                .findFirst();
    }


    // =========================================================
    // 34. DEPARTMENT REVENUE
    // =========================================================
    @Override
    public Map<String, Double> getDepartmentRevenue() {

        Map<String, Double> revenue =
                new java.util.HashMap<>();

        List<Bill> bills =
                billService.getAllBills();

        List<Appointment> appointments =
                appointmentService.getAllAppointments();

        for (Bill bill : bills) {

            if (bill == null ||
                    bill.getPatient() == null) {
                continue;
            }

            String patientId =
                    bill.getPatient().getPatientId();

            if (patientId == null) {
                continue;
            }

            for (Appointment appointment : appointments) {

                if (appointment == null ||
                        appointment.getPatient() == null ||
                        appointment.getDoctor() == null ||
                        appointment.getDoctor().getDepartment() == null) {
                    continue;
                }

                String appointmentPatientId =
                        appointment.getPatient().getPatientId();

                if (patientId.equals(appointmentPatientId)) {

                    String department =
                            appointment.getDoctor()
                                    .getDepartment()
                                    .getDepartmentName();

                    if (department != null &&
                            !department.trim().isEmpty()) {

                        revenue.merge(
                                department,
                                bill.getTotalAmount(),
                                Double::sum
                        );

                        break;
                    }
                }
            }
        }

        return revenue;
    }
   
    // =========================================================
    // 35. SEQUENTIAL VS PARALLEL STREAM
    // =========================================================

    @Override
    public Map<String, Object>
    getSequentialVsParallelResult() {

        List<Patient> patients =
                patientService.getAllPatients();

        long sequentialStart =
                System.nanoTime();

        long sequentialCount =
                patients.stream()
                        .filter(p ->
                                p.getAge() >= 18)
                        .count();

        long sequentialEnd =
                System.nanoTime();

        long parallelStart =
                System.nanoTime();

        long parallelCount =
                patients.parallelStream()
                        .filter(p ->
                                p.getAge() >= 18)
                        .count();

        long parallelEnd =
                System.nanoTime();

        Map<String, Object> result =
                new LinkedHashMap<>();

        result.put(
                "sequentialCount",
                sequentialCount);

        result.put(
                "parallelCount",
                parallelCount);

        result.put(
                "sequentialTime",
                sequentialEnd - sequentialStart);

        result.put(
                "parallelTime",
                parallelEnd - parallelStart);

        return result;
    }
}