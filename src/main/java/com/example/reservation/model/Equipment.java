package com.example.reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Equipment {
    @Id
    private long id;
    private String name;
    @ManyToMany(mappedBy = Reservation_.EQUIPMENTS)
    private Set<Reservation> reservations = new HashSet<>();
    @ManyToMany(mappedBy = Room_.EQUIPMENTS)
    private Set<Room> rooms = new HashSet<>();
    public void addRoom(Room room) {
        this.rooms.add(room);
        room.getEquipments().add(this);
    }
}
