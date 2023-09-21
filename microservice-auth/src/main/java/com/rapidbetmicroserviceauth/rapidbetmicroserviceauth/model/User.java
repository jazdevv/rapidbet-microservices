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

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", email='" + email + '\'' + ", lastName='" + password
                + '\'' + '}';
    }

    // Default constructor without any arguments
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.credit = 0.0F;
    }
}

