package com.crimsonlogic.hospitalmanagement.services;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import com.crimsonlogic.hospitalmanagement.exceptions.PrescriptionNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IPrescriptionMapper;
import com.crimsonlogic.hospitalmanagement.mapper.PrescriptionMedicineMapper;
import com.crimsonlogic.hospitalmanagement.mapper.PrescriptionTestMapper;
import com.crimsonlogic.hospitalmanagement.model.Prescription;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionMedicine;
import com.crimsonlogic.hospitalmanagement.model.PrescriptionTest;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class PrescriptionServiceImpl
        implements IPrescriptionMapper {

    private final PatientServiceImpl patientServiceImpl =
            new PatientServiceImpl();

    private final DoctorServiceImpl doctorServiceImpl =
            new DoctorServiceImpl();

    private final MedicineServiceImpl medicineServiceImpl =
            new MedicineServiceImpl();

    private final LaboratoryTestServiceImpl laboratoryTestServiceImpl =
            new LaboratoryTestServiceImpl();


    // =========================================================
    // ADD PRESCRIPTION
    // =========================================================

    @Override
    public void addPrescription(
            Prescription prescription)
            throws ValidationException {

        validatePrescription(
                prescription);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPrescriptionMapper prescriptionMapper =
                    session.getMapper(
                            IPrescriptionMapper.class);

            PrescriptionMedicineMapper medicineMapper =
                    session.getMapper(
                            PrescriptionMedicineMapper.class);

            PrescriptionTestMapper testMapper =
                    session.getMapper(
                            PrescriptionTestMapper.class);

            try {

                // Generate prescription ID
                String prescriptionId =
                        IdGenerator.generateRandomId(
                                "PRE");

                prescription.setPrescriptionId(
                        prescriptionId);

                prescription.setActive(true);


                // Insert main prescription
                prescriptionMapper.addPrescription(
                        prescription);


                // Insert medicines
                if (prescription.getMedicines() != null) {

                    for (PrescriptionMedicine medicine :
                            prescription.getMedicines()) {

                        validatePrescriptionMedicine(
                                medicine);

                        medicine.setPrescriptionId(
                                prescriptionId);

                        medicineMapper
                                .addPrescriptionMedicine(
                                        medicine);
                    }
                }


                // Insert tests
                if (prescription.getTests() != null) {

                    for (PrescriptionTest test :
                            prescription.getTests()) {

                        validatePrescriptionTest(
                                test);

                        test.setPrescriptionId(
                                prescriptionId);

                        testMapper.addPrescriptionTest(
                                test);
                    }
                }

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
    // GET PRESCRIPTION BY ID
    // =========================================================

    @Override
    public Prescription getPrescriptionById(
            String prescriptionId)
            throws PrescriptionNotFoundException,
            ValidationException {

        validateId(
                prescriptionId,
                "Prescription ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPrescriptionMapper mapper =
                    session.getMapper(
                            IPrescriptionMapper.class);

            Prescription prescription =
                    mapper.getPrescriptionById(
                            prescriptionId.trim());

            if (prescription == null) {

                throw new PrescriptionNotFoundException(
                        "Prescription not found with ID : "
                                + prescriptionId);
            }

            return prescription;
        }
    }


    // =========================================================
    // GET ALL PRESCRIPTIONS
    // =========================================================

    @Override
    public List<Prescription>
    getAllPrescriptions() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPrescriptionMapper mapper =
                    session.getMapper(
                            IPrescriptionMapper.class);

            return mapper.getAllPrescriptions();
        }
    }


    // =========================================================
    // UPDATE PRESCRIPTION
    // =========================================================

    @Override
    public void updatePrescription(
            Prescription prescription)
            throws ValidationException,
            PrescriptionNotFoundException {

        validatePrescription(
                prescription);

        validateId(
                prescription.getPrescriptionId(),
                "Prescription ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPrescriptionMapper prescriptionMapper =
                    session.getMapper(
                            IPrescriptionMapper.class);

            PrescriptionMedicineMapper medicineMapper =
                    session.getMapper(
                            PrescriptionMedicineMapper.class);

            PrescriptionTestMapper testMapper =
                    session.getMapper(
                            PrescriptionTestMapper.class);

            try {

                // Check existing prescription
                Prescription existing =
                        prescriptionMapper
                                .getPrescriptionById(
                                        prescription
                                                .getPrescriptionId()
                                                .trim());

                if (existing == null) {

                    throw new PrescriptionNotFoundException(
                            "Prescription not found with ID : "
                                    + prescription
                                    .getPrescriptionId());
                }


                // Update main prescription
                prescriptionMapper
                        .updatePrescription(
                                prescription);


                // Delete old medicines
                medicineMapper
                        .deleteByPrescriptionId(
                                prescription
                                        .getPrescriptionId());


                // Delete old tests
                testMapper
                        .deleteByPrescriptionId(
                                prescription
                                        .getPrescriptionId());


                // Insert new medicines
                if (prescription.getMedicines() != null) {

                    for (PrescriptionMedicine medicine :
                            prescription.getMedicines()) {

                        validatePrescriptionMedicine(
                                medicine);

                        medicine.setPrescriptionId(
                                prescription
                                        .getPrescriptionId());

                        medicineMapper
                                .addPrescriptionMedicine(
                                        medicine);
                    }
                }


                // Insert new tests
                if (prescription.getTests() != null) {

                    for (PrescriptionTest test :
                            prescription.getTests()) {

                        validatePrescriptionTest(
                                test);

                        test.setPrescriptionId(
                                prescription
                                        .getPrescriptionId());

                        testMapper.addPrescriptionTest(
                                test);
                    }
                }

                session.commit();

            } catch (PrescriptionNotFoundException e) {

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
    // DELETE PRESCRIPTION
    // =========================================================

    @Override
    public void deletePrescription(
            String prescriptionId)
            throws PrescriptionNotFoundException,
            ValidationException {

        validateId(
                prescriptionId,
                "Prescription ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPrescriptionMapper mapper =
                    session.getMapper(
                            IPrescriptionMapper.class);

            try {

                Prescription prescription =
                        mapper.getPrescriptionById(
                                prescriptionId.trim());

                if (prescription == null) {

                    throw new PrescriptionNotFoundException(
                            "Prescription not found with ID : "
                                    + prescriptionId);
                }

                mapper.deletePrescription(
                        prescriptionId.trim());

                session.commit();

            } catch (PrescriptionNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // VALIDATE PRESCRIPTION
    // =========================================================

    private void validatePrescription(
            Prescription prescription)
            throws ValidationException {

        if (prescription == null) {

            throw new ValidationException(
                    "Prescription cannot be null");
        }


        // Patient
        if (prescription.getPatient() == null) {

            throw new ValidationException(
                    "Patient is required");
        }

        String patientId =
                prescription.getPatient()
                        .getPatientId();

        validateId(
                patientId,
                "Patient ID");


        // Doctor
        if (prescription.getDoctor() == null) {

            throw new ValidationException(
                    "Doctor is required");
        }

        String doctorId =
                prescription.getDoctor()
                        .getStaffId();

        validateId(
                doctorId,
                "Doctor ID");


        // Instructions
        if (prescription.getInstructions() == null
                || prescription.getInstructions()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Instructions cannot be empty");
        }

        if (prescription.getInstructions()
                .trim()
                .length() > 255) {

            throw new ValidationException(
                    "Instructions cannot exceed "
                            + "255 characters");
        }


        // Date
        if (prescription.getPrescriptionDate()
                == null) {

            throw new ValidationException(
                    "Prescription date is required");
        }


        // Patient must exist
        patientServiceImpl.getPatientById(
                patientId.trim());


        // Doctor must exist
        doctorServiceImpl.getDoctorById(
                doctorId.trim());


        // At least one medicine or test
        boolean noMedicines =
                prescription.getMedicines() == null
                        || prescription.getMedicines()
                        .isEmpty();

        boolean noTests =
                prescription.getTests() == null
                        || prescription.getTests()
                        .isEmpty();

        if (noMedicines && noTests) {

            throw new ValidationException(
                    "Prescription must contain "
                            + "at least one medicine or test");
        }
    }


    // =========================================================
    // VALIDATE PRESCRIPTION MEDICINE
    // =========================================================

    private void validatePrescriptionMedicine(
            PrescriptionMedicine medicine)
            throws ValidationException {

        if (medicine == null) {

            throw new ValidationException(
                    "Prescription medicine cannot be null");
        }

        validateId(
                medicine.getMedicineId(),
                "Medicine ID");


        if (medicine.getDosage() == null
                || medicine.getDosage()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Dosage cannot be empty");
        }

        if (medicine.getDosage()
                .trim()
                .length() > 100) {

            throw new ValidationException(
                    "Dosage cannot exceed "
                            + "100 characters");
        }


        if (medicine.getQuantity() <= 0) {

            throw new ValidationException(
                    "Medicine quantity must be "
                            + "greater than zero");
        }


        medicineServiceImpl.getMedicineById(
                medicine.getMedicineId()
                        .trim());
    }


    // =========================================================
    // VALIDATE PRESCRIPTION TEST
    // =========================================================

    private void validatePrescriptionTest(
            PrescriptionTest test)
            throws ValidationException {

        if (test == null) {

            throw new ValidationException(
                    "Prescription test cannot be null");
        }

        validateId(
                test.getTestId(),
                "Test ID");


        laboratoryTestServiceImpl.getTestById(
                test.getTestId()
                        .trim());
    }


    // =========================================================
    // GENERIC ID VALIDATION
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