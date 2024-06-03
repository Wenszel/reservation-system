package com.example.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getRooms().add(this);
    }

    @ManyToMany()
    @JoinTable(
        name = "RoomEquipments",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipments = new HashSet<>();
    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
        equipment.getRooms().add(this);
    }

    @OneToMany(mappedBy = Reservation_.ROOM)
    private Set<Reservation> reservations = new HashSet<>();

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setRoom(this);
    }

    private int capacity;
    private int floor;
    private int squareMeters;

    public void setId (long id) {
        this.id = id;
    }
}
