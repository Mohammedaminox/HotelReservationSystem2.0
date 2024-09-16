package com.hotelreservation;

import com.hotelreservation.controller.ReservationController;
import com.hotelreservation.repository.ReservationRepository;
import com.hotelreservation.repository.RoomRepository;
import com.hotelreservation.service.ReservationService;
import com.hotelreservation.service.RoomService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Set up repository, service, and controller
        RoomRepository roomRepository = new RoomRepository();
        ReservationRepository reservationRepository = new ReservationRepository();

        RoomService roomService = new RoomService(roomRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);

        ReservationController reservationController = new ReservationController(reservationService, roomService);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Create Reservation");
            System.out.println("2. Update Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservation");
            System.out.println("5. List All Reservations");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    reservationController.createReservation();
                    break;

                case 2:
                    reservationController.updateReservation();
                    break;
                case 3:
                    reservationController.cancelReservation();
                    break;
                // Add other cases as needed
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);

        scanner.close();
    }
}
