package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.DepartmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IDepartmentMapper;
import com.crimsonlogic.hospitalmanagement.model.Department;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class DepartmentServiceImpl
        implements IDepartmentMapper {

    // =========================================================
    // ADD DEPARTMENT
    // =========================================================

    @Override
    public void addDepartment(
            Department department)
            throws ValidationException {

        validateDepartment(
                department);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDepartmentMapper mapper =
                    session.getMapper(
                            IDepartmentMapper.class);

            try {

                String departmentId =
                        IdGenerator.generateRandomId(
                                "DEP");

                department.setDepartmentId(
                        departmentId);

                department.setActive(
                        true);

                mapper.addDepartment(
                        department);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET DEPARTMENT BY ID
    // =========================================================

    @Override
    public Department getDepartmentById(
            String id)
            throws ValidationException,
            DepartmentNotFoundException {

        validateId(
                id,
                "Department ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDepartmentMapper mapper =
                    session.getMapper(
                            IDepartmentMapper.class);

            Department department =
                    mapper.getDepartmentById(
                            id.trim());

            if (department == null) {

                throw new DepartmentNotFoundException(
                        "Department with ID "
                                + id
                                + " not found");
            }

            return department;
        }
    }


    // =========================================================
    // GET ALL DEPARTMENTS
    // =========================================================

    @Override
    public List<Department> getAllDepartments() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDepartmentMapper mapper =
                    session.getMapper(
                            IDepartmentMapper.class);

            return mapper.getAllDepartments();
        }
    }


    // =========================================================
    // UPDATE DEPARTMENT
    // =========================================================

    @Override
    public void updateDepartment(
            Department department)
            throws ValidationException,
            DepartmentNotFoundException {

        validateDepartment(
                department);

        validateId(
                department.getDepartmentId(),
                "Department ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDepartmentMapper mapper =
                    session.getMapper(
                            IDepartmentMapper.class);

            try {

                Department existingDepartment =
                        mapper.getDepartmentById(
                                department
                                        .getDepartmentId());

                if (existingDepartment == null) {

                    throw new DepartmentNotFoundException(
                            "Department with ID "
                                    + department
                                    .getDepartmentId()
                                    + " not found");
                }

                mapper.updateDepartment(
                        department);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE DEPARTMENT
    // =========================================================

    @Override
    public void deleteDepartment(
            String departmentId)
            throws ValidationException,
            DepartmentNotFoundException {

        validateId(
                departmentId,
                "Department ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IDepartmentMapper mapper =
                    session.getMapper(
                            IDepartmentMapper.class);

            try {

                Department department =
                        mapper.getDepartmentById(
                                departmentId);

                if (department == null) {

                    throw new DepartmentNotFoundException(
                            "Department with ID "
                                    + departmentId
                                    + " not found");
                }

                mapper.deleteDepartment(
                        departmentId);

                session.commit();

            } catch (Exception e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // VALIDATE DEPARTMENT
    // =========================================================

    private void validateDepartment(
            Department department)
            throws ValidationException {

        if (department == null) {

            throw new ValidationException(
                    "Department cannot be null");
        }


        // -----------------------------------------------------
        // DEPARTMENT NAME
        // -----------------------------------------------------

        if (department.getDepartmentName() == null
                || department.getDepartmentName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Department name cannot be empty");
        }

        if (!department.getDepartmentName()
                .trim()
                .matches(
                        "^[A-Za-z ]{2,50}$")) {

            throw new ValidationException(
                    "Department name should contain "
                            + "only alphabets and spaces");
        }


        // -----------------------------------------------------
        // LOCATION
        // -----------------------------------------------------

        if (department.getLocation() == null
                || department.getLocation()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Location cannot be empty");
        }

        if (!department.getLocation()
                .trim()
                .matches(
                        "^[A-Za-z ]{2,50}$")) {

            throw new ValidationException(
                    "Location should contain "
                            + "only alphabets and spaces");
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