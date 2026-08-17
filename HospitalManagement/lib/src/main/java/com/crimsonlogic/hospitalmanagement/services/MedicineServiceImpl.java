package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.MedicineNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IMedicineMapper;
import com.crimsonlogic.hospitalmanagement.model.Medicine;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class MedicineServiceImpl
        implements IMedicineMapper {

    // =========================================================
    // ADD MEDICINE
    // =========================================================

    @Override
    public void addMedicine(
            Medicine medicine)
            throws ValidationException {

        validateMedicine(medicine);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            try {

                String medicineId =
                        IdGenerator.generateRandomId(
                                "MED");

                medicine.setMedicineId(
                        medicineId);

                medicine.setActive(true);

                mapper.addMedicine(
                        medicine);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET MEDICINE BY ID
    // =========================================================

    @Override
    public Medicine getMedicineById(
            String medicineId)
            throws ValidationException,
            MedicineNotFoundException {

        validateId(
                medicineId,
                "Medicine ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            Medicine medicine =
                    mapper.getMedicineById(
                            medicineId.trim());

            if (medicine == null) {

                throw new MedicineNotFoundException(
                        "Medicine with ID "
                                + medicineId
                                + " not found");
            }

            return medicine;
        }
    }


    // =========================================================
    // GET ALL MEDICINES
    // =========================================================

    @Override
    public List<Medicine> getAllMedicines() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            return mapper.getAllMedicines();
        }
    }


    // =========================================================
    // UPDATE MEDICINE
    // =========================================================

    @Override
    public void updateMedicine(
            Medicine medicine)
            throws ValidationException,
            MedicineNotFoundException {

        validateMedicine(medicine);

        validateId(
                medicine.getMedicineId(),
                "Medicine ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            try {

                Medicine existingMedicine =
                        mapper.getMedicineById(
                                medicine
                                        .getMedicineId()
                                        .trim());

                if (existingMedicine == null) {

                    throw new MedicineNotFoundException(
                            "Medicine with ID "
                                    + medicine
                                    .getMedicineId()
                                    + " not found");
                }

                mapper.updateMedicine(
                        medicine);

                session.commit();

            } catch (MedicineNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE MEDICINE
    // =========================================================

    @Override
    public void deleteMedicine(
            String medicineId)
            throws ValidationException,
            MedicineNotFoundException {

        validateId(
                medicineId,
                "Medicine ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            try {

                Medicine medicine =
                        mapper.getMedicineById(
                                medicineId.trim());

                if (medicine == null) {

                    throw new MedicineNotFoundException(
                            "Medicine with ID "
                                    + medicineId
                                    + " not found");
                }

                mapper.deleteMedicine(
                        medicineId.trim());

                session.commit();

            } catch (MedicineNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET MAX MEDICINE NUMBER
    // =========================================================

    @Override
    public Integer getMaxMedicineNumber() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IMedicineMapper mapper =
                    session.getMapper(
                            IMedicineMapper.class);

            return mapper.getMaxMedicineNumber();
        }
    }


    // =========================================================
    // VALIDATE MEDICINE NAME
    // =========================================================

    public void validateMedicineName(
            String name)
            throws ValidationException {

        if (name == null
                || name.trim().isEmpty()) {

            throw new ValidationException(
                    "Medicine name cannot be empty");
        }

        name = name.trim();

        if (!name.matches(
                "^[A-Za-z0-9][A-Za-z0-9 .-]{1,49}$")) {

            throw new ValidationException(
                    "Medicine name can contain only "
                            + "letters, numbers, spaces "
                            + "and hyphens");
        }

        if (name.matches(
                ".*(.)\\1\\1.*")) {

            throw new ValidationException(
                    "The same character cannot be repeated "
                            + "more than 2 times consecutively");
        }
    }


    // =========================================================
    // VALIDATE MANUFACTURER
    // =========================================================

    public void validateManufacturer(
            String manufacturer)
            throws ValidationException {

        if (manufacturer == null
                || manufacturer.trim().isEmpty()) {

            throw new ValidationException(
                    "Manufacturer cannot be empty");
        }

        manufacturer = manufacturer.trim();

        if (!manufacturer.matches(
                "^[A-Za-z0-9][A-Za-z0-9 .&-]{1,49}$")) {

            throw new ValidationException(
                    "Manufacturer can contain only "
                            + "letters, numbers, spaces, '.', "
                            + "'&' and '-'");
        }
    }


    // =========================================================
    // VALIDATE PRICE
    // =========================================================

    public void validatePrice(
            double price)
            throws ValidationException {

        if (price <= 0) {

            throw new ValidationException(
                    "Price must be greater than zero");
        }
    }


    // =========================================================
    // VALIDATE MEDICINE
    // =========================================================

    public void validateMedicine(
            Medicine medicine)
            throws ValidationException {

        if (medicine == null) {

            throw new ValidationException(
                    "Medicine cannot be null");
        }


        // -----------------------------------------------------
        // MEDICINE NAME
        // -----------------------------------------------------

        if (medicine.getMedicineName() == null
                || medicine.getMedicineName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Medicine name cannot be empty");
        }

        if (!medicine.getMedicineName()
                .trim()
                .matches(
                        "^[A-Za-z0-9][A-Za-z0-9 .-]{1,49}$")) {

            throw new ValidationException(
                    "Medicine name must contain 2 to "
                            + "50 valid characters");
        }

        String medicineName =
                medicine.getMedicineName()
                        .trim();

        if (!medicineName.matches(
                "^[A-Za-z0-9][A-Za-z0-9 .-]{1,49}$")) {

            throw new ValidationException(
                    "Medicine name must contain 2 to "
                            + "50 valid characters");
        }


        // -----------------------------------------------------
        // REPEATED CHARACTERS
        // -----------------------------------------------------

        if (medicineName.matches(
                ".*(.)\\1\\1.*")) {

            throw new ValidationException(
                    "The same character cannot be repeated "
                            + "more than 2 times consecutively");
        }


        // -----------------------------------------------------
        // MANUFACTURER
        // -----------------------------------------------------

        if (medicine.getManufacturer() == null
                || medicine.getManufacturer()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Manufacturer cannot be empty");
        }

        if (!medicine.getManufacturer()
                .trim()
                .matches(
                        "^[A-Za-z0-9][A-Za-z0-9 .&-]{1,49}$")) {

            throw new ValidationException(
                    "Manufacturer must contain 2 to "
                            + "50 valid characters");
        }


        // -----------------------------------------------------
        // PRICE
        // -----------------------------------------------------

        if (medicine.getPrice() <= 0) {

            throw new ValidationException(
                    "Price must be greater than zero");
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