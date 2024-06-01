package com.example.reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Equipment {
    @Id
    private long id;
    private String name;
    @ManyToMany(mappedBy = Reservation_.EQUIPMENTS)
    private Set<Reservation> reservations;
    @ManyToMany(mappedBy = Room_.EQUIPMENTS)
    private Set<Room> rooms;
}
