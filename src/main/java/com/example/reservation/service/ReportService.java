package com.example.reservation.service;

import com.example.reservation.dto.ReportResponse;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final RoomRepository roomRepository;

    public ReportService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public ReportResponse getOneRoomReport(long roomId) {
        Room room = roomRepository.findRoomWithReservationsById(roomId);
        double total = room.getReservations().stream()
                .map(Reservation::getPrice)
                .reduce(0.0, Double::sum);
        return new ReportResponse(room.getName(), room.getReservations(), total);
    }
}
