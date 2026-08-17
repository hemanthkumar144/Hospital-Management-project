package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.BedIdNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.exceptions.WardNotFoundException;
import com.crimsonlogic.hospitalmanagement.mapper.IBedMapper;
import com.crimsonlogic.hospitalmanagement.model.Bed;
import com.crimsonlogic.hospitalmanagement.model.Patient;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class BedServiceImpl implements IBedMapper {

    private WardServiceImpl wardService =
            new WardServiceImpl();

    private PatientServiceImpl patientService =
            new PatientServiceImpl();


    // =========================================================
    // ADD BED
    // =========================================================

    @Override
    public void addBed(Bed bed)
            throws ValidationException {

        validateBed(bed);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBedMapper mapper =
                    session.getMapper(
                            IBedMapper.class);

            try {

                String bedId =
                        IdGenerator.generateRandomId(
                                "BED");

                bed.setBedId(bedId);

                bed.setActive(true);

                mapper.addBed(bed);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET BED BY ID
    // =========================================================

    @Override
    public Bed getBedById(
            String bedId)
            throws BedIdNotFoundException,
            ValidationException {

        validateId(
                bedId,
                "Bed ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBedMapper mapper =
                    session.getMapper(
                            IBedMapper.class);

            Bed bed =
                    mapper.getBedById(
                            bedId.trim());

            if (bed == null) {

                throw new BedIdNotFoundException(
                        "Bed not found with ID : "
                                + bedId);
            }

            return bed;
        }
    }


    // =========================================================
    // GET ALL BEDS
    // =========================================================

    @Override
    public List<Bed> getAllBeds() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBedMapper mapper =
                    session.getMapper(
                            IBedMapper.class);

            return mapper.getAllBeds();
        }
    }


    // =========================================================
    // UPDATE BED
    // =========================================================

    @Override
    public void updateBed(
            Bed bed)
            throws ValidationException,
            BedIdNotFoundException {

        if (bed == null) {

            throw new ValidationException(
                    "Bed cannot be null");
        }

        validateId(
                bed.getBedId(),
                "Bed ID");

        validateBed(bed);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBedMapper mapper =
                    session.getMapper(
                            IBedMapper.class);

            try {

                Bed existingBed =
                        mapper.getBedById(
                                bed.getBedId().trim());

                if (existingBed == null) {

                    throw new BedIdNotFoundException(
                            "Bed not found with ID : "
                                    + bed.getBedId());
                }

                mapper.updateBed(bed);

                session.commit();

            } catch (BedIdNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE BED
    // =========================================================

    @Override
    public void deleteBed(
            String bedId)
            throws BedIdNotFoundException,
            ValidationException {

        validateId(
                bedId,
                "Bed ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IBedMapper mapper =
                    session.getMapper(
                            IBedMapper.class);

            try {

                Bed bed =
                        mapper.getBedById(
                                bedId.trim());

                if (bed == null) {

                    throw new BedIdNotFoundException(
                            "Bed not found with ID : "
                                    + bedId);
                }

                if ("OCCUPIED".equalsIgnoreCase(
                        bed.getAvailability())) {

                    throw new ValidationException(
                            "Occupied bed cannot be deleted");
                }

                mapper.deleteBed(
                        bedId.trim());

                session.commit();

            } catch (BedIdNotFoundException e) {

                session.rollback();

                throw e;

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
    // BED VALIDATION
    // =========================================================

    private void validateBed(
            Bed bed)
            throws ValidationException {

        if (bed == null) {

            throw new ValidationException(
                    "Bed cannot be null");
        }


        // -----------------------------------------------------
        // WARD VALIDATION
        // -----------------------------------------------------

        if (bed.getWard() == null) {

            throw new ValidationException(
                    "Ward is required");
        }

        if (bed.getWard().getWardId() == null
                || bed.getWard()
                .getWardId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Ward ID cannot be empty");
        }

        Ward ward;

        try {

            ward =
                    wardService.getWardById(
                            bed.getWard()
                                    .getWardId()
                                    .trim());

        } catch (WardNotFoundException e) {

            throw new ValidationException(
                    "Ward not found with ID : "
                            + bed.getWard()
                            .getWardId());
        }

        if (!ward.isActive()) {

            throw new ValidationException(
                    "Selected ward is inactive");
        }


        // -----------------------------------------------------
        // AVAILABILITY VALIDATION
        // -----------------------------------------------------

        if (bed.getAvailability() == null
                || bed.getAvailability()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Availability cannot be empty");
        }

        String availability =
                bed.getAvailability()
                        .trim()
                        .toUpperCase();

        if (!availability.equals("AVAILABLE")
                && !availability.equals("OCCUPIED")) {

            throw new ValidationException(
                    "Availability must be "
                            + "AVAILABLE or OCCUPIED");
        }

        bed.setAvailability(
                availability);


        // -----------------------------------------------------
        // PATIENT VALIDATION
        // -----------------------------------------------------

        if ("AVAILABLE".equals(
                availability)) {

            if (bed.getPatient() != null) {

                throw new ValidationException(
                        "Available bed cannot have "
                                + "a patient assigned");
            }

            return;
        }


        if (bed.getPatient() == null) {

            throw new ValidationException(
                    "Occupied bed must have "
                            + "a patient assigned");
        }

        Patient patient =
                bed.getPatient();

        if (patient.getPatientId() == null
                || patient.getPatientId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Patient ID cannot be empty");
        }

        Patient existingPatient =
                patientService.getPatientById(
                        patient.getPatientId()
                                .trim());

        if (!existingPatient.isActive()) {

            throw new ValidationException(
                    "Selected patient is inactive");
        }

        bed.setPatient(
                existingPatient);
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