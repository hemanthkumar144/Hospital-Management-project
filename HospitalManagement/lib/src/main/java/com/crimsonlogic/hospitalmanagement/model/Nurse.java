package com.crimsonlogic.hospitalmanagement.model;

public class Nurse extends Staff {

    
    private String shift;
    private boolean active;


    public Nurse(String staffId, String name, int age, String gender, String phone, double salary, Department department, String shift, boolean active) {
        super(staffId, name, age, gender, phone, salary, department);
        
        this.shift = shift;
        this.active = active;
    }

   



    @Override
    public String toString() {
        return 
                "staffId='" + getStaffId() + '\'' +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", gender='" + getGender() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", salary=" + getSalary() +
                ", department=" + getDepartment() +
                ", shift='" + shift + '\'' +
                ", active=" + active ;
    }






	public Nurse() {
		super();
	}



	public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void performDuty() {
        System.out.println("Nurse is assisting patients");
    }

    // getters/setters
}