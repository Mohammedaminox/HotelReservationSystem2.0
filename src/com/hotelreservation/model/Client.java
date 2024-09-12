package com.hotelreservation.model;

public class Client {

    //fields
    private int clientId;
    private String clientName;



    //constructeur
    public Client(int clientId, String clientName){
        this.clientId = clientId;
        this.clientName = clientName;

    }

    //getters
    public int getClientId(){
        return clientId;
    }
    public String getClientName(){
        return clientName;
    }

}
