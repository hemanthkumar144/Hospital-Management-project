package com.crimsonlogic.hospitalmanagement.services;

import org.apache.ibatis.session.SqlSession;


import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.*;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;
import com.crimsonlogic.hospitalmanagement.util.PasswordUtil;

public class AuthenticationServiceImpl {

	public UserAccount login(
	        String username,
	        String password)
	        throws ValidationException {

        validateLoginDetails(
                username,
                password);
        

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IUserMapper mapper =
                    session.getMapper(
                            IUserMapper.class);

            UserAccount user =
                    mapper.getUserByUsername(
                            username.trim());

            if (user == null) {

                throw new ValidationException(
                        "Invalid username or password");
            }

            if (!user.isActive()) {

                throw new ValidationException(
                        "User account is inactive");
            }

           

            boolean passwordValid =
                    PasswordUtil.verifyPassword(
                            password,
                            user.getPasswordHash());

            if (!passwordValid) {

                throw new ValidationException(
                        "Invalid username or password");
            }

            return user;
        }
    }

    private void validateLoginDetails(
            String username,
            String password)
            throws ValidationException {

        if (username == null
                || username.trim().isEmpty()) {

            throw new ValidationException(
                    "Username cannot be empty");
        }

        if (!username.matches(
                "^[A-Za-z0-9_]{4,50}$")) {

            throw new ValidationException(
                    "Username must contain 4 to 50 "
                    + "letters, digits or underscores");
        }

        if (password == null
                || password.isEmpty()) {

            throw new ValidationException(
                    "Password cannot be empty");
        }
    }
    public UserAccount login(
            String username,
            String password,
            String expectedRole)
            throws ValidationException {

        UserAccount user = login(username, password);

        if (expectedRole != null &&
            !expectedRole.equalsIgnoreCase(user.getRole())) {

            throw new ValidationException(
                    "Invalid role for this account.");
        }

        return user;
    }
}