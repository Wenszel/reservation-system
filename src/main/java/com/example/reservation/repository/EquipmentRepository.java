package com.example.reservation.repository;

import com.example.reservation.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e LEFT JOIN FETCH e.reservations as r LEFT JOIN FETCH r.room as room LEFT JOIN FETCH room.categories")
    List<Equipment> findEquipmentsWithReservationsAndCategoriesAndRooms();
}
