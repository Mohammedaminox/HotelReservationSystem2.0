package com.hotelreservation.model;

import java.time.LocalDate;

public class Reservation {

    //fields
    private int reservationId;
    private Client client;
    private Room room;
    LocalDate checkInDate;
    LocalDate checkOutDate;

    //constructeur
    public Reservation(int reservationId, Client client, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
            this.reservationId = reservationId;
            this.client = client;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
    }

    //getters
    public int getReservationId() {
        return reservationId;
    }

    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
    }


    //setters
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
