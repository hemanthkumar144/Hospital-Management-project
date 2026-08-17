package com.crimsonlogic.hospitalmanagement.model;

public class Department {

	private String departmentId;
    private String departmentName;
    private String location;
    private boolean active;


    public Department() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Department(String departmentId, String departmentName, String location, boolean active) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
        this.active=active;
    }

  

    public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "departmentId=" + departmentId
                + ", departmentName=" + departmentName
                + ", location=" + location ;
    }
}