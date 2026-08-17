package com.crimsonlogic.hospitalmanagement.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for securely hashing and verifying user passwords.
 *
 * <p>Passwords are never stored as plain text in the database.
 * BCrypt is used to generate a one-way password hash.</p>
 */
public class PasswordUtil {

    /**
     * Hashes a plain-text password using BCrypt.
     *
     * @param password plain-text password
     * @return BCrypt hashed password
     */
    public static String hashPassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(
                    "Password cannot be empty");
        }

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt());
    }

    /**
     * Verifies a plain-text password against a stored BCrypt hash.
     *
     * @param password plain-text password entered by the user
     * @param passwordHash stored BCrypt password hash
     * @return true if the password matches, otherwise false
     */
    public static boolean verifyPassword(
            String password,
            String passwordHash) {

        if (password == null || passwordHash == null) {
            return false;
        }

        return BCrypt.checkpw(
                password,
                passwordHash);
    }
}