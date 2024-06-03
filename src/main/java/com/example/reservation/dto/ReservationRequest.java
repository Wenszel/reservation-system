package com.example.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ReservationRequest {
    private long roomId;
    private Date startDate;
    private Date endDate;
    private long userId;
    private List<Long> equipmentsId;
}