import ControllerLayer.*;
import ModelLayer.*;
import Parser.*;
import RepositoryLayer.FileRepository;
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
        FileRepository<Client> clientFileRepository = new FileRepository<>("clients.txt",new ClientParser());
        FileRepository<Employee> employeeFileRepository = new FileRepository<>("employees.txt", new EmployeeParser());
        FileRepository<Project> projectFileRepository = new FileRepository<>("projects.txt", new ProjectParser());

        // Services
        List<Material> materials= new ArrayList<Material>();
        Inventory inventory = new Inventory(materials);
        ClientService clientService = new ClientService(clientFileRepository);
        ProjectService projectService = new ProjectService(projectFileRepository, employeeFileRepository, materialRepository, clientFileRepository,inventory);
        EmployeeService employeeService = new EmployeeService(employeeFileRepository);
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
