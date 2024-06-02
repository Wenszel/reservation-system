package com.example.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Data
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
    @OneToMany(mappedBy = Reservation_.ROOM)
    private Set<Reservation> reservations;
    private int capacity;
    private int floor;
    private int squareMeters;

    public long getId() {
        return id;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

}
