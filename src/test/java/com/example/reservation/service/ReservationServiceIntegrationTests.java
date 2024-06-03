package com.example.reservation.service;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ReservationService.class)
public class ReservationServiceIntegrationTests {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TestEntityManager testEntityManager;

    private User user;
    private List<Reservation> firstPageReservations;
    private List<Reservation> secondPageReservations;

    @BeforeEach
    public void setUp() {
        user = new User();
        testEntityManager.persistAndFlush(user);
        firstPageReservations = new LinkedList<>();
        secondPageReservations = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            Reservation reservation = new Reservation();
            reservation.setId(i);
            reservation.setUser(user);
            testEntityManager.persist(reservation);
            if(i < 10) firstPageReservations.add(reservation);
            if (i >= 10) secondPageReservations.add(reservation);
        }
        testEntityManager.flush();
    }

    @Test
    @Transactional
    public void givenTwentyReservations_whenGetUsersReservationsWithTenElementsPage_thenReturnsTenElements() {
        // when
        Page<Reservation> reservations = reservationService.getUserReservations(user.getId(), 0, 10);

        // then
        Assertions.assertEquals(10, reservations.getContent().size());
        assertThat(reservations.getContent()).containsAll(firstPageReservations);
    }
    @Test
    @Transactional
    public void givenTwentyReservations_whenGetUsersReservationsWithSecondPage_thenReturnsSecondPageElements() {
        // when
        Page<Reservation> reservations = reservationService.getUserReservations(user.getId(), 1, 10);

        // then
        Assertions.assertEquals(10, reservations.getContent().size());
        assertThat(reservations.getContent()).containsAll(secondPageReservations);
    }
}