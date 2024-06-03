package com.example.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class ReservationRequest {
    private long roomId;
    private Date startDate;
    private Date endDate;
    private long userId;
}