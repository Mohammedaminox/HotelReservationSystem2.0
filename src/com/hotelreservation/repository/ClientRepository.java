package com.hotelreservation.repository;

import com.hotelreservation.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    private ArrayList<Client> clients = new ArrayList<Client>();

    public List<Client> getAllClients(){
        return clients;
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public Client getClient(int clientId){
        return clients.stream()
                .filter(client -> client.getClientId() == clientId)
                .findFirst()
                .orElse(null);
    }

}
