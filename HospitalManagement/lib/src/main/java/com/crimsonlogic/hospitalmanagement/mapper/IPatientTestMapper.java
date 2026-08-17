package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crimsonlogic.hospitalmanagement.model.PatientTest;

public interface IPatientTestMapper {

    void addPatientTest(
            PatientTest patientTest);

    PatientTest getPatientTestById(
            @Param("patientTestId")
            String patientTestId);

    List<PatientTest> getPatientTestsByPatientId(
            @Param("patientId")
            String patientId);

    List<PatientTest> getAllPatientTests();

    void updateTestStatus(
            @Param("patientTestId")
            String patientTestId,
            @Param("status")
            String status);
}