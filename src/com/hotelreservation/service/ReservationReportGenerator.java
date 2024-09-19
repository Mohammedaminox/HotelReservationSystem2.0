package com.hotelreservation.service;

import com.hotelreservation.model.Reservation;
import com.hotelreservation.model.Room;
import com.hotelreservation.repository.ReservationRepository;
import com.hotelreservation.repository.RoomRepository;

import java.sql.SQLException;
import java.util.List;

public class ReservationReportGenerator {

    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;

    public ReservationReportGenerator(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public void generateReport() {
        try {
            // Fetch all reservations and rooms
            List<Reservation> reservations = reservationRepository.getAllReservations();
            List<Room> rooms = roomRepository.getAllRooms();

            // Calculate statistics
            double occupancyRate = calculateOccupancyRate(reservations, rooms);
            double totalRevenue = calculateTotalRevenue(reservations);
            long cancelledReservations = countCancelledReservations(reservations);

            // Print the report
            System.out.println("Occupancy Rate: " + String.format("%.2f", occupancyRate * 100) + "%");
            System.out.println("Total Revenue: $" + totalRevenue);
            System.out.println("Cancelled Reservations: " + cancelledReservations);

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private double calculateOccupancyRate(List<Reservation> reservations, List<Room> rooms) {
        long totalRooms = rooms.size();
        long occupiedRooms = reservations.stream()
                .map(Reservation::getRoom)
                .distinct()
                .count();
        return (double) occupiedRooms / totalRooms;
    }

    private double calculateTotalRevenue(List<Reservation> reservations) {
        return reservations.stream()
                .mapToDouble(Reservation::getPrice)
                .sum();
    }

    private long countCancelledReservations(List<Reservation> reservations) {
        return reservations.stream()
                .filter(Reservation::isDeleted)
                .count();
    }
}
