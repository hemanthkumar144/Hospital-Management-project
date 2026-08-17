package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.PatientNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IPatientMapper;
import com.crimsonlogic.hospitalmanagement.model.Address;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class PatientServiceImpl implements IPatientMapper {

    // =========================================================
    // ADD PATIENT
    // =========================================================

    @Override
    public void addPatient(
            Patient patient)
            throws ValidationException {

        validatePatient(patient);

        if (patient.getUserId() == null
                || patient.getUserId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "User ID is required for patient registration");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            try {

                Patient existingPatient =
                        mapper.getPatientByUserId(
                                patient.getUserId()
                                        .trim());

                if (existingPatient != null) {

                    throw new ValidationException(
                            "A patient profile already exists "
                                    + "for this user");
                }

                String addressId =
                        IdGenerator.generateRandomId(
                                "ADDR");

                patient.getAddress()
                        .setAddressId(addressId);

                mapper.addAddress(
                        patient.getAddress());

                String patientId =
                        IdGenerator.generateRandomId(
                                "PAT");

                patient.setPatientId(
                        patientId);

                patient.setActive(true);

                mapper.addPatient(patient);

                session.commit();

            } catch (ValidationException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET PATIENT BY ID
    // =========================================================

    @Override
    public Patient getPatientById(
            String patientId)
            throws ValidationException,
            PatientNotFoundException {

        if (patientId == null
                || patientId.trim().isEmpty()) {

            throw new ValidationException(
                    "Patient ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            Patient patient =
                    mapper.getPatientById(
                            patientId.trim());

            if (patient == null) {

                throw new PatientNotFoundException(
                        "Patient with ID "
                                + patientId
                                + " not found");
            }

            return patient;
        }
    }


    // =========================================================
    // GET PATIENT BY USER ID
    // =========================================================

    @Override
    public Patient getPatientByUserId(
            String userId) {

        if (userId == null
                || userId.trim().isEmpty()) {

            return null;
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            return mapper.getPatientByUserId(
                    userId.trim());
        }
    }


    // =========================================================
    // GET ALL PATIENTS
    // =========================================================

    @Override
    public List<Patient> getAllPatients() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            return mapper.getAllPatients();
        }
    }


    // =========================================================
    // ADD ADDRESS
    // =========================================================

    @Override
    public void addAddress(
            Address address) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            mapper.addAddress(address);

            session.commit();
        }
    }


    // =========================================================
    // UPDATE PATIENT
    // =========================================================

    @Override
    public void updatePatient(
            Patient patient)
            throws ValidationException,
            PatientNotFoundException {

        validatePatient(patient);

        if (patient.getPatientId() == null
                || patient.getPatientId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Patient ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            try {

                Patient existingPatient =
                        mapper.getPatientById(
                                patient.getPatientId()
                                        .trim());

                if (existingPatient == null) {

                    throw new PatientNotFoundException(
                            "Patient with ID "
                                    + patient.getPatientId()
                                    + " not found");
                }

                mapper.updatePatient(patient);

                session.commit();

            } catch (PatientNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE PATIENT
    // =========================================================

    @Override
    public void deletePatient(
            String patientId)
            throws PatientNotFoundException,
            ValidationException {

        if (patientId == null
                || patientId.trim().isEmpty()) {

            throw new ValidationException(
                    "Patient ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            try {

                Patient patient =
                        mapper.getPatientById(
                                patientId.trim());

                if (patient == null) {

                    throw new PatientNotFoundException(
                            "Patient with ID "
                                    + patientId
                                    + " not found");
                }

                mapper.deletePatient(
                        patientId.trim());

                session.commit();

            } catch (PatientNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET MAX PATIENT NUMBER
    // =========================================================

    @Override
    public Integer getMaxPatientNumber() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper mapper =
                    session.getMapper(
                            IPatientMapper.class);

            return mapper.getMaxPatientNumber();
        }
    }


    // =========================================================
    // VALIDATE PATIENT
    // =========================================================

    private void validatePatient(
            Patient patient)
            throws ValidationException {

        if (patient == null) {

            throw new ValidationException(
                    "Patient cannot be null");
        }

        if (patient.getPatientName() == null
                || patient.getPatientName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Patient name cannot be empty");
        }

        if (!patient.getPatientName()
                .trim()
                .matches("^[A-Za-z ]{2,50}$")) {

            throw new ValidationException(
                    "Patient name must contain only "
                            + "letters and spaces");
        }

        if (patient.getAge() < 1
                || patient.getAge() > 120) {

            throw new ValidationException(
                    "Age must be between 1 and 120");
        }

        if (patient.getGender() == null
                || !patient.getGender()
                .trim()
                .matches(
                        "(?i)Male|Female|Other")) {

            throw new ValidationException(
                    "Gender must be Male, Female or Other");
        }

        if (patient.getPhone() == null
                || !patient.getPhone()
                .trim()
                .matches(
                        "^[6-9][0-9]{9}$")) {

            throw new ValidationException(
                    "Phone number must contain exactly "
                            + "10 digits and start with 6-9");
        }

        if (patient.getAddress() == null) {

            throw new ValidationException(
                    "Address is required");
        }
    }
}