package com.hotelreservation.model;

import java.time.LocalDate;

public class Reservation {

    //fields
    private int reservationId;
    private boolean isDeleted;
    private Client client;
    private Room room;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    private double price; // Total price for the stay

    //constructeur
    public Reservation(int reservationId, Client client, Room room, LocalDate checkInDate, LocalDate checkOutDate, double price) {
            this.reservationId = reservationId;
            this.client = client;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.isDeleted = false;
            this.price = price;
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

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getPrice() {
        return price;
    }

    public boolean isDeleted() {
        return isDeleted;
    }



    //setters
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }




}
