package com.example.reservation.dto;

import com.example.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    public String name;
    public Set<Reservation> reservations;
    public double totalPrice;
}
