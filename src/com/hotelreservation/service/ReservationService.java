package com.hotelreservation.service;

import com.hotelreservation.model.Reservation;
import com.hotelreservation.repository.ReservationRepository;

import java.sql.SQLException;
import java.util.List;

public class ReservationService {

    ReservationRepository reservationRepository = new ReservationRepository();

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Add a new reservation
    public void addReservation(Reservation reservation) throws SQLException {
        // Check if the dates overlap with any existing reservations for the same room
        boolean isOverlapping = reservationRepository.isReservationOverlapping(
                reservation.getRoom().getRoomId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        // If there's an overlap, throw an exception or handle it accordingly
        if (isOverlapping) {
            throw new SQLException("The reservation dates overlap with an existing reservation for this room.");
        }

        // If no overlap, proceed to add the reservation to the repository
        reservationRepository.addReservation(reservation);
    }


    public void updateReservation(Reservation reservation) throws SQLException {
        reservationRepository.updateReservation(reservation);
    }

    public List<Reservation> getReservationsByClient(String clientCin) throws SQLException {
        return reservationRepository.getReservationsByClientCin(clientCin);
    }

    public void deleteReservationById(int reservationId) throws SQLException {
        reservationRepository.deleteReservationById(reservationId);
    }




}
