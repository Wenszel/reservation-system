package com.example.reservation.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;

@Entity
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
    private Set<Equipment> equipments;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Room getRoom() {
        return room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

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
