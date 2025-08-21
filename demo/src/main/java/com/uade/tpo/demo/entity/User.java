package com.uade.tpo.demo.entity;

import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
public class User {
    public User(){}
   

    public User(String fullname, String email, String password){
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean status = true;

    @Column(nullable = false)
    private boolean admin;
}
