package com.crimsonlogic.hospitalmanagement.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.exceptions.WardNotFoundException;
import com.crimsonlogic.hospitalmanagement.mapper.IWardMapper;
import com.crimsonlogic.hospitalmanagement.model.Ward;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class WardServiceImpl implements IWardMapper {

    // =========================================================
    // ADD WARD
    // =========================================================

    @Override
    public void addWard(Ward ward)
            throws ValidationException {

        validateWard(ward);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IWardMapper mapper =
                    session.getMapper(
                            IWardMapper.class);

            try {

                String wardId =
                        IdGenerator.generateRandomId(
                                "WAR");

                ward.setWardId(wardId);
                ward.setActive(true);

                mapper.addWard(ward);

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // GET WARD BY ID
    // =========================================================

    @Override
    public Ward getWardById(
            String wardId)
            throws ValidationException,
            WardNotFoundException {

        validateId(
                wardId,
                "Ward ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IWardMapper mapper =
                    session.getMapper(
                            IWardMapper.class);

            Ward ward =
                    mapper.getWardById(
                            wardId.trim());

            if (ward == null) {

                throw new WardNotFoundException(
                        "Ward with ID "
                                + wardId
                                + " not found");
            }

            return ward;
        }
    }


    // =========================================================
    // GET ALL WARDS
    // =========================================================

    @Override
    public List<Ward> getAllWards() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IWardMapper mapper =
                    session.getMapper(
                            IWardMapper.class);

            return mapper.getAllWards();
        }
    }


    // =========================================================
    // UPDATE WARD
    // =========================================================

    @Override
    public void updateWard(
            Ward ward)
            throws ValidationException,
            WardNotFoundException {

        if (ward == null) {

            throw new ValidationException(
                    "Ward cannot be null");
        }

        validateId(
                ward.getWardId(),
                "Ward ID");

        validateWard(ward);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IWardMapper mapper =
                    session.getMapper(
                            IWardMapper.class);

            try {

                Ward existingWard =
                        mapper.getWardById(
                                ward.getWardId()
                                        .trim());

                if (existingWard == null) {

                    throw new WardNotFoundException(
                            "Ward with ID "
                                    + ward.getWardId()
                                    + " not found");
                }

                mapper.updateWard(ward);

                session.commit();

            } catch (WardNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // DELETE WARD
    // =========================================================

    @Override
    public void deleteWard(
            String wardId)
            throws ValidationException,
            WardNotFoundException {

        validateId(
                wardId,
                "Ward ID");

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IWardMapper mapper =
                    session.getMapper(
                            IWardMapper.class);

            try {

                Ward ward =
                        mapper.getWardById(
                                wardId.trim());

                if (ward == null) {

                    throw new WardNotFoundException(
                            "Ward with ID "
                                    + wardId
                                    + " not found");
                }

                mapper.deleteWard(
                        wardId.trim());

                session.commit();

            } catch (WardNotFoundException e) {

                session.rollback();

                throw e;

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // WARD VALIDATION
    // =========================================================

    private void validateWard(
            Ward ward)
            throws ValidationException {

        if (ward == null) {

            throw new ValidationException(
                    "Ward cannot be null");
        }


        // -----------------------------------------------------
        // WARD NAME
        // -----------------------------------------------------

        if (ward.getWardName() == null
                || ward.getWardName()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Ward name cannot be empty");
        }

        String wardName =
                ward.getWardName().trim();

        if (!wardName.matches(
                "^[A-Za-z][A-Za-z ]{1,49}$")) {

            throw new ValidationException(
                    "Ward name must contain only "
                            + "letters and spaces and must "
                            + "contain 2 to 50 characters");
        }

        if (hasThreeConsecutiveSameCharacters(
                wardName)) {

            throw new ValidationException(
                    "The same character cannot be "
                            + "repeated more than 2 times "
                            + "consecutively");
        }


        // -----------------------------------------------------
        // WARD TYPE
        // -----------------------------------------------------

        if (ward.getWardType() == null) {

            throw new ValidationException(
                    "Ward type cannot be null");
        }


        // -----------------------------------------------------
        // BED CHARGE
        // -----------------------------------------------------

        if (Double.isNaN(
                ward.getBedCharge())
                || Double.isInfinite(
                ward.getBedCharge())) {

            throw new ValidationException(
                    "Invalid bed charge");
        }

        if (ward.getBedCharge() <= 0) {

            throw new ValidationException(
                    "Bed charge must be greater than 0");
        }

        if (ward.getBedCharge() > 1000000) {

            throw new ValidationException(
                    "Bed charge is too high");
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


    // =========================================================
    // REPEATED CHARACTER VALIDATION
    // =========================================================

    private boolean hasThreeConsecutiveSameCharacters(
            String value) {

        for (int i = 0;
             i < value.length() - 2;
             i++) {

            char first =
                    Character.toLowerCase(
                            value.charAt(i));

            char second =
                    Character.toLowerCase(
                            value.charAt(i + 1));

            char third =
                    Character.toLowerCase(
                            value.charAt(i + 2));

            if (first == second
                    && second == third) {

                return true;
            }
        }

        return false;
    }
}