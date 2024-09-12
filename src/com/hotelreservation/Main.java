package com.hotelreservation;

import com.hotelreservation.model.Reservation;
import com.hotelreservation.repository.ReservationRepository;
import com.hotelreservation.service.ReservationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Hotel hotel = new Hotel(5);
        Scanner scanner = new Scanner(System.in);

        int choix;

        do {
            System.out.println("\n--- Bienvenu ---");
            System.out.println("1. Créer une réservation");
            System.out.println("2. Modifier une réservation");
            System.out.println("3. Annuler une réservation");
            System.out.println("4. Afficher les réservations");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ReservationRepository reservationRepository = new ReservationRepository();
                    reservationRepository.addReservation(Reservation reservation);

                    break;
                case 2:

                    hotel.modifierReservation();
                    break;
                case 3:

                    hotel.annulerReservation();
                    break;
                case 4:
                    hotel.afficherReservations();
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }

        } while (choix != 0);

        scanner.close();
    }
}
