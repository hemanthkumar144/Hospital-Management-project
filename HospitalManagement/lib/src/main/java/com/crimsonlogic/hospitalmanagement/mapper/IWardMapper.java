package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.exceptions.WardNotFoundException;
import com.crimsonlogic.hospitalmanagement.model.Ward;

public interface IWardMapper {

    void addWard(Ward ward) throws ValidationException;

    Ward getWardById(String wardId) throws ValidationException, WardNotFoundException;

    List<Ward> getAllWards();

    void updateWard(Ward ward) throws ValidationException, WardNotFoundException;

    void deleteWard(String wardId) throws ValidationException, WardNotFoundException;
}