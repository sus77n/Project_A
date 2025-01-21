package com.example.project_a.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "role")
    private String role = "Client";

    @Column(name = "citizen_id")
    private String citizenId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;



    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String DOB) {
        this.dateOfBirth = DOB;
    }

    public String getPhoneNumber() {
        return ""+phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenID) {
        this.citizenId = citizenID;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + id +
                ", Username='" + username + '\'' +
                ", Password='" + password + '\'' +
                ", Gender='" + gender + '\'' +
                ", DOB='" + dateOfBirth + '\'' +
                ", PhoneNumber=" + phoneNumber +
                ", Address='" + address + '\'' +
                ", Email='" + email + '\'' +
                ", Status='" + status + '\'' +
                ", Role='" + role + '\'' +
                ", CitizenID='" + citizenId + '\'' +
                '}';
    }
}
