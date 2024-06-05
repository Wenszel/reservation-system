package com.example.reservation.dto;

import com.example.reservation.model.Equipment;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
@Getter
@Setter
@Builder
public class ReservationResponse {
    private long id;
    private Date startDate;
    private Date endDate;
    private String roomName;
    private long roomId;
    private double price;
    private List<String> equipments;
    private String user;

    public static ReservationResponse getReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .price(reservation.getPrice())
                .roomId(reservation.getRoom().getId())
                .roomName(reservation.getRoom().getName())
                .user(reservation.getUser().getEmail())
                .equipments(reservation.getEquipments().stream().map(Equipment::getName).toList())
                .build();
    }
}
