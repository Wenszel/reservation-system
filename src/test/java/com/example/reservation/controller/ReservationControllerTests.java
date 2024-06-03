package com.example.reservation.controller;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.model.Reservation;
import com.example.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationControllerTests {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRequest_whenAddReservation_thenReturnCreatedStatus() {
        ReservationRequest request = new ReservationRequest();
        request.setRoomId(1L);
        request.setStartDate(Date.valueOf(LocalDate.of(2024, 6, 11)));
        request.setEndDate(Date.valueOf(LocalDate.of(2024, 6, 15)));

        ResponseEntity<String> response = reservationController.addReservation(request);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationService, times(1)).addReservation(reservationCaptor.capture());

        Reservation capturedReservation = reservationCaptor.getValue();
        assertEquals(request.getRoomId(), capturedReservation.getId());
        assertEquals(request.getStartDate(), capturedReservation.getStartDate());
        assertEquals(request.getEndDate(), capturedReservation.getEndDate());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Reservation added successfully", response.getBody());
    }

    @Test
    void givenInvalidRequest_whenAddReservation_thenReturnInternalServerError() {
        ReservationRequest request = new ReservationRequest();
        request.setRoomId(1L);
        request.setStartDate(Date.valueOf(LocalDate.of(2024, 6, 11)));
        request.setEndDate(Date.valueOf(LocalDate.of(2024, 6, 15)));

        doThrow(new RuntimeException("Service error")).when(reservationService).addReservation(any(Reservation.class));

        ResponseEntity<String> response = reservationController.addReservation(request);

        verify(reservationService, times(1)).addReservation(any(Reservation.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add reservation: Service error", response.getBody());
    }
}
