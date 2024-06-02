package com.example.reservation.service;

import com.example.reservation.model.Reservation;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void addReservation(Reservation reservation) {
        if (!roomRepository.isRoomAvailable(
                reservation.getRoom().getId(),
                reservation.getStartDate(),
                reservation.getEndDate())) {
            throw new IllegalArgumentException("Room is not available");
        }
        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<Reservation> getUserReservations(long userID, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reservationRepository.findAllByUserId(userID, pageable);
    }

    @Transactional
    public void updateReservation(Reservation reservation) {
        if (!roomRepository.isRoomAvailable(
                reservation.getRoom().getId(),
                reservation.getStartDate(),
                reservation.getEndDate())) {
            throw new IllegalArgumentException("Room is not available");
        }
        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findById(roomId);
    }
}
