package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Nurse;

public interface INurseMapper {

	void addNurse(Nurse nurse)
			throws ValidationException;

	Nurse getNurseById(String staffId)
			throws ValidationException;

	List<Nurse> getAllNurses();

	void updateNurse(Nurse nurse)
			throws ValidationException;

	void deactivateNurse(String staffId)
			throws ValidationException;

	Nurse getNurseByUserId(String userId);
}