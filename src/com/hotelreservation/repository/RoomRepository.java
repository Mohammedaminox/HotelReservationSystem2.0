package com.hotelreservation.repository;

import com.hotelreservation.model.Room;
import com.hotelreservation.model.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private ArrayList<Room> rooms = new ArrayList<>();
//    public RoomRepository() {
//        //Adding Rooms
//        rooms.add(new Room(1, RoomType.DOUBLE, 100));
//        rooms.add(new Room(2, RoomType.SINGLE, 200));
//        rooms.add(new Room(3, RoomType.SUITE, 250));
//    }

    //Add a new room
    public void addRoom(Room room){
        rooms.add(room);
    }

    //get all rooms
    public List<Room> getAllRooms() {
        return rooms;
    }

    //find room by roomID
    public Room getRoomById(int roomId){
        return rooms.stream()
                .filter(room -> room.getRoomId() == roomId)
                .findFirst()
                .orElse(null);
    }
}
