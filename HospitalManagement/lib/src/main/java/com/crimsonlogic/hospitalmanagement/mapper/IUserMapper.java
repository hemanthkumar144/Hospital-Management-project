package com.crimsonlogic.hospitalmanagement.mapper;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;

public interface IUserMapper {

    UserAccount getUserByUsername(
            String username);

    void addUser(
            UserAccount user)
            throws ValidationException;

    void deactivateUser(
            String userId)
            throws ValidationException;

    UserAccount getUserById(
            String userId)
            throws ValidationException;
}