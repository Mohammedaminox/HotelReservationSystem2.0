package com.hotelreservation.repository;

import com.hotelreservation.config.DatabaseConfig;
import com.hotelreservation.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository {

    // Add a new client to the database
    public int addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (client_name, cin) VALUES (?,   ?) RETURNING client_id";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getCin());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("client_id");
            }
        }
        return -1; // Return -1 or handle as needed
    }


    public Client getClientByCin(String cin) throws SQLException {
        String query = "SELECT * FROM clients WHERE cin = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Client(resultSet.getInt("client_id"), resultSet.getString("client_name"), resultSet.getString("cin"));
            }
        }
        return null; // Return null or handle as needed
    }
}