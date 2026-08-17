package com.crimsonlogic.hospitalmanagement.model;

public class Medicine {

	private String medicineId;
    private String medicineName;
    private String manufacturer;
    private double price;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
	public String toString() {
		return "Medicine [medicineId=" + medicineId + ", medicineName=" + medicineName + ", manufacturer="
				+ manufacturer + ", price=" + price + "]";
	}

	public Medicine() {}

    public Medicine(String medicineId, String medicineName,
                    String manufacturer, double price,boolean active) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.active=active;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
