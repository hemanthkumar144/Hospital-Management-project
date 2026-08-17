package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.DoctorNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IDoctorMapper;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.model.Doctor;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class DoctorServiceImpl implements IDoctorMapper {

    private final DepartmentServiceImpl departmentService =
            new DepartmentServiceImpl();


    // =========================================================
    // ADD DOCTOR
    // =========================================================

    @Override
    public void addDoctor(Doctor doctor) throws ValidationException {

        validateDoctor(doctor);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            try {

                String doctorId =
                        IdGenerator.generateRandomId("DOC");

                doctor.setStaffId(doctorId);
                doctor.setActive(true);

                mapper.addDoctor(doctor);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();
                throw e;
            }
        }
    }


    // =========================================================
    // GET DOCTOR BY ID
    // =========================================================

    @Override
    public Doctor getDoctorById(String staffId)
            throws ValidationException,
            DoctorNotFoundException {

        validateId(staffId, "Doctor ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            Doctor doctor =
                    mapper.getDoctorById(
                            staffId.trim());

            if (doctor == null) {

                throw new DoctorNotFoundException(
                        "Doctor with ID "
                                + staffId
                                + " not found");
            }

            return doctor;
        }
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    @Override
    public List<Doctor> getAllDoctors() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            return mapper.getAllDoctors();
        }
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    @Override
    public void updateDoctor(Doctor doctor)
            throws ValidationException,
            DoctorNotFoundException {

        validateDoctor(doctor);

        validateId(
                doctor.getStaffId(),
                "Doctor ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            try {

                Doctor existingDoctor =
                        mapper.getDoctorById(
                                doctor.getStaffId());

                if (existingDoctor == null) {

                    throw new DoctorNotFoundException(
                            "Doctor with ID "
                                    + doctor.getStaffId()
                                    + " not found");
                }

                mapper.updateDoctor(doctor);

                session.commit();

            } catch (DoctorNotFoundException e) {

                session.rollback();
                throw e;

            } catch (RuntimeException e) {

                session.rollback();
                throw e;
            }
        }
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    @Override
    public void deleteDoctor(String staffId)
            throws ValidationException,
            DoctorNotFoundException {

        validateId(staffId, "Doctor ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            try {

                Doctor doctor =
                        mapper.getDoctorById(
                                staffId.trim());

                if (doctor == null) {

                    throw new DoctorNotFoundException(
                            "Doctor with ID "
                                    + staffId
                                    + " not found");
                }

                mapper.deleteDoctor(
                        staffId.trim());

                session.commit();

            } catch (DoctorNotFoundException e) {

                session.rollback();
                throw e;

            } catch (RuntimeException e) {

                session.rollback();
                throw e;
            }
        }
    }


    // =========================================================
    // GET DOCTOR BY USER ID
    // =========================================================

    @Override
    public Doctor getDoctorByUserId(
            String userId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDoctorMapper mapper =
                    session.getMapper(IDoctorMapper.class);

            return mapper.getDoctorByUserId(
                    userId);
        }
    }


    // =========================================================
    // VALIDATE DEPARTMENT
    // =========================================================

    public Department getValidatedDepartment(
            String departmentId,
            String departmentName)
            throws ValidationException {

        if (departmentId == null
                || departmentId.trim().isEmpty()) {

            throw new ValidationException(
                    "Department ID cannot be empty");
        }

        if (departmentName == null
                || departmentName.trim().isEmpty()) {

            throw new ValidationException(
                    "Department Name cannot be empty");
        }

        Department department =
                departmentService.getDepartmentById(
                        departmentId.trim());

        if (!department.getDepartmentName()
                .equalsIgnoreCase(
                        departmentName.trim())) {

            throw new ValidationException(
                    "Department ID "
                            + departmentId
                            + " belongs to "
                            + department.getDepartmentName()
                            + ", not "
                            + departmentName);
        }

        return department;
    }


    // =========================================================
    // VALIDATE DOCTOR
    // =========================================================

    private void validateDoctor(
            Doctor doctor)
            throws ValidationException {

        if (doctor == null) {

            throw new ValidationException(
                    "Doctor cannot be null");
        }


        // -----------------------------------------------------
        // NAME
        // -----------------------------------------------------

        if (doctor.getName() == null
                || doctor.getName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Doctor name cannot be empty");
        }

        if (!doctor.getName()
                .trim()
                .matches("^[A-Za-z ]{2,50}$")) {

            throw new ValidationException(
                    "Doctor name must contain only "
                            + "letters and spaces");
        }


        // -----------------------------------------------------
        // AGE
        // -----------------------------------------------------

        if (doctor.getAge() < 21
                || doctor.getAge() > 80) {

            throw new ValidationException(
                    "Doctor age must be between 21 and 80");
        }


        // -----------------------------------------------------
        // GENDER
        // -----------------------------------------------------

        if (doctor.getGender() == null
                || !doctor.getGender()
                .trim()
                .matches(
                        "(?i)Male|Female|Other")) {

            throw new ValidationException(
                    "Gender must be Male, Female or Other");
        }


        // -----------------------------------------------------
        // PHONE
        // -----------------------------------------------------

        if (doctor.getPhone() == null
                || !doctor.getPhone()
                .trim()
                .matches(
                        "^[6-9][0-9]{9}$")) {

            throw new ValidationException(
                    "Phone number must contain exactly "
                            + "10 digits and start with 6-9");
        }


        // -----------------------------------------------------
        // SALARY
        // -----------------------------------------------------

        if (doctor.getSalary() <= 0) {

            throw new ValidationException(
                    "Salary must be greater than zero");
        }

        if (doctor.getSalary() > 1000000) {

            throw new ValidationException(
                    "Salary cannot exceed 10,00,000");
        }


        // -----------------------------------------------------
        // EXPERIENCE
        // -----------------------------------------------------

        if (doctor.getExperience() < 0
                || doctor.getExperience() > 60) {

            throw new ValidationException(
                    "Experience must be between 0 and 60 years");
        }


        // -----------------------------------------------------
        // SPECIALIZATION
        // -----------------------------------------------------

        if (doctor.getSpecialization() == null
                || doctor.getSpecialization()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Specialization cannot be empty");
        }

        if (!doctor.getSpecialization()
                .trim()
                .matches("^[A-Za-z ]{2,50}$")) {

            throw new ValidationException(
                    "Specialization must contain only "
                            + "letters and spaces");
        }


        // -----------------------------------------------------
        // CONSULTATION FEE
        // -----------------------------------------------------

        if (doctor.getConsultationFee() <= 0) {

            throw new ValidationException(
                    "Consultation fee must be greater than zero");
        }

        if (doctor.getConsultationFee() > 100000) {

            throw new ValidationException(
                    "Consultation fee cannot exceed 1,00,000");
        }


        // -----------------------------------------------------
        // DEPARTMENT
        // -----------------------------------------------------

        if (doctor.getDepartment() == null) {

            throw new ValidationException(
                    "Department is required");
        }

        if (doctor.getDepartment()
                .getDepartmentId() == null
                || doctor.getDepartment()
                .getDepartmentId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Department ID is required");
        }

        Department department =
                departmentService.getDepartmentById(
                        doctor.getDepartment()
                                .getDepartmentId()
                                .trim());

        doctor.setDepartment(department);
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