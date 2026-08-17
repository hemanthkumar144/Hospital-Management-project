package com.crimsonlogic.hospitalmanagement.model;

import java.time.LocalDate;

/**
 * Model class representing a Bill generated for a patient.
 * A bill contains consultation charges, medicine charges,
 * bed charges, and the total payable amount.
 *
 * The total amount is calculated automatically
 * in the service layer.
 */
public class Bill {

    /**
     * Unique bill identifier.
     * Example: BIL101
     */
    private String billId;

    /**
     * Patient associated with the bill.
     */
    private Patient patient;

    /**
     * Doctor consultation fee.
     */
    private double consultationFee;

    /**
     * Total medicine charges.
     */
    private double medicineCharges;

    /**
     * Bed and ward charges.
     */
    private double bedCharges;
    private double laboratoryCharges;

    /**
     * Total amount payable by the patient.
     */
    private double totalAmount;
    private LocalDate billDate;
    private String status;
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
     * Default constructor.
     */
    public Bill() {
    }

//    /**
//     * Parameterized constructor.
//     *
//     * @param billId unique bill identifier
//     * @param patient patient associated with the bill
//     * @param consultationFee consultation fee
//     * @param medicineCharges medicine charges
//     * @param bedCharges bed charges
//     * @param totalAmount total payable amount
//     */


   

  
    // Getters and Setters
	public String getBillId() {
		return billId;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", patient=" + patient + ", consultationFee=" + consultationFee
				+ ", medicineCharges=" + medicineCharges + ", bedCharges=" + bedCharges + ", totalAmount=" + totalAmount
				+ ", billDate=" + billDate + ", status=" + status + "]";
	}


	public Bill(String billId, Patient patient, double consultationFee, double medicineCharges, double bedCharges,
			double laboratoryCharges, double totalAmount, LocalDate billDate, String status,boolean active) {
		super();
		this.billId = billId;
		this.patient = patient;
		this.consultationFee = consultationFee;
		this.medicineCharges = medicineCharges;
		this.bedCharges = bedCharges;
		this.laboratoryCharges = laboratoryCharges;
		this.totalAmount = totalAmount;
		this.billDate = billDate;
		this.status = status;
		this.active=active;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public double getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(double consultationFee) {
		this.consultationFee = consultationFee;
	}

	public double getMedicineCharges() {
		return medicineCharges;
	}

	public void setMedicineCharges(double medicineCharges) {
		this.medicineCharges = medicineCharges;
	}

	public double getBedCharges() {
		return bedCharges;
	}

	public void setBedCharges(double bedCharges) {
		this.bedCharges = bedCharges;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getLaboratoryCharges() {
		return laboratoryCharges;
	}

	public void setLaboratoryCharges(double laboratoryCharges) {
		this.laboratoryCharges = laboratoryCharges;
	}
}