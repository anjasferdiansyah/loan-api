package com.enigmacamp.loanapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mst_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @OneToMany
    private List<Role> roles;



}
