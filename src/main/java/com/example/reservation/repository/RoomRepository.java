package com.example.reservation.repository;

import com.example.reservation.model.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN false ELSE true END " +
            "FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.startDate <= :endDate " +
            "AND r.endDate >= :startDate")
    boolean isRoomAvailable(@Param("roomId") Long roomId,
                            @Param("startDate") Date startDate,
                            @Param("endDate") Date endDate);

    // This method uses JOIN FETCH to handle the N+1 requests problem
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.reservations WHERE r.id = :id")
    Room findRoomWithReservationsById(Long id);
    @Query("SELECT r FROM Room r WHERE r.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Room> findByIdLocked(Long id);
}
