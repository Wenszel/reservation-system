package com.example.reservation.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = Reservation_.USER)
    private Set<Reservation> reservations = new HashSet<>();
}
