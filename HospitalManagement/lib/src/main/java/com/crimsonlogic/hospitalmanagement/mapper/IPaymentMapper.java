package com.crimsonlogic.hospitalmanagement.mapper;

import java.util.List;

import com.crimsonlogic.hospitalmanagement.model.Payment;

public interface IPaymentMapper {

	void addPayment(Payment payment);

	Payment getPaymentById(String paymentId);

	List<Payment> getAllPayments();

	Integer getMaxPaymentNumber();
}