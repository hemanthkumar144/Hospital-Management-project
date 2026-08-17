package com.crimsonlogic.hospitalmanagement.model;

import java.time.LocalDate;

/**
 * Model class representing a Bill Payment.

 */
public class Payment {

	private String paymentId;
	private Bill bill;
	private String paymentType;
	private double amount;
	private LocalDate paymentDate;
	private String status;
    private boolean active;

    //constructor
    public Payment() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //parametarized constructor
    public Payment(String paymentId, Bill bill,
                   String paymentType,
                   double amount,
                   LocalDate paymentDate,boolean active) {

        this.paymentId = paymentId;
        this.bill = bill;
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.active=active;
       
        
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

  

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId
                + ", bill=" + bill
                + ", paymentType=" + paymentType
                + ", amount=" + amount
                + ", paymentDate=" + paymentDate
                + ", status=" + "]";
    }
}