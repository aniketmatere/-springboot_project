package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "User_table")
public class User15 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Userid;
    private String name;
    private String email;
    private String password;
    private int contactNo;
    private String address;

    public User15() {
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int userid) {
        Userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
