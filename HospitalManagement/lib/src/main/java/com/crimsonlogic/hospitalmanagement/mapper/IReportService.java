package com.crimsonlogic.hospitalmanagement.mapper;

import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IReportService {

    // =========================================================
    // PATIENT REPORTS
    // =========================================================

    List<Patient> getAdmittedPatients();

    List<Patient> getPatientsByDepartment(
            String departmentName);

    List<Patient> getPatientsByAge();

    List<Patient> getPatientsByWard(
            String wardName);


    // =========================================================
    // DOCTOR REPORTS
    // =========================================================

    List<Doctor> getAvailableDoctors();

    List<Doctor> getDoctorsByExperience();

    Doctor getHighestConsultationFeeDoctor();

    Doctor getLowestConsultationFeeDoctor();

    Map<String, List<Doctor>>
    getDoctorsBySpecialization();

    Map<String, Long>
    getAppointmentsPerDoctor();

    Map.Entry<String, Long>
    getMostConsultedDoctor();


    // =========================================================
    // APPOINTMENT REPORTS
    // =========================================================

    List<Appointment> getPendingAppointments();

    Map<String, List<Appointment>>
    getAppointmentsByStatus();

    Appointment getEarliestAppointment();


    // =========================================================
    // ADMISSION REPORTS
    // =========================================================

    Admission getLatestDischarge();


    // =========================================================
    // BILL REPORTS
    // =========================================================

    List<Bill> getTop5ExpensiveBills();

    double getTotalHospitalRevenue();

    double getAverageBillAmount();

    List<Bill> getOverdueBills();

    Map<Boolean, List<Bill>>
    getPaidUnpaidBills();

    Map<String, Double>
    getBillSummaryStatistics();

    Map<String, Double>
    getDepartmentRevenue();


    // =========================================================
    // PHARMACY / MEDICINE REPORTS
    // =========================================================

    double getTotalPharmacySales();

    List<Medicine> getUnavailableMedicines();

    Set<String> getDistinctMedicineManufacturers();


    // =========================================================
    // PATIENT / DEPARTMENT REPORTS
    // =========================================================

    Map<String, Set<String>>
    getPatientsByDepartmentGrouped();


    // =========================================================
    // WARD REPORTS
    // =========================================================

    Map<String, Set<String>>
    getPatientsPerWard();


    // =========================================================
    // DOCTOR / SPECIALIZATION REPORTS
    // =========================================================

    Set<String> getDistinctSpecializations();


    // =========================================================
    // LABORATORY REPORTS
    // =========================================================

    boolean areAllTestReportsDelivered();

    List<PatientTest> getIncompleteTestReports();


    // =========================================================
    // PATIENT NAME REPORT
    // =========================================================

    String getJoinedPatientNames();


    // =========================================================
    // PATIENT LOOKUP
    // =========================================================

    Optional<Patient>
    findPatientById(String patientId);


    // =========================================================
    // IMMUTABLE PATIENT LIST
    // =========================================================

    List<Patient> getImmutablePatientList();


    // =========================================================
    // ICU REPORT
    // =========================================================

    boolean isICUBedAvailable();


    // =========================================================
    // STREAM PERFORMANCE REPORT
    // =========================================================

    Map<String, Object>
    getSequentialVsParallelResult();
}