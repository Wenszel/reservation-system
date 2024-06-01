package com.example.reservation.service;

import com.example.reservation.model.Reservation;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public void addReservation(Reservation reservation) {
        if (!roomRepository.isRoomAvailable(
                reservation.getRoom().getId(),
                reservation.getStartDate(),
                reservation.getEndDate())) {
            throw new IllegalArgumentException("Room is not available");
        }
        reservationRepository.save(reservation);
    }
}
