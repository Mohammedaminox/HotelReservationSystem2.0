package com.hotelreservation.model;

public class Client {

    //fields
    private int clientId;
    private String clientName;
    private String clientEmail;


    //constructeur
    public Client(int clientId, String clientName, String clientEmail){
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
    }

    //getters
    public int getClientId(){
        return clientId;
    }
    public String getClientName(){
        return clientName;
    }
    public String getClientEmail(){
        return clientEmail;
    }
}
