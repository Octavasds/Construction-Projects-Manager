package ControllerLayer;

import Exceptions.ValidationException;
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
        // Validate input parameters
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Client name cannot be null or empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new ValidationException("Client address cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Client phone cannot be null or empty.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new ValidationException("Client email is invalid.");
        }

        clientService.createClient(name, address, phone, email);
    }

    public Map<Integer, Client> getAllClients() {
        return clientService.getAllClients();
    }
}
