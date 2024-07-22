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
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(RoomRepository roomRepo, CustomerRepository customerRepo, ReservationRepository reservationRepo) {
        return args -> {

            Room room = new Room();
            room.setRoomNumber(505);
            room.setType("Executive");
            room.setPrice(450.00);
            room = roomRepo.save(room);

            Customer customer = new Customer();
            customer.setName("Michael Brown");
            customer.setEmail("michael.brown@example.com");
            customer.setPhone("5559876");
            customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            customer = customerRepo.save(customer);

            Reservation reservation = new Reservation();
            reservation.setCustomer(customer);
            reservation.setRoom(room);
            reservation.setCheckin(new Timestamp(System.currentTimeMillis()));
            reservation.setCheckout(new Timestamp(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L));
            reservation.setStatus(ReservationStatus.SCHEDULED);
            reservationRepo.save(reservation);

            System.out.println("Dados carregados com sucesso!");
        };
    }
}
