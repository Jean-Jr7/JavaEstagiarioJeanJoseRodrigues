package com.example.Hotel.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCustomer", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idRoom", nullable = false)
    private Room room;

    @Column(name = "checkin", nullable = false)
    private Timestamp checkin;

    @Column(name = "checkout", nullable = false)
    private Timestamp checkout;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;


}
