package com.example.project_a.user;

import jakarta.persistence.*;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Integer ID;

    @Column(nullable = false, unique = true)
    private String Username;

    @Column(nullable = false, unique = false)
    private String Password;

    @Column
    private String Gender;

    @Column
    private String DOB;

    @Column
    private int PhoneNumber;

    @Column
    private String Address;

    @Column(nullable = false, unique = true)
    private String Email;

    @Column
    private String Status;

    @Column
    private String Role;

    @Column
    private String CitizenID ;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getCitizenID() {
        return CitizenID;
    }

    public void setCitizenID(String citizenID) {
        CitizenID = citizenID;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Gender='" + Gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", PhoneNumber=" + PhoneNumber +
                ", Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", Status='" + Status + '\'' +
                ", Role='" + Role + '\'' +
                ", CitizenID='" + CitizenID + '\'' +
                '}';
    }
}
