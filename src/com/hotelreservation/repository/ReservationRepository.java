package com.hotelreservation.repository;

import com.hotelreservation.config.DatabaseConfig;
import com.hotelreservation.model.Reservation;
import com.hotelreservation.model.Room;
import com.hotelreservation.model.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    // Add a new reservation to the database
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservations (check_in_date, check_out_date, room_id, client_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlCheckInDate = java.sql.Date.valueOf(reservation.getCheckInDate());
            java.sql.Date sqlCheckOutDate = java.sql.Date.valueOf(reservation.getCheckOutDate());

            // Set the parameters for the query
            preparedStatement.setDate(1, sqlCheckInDate);
            preparedStatement.setDate(2, sqlCheckOutDate);
            preparedStatement.setInt(3, reservation.getRoom().getRoomId()); // Assuming room ID is an integer
            preparedStatement.setInt(4, reservation.getClient().getClientId()); // Assuming client ID is an integer

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a list of all reservations from the database
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Fetch the reservation data from the result set
                int reservationId = resultSet.getInt("reservation_id");
                Date checkInDate = resultSet.getDate("check_in_date");
                Date checkOutDate = resultSet.getDate("check_out_date");
                int roomId = resultSet.getInt("room_id");
                int clientId = resultSet.getInt("client_id");

                // Convert java.sql.Date to LocalDate
                LocalDate localCheckInDate = checkInDate.toLocalDate();
                LocalDate localCheckOutDate = checkOutDate.toLocalDate();

                // Create Room and Client objects based on IDs
                Room room = getRoomById(roomId);
                Client client = getClientById(clientId);

                // Create a new Reservation object
                Reservation reservation = new Reservation(reservationId, client, room, localCheckInDate, localCheckOutDate);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Find a reservation by its ID
    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservations WHERE reservation_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Date checkInDate = resultSet.getDate("check_in_date");
                Date checkOutDate = resultSet.getDate("check_out_date");
                int roomId = resultSet.getInt("room_id");
                int clientId = resultSet.getInt("client_id");

                // Convert java.sql.Date to LocalDate
                LocalDate localCheckInDate = checkInDate.toLocalDate();
                LocalDate localCheckOutDate = checkOutDate.toLocalDate();

                Room room = getRoomById(roomId);
                Client client = getClientById(clientId);

                reservation = new Reservation(reservationId, client, room, localCheckInDate, localCheckOutDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    // Delete a reservation by its ID
    public boolean deleteReservation(int reservationId) {
        String query = "DELETE FROM reservations WHERE reservation_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, reservationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing reservation
    public boolean updateReservation(int reservationId, Reservation updatedReservation) {
        String query = "UPDATE reservations SET check_in_date = ?, check_out_date = ?, room_id = ?, client_id = ? WHERE reservation_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlCheckInDate = java.sql.Date.valueOf(updatedReservation.getCheckInDate());
            java.sql.Date sqlCheckOutDate = java.sql.Date.valueOf(updatedReservation.getCheckOutDate());

            // Set the parameters for the update
            preparedStatement.setDate(1, sqlCheckInDate);
            preparedStatement.setDate(2, sqlCheckOutDate);
            preparedStatement.setInt(3, updatedReservation.getRoom().getRoomId());
            preparedStatement.setInt(4, updatedReservation.getClient().getClientId());
            preparedStatement.setInt(5, reservationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to fetch Room by ID (to be implemented)
    private Room getRoomById(int roomId) {
        // Implement logic to retrieve Room object from the database
        // This might involve another repository or a similar approach
        return null;
    }

    // Method to fetch Client by ID (to be implemented)
    private Client getClientById(int clientId) {
        // Implement logic to retrieve Client object from the database
        // This might involve another repository or a similar approach
        return null;
    }
}
