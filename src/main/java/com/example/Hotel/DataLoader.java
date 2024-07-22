package com.example.Hotel;

import com.example.Hotel.model.Customer;
import com.example.Hotel.model.Room;
import com.example.Hotel.model.Reservation;
import com.example.Hotel.repository.CustomerRepository;
import com.example.Hotel.repository.RoomRepository;
import com.example.Hotel.repository.ReservationRepository;
import com.example.Hotel.model.ReservationStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DataLoader extends Customer {

    @Bean
    public CommandLineRunner loadData(RoomRepository roomRepo, CustomerRepository customerRepo, ReservationRepository reservationRepo) {
        return args -> {

            /*Room room = new Room();
            room.setRoomNumber(404);
            room.setType("Standard");
            room.setPrice(200.00);
            room = roomRepo.save(room);

            Customer customer = new Customer();
            customer.setName("Alice Johnson");
            customer.setEmail("alice.johnson@example.com");
            customer.setPhone("5554321");
            customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            customer = customerRepo.save(customer);

            Reservation reservation = new Reservation();
            reservation.setCustomer(customer);
            reservation.setRoom(room);
            reservation.setCheckin(new Timestamp(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L)); // 3 dias a partir de agora
            reservation.setCheckout(new Timestamp(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L)); // 10 dias a partir de agora
            reservation.setStatus(ReservationStatus.SCHEDULED);
            reservationRepo.save(reservation);

            */

            System.out.println("Dados carregados com sucesso!");
        };
    }
}
