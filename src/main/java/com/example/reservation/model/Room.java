package com.example.reservation.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Rooms")
public class Room {
    @Id
    private long id;
    private String name;
    @ManyToMany()
    @JoinTable(
        name = "RoomCategories",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
    @ManyToMany()
    @JoinTable(
        name = "RoomEquipments",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipments;
    private int capacity;
    private int floor;
    private int squareMeters;
}
