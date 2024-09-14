package com.hotelreservation.model;

public class Client {

    //fields
    private int clientId;
    private String clientName;
    private String cin;



    //constructeur
    public Client(int clientId, String clientName, String cin) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.cin = cin;
    }


    //getters
    public int getClientId(){
        return clientId;
    }
    public String getClientName(){
        return clientName;
    }
    public String getCin() {
        return cin;
    }

}
