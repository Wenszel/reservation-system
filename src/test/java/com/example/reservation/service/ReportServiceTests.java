package com.example.reservation.service;

import com.example.reservation.dto.EquipmentReportResponse;
import com.example.reservation.dto.ReportResponse;
import com.example.reservation.model.Category;
import com.example.reservation.model.Equipment;
import com.example.reservation.model.Reservation;
import com.example.reservation.model.Room;
import com.example.reservation.repository.EquipmentRepository;
import com.example.reservation.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

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

    @Test
    void testingEquipmentReport(){
        List<Room> rooms = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            Room room = new Room();
            room.setId(i);
            room.setName("Room " + i);
            rooms.add(room);
        }

        Equipment equipment = new Equipment();
        equipment.setId(0L);
        equipment.setName("Equipment 0");

        List<Category> categories = new LinkedList<>();
        for (int i = 0; i < 3; i++ ) {
            Category category = new Category();
            category.setId(i);
            category.setName("Category " + i);
            categories.add(category);
        }

        List<Reservation> reservations = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            Reservation reservation = new Reservation();
            reservation.setId(i);
            reservations.add(reservation);
        }
        reservations.get(0).addEquipment(equipment);

        equipment.addRoom(rooms.get(0));
        equipment.addRoom(rooms.get(1));

        rooms.get(0).addCategory(categories.get(0));
        rooms.get(0).addCategory(categories.get(1));
        rooms.get(1).addCategory(categories.get(1));
        rooms.get(1).addCategory(categories.get(2));

        for (int i = 0; i < 2; i++) {
            rooms.get(0).addReservation(reservations.get(i));
            rooms.get(0).addReservation(reservations.get(i + 4));
        }
        for (int i = 2; i < 4; i++) {
            rooms.get(1).addReservation(reservations.get(i));
        }

        when(roomRepository.findAll()).thenReturn(rooms);
        when(equipmentRepository.findEquipmentsWithReservationsAndCategoriesAndRooms()).thenReturn(List.of(equipment));

        reportService.getEquipmentReport().getEquipmentReports().forEach(System.out::println);

        verify(roomRepository, times(1)).findAll();
        verify(equipmentRepository, times(1)).findEquipmentsWithReservationsAndCategoriesAndRooms();

        EquipmentReportResponse correctResponse = new EquipmentReportResponse();
        EquipmentReportResponse.EquipmentReport equipmentReport = new EquipmentReportResponse.EquipmentReport();
        EquipmentReportResponse.EquipmentReport.UsageStats categoryUsageStats1 = new EquipmentReportResponse.EquipmentReport.UsageStats();
        categoryUsageStats1.setPropertyName("Category 0");
        categoryUsageStats1.setNumberOfUses(1);
        categoryUsageStats1.setNumberOfAllPossibleUses(4);
        equipmentReport.addCategoryUsageStats(categoryUsageStats1);
        EquipmentReportResponse.EquipmentReport.UsageStats categoryUsageStats2 = new EquipmentReportResponse.EquipmentReport.UsageStats();
        categoryUsageStats2.setPropertyName("Category 1");
        categoryUsageStats2.setNumberOfUses(1);
        categoryUsageStats2.setNumberOfAllPossibleUses(6);
        equipmentReport.addCategoryUsageStats(categoryUsageStats2);
        EquipmentReportResponse.EquipmentReport.UsageStats categoryUsageStats3 = new EquipmentReportResponse.EquipmentReport.UsageStats();
        categoryUsageStats3.setPropertyName("Category 2");
        categoryUsageStats3.setNumberOfUses(0);
        categoryUsageStats3.setNumberOfAllPossibleUses(2);
        equipmentReport.addCategoryUsageStats(categoryUsageStats3);
        EquipmentReportResponse.EquipmentReport.UsageStats roomUsageStats1 = new EquipmentReportResponse.EquipmentReport.UsageStats();
        roomUsageStats1.setPropertyName("Room 0");
        roomUsageStats1.setNumberOfUses(1);
        roomUsageStats1.setNumberOfAllPossibleUses(4);
        equipmentReport.addRoomUsageStats(roomUsageStats1);
        EquipmentReportResponse.EquipmentReport.UsageStats roomUsageStats2 = new EquipmentReportResponse.EquipmentReport.UsageStats();
        roomUsageStats2.setPropertyName("Room 1");
        roomUsageStats2.setNumberOfUses(0);
        roomUsageStats2.setNumberOfAllPossibleUses(2);
        equipmentReport.addRoomUsageStats(roomUsageStats2);
        correctResponse.addEquipmentReport(equipmentReport);

        equipmentReport.setEquipmentName("Equipment 0");

        EquipmentReportResponse response = reportService.getEquipmentReport();


    }
}