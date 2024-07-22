package com.example.Hotel.repository;

import com.example.Hotel.model.Reservation;
import com.example.Hotel.model.ReservationStatus;
import com.example.Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomAndStatusNot(Room room, ReservationStatus status);
    List<Reservation> findAllByCheckinBetween(Timestamp start, Timestamp end);
    List<Reservation> findAllByStatus(ReservationStatus status);
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
