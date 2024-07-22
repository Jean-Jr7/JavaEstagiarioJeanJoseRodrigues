package com.example.Hotel.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table( name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 12, nullable = false)
    private String phone;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
