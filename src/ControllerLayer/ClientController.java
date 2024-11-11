package ControllerLayer;

import ModelLayer.Client;
import ModelLayer.Employee;
import ServiceLayer.ClientService;

import java.util.Map;

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void createClient(String name, String address, String phone, String email) {
        clientService.createClient(name, address, phone, email);
    }

    public Map<Integer, Client> getAllClients() {
        return clientService.getAllClients();
    }
}
