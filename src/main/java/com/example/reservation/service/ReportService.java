package com.example.reservation.service;

import com.example.reservation.dto.EquipmentReportResponse;
import com.example.reservation.dto.ReportResponse;
import com.example.reservation.dto.ReservationResponse;
import com.example.reservation.model.Category;
import com.example.reservation.model.Equipment;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.repository.EquipmentRepository;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final EquipmentRepository equipmentRepository;

    public ReportService(RoomRepository roomRepository,
                         ReservationRepository reservationRepository,
                         EquipmentRepository equipmentRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional(readOnly = true)
    public ReportResponse getOneRoomReport(long roomId) {
        Room room = roomRepository.findRoomWithReservationsById(roomId);
        double total = room.getReservations().stream()
                .map(Reservation::getPrice)
                .reduce(0.0, Double::sum);
        return new ReportResponse(room.getName(),
                room.getReservations().stream().map(ReservationResponse::getReservationResponse).collect(Collectors.toSet()),
                total);
    }

    @Transactional(readOnly = true)
    public ReportResponse getReportBetweenDates(Date startDate, Date endDate) {
        Set<Reservation> reservations = reservationRepository.findByStartDateBetween(startDate, endDate);
        double total = reservations.stream()
                .map(Reservation::getPrice)
                .reduce(0.0, Double::sum);
        return new ReportResponse("All rooms",
                reservations.stream().map(ReservationResponse::getReservationResponse).collect(Collectors.toSet()),
                total);
    }

    @Transactional(readOnly = true)
    public EquipmentReportResponse getEquipmentReport() {
        EquipmentReportResponse response = new EquipmentReportResponse();
        List<Room> rooms = roomRepository.findAll();
        List<Equipment> equipments = equipmentRepository.findEquipmentsWithReservationsAndCategoriesAndRooms();

        equipments.forEach(equipment -> {
            EquipmentReportResponse.EquipmentReport equipmentReport = new EquipmentReportResponse.EquipmentReport();
            equipmentReport.setEquipmentName(equipment.getName());

            Map<Room, Long> numberOfEquipmentReservationsPerRoom = getNumberOfEquipmentReservationsPerRoom(equipment);

            rooms.stream()
                    .map(room -> getRoomStats(room, numberOfEquipmentReservationsPerRoom.getOrDefault(room, 0L)))
                    .forEach(equipmentReport::addRoomUsageStats);

            Map<Category, Long>  categoriesUsages = getNumberOfEquipmentReservationsPerCategory(equipment);
            List<Category> categories = equipment.getRooms().stream().map(Room::getCategories).flatMap(Set::stream).toList();
            categories.forEach(category -> {
                if (!categoriesUsages.containsKey(category)) {
                    categoriesUsages.put(category, 0L);
                }
            });

            categoriesUsages.entrySet().stream()
                    .map(entry -> getCategoryStats(entry.getKey(), entry.getValue(), equipment))
                    .forEach(equipmentReport::addCategoryUsageStats);

            response.addEquipmentReport(equipmentReport);
        });
        return response;
    }

    public EquipmentReportResponse.EquipmentReport.UsageStats getCategoryStats(Category category, Long count, Equipment equipment) {
        EquipmentReportResponse.EquipmentReport.UsageStats usageStats = new EquipmentReportResponse.EquipmentReport.UsageStats();
        usageStats.setPropertyName(category.getName());
        usageStats.setNumberOfUses(count);

        usageStats.setNumberOfAllPossibleUses(
                category
                        .getRooms().stream()
                        .map(Room::getReservations)
                        .flatMap(Set::stream)
                        .count());
        return usageStats;
    }

    private Map<Category, Long> getNumberOfEquipmentReservationsPerCategory(Equipment equipment) {
        return equipment.getReservations().stream()
                .map(Reservation::getRoom)
                .filter(Objects::nonNull)
                .map(Room::getCategories)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(category -> category, Collectors.counting()));
    }

    private EquipmentReportResponse.EquipmentReport.UsageStats getRoomStats(Room room, Long numberOfEquipmentReservationsForThisRoom) {
        EquipmentReportResponse.EquipmentReport.UsageStats usageStats = new EquipmentReportResponse.EquipmentReport.UsageStats();
        usageStats.setPropertyName(room.getName());
        usageStats.setNumberOfUses(numberOfEquipmentReservationsForThisRoom);
        usageStats.setNumberOfAllPossibleUses(room.getReservations().size());
        return usageStats;
    }

    private Map<Room, Long> getNumberOfEquipmentReservationsPerRoom(Equipment equipment) {
        return equipment.getReservations().stream()
                .map(Reservation::getRoom)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(room -> room, Collectors.counting()));
    }
}