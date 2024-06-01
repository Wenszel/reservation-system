package com.example.reservation.service;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class ReservationServiceTests {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Room room;
    private Reservation reservation;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setStartDate(Date.valueOf(LocalDate.of(2024, 6, 11)));
        reservation.setEndDate(Date.valueOf(LocalDate.of(2024, 6, 15)));
    }

    @Test
    void givenRoomIsAvailable_whenAddReservation_thenReservationIsSaved() {
        when(roomRepository.isRoomAvailable(room.getId(), reservation.getStartDate(), reservation.getEndDate())).thenReturn(true);

        reservationService.addReservation(reservation);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository, times(1)).save(reservationCaptor.capture());
        Assertions.assertEquals(reservation, reservationCaptor.getValue());
    }

    @Test
    void givenRoomIsNotAvailable_whenAddReservation_thenThrowsException() {
        when(roomRepository.isRoomAvailable(room.getId(), reservation.getStartDate(), reservation.getEndDate())).thenReturn(false);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(reservation);
        });
        Assertions.assertEquals("Room is not available", exception.getMessage());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
