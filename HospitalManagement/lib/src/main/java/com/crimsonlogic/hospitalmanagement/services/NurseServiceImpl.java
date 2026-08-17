package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.INurseMapper;
import com.crimsonlogic.hospitalmanagement.model.Nurse;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class NurseServiceImpl implements INurseMapper {

    private final DepartmentServiceImpl departmentService =
            new DepartmentServiceImpl();


    // =========================================================
    // ADD NURSE
    // =========================================================

    @Override
    public void addNurse(Nurse nurse)
            throws ValidationException {

        validateNurse(nurse);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            try {

                nurse.setStaffId(
                        IdGenerator.generateRandomId(
                                "NUR"));

                nurse.setActive(true);

                mapper.addNurse(nurse);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET NURSE BY ID
    // =========================================================

    @Override
    public Nurse getNurseById(
            String staffId)
            throws ValidationException {

        if (staffId == null
                || staffId.trim().isEmpty()) {

            throw new ValidationException(
                    "Nurse ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            return mapper.getNurseById(
                    staffId.trim());
        }
    }


    // =========================================================
    // GET ALL NURSES
    // =========================================================

    @Override
    public List<Nurse> getAllNurses() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            return mapper.getAllNurses();
        }
    }


    // =========================================================
    // UPDATE NURSE
    // =========================================================

    @Override
    public void updateNurse(
            Nurse nurse)
            throws ValidationException {

        if (nurse == null
                || nurse.getStaffId() == null
                || nurse.getStaffId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Nurse ID cannot be empty");
        }

        validateNurse(nurse);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            try {

                Nurse existingNurse =
                        mapper.getNurseById(
                                nurse.getStaffId()
                                        .trim());

                if (existingNurse == null) {

                    throw new ValidationException(
                            "Nurse not found with ID : "
                                    + nurse.getStaffId());
                }

                mapper.updateNurse(nurse);

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
    // DEACTIVATE NURSE
    // =========================================================

    @Override
    public void deactivateNurse(
            String staffId)
            throws ValidationException {

        if (staffId == null
                || staffId.trim().isEmpty()) {

            throw new ValidationException(
                    "Nurse ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            try {

                Nurse existingNurse =
                        mapper.getNurseById(
                                staffId.trim());

                if (existingNurse == null) {

                    throw new ValidationException(
                            "Nurse not found with ID : "
                                    + staffId);
                }

                mapper.deactivateNurse(
                        staffId.trim());

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
    // GET NURSE BY USER ID
    // =========================================================

    @Override
    public Nurse getNurseByUserId(
            String userId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            INurseMapper mapper =
                    session.getMapper(
                            INurseMapper.class);

            return mapper.getNurseByUserId(
                    userId);
        }
    }


    // =========================================================
    // VALIDATE NAME
    // =========================================================

    public void validateName(
            String name)
            throws ValidationException {

        if (name == null
                || name.trim().isEmpty()) {

            throw new ValidationException(
                    "Name cannot be empty");
        }

        name = name.trim();

        if (name.length() < 2
                || name.length() > 50) {

            throw new ValidationException(
                    "Name must be 2 to 50 characters long");
        }

        if (!name.matches(
                "^[A-Za-z]+( [A-Za-z]+)*$")) {

            throw new ValidationException(
                    "Name must contain only letters and spaces");
        }

        if (name.matches(
                ".*(.)\\1\\1.*")) {

            throw new ValidationException(
                    "The same letter cannot be repeated "
                            + "more than 2 times consecutively");
        }
    }


    // =========================================================
    // VALIDATE AGE
    // =========================================================

    public void validateAge(
            int age)
            throws ValidationException {

        if (age < 18 || age > 65) {

            throw new ValidationException(
                    "Age must be between 18 and 65");
        }
    }


    // =========================================================
    // VALIDATE GENDER
    // =========================================================

    public void validateGender(
            String gender)
            throws ValidationException {

        if (gender == null
                || gender.trim().isEmpty()) {

            throw new ValidationException(
                    "Gender cannot be empty");
        }

        String value =
                gender.trim().toUpperCase();

        if (!value.equals("MALE")
                && !value.equals("FEMALE")
                && !value.equals("OTHER")) {

            throw new ValidationException(
                    "Gender must be Male, Female or Other");
        }
    }


    // =========================================================
    // VALIDATE PHONE
    // =========================================================

    public void validatePhone(
            String phone)
            throws ValidationException {

        if (phone == null
                || phone.trim().isEmpty()) {

            throw new ValidationException(
                    "Phone number cannot be empty");
        }

        if (!phone.trim().matches(
                "^[6-9][0-9]{9}$")) {

            throw new ValidationException(
                    "Phone number must contain exactly "
                            + "10 digits and start with 6, 7, 8 or 9");
        }
    }


    // =========================================================
    // VALIDATE SALARY
    // =========================================================

    public void validateSalary(
            double salary)
            throws ValidationException {

        if (salary <= 0) {

            throw new ValidationException(
                    "Salary must be greater than 0");
        }

        if (salary > 1000000) {

            throw new ValidationException(
                    "Salary cannot exceed 10,00,000");
        }
    }


    // =========================================================
    // VALIDATE DEPARTMENT
    // =========================================================

    public void validateDepartment(
            String departmentId)
            throws ValidationException {

        if (departmentId == null
                || departmentId.trim().isEmpty()) {

            throw new ValidationException(
                    "Department ID cannot be empty");
        }

        departmentService.getDepartmentById(
                departmentId.trim());
    }


    // =========================================================
    // VALIDATE SHIFT
    // =========================================================

    public void validateShift(
            String shift)
            throws ValidationException {

        if (shift == null
                || shift.trim().isEmpty()) {

            throw new ValidationException(
                    "Shift cannot be empty");
        }

        String value =
                shift.trim().toUpperCase();

        if (!value.equals("MORNING")
                && !value.equals("EVENING")
                && !value.equals("NIGHT")
                && !value.equals("ROTATIONAL")) {

            throw new ValidationException(
                    "Shift must be Morning, Evening, "
                            + "Night or Rotational");
        }
    }


    // =========================================================
    // COMPLETE NURSE VALIDATION
    // =========================================================

    private void validateNurse(
            Nurse nurse)
            throws ValidationException {

        if (nurse == null) {

            throw new ValidationException(
                    "Nurse cannot be null");
        }

        validateName(
                nurse.getName());

        validateAge(
                nurse.getAge());

        validateGender(
                nurse.getGender());

        validatePhone(
                nurse.getPhone());

        validateSalary(
                nurse.getSalary());

        if (nurse.getDepartment() == null) {

            throw new ValidationException(
                    "Department cannot be null");
        }

        validateDepartment(
                nurse.getDepartment()
                        .getDepartmentId());

        validateShift(
                nurse.getShift());
    }
}