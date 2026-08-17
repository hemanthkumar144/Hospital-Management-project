package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDateTime;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.enums.AdmissionStatus;
import com.crimsonlogic.hospitalmanagement.enums.WardType;
import com.crimsonlogic.hospitalmanagement.exceptions.BedIdNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.*;
import com.crimsonlogic.hospitalmanagement.model.Admission;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class AdmissionServiceImpl implements IAdmissionMapper {

    // =========================================================
    // BASIC ADMISSION OPERATIONS
    // =========================================================

    @Override
    public void addAdmission(
            Admission admission) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            mapper.addAdmission(admission);

            session.commit();
        }
    }


    @Override
    public Admission getAdmissionById(
            String admissionId)
            throws ValidationException {

        validateId(
                admissionId,
                "Admission ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            Admission admission =
                    mapper.getAdmissionById(
                            admissionId.trim());

            if (admission == null) {

                throw new ValidationException(
                        "Admission not found with ID : "
                                + admissionId);
            }

            return admission;
        }
    }


    @Override
    public Admission getActiveAdmissionByPatient(
            String patientId)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            return mapper.getActiveAdmissionByPatient(
                    patientId.trim());
        }
    }


    @Override
    public Admission getActiveAdmissionByBed(
            String bedId)
            throws ValidationException {

        validateId(
                bedId,
                "Bed ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            return mapper.getActiveAdmissionByBed(
                    bedId.trim());
        }
    }


    @Override
    public List<Admission> getAllAdmissions() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            return mapper.getAllAdmissions();
        }
    }


    @Override
    public void updateAdmission(
            Admission admission) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            mapper.updateAdmission(admission);

            session.commit();
        }
    }


    // =========================================================
    // DISCHARGE ADMISSION - MAPPER METHOD
    // =========================================================

    @Override
    public void dischargeAdmission(
            Admission admission) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            mapper.dischargeAdmission(
                    admission);

            session.commit();
        }
    }


    // =========================================================
    // BED OPERATIONS
    // =========================================================

    @Override
    public Bed findAvailableBedByWardType(
            WardType wardType) {

        if (wardType == null) {

            throw new IllegalArgumentException(
                    "Ward type is required");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            return mapper.findAvailableBedByWardType(
                    wardType);
        }
    }


    // =========================================================
    // FIND AVAILABLE BED
    // =========================================================

    public Bed findAvailableBed(
            String patientId,
            WardType wardType)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        if (wardType == null) {

            throw new ValidationException(
                    "Ward type is required");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper patientMapper =
                    session.getMapper(
                            IPatientMapper.class);

            IAdmissionMapper admissionMapper =
                    session.getMapper(
                            IAdmissionMapper.class);


            // =================================================
            // PATIENT VALIDATION
            // =================================================

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


            // =================================================
            // EXISTING ADMISSION
            // =================================================

            Admission existingAdmission =
                    admissionMapper
                            .getActiveAdmissionByPatient(
                                    patientId.trim());

            if (existingAdmission != null) {

                throw new ValidationException(
                        "Patient already has an active "
                                + "admission : "
                                + existingAdmission
                                .getAdmissionId());
            }


            // =================================================
            // FIND AVAILABLE BED
            // =================================================

            Bed bed =
                    admissionMapper
                            .findAvailableBedByWardType(
                                    wardType);

            if (bed == null) {

                throw new ValidationException(
                        "No available beds found in "
                                + wardType.name()
                                .replace("_", " "));
            }

            return bed;
        }
    }


    // =========================================================
    // ADMIT PATIENT
    // =========================================================

    @Override
    public Admission admitPatient(
            String patientId,
            WardType wardType,
            String selectedBedId)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        validateId(
                selectedBedId,
                "Bed ID");

        if (wardType == null) {

            throw new ValidationException(
                    "Ward type is required");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPatientMapper patientMapper =
                    session.getMapper(
                            IPatientMapper.class);

            IBedMapper bedMapper =
                    session.getMapper(
                            IBedMapper.class);

            IAdmissionMapper admissionMapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            try {

                // =============================================
                // PATIENT
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
                // CHECK ACTIVE ADMISSION
                // =============================================

                Admission existingPatientAdmission =
                        admissionMapper
                                .getActiveAdmissionByPatient(
                                        patientId.trim());

                if (existingPatientAdmission != null) {

                    throw new ValidationException(
                            "Patient already has an active "
                                    + "admission : "
                                    + existingPatientAdmission
                                    .getAdmissionId());
                }


                // =============================================
                // FIND BED
                // =============================================

                Bed bed;

                try {

                    bed =
                            bedMapper.getBedById(
                                    selectedBedId.trim());

                } catch (BedIdNotFoundException e) {

                    throw new ValidationException(
                            "Bed not found with ID : "
                                    + selectedBedId);
                }

                if (bed == null) {

                    throw new ValidationException(
                            "Bed not found with ID : "
                                    + selectedBedId);
                }

                if (!bed.isActive()) {

                    throw new ValidationException(
                            "Selected bed is inactive");
                }

                if (!"AVAILABLE".equalsIgnoreCase(
                        bed.getAvailability())) {

                    throw new ValidationException(
                            "Selected bed is no longer available");
                }


                // =============================================
                // VERIFY WARD
                // =============================================

                if (bed.getWard() == null
                        || bed.getWard().getWardType() == null) {

                    throw new ValidationException(
                            "Selected bed is not associated "
                                    + "with a valid ward");
                }

                if (bed.getWard().getWardType()
                        != wardType) {

                    throw new ValidationException(
                            "Selected bed does not belong "
                                    + "to the requested ward type");
                }


                // =============================================
                // CHECK BED ACTIVE ADMISSION
                // =============================================

                Admission existingBedAdmission =
                        admissionMapper
                                .getActiveAdmissionByBed(
                                        selectedBedId.trim());

                if (existingBedAdmission != null) {

                    throw new ValidationException(
                            "Bed already has an active "
                                    + "admission : "
                                    + existingBedAdmission
                                    .getAdmissionId());
                }


                // =============================================
                // OCCUPY BED
                // =============================================

                bed.setAvailability(
                        "OCCUPIED");

                bed.setPatient(
                        patient);


                // =============================================
                // CREATE ADMISSION
                // =============================================

                Admission admission =
                        new Admission();

                admission.setAdmissionId(
                        IdGenerator.generateRandomId(
                                "ADM"));

                admission.setPatient(
                        patient);

                admission.setBed(
                        bed);

                admission.setAdmissionDate(
                        LocalDateTime.now());

                admission.setDischargeDate(
                        null);

                admission.setStatus(
                        AdmissionStatus.ADMITTED);

                admission.setActive(
                        true);


                // =============================================
                // INSERT ADMISSION
                // =============================================

                admissionMapper.addAdmission(
                        admission);


                // =============================================
                // UPDATE BED
                // =============================================

                try {

                    bedMapper.updateBed(
                            bed);

                } catch (BedIdNotFoundException e) {

                    throw new ValidationException(
                            "Bed not found while updating bed");
                }


                // =============================================
                // COMMIT
                // =============================================

                session.commit();

                return admission;

            } catch (ValidationException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }
    @Override
    public List<Admission> getAdmissionsByPatientId(
            String patientId)
            throws ValidationException {

        validateId(
                patientId,
                "Patient ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper mapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            return mapper.getAdmissionsByPatientId(
                    patientId.trim());
        }
    }


    // =========================================================
    // DISCHARGE ADMISSION BY ID
    // =========================================================

    @Override
    public void dischargeAdmission(
            String admissionId)
            throws ValidationException {

        validateId(
                admissionId,
                "Admission ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IAdmissionMapper admissionMapper =
                    session.getMapper(
                            IAdmissionMapper.class);

            IBedMapper bedMapper =
                    session.getMapper(
                            IBedMapper.class);

            try {

                // =============================================
                // GET ADMISSION
                // =============================================

                Admission admission =
                        admissionMapper
                                .getAdmissionById(
                                        admissionId.trim());

                if (admission == null) {

                    throw new ValidationException(
                            "Admission not found with ID : "
                                    + admissionId);
                }


                // =============================================
                // CHECK STATUS
                // =============================================

                if (admission.getStatus()
                        != AdmissionStatus.ADMITTED) {

                    throw new ValidationException(
                            "Admission is already discharged");
                }


                // =============================================
                // DISCHARGE ADMISSION
                // =============================================

                admission.setDischargeDate(
                        LocalDateTime.now());

                admission.setStatus(
                        AdmissionStatus.DISCHARGED);

                admission.setActive(
                        false);

                admissionMapper.dischargeAdmission(
                        admission);


                // =============================================
                // RELEASE BED
                // =============================================

                Bed bed =
                        admission.getBed();

                if (bed == null) {

                    throw new ValidationException(
                            "Bed associated with admission "
                                    + "was not found");
                }

                bed.setAvailability(
                        "AVAILABLE");

                bed.setPatient(
                        null);

                try {

                    bedMapper.updateBed(
                            bed);

                } catch (BedIdNotFoundException e) {

                    throw new ValidationException(
                            "Bed not found while updating bed");
                }


                // =============================================
                // COMMIT
                // =============================================

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