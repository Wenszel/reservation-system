package com.example.reservation;

import com.example.reservation.model.*;
import com.example.reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DataAdder implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) throws Exception {

        // 2 userow, 4 kategorie, 5 equipment, 10 pokoi (skrypt), pare rezerwacji
        User user1 = new User();
        //user1.setId(0);
        user1.setFirstName("John");
        user1.setLastName("Smith");
        user1.setEmail("john.smith@gmail.com");
        user1.setPassword("password");
        userRepository.save(user1);

        User user2 = new User();
        //user2.setId(1);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@gmail.com");
        user2.setPassword("12345");

        userRepository.save(user2);

        //userRepository.saveAll(Arrays.asList(user1, user2));

        // Equipments
        Equipment equipment1 = new Equipment();
        equipment1.setId(0);
        equipment1.setName("Projector");

        Equipment equipment2 = new Equipment();
        equipment2.setId(1);
        equipment2.setName("Whiteboard");

        Equipment equipment3 = new Equipment();
        equipment3.setId(2);
        equipment3.setName("Sound System");

        Equipment equipment4 = new Equipment();
        equipment4.setId(3);
        equipment4.setName("Microphone");

        Equipment equipment5 = new Equipment();
        equipment5.setId(4);
        equipment5.setName("Laptop");

        equipmentRepository.saveAll(Arrays.asList(equipment1, equipment2, equipment3, equipment4, equipment5));



        // Categories
        Category category1 = new Category();
        category1.setId(0);
        category1.setName("Conference Room");

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Meeting Room");

        Category category3 = new Category();
        category3.setId(2);
        category3.setName("Workshop Room");

        Category category4 = new Category();
        category4.setId(3);
        category4.setName("Training Room");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4));

        // reservations
        Reservation reservation1 = new Reservation();
        reservation1.setId(0);
        Date datestart1 = Date.valueOf("2024-06-02");
        Date dateend1 = Date.valueOf("2024-06-06");
        reservation1.setStartDate(datestart1);
        reservation1.setEndDate(dateend1);
        reservation1.setUser(user1);

        // reservations
        Reservation reservation2 = new Reservation();
        reservation2.setId(1);
        Date datestart2 = Date.valueOf("2024-05-12");
        Date dateend2 = Date.valueOf("2024-05-13");
        reservation2.setStartDate(datestart2);
        reservation2.setEndDate(dateend2);
        reservation2.setUser(user2);


        // Rooms
        for (int i = 1; i <= 10; i++) {
            Room room = new Room();
            room.setId(i-1);
            room.setName("Room " + i);
            room.setCapacity(100 + i * 10);
            room.setFloor(i % 3 + 1);
            room.setSquareMeters(50 + i * 5);
            int price = 100 + i * 20;
            room.setPriceByDay(price);

            switch(i-1 % 5){
                case 0: {
                    room.addEquipment(equipment1);
                    room.addEquipment(equipment5);
                    break;
                }
                case 1: {
                    room.addEquipment(equipment2);
                    room.addEquipment(equipment5);
                    break;
                }
                case 2: {
                    room.addEquipment(equipment3);
                    room.addEquipment(equipment4);
                    break;
                }
                case 3: {
                    room.addEquipment(equipment4);
                    room.addEquipment(equipment3);
                    room.addEquipment(equipment5);
                    break;
                }
                case 4: {
                    room.addEquipment(equipment5);
                    room.addEquipment(equipment1);
                    room.addEquipment(equipment2);
                    room.addEquipment(equipment3);
                    break;
                }
            }

            switch(i-1 % 4){
                case 0: {
                    room.addCategory(category1);
                    room.addCategory(category2);
                    break;
                }
                case 1: {
                    room.addCategory(category3);
                    break;
                }
                case 2: {
                    room.addCategory(category3);
                    room.addCategory(category4);
                    break;
                }
                case 3: {
                    room.addCategory(category4);
                    room.addCategory(category1);
                    room.addCategory(category2);
                    break;
                }
            }

            //room.getEquipments().addAll(Arrays.asList(equipment1, equipment2, equipment3, equipment4, equipment5));
            //room.getCategories().addAll(Arrays.asList(category1, category2, category3, category4));

            roomRepository.save(room);
            if(i == 1){
                reservation1.setRoom(room);
                reservation1.addEquipment(equipment1);
                reservation1.addEquipment(equipment5);
                long days1 = reservation1.getDays(datestart1, dateend1);
                reservation1.setPrice(days1 * price);
                reservationRepository.save(reservation1);
            }
            if (i == 5){
                reservation2.setRoom(room);
                reservation2.addEquipment(equipment2);
                reservation2.addEquipment(equipment5);
                reservation2.addEquipment(equipment3);
                reservation2.addEquipment(equipment1);
                long days2 = reservation2.getDays(datestart2, dateend2);
                reservation2.setPrice(days2 * price);
                reservationRepository.save(reservation2);
            }

        }





//        equipment1.setId(0);
//        equipment1.setName("Projector");
//        //equipment1.addReservation(new Reservation());
//        equipment1.addRoom(room1);

//        Category category1 = new Category();
//        category1.setId(0);
//        category1.setName("Conference Venue");
//        category1.addRoom(room1);

//        room1.setId(0);
//        room1.setName("Room 1");
//        room1.addEquipment(equipment1);
//        room1.setCapacity(100);
//        room1.addCategory(category1);
//        //room1.addReservation(new Reservation());
//        room1.setFloor(1);
//        room1.setSquareMeters(65);
//        room1.setPriceByDay(200);
    }
}
