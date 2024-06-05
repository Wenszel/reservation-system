package com.example.reservation.service;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.model.Equipment;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.model.User;
import com.example.reservation.repository.EquipmentRepository;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.RoomRepository;
import com.example.reservation.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository,
                              UserRepository userRepository, EquipmentRepository equipmentRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
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
    public Reservation createReservation(ReservationRequest reservationRequest) {
        Room room = roomRepository.findByIdLocked(reservationRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<Equipment> equipments = new HashSet<>();
        for (Long equipmentId : reservationRequest.getEquipmentsId()) {
            Equipment equipment = equipmentRepository.findById(equipmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
            equipments.add(equipment);
        }

        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .equipments(equipments)
                .build();

        double price = room.getPriceByDay() * reservation.getDays(reservationRequest.getStartDate(), reservationRequest.getEndDate());
        reservation.setPrice(price);

        return reservation;
    }

    @Transactional
    public void deleteReservation(long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("Reservation not found");
        }
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public void updateReservation(long reservationId, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<Equipment> equipments = new HashSet<>();
        for (Long equipmentId : reservationRequest.getEquipmentsId()) {
            Equipment equipment = equipmentRepository.findById(equipmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
            equipments.add(equipment);
        }

        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setEquipments(equipments);

        double price = room.getPriceByDay() * reservation.getDays(reservationRequest.getStartDate(), reservationRequest.getEndDate());
        reservation.setPrice(price);
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

    public Optional<Reservation> getReservationById(long reservationId) {
        return reservationRepository.findById(reservationId);
    }

//    @Transactional(readOnly = true)
//    public List<Reservation> getAllReservations() {
//        return reservationRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Reservation> getReservationsByRoom(Long roomId) {
//        return reservationRepository.findById(roomId);
//    }


}
