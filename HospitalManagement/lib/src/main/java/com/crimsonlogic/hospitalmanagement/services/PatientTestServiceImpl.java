package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.enums.TestStatus;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.ILaboratoryTestMapper;
import com.crimsonlogic.hospitalmanagement.mapper.IPatientMapper;
import com.crimsonlogic.hospitalmanagement.mapper.IPatientTestMapper;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.PatientTest;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class PatientTestServiceImpl
        implements IPatientTestMapper {


    // =========================================================
    // ADD PATIENT TEST
    // BUSINESS METHOD
    // =========================================================

    public PatientTest addPatientTest(
            String patientId,
            String testId)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        validateId(
                testId,
                "Test ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper patientMapper =
                    session.getMapper(
                            IPatientMapper.class);

            ILaboratoryTestMapper testMapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            IPatientTestMapper patientTestMapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            try {

                // =============================================
                // CHECK PATIENT
                // =============================================

                Patient patient =
                        patientMapper.getPatientById(
                                patientId.trim());

                if (patient == null) {

                    throw new ValidationException(
                            "Patient not found with ID : "
                                    + patientId);
                }

                if (!patient.isActive()) {

                    throw new ValidationException(
                            "Selected patient is inactive");
                }


                // =============================================
                // CHECK LABORATORY TEST
                // =============================================

                LaboratoryTest test =
                        testMapper.getTestById(
                                testId.trim());

                if (test == null) {

                    throw new ValidationException(
                            "Laboratory test not found with ID : "
                                    + testId);
                }

                if (!test.isActive()) {

                    throw new ValidationException(
                            "Selected laboratory test is inactive");
                }


                // =============================================
                // CREATE PATIENT TEST
                // =============================================

                PatientTest patientTest =
                        new PatientTest();

                patientTest.setPatientTestId(
                        IdGenerator.generateRandomId(
                                "PTT"));

                patientTest.setPatientId(
                        patientId.trim());

                patientTest.setTestId(
                        testId.trim());

                patientTest.setTestDate(
                        LocalDateTime.now());

                patientTest.setStatus(
                        TestStatus.COMPLETED);

                patientTest.setCharge(
                        test.getTestCharge());


                // =============================================
                // CALL MAPPER METHOD
                // =============================================

                patientTestMapper.addPatientTest(
                        patientTest);

                session.commit();

                return patientTest;

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
    // MAPPER METHOD
    // ADD PATIENT TEST
    // =========================================================

    @Override
    public void addPatientTest(
            PatientTest patientTest) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            mapper.addPatientTest(
                    patientTest);

            session.commit();
        }
    }


    // =========================================================
    // MAPPER METHOD
    // GET PATIENT TEST BY ID
    // =========================================================

    @Override
    public PatientTest getPatientTestById(
            String patientTestId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            return mapper.getPatientTestById(
                    patientTestId);
        }
    }


    // =========================================================
    // MAPPER METHOD
    // GET TESTS BY PATIENT ID
    // =========================================================

    @Override
    public List<PatientTest>
    getPatientTestsByPatientId(
            String patientId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            return mapper.getPatientTestsByPatientId(
                    patientId);
        }
    }


    // =========================================================
    // MAPPER METHOD
    // GET ALL PATIENT TESTS
    // =========================================================

    @Override
    public List<PatientTest>
    getAllPatientTests() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            return mapper.getAllPatientTests();
        }
    }


    // =========================================================
    // MAPPER METHOD
    // UPDATE TEST STATUS
    // =========================================================

    @Override
    public void updateTestStatus(
            String patientTestId,
            String status) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            mapper.updateTestStatus(
                    patientTestId,
                    status);

            session.commit();
        }
    }


    // =========================================================
    // VALIDATED GET PATIENT TEST BY ID
    // SERVICE METHOD
    // =========================================================

    public PatientTest
    getValidatedPatientTestById(
            String patientTestId)
            throws ValidationException {

        validateId(
                patientTestId,
                "Patient Test ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            PatientTest patientTest =
                    mapper.getPatientTestById(
                            patientTestId.trim());

            if (patientTest == null) {

                throw new ValidationException(
                        "Patient test not found with ID : "
                                + patientTestId);
            }

            return patientTest;
        }
    }


    // =========================================================
    // VALIDATED GET TESTS BY PATIENT
    // SERVICE METHOD
    // =========================================================

    public List<PatientTest>
    getValidatedPatientTestsByPatientId(
            String patientId)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            return mapper.getPatientTestsByPatientId(
                    patientId.trim());
        }
    }


    // =========================================================
    // VALIDATED UPDATE STATUS
    // SERVICE METHOD
    // =========================================================

    public void updateValidatedTestStatus(
            String patientTestId,
            TestStatus status)
            throws ValidationException {

        validateId(
                patientTestId,
                "Patient Test ID");

        if (status == null) {

            throw new ValidationException(
                    "Test status cannot be null");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientTestMapper mapper =
                    session.getMapper(
                            IPatientTestMapper.class);

            try {

                PatientTest existing =
                        mapper.getPatientTestById(
                                patientTestId.trim());

                if (existing == null) {

                    throw new ValidationException(
                            "Patient test not found with ID : "
                                    + patientTestId);
                }

                mapper.updateTestStatus(
                        patientTestId.trim(),
                        status.name());

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
    // ID VALIDATION
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