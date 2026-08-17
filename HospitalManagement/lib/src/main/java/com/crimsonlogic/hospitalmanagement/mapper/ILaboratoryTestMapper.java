package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import org.apache.ibatis.annotations.Param;

import com.crimsonlogic.hospitalmanagement.model.LaboratoryTest;

public interface ILaboratoryTestMapper {

    List<LaboratoryTest> getAllTests();

    LaboratoryTest getTestById(
            @Param("testId") String testId) throws ValidationException;

    void addTest(LaboratoryTest test) throws ValidationException;

    void updateTest(LaboratoryTest test) throws ValidationException;

    void deactivateTest(
            @Param("testId") String testId) throws ValidationException;
}