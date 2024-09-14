package com.hotelreservation.setup;

import com.hotelreservation.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomSetup {

    // Method to add test rooms to the database
    public static void addTestRooms() {
        String query = "INSERT INTO rooms (room_id, room_type, price, is_available) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Room 1
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "SINGLE");
            preparedStatement.setDouble(3, 100.0);
            preparedStatement.setBoolean(4, true);
            preparedStatement.executeUpdate();

            // Room 2
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "DOUBLE");
            preparedStatement.setDouble(3, 150.0);
            preparedStatement.setBoolean(4, true);
            preparedStatement.executeUpdate();

            // Room 3
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "SUITE");
            preparedStatement.setDouble(3, 250.0);
            preparedStatement.setBoolean(4, true);
            preparedStatement.executeUpdate();

            System.out.println("Test rooms added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addTestRooms(); // Call the method to add test rooms
    }
}
