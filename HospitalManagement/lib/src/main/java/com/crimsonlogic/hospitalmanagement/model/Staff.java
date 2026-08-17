package com.crimsonlogic.hospitalmanagement.model;

public abstract class Staff {

    public String staffId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private double salary;
    private Department department;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Staff() {
    }

    public Staff(String staffId,
                 String name,
                 int age,
                 String gender,
                 String phone,
                 double salary,
                 Department department) {

        this.staffId = staffId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.salary = salary;
        this.department = department;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public abstract void performDuty();
}