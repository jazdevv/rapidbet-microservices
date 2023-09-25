package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "credit")
    private Float credit;

    @Column(name="isAdmin", columnDefinition = "BOOLEAN DEFAULT true") //true set for development purposes
    private Boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    // Default constructor without any arguments
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.credit = 0.0F;
        this.isAdmin = true;
    }

    public User(Long id, String email, String password, Float credit, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.credit = credit;
        this.isAdmin = isAdmin;
    }
}

