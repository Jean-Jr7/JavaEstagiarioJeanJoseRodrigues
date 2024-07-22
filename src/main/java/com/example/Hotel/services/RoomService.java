package com.example.Hotel.services;

import com.example.Hotel.Exception.RoomNotFoundException;
import com.example.Hotel.model.Room;
import com.example.Hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setRoomNumber(updatedRoom.getRoomNumber());
                    room.setType(updatedRoom.getType());
                    room.setPrice(updatedRoom.getPrice());
                    return roomRepository.save(room);
                })
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
