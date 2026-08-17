package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crimsonlogic.hospitalmanagement.exceptions.DepartmentNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Department;

@Mapper
public interface IDepartmentMapper {

    void addDepartment(
            Department department)
            throws ValidationException;

    Department getDepartmentById(
            String departmentId)
            throws ValidationException,
            DepartmentNotFoundException;

    List<Department> getAllDepartments();

    void updateDepartment(
            Department department)
            throws ValidationException,
            DepartmentNotFoundException;

    void deleteDepartment(
            String departmentId)
            throws ValidationException,
            DepartmentNotFoundException;
}