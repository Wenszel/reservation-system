package com.example.reservation.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
    private Date startDate;
    private Date endDate;
    @ManyToMany()
    @JoinTable(
        name = "ReservationEquipments",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipments = new HashSet<>();
    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
        equipment.getReservations().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private double price;
    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
