package com.example.Hotel.services;

import com.example.Hotel.Exception.RoomAlreadyBookedException;
import com.example.Hotel.Exception.ReservationNotFoundException;
import com.example.Hotel.model.Reservation;
import com.example.Hotel.model.ReservationStatus;
import com.example.Hotel.model.Room;
import com.example.Hotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public Reservation addReservation(Reservation reservation) {
        Room room = reservation.getRoom();
        Timestamp checkin = reservation.getCheckin();
        Timestamp checkout = reservation.getCheckout();


        List<Reservation> conflictingReservations = reservationRepository.findByRoomAndStatusNot(room, ReservationStatus.CANCELED);
        for (Reservation existingReservation : conflictingReservations) {
            if (checkin.before(existingReservation.getCheckout()) && checkout.after(existingReservation.getCheckin())) {
                throw new RoomAlreadyBookedException("O quarto já está reservado para o período solicitado.");
            }
        }

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservationStatus(Long id, ReservationStatus status) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus(status);
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    public List<Reservation> getReservationsByDateRange(Timestamp start, Timestamp end) {
        return reservationRepository.findAllByCheckinBetween(start, end);
    }

    public List<Reservation> getCurrentOccupiedRooms() {
        return reservationRepository.findAllByStatus(ReservationStatus.IN_USE);
    }
}
