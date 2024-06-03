package com.example.reservation.controller;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.model.Reservation;
import com.example.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<String> addReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation reservation = reservationService.createReservation(reservationRequest);
            reservationService.addReservation(reservation);
            return new ResponseEntity<>("Reservation added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to add reservation", e);
            return new ResponseEntity<>("Failed to add reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // oprocz /add jeszcze /delete i /get i /update
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable long id) {
        try {
            reservationService.deleteReservation(id);
            return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to delete reservation", e);
            return new ResponseEntity<>("Failed to delete reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable long id) {
        try {
            Optional<Reservation> reservation = reservationService.getReservationById(id);
            if (reservation.isPresent()) {
                return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Failed to get reservation", e);
            return new ResponseEntity<>("Failed to get reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable long id, @RequestBody ReservationRequest reservationRequest) {
        try {
            reservationService.updateReservation(id, reservationRequest);
            return new ResponseEntity<>("Reservation updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update reservation", e);
            return new ResponseEntity<>("Failed to update reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
