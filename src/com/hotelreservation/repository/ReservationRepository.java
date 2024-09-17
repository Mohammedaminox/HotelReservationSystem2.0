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

    // Add a new reservation
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

    // Fetch all reservations for a given client
    public List<Reservation> getReservationsByClientCin(String clientCin) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.* FROM reservations r " +
                "JOIN clients c ON r.client_id = c.client_id " +
                "WHERE c.cin = ? AND r.is_deleted = false";  // Fetch only non-deleted reservations

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clientCin);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int roomId = resultSet.getInt("room_id");
                LocalDate checkInDate = resultSet.getDate("check_in_date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("check_out_date").toLocalDate();

                Room room = new RoomRepository().getRoomById(roomId);  // Fetch room details
                Client client = new ClientRepository().getClientByCin(clientCin);  // Fetch client details

                reservations.add(new Reservation(reservationId, client, room, checkInDate, checkOutDate));
            }
        }

        return reservations;
    }

    // Update the reservation
    public void updateReservation(Reservation reservation) throws SQLException {
        String query = "UPDATE reservations SET check_in_date = ?, check_out_date = ? " +
                "WHERE reservation_id = ? AND is_deleted = false";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlCheckInDate = java.sql.Date.valueOf(reservation.getCheckInDate());
            java.sql.Date sqlCheckOutDate = java.sql.Date.valueOf(reservation.getCheckOutDate());

            // Set the parameters for the query
            preparedStatement.setDate(1, sqlCheckInDate);
            preparedStatement.setDate(2, sqlCheckOutDate);
            preparedStatement.setInt(3, reservation.getReservationId());

            preparedStatement.executeUpdate();
        }
    }

    // Delete the reservation
    public void deleteReservationById(int reservationId) throws SQLException {
        String query = "UPDATE reservations SET is_deleted = true WHERE reservation_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
        }
    }






}
