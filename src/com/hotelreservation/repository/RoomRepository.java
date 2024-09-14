package com.hotelreservation.repository;

import com.hotelreservation.config.DatabaseConfig;
import com.hotelreservation.model.Room;
import com.hotelreservation.model.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    // Fetch room details by roomId
    public Room getRoomById(int roomId) {
        String query = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("room_id");
                    RoomType roomType = RoomType.valueOf(resultSet.getString("room_type")); // Assuming RoomType is an enum
                    double price = resultSet.getDouble("price");
                    boolean isAvailable = resultSet.getBoolean("is_available");
                    return new Room(id, roomType, price);
                } else {
                    System.out.println("No room found with ID: " + roomId);
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    // Get a list of all rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int roomId = resultSet.getInt("room_id");
                String roomTypeStr = resultSet.getString("room_type"); // Assuming RoomType is stored as a string in DB
                boolean isAvailable = resultSet.getBoolean("is_available");
                double price = resultSet.getDouble("price");

                RoomType roomType = RoomType.valueOf(roomTypeStr); // Convert String to RoomType enum

                Room room = new Room(roomId, roomType, price);
                room.setAvailable(isAvailable);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
