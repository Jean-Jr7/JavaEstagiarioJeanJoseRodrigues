package com.example.Hotel.controller;

import com.example.Hotel.Exception.RoomAlreadyBookedException;
import com.example.Hotel.model.Reservation;
import com.example.Hotel.model.ReservationStatus;
import com.example.Hotel.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
        try {
            Reservation createdReservation = reservationService.addReservation(reservation);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (RoomAlreadyBookedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro interno do servidor. Tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id, @RequestBody ReservationStatus status) {
        Reservation updatedReservation = reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Reservation>> getReservationsByDateRange(@RequestParam Timestamp start, @RequestParam Timestamp end) {
        List<Reservation> reservations = reservationService.getReservationsByDateRange(start, end);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/occupied-rooms")
    public ResponseEntity<List<Reservation>> getCurrentOccupiedRooms() {
        List<Reservation> occupiedRooms = reservationService.getCurrentOccupiedRooms();
        return ResponseEntity.ok(occupiedRooms);
    }
}
