package com.hotelreservation.model;

public class Room {

    //fields
    private int roomId;
    private RoomType roomType;
    private boolean isAvailable;
    private double price;


    //constructeur
    public Room(int roomId, RoomType roomType, double price) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.isAvailable = true;
        this.price = price;

    }

    //getters
    public int getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    //set reservationStatus
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }
}
