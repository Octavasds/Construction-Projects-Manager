package Tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import ModelLayer.*;
import ServiceLayer.*;
import RepositoryLayer.*;

import java.util.*;

public class ApplicationTest {

    // Repositories
    private IRepository<Client> clientRepository;
    private IRepository<Project> projectRepository;
    private IRepository<Employee> employeeRepository;

    // Services
    private ClientService clientService;
    private ProjectService projectService;
    private EmployeeService employeeService;

    // Test Data
    private Client testClient;
    private Project testProject;
    private Employee testEmployee;

    @BeforeEach
    public void setUp() {
        // Initialize repositories
        clientRepository = new InMemoryRepository<>();
        projectRepository = new InMemoryRepository<>();
        employeeRepository = new InMemoryRepository<>();

        // Initialize services
        clientService = new ClientService(clientRepository);
        projectService = new ProjectService(projectRepository, employeeRepository, null, clientRepository, null);
        employeeService = new EmployeeService(employeeRepository);

        // Initialize test data
        testClient = new Client("Test Client", "123 Test Street", "123456789", "test@example.com", new ArrayList<>());
        testProject = new Project("Test Project", "Test Location", new Date(), new Date(), 1000, null, new ArrayList<>(), new ArrayList<>());
        testEmployee = new Worker("Doe", "John", "Worker", 5000, new ArrayList<>(), "Intermediate");
    }

    @Test
    public void testClientCRUD() {
        // Create
        clientService.createClient(testClient.getName(), testClient.getAddress(), testClient.getPhone(), testClient.getEmail());
        Map<Integer, Client> clients = clientService.getAllClients();
        assertEquals(1, clients.size());
        Client createdClient = clients.values().iterator().next();
        assertEquals("Test Client", createdClient.getName());

        // Read
        Client retrievedClient = clientRepository.getById(clientRepository.getID(createdClient));
        assertNotNull(retrievedClient);
        assertEquals("Test Client", retrievedClient.getName());

        // Update
        retrievedClient.setName("Updated Client");
        clientRepository.update(clientRepository.getID(retrievedClient), retrievedClient);
        Client updatedClient = clientRepository.getById(clientRepository.getID(retrievedClient));
        assertEquals("Updated Client", updatedClient.getName());

        // Delete
        clientRepository.delete(retrievedClient);
        assertTrue(clientRepository.getAll().isEmpty());
    }

    @Test
    public void testProjectCRUD() {
        // Create
        projectRepository.add(testProject);
        assertEquals(1, projectRepository.getAll().size());

        // Read
        Project retrievedProject = projectRepository.getById(projectRepository.getID(testProject));
        assertNotNull(retrievedProject);
        assertEquals("Test Project", retrievedProject.getName());

        // Update
        retrievedProject.setName("Updated Project");
        projectRepository.update(projectRepository.getID(retrievedProject), retrievedProject);
        Project updatedProject = projectRepository.getById(projectRepository.getID(retrievedProject));
        assertEquals("Updated Project", updatedProject.getName());

        // Delete
        projectRepository.delete(retrievedProject);
        assertTrue(projectRepository.getAll().isEmpty());
    }

    @Test
    public void testComplexBusinessLogic() {
        // Normal flow
        employeeService.createWorker(testEmployee.getLastName(), testEmployee.getFirstName(), testEmployee.getRole(), testEmployee.getSalary(), "Intermediate");
        Map<Integer, Employee> employees = employeeService.getAllEmployees();
        assertEquals(1, employees.size());

        // Exception flow
        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectService.addProject(null);
        });
        assertEquals("Project cannot be null", exception.getMessage());
    }
}
