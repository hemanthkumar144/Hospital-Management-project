package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.ILaboratoryTestMapper;
import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class LaboratoryTestServiceImpl
        implements ILaboratoryTestMapper {

    // =========================================================
    // ADD TEST
    // =========================================================

    @Override
    public void addTest(
            LaboratoryTest test)
            throws ValidationException {

        validateTest(test);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            ILaboratoryTestMapper mapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            try {

                String testId =
                        IdGenerator.generateRandomId(
                                "TST");

                test.setTestId(testId);

                test.setActive(true);

                test.setTestType(
                        test.getTestType()
                                .trim()
                                .toUpperCase());

                mapper.addTest(test);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET TEST BY ID
    // =========================================================

    @Override
    public LaboratoryTest getTestById(
            String testId)
            throws ValidationException {

        validateId(
                testId,
                "Test ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            ILaboratoryTestMapper mapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            LaboratoryTest test =
                    mapper.getTestById(
                            testId.trim());

            if (test == null) {

                throw new ValidationException(
                        "Laboratory test with ID "
                                + testId
                                + " not found");
            }

            return test;
        }
    }


    // =========================================================
    // GET ALL TESTS
    // =========================================================

    @Override
    public List<LaboratoryTest> getAllTests() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            ILaboratoryTestMapper mapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            return mapper.getAllTests();
        }
    }


    // =========================================================
    // UPDATE TEST
    // =========================================================

    @Override
    public void updateTest(
            LaboratoryTest test)
            throws ValidationException {

        if (test == null) {

            throw new ValidationException(
                    "Laboratory test cannot be null");
        }

        validateId(
                test.getTestId(),
                "Test ID");

        validateTest(test);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            ILaboratoryTestMapper mapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            try {

                LaboratoryTest existingTest =
                        mapper.getTestById(
                                test.getTestId()
                                        .trim());

                if (existingTest == null) {

                    throw new ValidationException(
                            "Laboratory test with ID "
                                    + test.getTestId()
                                    + " not found");
                }

                test.setTestId(
                        test.getTestId().trim());

                test.setTestName(
                        test.getTestName().trim());

                test.setTestDescription(
                        test.getTestDescription().trim());

                test.setTestType(
                        test.getTestType()
                                .trim()
                                .toUpperCase());

                mapper.updateTest(test);

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
    // DELETE / DEACTIVATE TEST
    // =========================================================

    @Override
    public void deactivateTest(
            String testId)
            throws ValidationException {

        validateId(
                testId,
                "Test ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            ILaboratoryTestMapper mapper =
                    session.getMapper(
                            ILaboratoryTestMapper.class);

            try {

                LaboratoryTest test =
                        mapper.getTestById(
                                testId.trim());

                if (test == null) {

                    throw new ValidationException(
                            "Laboratory test with ID "
                                    + testId
                                    + " not found");
                }

                mapper.deactivateTest(
                        testId.trim());

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
    // VALIDATE TEST
    // =========================================================

    private void validateTest(
            LaboratoryTest test)
            throws ValidationException {

        if (test == null) {

            throw new ValidationException(
                    "Laboratory test cannot be null");
        }


        // -----------------------------------------------------
        // TEST NAME
        // -----------------------------------------------------

        if (test.getTestName() == null
                || test.getTestName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Test name cannot be empty");
        }

        String testName =
                test.getTestName().trim();

        if (!testName.matches(
                "^[A-Za-z][A-Za-z0-9 -]{1,99}$")) {

            throw new ValidationException(
                    "Test name must contain only "
                            + "letters, numbers and spaces "
                            + "and must contain 2 to 100 "
                            + "characters");
        }


        // -----------------------------------------------------
        // TEST DESCRIPTION
        // -----------------------------------------------------

        if (test.getTestDescription() == null
                || test.getTestDescription()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Test description cannot be empty");
        }


        // -----------------------------------------------------
        // TEST CHARGE
        // -----------------------------------------------------

        if (Double.isNaN(
                test.getTestCharge())
                || Double.isInfinite(
                test.getTestCharge())) {

            throw new ValidationException(
                    "Invalid test charge");
        }

        if (test.getTestCharge() <= 0) {

            throw new ValidationException(
                    "Test charge must be greater than 0");
        }

        if (test.getTestCharge() > 1000000) {

            throw new ValidationException(
                    "Test charge is too high");
        }


        // -----------------------------------------------------
        // TEST TYPE
        // -----------------------------------------------------

        if (test.getTestType() == null
                || test.getTestType()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Test type cannot be empty");
        }

        String testType =
                test.getTestType()
                        .trim()
                        .toUpperCase();

        if (!testType.equals("BLOOD")
                && !testType.equals("XRAY")
                && !testType.equals("MRI")
                && !testType.equals("URINE")
                && !testType.equals("CT")) {

            throw new ValidationException(
                    "Invalid laboratory test type");
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