package com.example.reservation.repository;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.time.LocalDate;

@DataJpaTest
public class RoomRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        entityManager.persist(room);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setStartDate(Date.valueOf(LocalDate.of(2024, 6, 1)));
        reservation.setEndDate(Date.valueOf(LocalDate.of(2024, 6, 10)));
        entityManager.persist(reservation);
    }

    @Test
    void givenNoOverlappingReservation_whenCheckingAvailability_thenRoomIsAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 11)),
                Date.valueOf(LocalDate.of(2024, 6, 15))
        );
        Assertions.assertTrue(isAvailable);
    }

    @Test
    void givenOverlappingReservation_whenCheckingAvailability_thenRoomIsNotAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 5)),
                Date.valueOf(LocalDate.of(2024, 6, 15))
        );
        Assertions.assertFalse(isAvailable);
    }

    @Test
    void givenDatesBeforeReservation_whenCheckingAvailability_thenRoomIsAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 5, 25)),
                Date.valueOf(LocalDate.of(2024, 5, 30))
        );
        Assertions.assertTrue(isAvailable);
    }

    @Test
    void givenDatesAfterReservation_whenCheckingAvailability_thenRoomIsAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 11)),
                Date.valueOf(LocalDate.of(2024, 6, 20))
        );
        Assertions.assertTrue(isAvailable);
    }

    @Test
    void givenStartDateOverlap_whenCheckingAvailability_thenRoomIsNotAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 1)),
                Date.valueOf(LocalDate.of(2024, 6, 5))
        );
        Assertions.assertFalse(isAvailable);
    }

    @Test
    void givenEndDateOverlap_whenCheckingAvailability_thenRoomIsNotAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 5)),
                Date.valueOf(LocalDate.of(2024, 6, 10))
        );
        Assertions.assertFalse(isAvailable);
    }

    @Test
    void givenExactDateOverlap_whenCheckingAvailability_thenRoomIsNotAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 1)),
                Date.valueOf(LocalDate.of(2024, 6, 10))
        );
        Assertions.assertFalse(isAvailable);
    }

    @Test
    void givenInnerDateOverlap_whenCheckingAvailability_thenRoomIsNotAvailable() {
        boolean isAvailable = roomRepository.isRoomAvailable(
                room.getId(),
                Date.valueOf(LocalDate.of(2024, 6, 2)),
                Date.valueOf(LocalDate.of(2024, 6, 9))
        );
        Assertions.assertFalse(isAvailable);
    }
}