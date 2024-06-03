package com.example.reservation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="Categories")
public class Category {
    @Id
    private long id;
    private String name;
    @ManyToMany(mappedBy = Room_.CATEGORIES)
    private Set<Room> rooms = new HashSet<>();
}
