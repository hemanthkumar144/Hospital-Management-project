package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.exceptions.BedIdNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.Bed;

/**
 * MyBatis mapper for Bed database operations.
 */
public interface IBedMapper {

    /**
     * Adds a new bed.
     *
     * @param bed bed to be added
     */
    void addBed(Bed bed) throws ValidationException;


    /**
     * Retrieves a bed by its ID.
     *
     * @param bedId unique bed ID
     * @return matching bed
     */
    Bed getBedById(String bedId) throws BedIdNotFoundException, ValidationException;


    /**
     * Retrieves all active beds.
     *
     * @return list of active beds
     */
    List<Bed> getAllBeds();


    /**
     * Updates an existing bed.
     *
     * @param bed bed containing updated information
     */
    void updateBed(Bed bed) throws ValidationException, BedIdNotFoundException;


    /**
     * Soft-deletes a bed.
     *
     * @param bedId unique bed ID
     */
    void deleteBed(String bedId) throws BedIdNotFoundException, ValidationException;
}