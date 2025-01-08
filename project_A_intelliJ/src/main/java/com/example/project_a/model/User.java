package com.example.project_a.model;

import jakarta.persistence.*;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Integer ID;

    @Column(name = "username", nullable = false, unique = true)
    private String Username;

    @Column(name = "password", nullable = false, unique = false)
    private String Password;

    @Column(name = "gender")
    private String Gender;

    @Column(name = "date_of_birth")
    private String DOB;

    @Column(name = "phone_number")
    private int PhoneNumber;

    @Column(name = "address")
    private String Address;

    @Column(name = "email", nullable = false, unique = true)
    private String Email;

    @Column(name = "status", columnDefinition = "Active")
    private String Status;

    @Column(name = "role", columnDefinition = "Client")
    private String Role;

    @Column(name = "citizen_id")
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
