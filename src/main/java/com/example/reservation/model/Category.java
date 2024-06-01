package com.example.reservation.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Categories")
public class Category {
    @Id
    private long id;
    private String name;
    @ManyToMany(mappedBy = Room_.CATEGORIES)
    private Set<Room> rooms;
}
