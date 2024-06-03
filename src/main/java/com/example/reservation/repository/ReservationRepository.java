package com.example.reservation.repository;

import com.example.reservation.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> { // JpaRepository extends PagingAndSortingRepository
    List<Reservation> findAllByUserId(Long userId);
    Page<Reservation> findAllByUserId(Long userId, Pageable pageable);
    Set<Reservation> findByStartDateBetween(Date startDate, Date endDate);
}
