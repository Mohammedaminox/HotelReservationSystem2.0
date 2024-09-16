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
    public void addReservation(Reservation reservation) {
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
