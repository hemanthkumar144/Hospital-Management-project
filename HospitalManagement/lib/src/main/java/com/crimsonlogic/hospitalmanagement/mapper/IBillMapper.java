package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crimsonlogic.hospitalmanagement.model.Bill;

public interface IBillMapper {

    void addBill(Bill bill);

    Bill getBillById(String billId);

    List<Bill> getAllBills();

    void updateBill(Bill bill);

    void deleteBill(String billId);

    void updateBillStatus(
            @Param("billId") String billId,
            @Param("status") String status);

    Integer getMaxBillNumber();

    List<Bill> getPendingBillsByPatientId(
            String patientId);
}