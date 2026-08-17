package com.crimsonlogic.hospitalmanagement.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crimsonlogic.hospitalmanagement.mapper.IReportService;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final IReportService reportService;

    public AdminReportController(
            IReportService reportService) {

        this.reportService = reportService;
    }


    // =========================================================
    // REPORT DASHBOARD
    // =========================================================

    @GetMapping
    public String reportsHome() {

        return "admin/reports/report-dashboard";
    }


    // =========================================================
    // 1. ADMITTED PATIENTS
    // =========================================================

    @GetMapping("/admitted-patients")
    public String admittedPatients(Model model) {

        List<Patient> patients =
                reportService.getAdmittedPatients();

        model.addAttribute(
                "patients",
                patients);

        return "admin/reports/admitted-patients";
    }


    // =========================================================
    // 2. PATIENTS BY DEPARTMENT
    // =========================================================

    @GetMapping("/patients-by-department")
    public String patientsByDepartment(
            @RequestParam(
                    value = "departmentName",
                    required = false)
            String departmentName,
            Model model) {

        if (departmentName != null
                && !departmentName.trim().isEmpty()) {

            List<Patient> patients =
                    reportService
                            .getPatientsByDepartment(
                                    departmentName);

            model.addAttribute(
                    "patients",
                    patients);
        }

        model.addAttribute(
                "departmentName",
                departmentName);

        return "admin/reports/patients-by-department";
    }


    // =========================================================
    // 3. PATIENTS BY AGE
    // =========================================================

    @GetMapping("/patients-by-age")
    public String patientsByAge(Model model) {

        List<Patient> patients =
                reportService.getPatientsByAge();

        model.addAttribute(
                "patients",
                patients);

        return "admin/reports/patients-by-age";
    }


    // =========================================================
    // 4. PATIENTS BY WARD
    // =========================================================

    @GetMapping("/patients-by-ward")
    public String patientsByWard(
            @RequestParam(
                    value = "wardName",
                    required = false)
            String wardName,
            Model model) {

        if (wardName != null
                && !wardName.trim().isEmpty()) {

            List<Patient> patients =
                    reportService
                            .getPatientsByWard(
                                    wardName);

            model.addAttribute(
                    "patients",
                    patients);
        }

        model.addAttribute(
                "wardName",
                wardName);

        return "admin/reports/patients-by-ward";
    }


    // =========================================================
    // 5. AVAILABLE DOCTORS
    // =========================================================

    @GetMapping("/available-doctors")
    public String availableDoctors(Model model) {

        List<Doctor> doctors =
                reportService.getAvailableDoctors();

        model.addAttribute(
                "doctors",
                doctors);

        return "admin/reports/available-doctors";
    }


    // =========================================================
    // 6. DOCTORS BY EXPERIENCE
    // =========================================================

    @GetMapping("/doctors-by-experience")
    public String doctorsByExperience(
            Model model) {

        List<Doctor> doctors =
                reportService
                        .getDoctorsByExperience();

        model.addAttribute(
                "doctors",
                doctors);

        return "admin/reports/doctors-by-experience";
    }


    // =========================================================
    // 7. HIGHEST CONSULTATION FEE
    // =========================================================

    @GetMapping("/highest-consultation-fee")
    public String highestConsultationFee(
            Model model) {

        Doctor doctor =
                reportService
                        .getHighestConsultationFeeDoctor();

        model.addAttribute(
                "doctor",
                doctor);

        return "admin/reports/highest-consultation-fee";
    }


    // =========================================================
    // 8. LOWEST CONSULTATION FEE
    // =========================================================

    @GetMapping("/lowest-consultation-fee")
    public String lowestConsultationFee(
            Model model) {

        Doctor doctor =
                reportService
                        .getLowestConsultationFeeDoctor();

        model.addAttribute(
                "doctor",
                doctor);

        return "admin/reports/lowest-consultation-fee";
    }


    // =========================================================
    // 9. DOCTORS BY SPECIALIZATION
    // =========================================================

    @GetMapping("/doctors-by-specialization")
    public String doctorsBySpecialization(
            Model model) {

        Map<String, List<Doctor>> groupedDoctors =
                reportService
                        .getDoctorsBySpecialization();

        model.addAttribute(
                "groupedDoctors",
                groupedDoctors);

        return "admin/reports/doctors-by-specialization";
    }


    // =========================================================
    // 10. APPOINTMENTS PER DOCTOR
    // =========================================================

    @GetMapping("/appointments-per-doctor")
    public String appointmentsPerDoctor(
            Model model) {

        Map<String, Long> results =
                reportService
                        .getAppointmentsPerDoctor();

        model.addAttribute(
                "results",
                results);

        return "admin/reports/appointments-per-doctor";
    }


    // =========================================================
    // 11. MOST CONSULTED DOCTOR
    // =========================================================

    @GetMapping("/most-consulted-doctor")
    public String mostConsultedDoctor(
            Model model) {

        Map.Entry<String, Long> result =
                reportService
                        .getMostConsultedDoctor();

        model.addAttribute(
                "result",
                result);

        return "admin/reports/most-consulted-doctor";
    }


    // =========================================================
    // 12. PENDING APPOINTMENTS
    // =========================================================

    @GetMapping("/pending-appointments")
    public String pendingAppointments(
            Model model) {

        List<Appointment> appointments =
                reportService
                        .getPendingAppointments();

        model.addAttribute(
                "appointments",
                appointments);

        return "admin/reports/pending-appointments";
    }


    // =========================================================
    // 13. APPOINTMENTS BY STATUS
    // =========================================================

    @GetMapping("/appointments-by-status")
    public String appointmentsByStatus(
            Model model) {

        Map<String, List<Appointment>>
                groupedAppointments =
                reportService
                        .getAppointmentsByStatus();

        model.addAttribute(
                "groupedAppointments",
                groupedAppointments);

        return "admin/reports/appointments-by-status";
    }


    // =========================================================
    // 14. EARLIEST APPOINTMENT
    // =========================================================

    @GetMapping("/earliest-appointment")
    public String earliestAppointment(
            Model model) {

        Appointment appointment =
                reportService
                        .getEarliestAppointment();

        model.addAttribute(
                "appointment",
                appointment);

        return "admin/reports/earliest-appointment";
    }


    // =========================================================
    // 15. LATEST DISCHARGE
    // =========================================================

    @GetMapping("/latest-discharge")
    public String latestDischarge(
            Model model) {

        Admission admission =
                reportService
                        .getLatestDischarge();

        model.addAttribute(
                "admission",
                admission);

        return "admin/reports/latest-discharge";
    }


    // =========================================================
    // 16. TOP 5 EXPENSIVE BILLS
    // =========================================================

    @GetMapping("/top-expensive-bills")
    public String topExpensiveBills(
            Model model) {

        List<Bill> bills =
                reportService
                        .getTop5ExpensiveBills();

        model.addAttribute(
                "bills",
                bills);

        return "admin/reports/top-expensive-bills";
    }


    // =========================================================
    // 17. TOTAL HOSPITAL REVENUE
    // =========================================================

    @GetMapping("/total-revenue")
    public String totalRevenue(
            Model model) {

        double revenue =
                reportService
                        .getTotalHospitalRevenue();

        model.addAttribute(
                "revenue",
                revenue);

        return "admin/reports/total-revenue";
    }


    // =========================================================
    // 18. AVERAGE BILL AMOUNT
    // =========================================================

    @GetMapping("/average-bill")
    public String averageBill(
            Model model) {

        double average =
                reportService
                        .getAverageBillAmount();

        model.addAttribute(
                "average",
                average);

        return "admin/reports/average-bill";
    }


    // =========================================================
    // 19. TOTAL PHARMACY SALES
    // =========================================================

    @GetMapping("/pharmacy-sales")
    public String pharmacySales(
            Model model) {

        double sales =
                reportService
                        .getTotalPharmacySales();

        model.addAttribute(
                "sales",
                sales);

        return "admin/reports/pharmacy-sales";
    }


    // =========================================================
    // 20. PATIENTS BY DEPARTMENT - GROUPED
    // =========================================================

    @GetMapping("/grouped-patients-department")
    public String groupedPatientsDepartment(
            Model model) {

        Map<String, Set<String>> results =
                reportService
                        .getPatientsByDepartmentGrouped();

        model.addAttribute(
                "results",
                results);

        return "admin/reports/grouped-patients-department";
    }


    // =========================================================
    // 21. PATIENTS PER WARD
    // =========================================================

    @GetMapping("/patients-per-ward")
    public String patientsPerWard(
            Model model) {

        Map<String, Set<String>> results =
                reportService
                        .getPatientsPerWard();

        model.addAttribute(
                "results",
                results);

        return "admin/reports/patients-per-ward";
    }


    // =========================================================
    // 22. DISTINCT SPECIALIZATIONS
    // =========================================================

    @GetMapping("/distinct-specializations")
    public String distinctSpecializations(
            Model model) {

        Set<String> specializations =
                reportService
                        .getDistinctSpecializations();

        model.addAttribute(
                "specializations",
                specializations);

        return "admin/reports/distinct-specializations";
    }


    // =========================================================
    // 23. DISTINCT MEDICINE MANUFACTURERS
    // =========================================================

    @GetMapping("/distinct-medicine-manufacturers")
    public String distinctMedicineManufacturers(
            Model model) {

        Set<String> manufacturers =
                reportService
                        .getDistinctMedicineManufacturers();

        model.addAttribute(
                "manufacturers",
                manufacturers);

        return "admin/reports/distinct-medicine-manufacturers";
    }


    // =========================================================
    // 24. OVERDUE BILLS
    // =========================================================

    @GetMapping("/overdue-bills")
    public String overdueBills(
            Model model) {

        List<Bill> bills =
                reportService
                        .getOverdueBills();

        model.addAttribute(
                "bills",
                bills);

        return "admin/reports/overdue-bills";
    }


    // =========================================================
    // 25. UNAVAILABLE MEDICINES
    // =========================================================

    @GetMapping("/unavailable-medicines")
    public String unavailableMedicines(
            Model model) {

        List<Medicine> medicines =
                reportService
                        .getUnavailableMedicines();

        model.addAttribute(
                "medicines",
                medicines);

        return "admin/reports/unavailable-medicines";
    }


    // =========================================================
    // 26. PAID / UNPAID BILLS
    // =========================================================

    @GetMapping("/paid-unpaid-bills")
    public String paidUnpaidBills(
            Model model) {

        Map<Boolean, List<Bill>> bills =
                reportService
                        .getPaidUnpaidBills();

        model.addAttribute(
                "bills",
                bills);

        return "admin/reports/paid-unpaid-bills";
    }


    // =========================================================
    // 27. JOIN PATIENT NAMES
    // =========================================================

    @GetMapping("/patient-names")
    public String patientNames(
            Model model) {

        String names =
                reportService
                        .getJoinedPatientNames();

        model.addAttribute(
                "names",
                names);

        return "admin/reports/patient-names";
    }

    
    // =========================================================
    // 28. BILL SUMMARY STATISTICS
    // =========================================================

    @GetMapping("/bill-summary")
    public String billSummary(
            Model model) {

        Map<String, Double> statistics =
                reportService
                        .getBillSummaryStatistics();

        model.addAttribute(
                "statistics",
                statistics);

        return "admin/reports/bill-summary";
    }


    // =========================================================
    // 29. ICU BED AVAILABILITY
    // =========================================================

    @GetMapping("/icu-bed-availability")
    public String icuBedAvailability(
            Model model) {

        boolean available =
                reportService
                        .isICUBedAvailable();

        model.addAttribute(
                "available",
                available);

        return "admin/reports/icu-bed-availability";
    }


    // =========================================================
    // 30. ALL TEST REPORTS DELIVERED
    // =========================================================

    @GetMapping("/test-reports-status")
    public String testReportsStatus(
            Model model) {

        boolean allCompleted =
                reportService
                        .areAllTestReportsDelivered();

        List<PatientTest> incompleteTests =
                reportService
                        .getIncompleteTestReports();

        model.addAttribute(
                "allCompleted",
                allCompleted);

        model.addAttribute(
                "incompleteTests",
                incompleteTests);

        return "admin/reports/test-reports-status";
    }


    // =========================================================
    // 31. IMMUTABLE PATIENT LIST
    // =========================================================

    @GetMapping("/immutable-patients")
    public String immutablePatients(
            Model model) {

        List<Patient> patients =
                reportService
                        .getImmutablePatientList();

        model.addAttribute(
                "patients",
                patients);

        return "admin/reports/immutable-patients";
    }


    // =========================================================
    // 32. OPTIONAL PATIENT LOOKUP
    // =========================================================

    @GetMapping("/patient-lookup")
    public String patientLookup(
            @RequestParam(
                    value = "patientId",
                    required = false)
            String patientId,
            Model model) {

        if (patientId != null
                && !patientId.trim().isEmpty()) {

            Optional<Patient> result =
                    reportService
                            .findPatientById(
                                    patientId);

            model.addAttribute(
                    "patient",
                    result.orElse(null));
        }

        model.addAttribute(
                "patientId",
                patientId);

        return "admin/reports/patient-lookup";
    }


    // =========================================================
    // 33. DEPARTMENT REVENUE
    // =========================================================

    @GetMapping("/department-revenue")
    public String departmentRevenue(
            Model model) {

        Map<String, Double> revenue =
                reportService
                        .getDepartmentRevenue();

        model.addAttribute(
                "revenue",
                revenue);

        return "admin/reports/department-revenue";
    }


    // =========================================================
    // 34. SEQUENTIAL VS PARALLEL
    // =========================================================

    @GetMapping("/stream-performance")
    public String streamPerformance(
            Model model) {

        Map<String, Object> result =
                reportService
                        .getSequentialVsParallelResult();

        model.addAttribute(
                "result",
                result);

        return "admin/reports/stream-performance";
    }
    @GetMapping("/medicine-stock")
    public String medicineStock(Model model) {

        model.addAttribute(
                "medicines",
                reportService.getUnavailableMedicines());

        return "admin/reports/medicine-stock";
    }
    
}