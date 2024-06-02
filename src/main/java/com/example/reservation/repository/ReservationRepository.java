package com.example.reservation.repository;

import com.example.reservation.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> { // JpaRepository extends PagingAndSortingRepository
    List<Reservation> findAllByUserId(Long userId);
    Page<Reservation> findAllByUserId(Long userId, Pageable pageable);
}
