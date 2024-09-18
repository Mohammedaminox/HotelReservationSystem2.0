package com.hotelreservation.controller;

import com.hotelreservation.model.Client;
import com.hotelreservation.model.Reservation;
import com.hotelreservation.model.Room;
import com.hotelreservation.repository.ClientRepository;
import com.hotelreservation.service.ReservationService;
import com.hotelreservation.service.RoomService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ReservationController {
    private ReservationService reservationService;
    private RoomService roomService; // Add RoomService to fetch room details
    private Scanner scanner;

    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.scanner = new Scanner(System.in);
    }

    public void createReservation() {
        try {
            System.out.println("Enter your name:");
            String clientName = scanner.nextLine().trim();

            System.out.println("Enter your CIN:");
            String clientCin = scanner.nextLine().trim();

            // Check if name and CIN are not empty
            if (clientName.isEmpty() || clientCin.isEmpty()) {
                System.out.println("Name and CIN cannot be empty. Please enter valid details.");
                return;
            }

            // Use ClientRepository to check if the client exists
            ClientRepository clientRepository = new ClientRepository();
            Client client = clientRepository.getClientByCin(clientCin);

            if (client == null) {
                // Add client if not found
                int clientId = clientRepository.addClient(new Client(0, clientName, clientCin));
                client = new Client(clientId, clientName, clientCin);
            }

            // Fetch and display all available rooms
            List<Room> rooms = roomService.getAllRooms(); // Fetch rooms from the RoomService
            System.out.println("Available rooms:");
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    System.out.println("Room ID: " + room.getRoomId() + ", RoomType: " + room.getRoomType());
                }
            }

            // Ask the client to choose a room ID from the list
            System.out.println("Enter room ID from the available rooms:");
            int roomId = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            Room room = roomService.getRoomById(roomId);

            if (room == null || !room.isAvailable()) {
                System.out.println("Room not available.");
                return;
            }

            System.out.println("Enter check-in date (YYYY-MM-DD):");
            LocalDate checkInDate;
            try {
                checkInDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid check-in date format. Please use YYYY-MM-DD.");
                return;
            }

            System.out.println("Enter check-out date (YYYY-MM-DD):");
            LocalDate checkOutDate;
            try {
                checkOutDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid check-out date format. Please use YYYY-MM-DD.");
                return;
            }

            // Calculate total price
            double totalPrice = reservationService.calculateTotalPrice(room, checkInDate, checkOutDate);
            System.out.println("The total price for your stay is: " + totalPrice);

            // Create a new Reservation object
            Reservation reservation = new Reservation(0, client, room, checkInDate, checkOutDate, totalPrice);

            // Add the reservation
            reservationService.addReservation(reservation);

            System.out.println("Reservation created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
        }
    }

    public void updateReservation() {
        try {
            System.out.println("Enter your CIN:");
            String clientCin = scanner.nextLine().trim();

            // Check if CIN is not empty
            if (clientCin.isEmpty()) {
                System.out.println("CIN cannot be empty. Please enter valid details.");
                return;
            }

            // Use ClientRepository to find the client
            ClientRepository clientRepository = new ClientRepository();
            Client client = clientRepository.getClientByCin(clientCin);

            if (client == null) {
                System.out.println("No client found with the given CIN.");
                return;
            }

            // Use ReservationService to fetch all reservations for this client
            List<Reservation> reservations = reservationService.getReservationsByClient(clientCin);

            if (reservations.isEmpty()) {
                System.out.println("No reservations found for this client.");
                return;
            }

            // Display the reservations to the client
            System.out.println("Your reservations:");
            for (int i = 0; i < reservations.size(); i++) {
                Reservation res = reservations.get(i);
                System.out.println((i + 1) + ". Reservation ID: " + res.getReservationId() +
                        ", Room ID: " + res.getRoom().getRoomId() +
                        ", Check-in: " + res.getCheckInDate() +
                        ", Check-out: " + res.getCheckOutDate());
            }

            // Let the user choose which reservation to update
            System.out.println("Enter the number of the reservation you want to update:");
            int reservationIndex = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            if (reservationIndex < 1 || reservationIndex > reservations.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            // Get the selected reservation
            Reservation selectedReservation = reservations.get(reservationIndex - 1);

            // Ask for new check-in and check-out dates
            System.out.println("Enter new check-in date (YYYY-MM-DD):");
            LocalDate newCheckInDate = LocalDate.parse(scanner.nextLine());


            System.out.println("Enter new check-out date (YYYY-MM-DD):");
            LocalDate newCheckOutDate = LocalDate.parse(scanner.nextLine());

            // Set the new dates in the reservation object
            selectedReservation.setCheckInDate(newCheckInDate);
            selectedReservation.setCheckOutDate(newCheckOutDate);

            // Call the ReservationService to update the reservation
            reservationService.updateReservation(selectedReservation);

            System.out.println("Reservation updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    public void cancelReservation() {
        try {
            // Step 1: Ask for CIN input
            System.out.println("Enter your CIN:");
            String clientCin = scanner.nextLine().trim();

            if (clientCin.isEmpty()) {
                System.out.println("CIN cannot be empty. Please enter valid details.");
                return;
            }

            // Step 2: Get client by CIN
            ClientRepository clientRepository = new ClientRepository();
            Client client = clientRepository.getClientByCin(clientCin);

            if (client == null) {
                System.out.println("No client found with the given CIN.");
                return;
            }

            // Step 3: Fetch reservations for the client
            List<Reservation> reservations = reservationService.getReservationsByClient(clientCin);

            if (reservations.isEmpty()) {
                System.out.println("No reservations found for this client.");
                return;
            }

            // Step 4: Display reservations to the user
            System.out.println("Your reservations:");
            for (int i = 0; i < reservations.size(); i++) {
                Reservation res = reservations.get(i);
                System.out.println((i + 1) + ". Reservation ID: " + res.getReservationId() +
                        ", Room ID: " + res.getRoom().getRoomId() +
                        ", Check-in: " + res.getCheckInDate() +
                        ", Check-out: " + res.getCheckOutDate() +
                        ", Total Price: " + res.getPrice());
            }

            // Step 5: Let the user select a reservation to cancel
            System.out.println("Enter the number of the reservation you want to cancel:");
            int reservationIndex = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            if (reservationIndex < 1 || reservationIndex > reservations.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            // Step 6: Confirm cancellation
            Reservation selectedReservation = reservations.get(reservationIndex - 1);
            System.out.println("Are you sure you want to cancel this reservation? (yes/no)");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Cancellation aborted.");
                return;
            }

            // Step 7: Cancel the reservation through the service layer
            reservationService.deleteReservationById(selectedReservation.getReservationId());

            // Only the controller prints the success message
            System.out.println("Reservation cancelled successfully.");

        } catch (SQLException e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
        }
    }





}
