package com.example.reservation.controller;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.model.Reservation;
import com.example.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PostMapping("/add")
    public ResponseEntity<String> addReservation(ReservationRequest reservationRequest) {
        try {
            Reservation reservation = getReservationFromRequest(reservationRequest);
            reservationService.addReservation(reservation);
            return new ResponseEntity<>("Reservation added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to add reservation", e);
            return new ResponseEntity<>("Failed to add reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Reservation getReservationFromRequest(ReservationRequest reservationRequest) {
        return Reservation.builder()
                .id(reservationRequest.getRoomId())
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .build();
    }
}
