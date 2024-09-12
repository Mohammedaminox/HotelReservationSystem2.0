package com.hotelreservation.service;

import com.hotelreservation.model.Reservation;
import com.hotelreservation.repository.ReservationRepository;

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

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.getReservationById(id);
    }

    public void deleteReservationById(int id) {
        reservationRepository.deleteReservation(id);
    }

}
