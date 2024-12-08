package ServiceLayer;
import Exceptions.EntityNotFoundException;
import ModelLayer.Client;
import ModelLayer.Employee;
import RepositoryLayer.IRepository;
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
        // Nu adăugăm validări aici, deoarece sunt gestionate în ControllerLayer.
        Client newClient = new Client(name, address, phone, email, new ArrayList<>());
        clientRepository.add(newClient);
    }

    /**
     * Description: Gets all existent Clients
     * @return Map with all Clients and their IDs
     * @throws EntityNotFoundException if no clients exist
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
