import ControllerLayer.*;
import ModelLayer.*;
import RepositoryLayer.InMemoryRepository;
import ServiceLayer.*;
import PresentationLayer.ConsoleApp;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Repositories
        InMemoryRepository<Client> clientRepository = new InMemoryRepository<>();
        InMemoryRepository<Project> projectRepository = new InMemoryRepository<>();
        InMemoryRepository<Employee> employeeRepository = new InMemoryRepository<>();
        InMemoryRepository<Material> materialRepository = new InMemoryRepository<>();

        // Services
        List<Material> materials= new ArrayList<Material>();
        Inventory inventory = new Inventory(materials);
        ClientService clientService = new ClientService(clientRepository);
        ProjectService projectService = new ProjectService(projectRepository, employeeRepository, materialRepository, clientRepository,inventory);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        MaterialService materialService = new MaterialService(materialRepository);

        // Controllers
        ClientController clientController = new ClientController(clientService);
        ProjectController projectController = new ProjectController(projectService);
        EmployeeController employeeController = new EmployeeController(employeeService);
        MaterialController materialController = new MaterialController(materialService);

        // Console Application
        ConsoleApp app = new ConsoleApp(projectController, employeeController, clientController, materialController);
        app.run();
    }
}
