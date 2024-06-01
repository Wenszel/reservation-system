package com.example.reservation.repository;

import com.example.reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

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
}
