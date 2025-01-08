package ServiceLayer;

import Exceptions.BusinessLogicException;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
import ModelLayer.Client;
import RepositoryLayer.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientService {
    private IRepository<Client> clientRepository;

    /**
     * Constructor
     * @param clientRepository The repository to manage clients
     */
    public ClientService(IRepository<Client> clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Creates a new Client
     * @param name Client name
     * @param address Client address
     * @param phone Client phone
     * @param email Client email
     * @throws BusinessLogicException if client already exists
     * @throws DatabaseException if a repository error occurs
     */
    public void createClient(String name, String address, String phone, String email) {
        try {
            // Business Rule Validation: Check for duplicate clients
            for (Client client : clientRepository.getAll()) {
                if (client.getEmail().equalsIgnoreCase(email)) {
                    throw new BusinessLogicException("A client with the email '" + email + "' already exists.");
                }
                if (client.getPhone().equals(phone)) {
                    throw new BusinessLogicException("A client with the phone number '" + phone + "' already exists.");
                }
            }

            // Add the new client to the repository
            Client newClient = new Client(name, address, phone, email, new ArrayList<>());
            clientRepository.add(newClient);

        } catch (RuntimeException e) {
            // Handle repository errors
            throw new DatabaseException("Repository error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all Clients
     * @return Map of Client IDs to Client objects
     * @throws EntityNotFoundException if no clients are found
     * @throws DatabaseException if a repository error occurs
     */
    public Map<Integer, Client> getAllClients() {
        Map<Integer, Client> allClients = new HashMap<>();

        try {
            for (Client client : clientRepository.getAll()) {
                allClients.put(clientRepository.getID(client), client);
            }
        } catch (RuntimeException e) {
            throw new DatabaseException("Repository error: " + e.getMessage());
        }

        if (allClients.isEmpty()) {
            throw new EntityNotFoundException("No clients found.");
        }

        return allClients;
    }
}
