package com.example.reservation.service;

import com.example.reservation.dto.ReportResponse;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenRoomWithTwoReservations_whenGetOnRoomReport_thenReturnsCorrectReport() {
        Room room = new Room();
        room.setName("Room A");

        Reservation reservation1 = new Reservation();
        reservation1.setPrice(100.0);

        Reservation reservation2 = new Reservation();
        reservation2.setPrice(150.0);

        room.setReservations(Set.of(reservation1, reservation2));

        when(roomRepository.findRoomWithReservationsById(1L)).thenReturn(room);

        ReportResponse report = reportService.getOneRoomReport(1L);

        assertEquals("Room A", report.getName());
        assertEquals(2, report.getReservations().size());
        assertEquals(250.0, report.getTotalPrice());

        verify(roomRepository, times(1)).findRoomWithReservationsById(1L);
    }
}