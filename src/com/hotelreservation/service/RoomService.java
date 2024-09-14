package com.hotelreservation.service;

import com.hotelreservation.model.Room;
import com.hotelreservation.repository.RoomRepository;

import java.util.List;

public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Get room by roomId
    public Room getRoomById(int roomId) {
        Room room = roomRepository.getRoomById(roomId);
        if (room == null) {
            System.out.println("No room found with ID: " + roomId);
        } else {
            System.out.println("Room fetched: " + room.getRoomId() + ", Available: " + room.isAvailable());
        }
        return room;
    }
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.getAllRooms();
        return rooms;
    }

}
