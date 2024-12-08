package ServiceLayer;

import ModelLayer.Client;
import RepositoryLayer.IRepository;
import Exceptions.ValidationException;
import Exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientService {
    private IRepository<Client> clientRepository;

    /**
     * Description: Constructor
     * @param clientRepository
     */
    public ClientService(IRepository<Client> clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Description: Creates a new Client
     * @param name
     * @param address
     * @param phone
     * @param email
     */
    public void createClient(String name, String address, String phone, String email) {
        // Input validation
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

        Client newClient = new Client(name, address, phone, email, new ArrayList<>());
        clientRepository.add(newClient);
        System.out.println("Client created successfully: " + newClient.getName());
    }

    /**
     * Description: Gets all existent Clients
     * @return Map with all Clients and their IDs
     */
    public Map<Integer, Client> getAllClients() {
        Map<Integer, Client> allClients = new HashMap<>();

        for (Client client : clientRepository.getAll()) {
            allClients.put(clientRepository.getID(client), client);
        }

        if (allClients.isEmpty()) {
            throw new EntityNotFoundException("No clients found.");
        }

        return allClients;
    }
}
