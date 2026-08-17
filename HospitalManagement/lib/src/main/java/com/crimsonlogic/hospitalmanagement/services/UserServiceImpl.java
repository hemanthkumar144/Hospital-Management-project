package com.crimsonlogic.hospitalmanagement.services;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IUserMapper;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;
import com.crimsonlogic.hospitalmanagement.util.PasswordUtil;

@Service
public class UserServiceImpl implements IUserMapper {

    // =========================================================
    // ADD USER
    // =========================================================

    @Override
    public void addUser(
            UserAccount user)
            throws ValidationException {

        validateUser(user);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IUserMapper mapper =
                    session.getMapper(
                            IUserMapper.class);

            try {

                // Check whether username already exists
                UserAccount existingUser =
                        mapper.getUserByUsername(
                                user.getUsername());

                if (existingUser != null) {

                    throw new ValidationException(
                            "Username already exists");
                }


                // Generate User ID
                String userId =
                        IdGenerator.generateRandomId(
                                "USR");

                user.setUserId(userId);


                // Validate password
                validatePassword(
                        user.getPasswordHash());


                // Hash password
                String passwordHash =
                        PasswordUtil.hashPassword(
                                user.getPasswordHash());

                user.setPasswordHash(
                        passwordHash);


                // Newly created account is active
                user.setActive(true);


                // Insert user
                mapper.addUser(user);

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
    // GET USER BY USERNAME
    // =========================================================

    @Override
    public UserAccount getUserByUsername(
            String username) {

        if (username == null
                || username.trim().isEmpty()) {

            return null;
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IUserMapper mapper =
                    session.getMapper(
                            IUserMapper.class);

            return mapper.getUserByUsername(
                    username.trim());
        }
    }


    // =========================================================
    // GET USER BY ID
    // =========================================================

    @Override
    public UserAccount getUserById(
            String userId)
            throws ValidationException {

        if (userId == null
                || userId.trim().isEmpty()) {

            throw new ValidationException(
                    "User ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IUserMapper mapper =
                    session.getMapper(
                            IUserMapper.class);

            return mapper.getUserById(
                    userId.trim());
        }
    }


    // =========================================================
    // DEACTIVATE USER
    // =========================================================

    @Override
    public void deactivateUser(
            String userId)
            throws ValidationException {

        if (userId == null
                || userId.trim().isEmpty()) {

            throw new ValidationException(
                    "User ID cannot be empty");
        }

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IUserMapper mapper =
                    session.getMapper(
                            IUserMapper.class);

            try {

                UserAccount user =
                        mapper.getUserById(
                                userId.trim());

                if (user == null) {

                    throw new ValidationException(
                            "User not found with ID : "
                                    + userId);
                }

                mapper.deactivateUser(
                        userId.trim());

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
    // VALIDATE PASSWORD
    // =========================================================

    private void validatePassword(
            String password)
            throws ValidationException {

        if (password == null
                || password.length() < 8) {

            throw new ValidationException(
                    "Password must contain at least 8 characters.");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch :
                password.toCharArray()) {

            if (Character.isUpperCase(ch)) {

                hasUpper = true;

            } else if (Character.isLowerCase(ch)) {

                hasLower = true;

            } else if (Character.isDigit(ch)) {

                hasDigit = true;

            } else {

                hasSpecial = true;
            }
        }

        if (!hasUpper) {

            throw new ValidationException(
                    "Password must contain at least one uppercase letter.");
        }

        if (!hasLower) {

            throw new ValidationException(
                    "Password must contain at least one lowercase letter.");
        }

        if (!hasDigit) {

            throw new ValidationException(
                    "Password must contain at least one number.");
        }

        if (!hasSpecial) {

            throw new ValidationException(
                    "Password must contain at least one special character.");
        }
    }


    // =========================================================
    // VALIDATE USER
    // =========================================================

    private void validateUser(
            UserAccount user)
            throws ValidationException {

        if (user == null) {

            throw new ValidationException(
                    "User cannot be null");
        }


        // -----------------------------------------------------
        // USERNAME
        // -----------------------------------------------------

        if (user.getUsername() == null
                || user.getUsername()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Username cannot be empty");
        }

        if (!user.getUsername()
                .trim()
                .matches(
                        "^[A-Za-z0-9_]{4,50}$")) {

            throw new ValidationException(
                    "Username must contain 4 to 50 "
                            + "letters, digits or underscores");
        }


        // -----------------------------------------------------
        // PASSWORD
        // -----------------------------------------------------

        if (user.getPasswordHash() == null
                || user.getPasswordHash()
                .isEmpty()) {

            throw new ValidationException(
                    "Password cannot be empty");
        }

        if (user.getPasswordHash()
                .length() < 8) {

            throw new ValidationException(
                    "Password must contain at least 8 characters");
        }

        if (user.getPasswordHash()
                .length() > 100) {

            throw new ValidationException(
                    "Password cannot exceed 100 characters");
        }


        // -----------------------------------------------------
        // ROLE
        // -----------------------------------------------------

        if (user.getRole() == null
                || user.getRole()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Role cannot be empty");
        }

        if (!user.getRole()
                .trim()
                .matches(
                        "(?i)ADMIN|DOCTOR|NURSE|PATIENT")) {

            throw new ValidationException(
                    "Invalid role. Allowed roles are "
                            + "ADMIN, DOCTOR, NURSE and PATIENT");
        }
    }
}