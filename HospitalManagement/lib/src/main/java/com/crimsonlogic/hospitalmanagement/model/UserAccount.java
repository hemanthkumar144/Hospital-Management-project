package com.crimsonlogic.hospitalmanagement.model;

/**
 * Represents a user account in the Hospital ERP system.
 *
 * <p>This class stores the login credentials and role information
 * required for user authentication and role-based menu access.</p>
 *
 * <p>The password is stored as a BCrypt hash rather than as
 * plain text for security.</p>
 */
public class UserAccount {

    /** Unique identifier assigned to the user account. */
    private String userId;

    /** Username used by the user to log into the system. */
    private String username;

    /** BCrypt hashed password of the user. */
    private String passwordHash;

    /** Role assigned to the user, such as ADMIN, DOCTOR or NURSE. */
    private String role;

    /** Indicates whether the user account is currently active. */
    private boolean active;

    /**
     * Default constructor required for object creation
     * and MyBatis mapping.
     */
    public UserAccount() {
    }

    /**
     * Creates a user account with the specified details.
     *
     * @param userId unique identifier of the user
     * @param username login username
     * @param passwordHash BCrypt hashed password
     * @param role role assigned to the user
     * @param active indicates whether the account is active
     */
    public UserAccount(String userId,
                       String username,
                       String passwordHash,
                       String role,
                       boolean active) {

        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
    }

    /**
     * Returns the unique user ID.
     *
     * @return user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the unique user ID.
     *
     * @param userId user ID to assign
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username username to assign
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the BCrypt password hash.
     *
     * @return password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the BCrypt password hash.
     *
     * @param passwordHash hashed password to assign
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the role assigned to the user.
     *
     * @return user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role assigned to the user.
     *
     * @param role role to assign
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Checks whether the user account is active.
     *
     * @return true if the account is active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the user account.
     *
     * @param active active status to assign
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns a string representation of the user account.
     *
     * <p>The password hash is intentionally excluded
     * for security reasons.</p>
     *
     * @return user account details
     */
    @Override
    public String toString() {
        return "UserAccount [userId=" + userId
                + ", username=" + username
                + ", role=" + role
                + ", active=" + active + "]";
    }
}