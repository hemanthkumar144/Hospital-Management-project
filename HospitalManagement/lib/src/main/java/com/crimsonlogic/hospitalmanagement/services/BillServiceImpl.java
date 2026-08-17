package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.BillNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IBillMapper;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Appointment;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;
import com.crimsonlogic.hospitalmanagement.model.Prescription;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionMedicine;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class BillServiceImpl implements IBillMapper {

    // =========================================================
    // EXISTING SERVICES
    // =========================================================

    private final PatientServiceImpl patientService =
            new PatientServiceImpl();

    private final DoctorServiceImpl doctorService =
            new DoctorServiceImpl();

    private final AppointmentServiceImpl appointmentService =
            new AppointmentServiceImpl();

    private final PrescriptionServiceImpl prescriptionService =
            new PrescriptionServiceImpl();

    private final MedicineServiceImpl medicineService =
            new MedicineServiceImpl();

    private final PatientTestServiceImpl patientTestService =
            new PatientTestServiceImpl();

    private final AdmissionServiceImpl admissionService =
            new AdmissionServiceImpl();


    // =========================================================
    // MAPPER METHODS
    // =========================================================

    @Override
    public void addBill(Bill bill) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            mapper.addBill(bill);

            session.commit();
        }
    }


    @Override
    public Bill getBillById(
            String billId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            return mapper.getBillById(
                    billId);
        }
    }


    @Override
    public List<Bill> getAllBills() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            return mapper.getAllBills();
        }
    }


    @Override
    public void updateBill(
            Bill bill) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            mapper.updateBill(bill);

            session.commit();
        }
    }


    @Override
    public void deleteBill(
            String billId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            mapper.deleteBill(billId);

            session.commit();
        }
    }


    @Override
    public void updateBillStatus(
            String billId,
            String status) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            mapper.updateBillStatus(
                    billId,
                    status);

            session.commit();
        }
    }


    @Override
    public Integer getMaxBillNumber() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            return mapper.getMaxBillNumber();
        }
    }


    @Override
    public List<Bill> getPendingBillsByPatientId(
            String patientId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            return mapper.getPendingBillsByPatientId(
                    patientId);
        }
    }


    // =========================================================
    // GENERATE BILL AUTOMATICALLY
    // =========================================================

    public Bill generateBill(
            String patientId)
            throws ValidationException {

        // -----------------------------------------------------
        // Validate Patient ID
        // -----------------------------------------------------

        validateId(
                patientId,
                "Patient ID");


        // -----------------------------------------------------
        // Get Patient
        // -----------------------------------------------------

        Patient patient =
                patientService.getPatientById(
                        patientId.trim());

        if (patient == null) {

            throw new ValidationException(
                    "Patient not found with ID : "
                            + patientId);
        }


        // =====================================================
        // 1. CALCULATE CONSULTATION CHARGES
        // =====================================================

        double consultationFee =
                calculateConsultationCharges(
                        patientId);


        // =====================================================
        // 2. CALCULATE MEDICINE CHARGES
        // =====================================================

        double medicineCharges =
                calculateMedicineCharges(
                        patientId);


        // =====================================================
        // 3. CALCULATE LABORATORY CHARGES
        // =====================================================

        double laboratoryCharges =
                calculateLaboratoryCharges(
                        patientId);


        // =====================================================
        // 4. CALCULATE BED CHARGES
        // =====================================================

        double bedCharges =
                calculateBedCharges(
                        patientId);


        // =====================================================
        // CREATE BILL OBJECT
        // =====================================================

        Bill bill =
                new Bill();

        bill.setPatient(
                patient);

        bill.setConsultationFee(
                consultationFee);

        bill.setMedicineCharges(
                medicineCharges);

        bill.setLaboratoryCharges(
                laboratoryCharges);

        bill.setBedCharges(
                bedCharges);


        // =====================================================
        // CALCULATE TOTAL
        // =====================================================

        double totalAmount =
                consultationFee
                        + medicineCharges
                        + laboratoryCharges
                        + bedCharges;

        bill.setTotalAmount(
                totalAmount);


        // =====================================================
        // BILL INFORMATION
        // =====================================================

        bill.setBillId(
                IdGenerator.generateRandomId(
                        "BIL"));

        bill.setBillDate(
                LocalDate.now());

        bill.setStatus(
                "PENDING");

        bill.setActive(
                true);


        // =====================================================
        // SAVE BILL
        // =====================================================

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                mapper.addBill(
                        bill);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }

        return bill;
    }


    // =========================================================
    // CONSULTATION CHARGES
    // =========================================================

    private double calculateConsultationCharges(
            String patientId)
            throws ValidationException {

        double total = 0.0;

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        if (appointments == null) {

            return 0.0;
        }

        for (Appointment appointment :
                appointments) {

            if (appointment == null) {
                continue;
            }

            if (appointment.getPatient() == null) {
                continue;
            }

            if (!patientId.equals(
                    appointment.getPatient()
                            .getPatientId())) {

                continue;
            }

            if (!appointment.isActive()) {
                continue;
            }

            Doctor doctor =
                    appointment.getDoctor();

            if (doctor == null) {
                continue;
            }

            total +=
                    doctor.getConsultationFee();
        }

        return total;
    }


    // =========================================================
    // MEDICINE CHARGES
    // =========================================================

    private double calculateMedicineCharges(
            String patientId)
            throws ValidationException {

        double total = 0.0;

        List<Prescription> prescriptions =
                prescriptionService
                        .getAllPrescriptions();

        if (prescriptions == null) {
            return 0.0;
        }

        for (Prescription prescription :
                prescriptions) {

            if (prescription == null) {
                continue;
            }

            if (prescription.getPatient() == null) {
                continue;
            }

            if (!patientId.equals(
                    prescription.getPatient()
                            .getPatientId())) {

                continue;
            }

            if (!prescription.isActive()) {
                continue;
            }

            if (prescription.getMedicines() != null) {

                for (PrescriptionMedicine prescriptionMedicine :
                        prescription.getMedicines()) {

                    if (prescriptionMedicine == null) {
                        continue;
                    }

                    String medicineId =
                            prescriptionMedicine
                                    .getMedicineId();

                    if (medicineId == null
                            || medicineId.trim().isEmpty()) {

                        continue;
                    }

                    Medicine medicine =
                            medicineService
                                    .getMedicineById(
                                            medicineId.trim());

                    if (medicine == null) {
                        continue;
                    }

                    double price =
                            medicine.getPrice();

                    int quantity =
                            prescriptionMedicine
                                    .getQuantity();

                    if (quantity <= 0) {
                        continue;
                    }

                    total +=
                            price * quantity;
                }
            }
        }

        return total;
    }


    // =========================================================
    // LABORATORY CHARGES
    // =========================================================

    private double calculateLaboratoryCharges(
            String patientId)
            throws ValidationException {

        double total = 0.0;

        List<PatientTest> tests =
                patientTestService
                        .getPatientTestsByPatientId(
                                patientId);

        if (tests == null) {
            return 0.0;
        }

        for (PatientTest test : tests) {

            if (test == null) {
                continue;
            }

            if (test.getStatus() == null
                    || !test.getStatus()
                    .name()
                    .equalsIgnoreCase(
                            "COMPLETED")) {

                continue;
            }

            total += test.getCharge();
        }

        return total;
    }


    // =========================================================
    // BED CHARGES
    // =========================================================

    private double calculateBedCharges(
            String patientId)
            throws ValidationException {

        double total = 0.0;

        List<Admission> admissions =
                admissionService.getAdmissionsByPatientId(
                        patientId);

        if (admissions == null) {
            return 0.0;
        }

        for (Admission admission :
                admissions) {

            if (admission == null) {
                continue;
            }

            if (admission.getPatient() == null) {
                continue;
            }

            if (!patientId.equals(
                    admission.getPatient()
                            .getPatientId())) {

                continue;
            }

            if (admission.getBed() == null) {
                continue;
            }

            if (admission.getBed().getWard()
                    == null) {

                continue;
            }

            total +=
                    admission.getBed()
                            .getWard()
                            .getBedCharge();

            break;
        }

        return total;
    }


    // =========================================================
    // GET PENDING BILLS BY PATIENT ID
    // =========================================================

    public List<Bill> getPendingBillsByPatientIdService(
            String patientId)
            throws ValidationException {

        if (patientId == null
                || patientId.trim().isEmpty()) {

            throw new ValidationException(
                    "Patient ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            return mapper.getPendingBillsByPatientId(
                    patientId.trim());
        }
    }


    // =========================================================
    // OLD GENERATE BILL
    // =========================================================

    public void GenerateBill(
            Bill bill)
            throws ValidationException {

        validateBill(
                bill);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                String billId =
                        IdGenerator.generateRandomId(
                                "BIL");

                bill.setBillId(
                        billId);

                bill.setTotalAmount(
                        bill.getConsultationFee()
                                + bill.getMedicineCharges()
                                + bill.getLaboratoryCharges()
                                + bill.getBedCharges());

                bill.setBillDate(
                        LocalDate.now());

                bill.setStatus(
                        "PENDING");

                bill.setActive(
                        true);

                mapper.addBill(
                        bill);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // VIEW BILL
    // =========================================================

    public Bill getBillByIdService(
            String billId)
            throws BillNotFoundException,
            ValidationException {

        validateId(
                billId,
                "Bill ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            Bill bill =
                    mapper.getBillById(
                            billId.trim());

            if (bill == null) {

                throw new BillNotFoundException(
                        "Bill not found with ID : "
                                + billId);
            }

            return bill;
        }
    }


    // =========================================================
    // MARK BILL AS PAID
    // =========================================================

    public void markBillAsPaid(
            String billId)
            throws BillNotFoundException,
            ValidationException {

        validateId(
                billId,
                "Bill ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                Bill bill =
                        mapper.getBillById(
                                billId);

                if (bill == null) {

                    throw new BillNotFoundException(
                            "Bill not found with ID : "
                                    + billId);
                }

                if ("PAID".equalsIgnoreCase(
                        bill.getStatus())) {

                    throw new ValidationException(
                            "Bill is already paid");
                }

                if ("CANCELLED".equalsIgnoreCase(
                        bill.getStatus())) {

                    throw new ValidationException(
                            "Cancelled bill cannot "
                                    + "be marked as paid");
                }

                mapper.updateBillStatus(
                        billId,
                        "PAID");

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // CANCEL BILL
    // =========================================================

    public void cancelBill(
            String billId)
            throws BillNotFoundException,
            ValidationException {

        validateId(
                billId,
                "Bill ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                Bill bill =
                        mapper.getBillById(
                                billId);

                if (bill == null) {

                    throw new BillNotFoundException(
                            "Bill not found with ID : "
                                    + billId);
                }

                if ("PAID".equalsIgnoreCase(
                        bill.getStatus())) {

                    throw new ValidationException(
                            "Paid bill cannot be cancelled");
                }

                mapper.updateBillStatus(
                        billId,
                        "CANCELLED");

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE BILL
    // =========================================================

    public void deleteBillService(
            String billId)
            throws BillNotFoundException,
            ValidationException {

        validateId(
                billId,
                "Bill ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBillMapper mapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                Bill bill =
                        mapper.getBillById(
                                billId);

                if (bill == null) {

                    throw new BillNotFoundException(
                            "Bill not found with ID : "
                                    + billId);
                }

                mapper.deleteBill(
                        billId);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // VALIDATE BILL
    // =========================================================

    public void validateBill(
            Bill bill)
            throws ValidationException {

        if (bill == null) {

            throw new ValidationException(
                    "Bill cannot be null");
        }

        if (bill.getPatient() == null) {

            throw new ValidationException(
                    "Patient is required");
        }

        if (bill.getPatient()
                .getPatientId() == null
                || bill.getPatient()
                .getPatientId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Patient ID is required");
        }

        patientService.getPatientById(
                bill.getPatient()
                        .getPatientId());

        if (bill.getConsultationFee() < 0) {

            throw new ValidationException(
                    "Consultation fee cannot be negative");
        }

        if (bill.getMedicineCharges() < 0) {

            throw new ValidationException(
                    "Medicine charges cannot be negative");
        }

        if (bill.getLaboratoryCharges() < 0) {

            throw new ValidationException(
                    "Laboratory charges cannot be negative");
        }

        if (bill.getBedCharges() < 0) {

            throw new ValidationException(
                    "Bed charges cannot be negative");
        }
    }


    // =========================================================
    // VALIDATE ID
    // =========================================================

    private void validateId(
            String id,
            String fieldName)
            throws ValidationException {

        if (id == null
                || id.trim().isEmpty()) {

            throw new ValidationException(
                    fieldName
                            + " cannot be empty");
        }
    }
}