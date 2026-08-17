package com.crimsonlogic.hospitalmanagement.model;

public class Address {

	private String addressId;;
	private String street;
	private String city;


	private String state;
	private String pincode;
    private boolean active;

    public Address() {}

    public Address(String addressId, String street, String city, String state, String pincode, boolean active) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

	public String getStreet() {
		return street;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", pincode=" + pincode + "]";
	}

	public void setStreet(String street) {
		this.street = street;
	}
}