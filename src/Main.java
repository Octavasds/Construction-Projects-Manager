import ControllerLayer.*;
import ModelLayer.*;
import Parser.*;
import RepositoryLayer.DBRepository;
import RepositoryLayer.FileRepository;
import RepositoryLayer.InMemoryRepository;
import ServiceLayer.*;
import PresentationLayer.ConsoleApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter 1 for In-Memory or 2 for File Repository or 3 for Database: ");
        if (scan.hasNextInt()) {
            int number = scan.nextInt();

            if (number == 2) {
                FileRepository<Project> projectFileRepository = new FileRepository<>("projects.txt", new ProjectParser());
                InMemoryRepository<Material> materialRepository = new InMemoryRepository<>();
                FileRepository<Client> clientFileRepository = new FileRepository<>("clients.txt", new ClientParser());
                FileRepository<Employee> employeeFileRepository = new FileRepository<>("employees.txt", new EmployeeParser());
                List<Material> materials = new ArrayList<>();
                Inventory inventory = new Inventory(materials);
                ClientService clientService = new ClientService(clientFileRepository);
                ProjectService projectService = new ProjectService(projectFileRepository, employeeFileRepository, materialRepository, clientFileRepository, inventory);
                EmployeeService employeeService = new EmployeeService(employeeFileRepository);
                MaterialService materialService = new MaterialService(materialRepository);
                ClientController clientController = new ClientController(clientService);
                ProjectController projectController = new ProjectController(projectService);
                EmployeeController employeeController = new EmployeeController(employeeService);
                MaterialController materialController = new MaterialController(materialService);

                ConsoleApp app = new ConsoleApp(projectController, employeeController, clientController, materialController);
                app.run();
            } else if (number == 1) {
                InMemoryRepository<Project> projectRepository = new InMemoryRepository<>();
                InMemoryRepository<Material> materialRepository = new InMemoryRepository<>();
                InMemoryRepository<Client> clientRepository = new InMemoryRepository<>();
                InMemoryRepository<Employee> employeeRepository = new InMemoryRepository<>();
                List<Material> materials = new ArrayList<>();
                Inventory inventory = new Inventory(materials);
                ClientService clientService = new ClientService(clientRepository);
                ProjectService projectService = new ProjectService(projectRepository, employeeRepository, materialRepository, clientRepository, inventory);
                EmployeeService employeeService = new EmployeeService(employeeRepository);
                MaterialService materialService = new MaterialService(materialRepository);
                ClientController clientController = new ClientController(clientService);
                ProjectController projectController = new ProjectController(projectService);
                EmployeeController employeeController = new EmployeeController(employeeService);
                MaterialController materialController = new MaterialController(materialService);

                ConsoleApp app = new ConsoleApp(projectController, employeeController, clientController, materialController);
                app.run();
            }
            else {
                if (number == 3) {
                    DBRepository<Project> projectDBRepository = new DBRepository<>("projects");
                    InMemoryRepository<Material> materialRepository = new InMemoryRepository<>();
                    DBRepository<Client> clientDBRepository = new DBRepository<>("clients");
                    DBRepository<Employee> employeeDBRepository = new DBRepository<>("employees");
                    List<Material> materials = new ArrayList<>();
                    Inventory inventory = new Inventory(materials);
                    ClientService clientService = new ClientService(clientDBRepository);
                    ProjectService projectService = new ProjectService(projectDBRepository, employeeDBRepository, materialRepository, clientDBRepository, inventory);
                    EmployeeService employeeService = new EmployeeService(employeeDBRepository);
                    MaterialService materialService = new MaterialService(materialRepository);
                    ClientController clientController = new ClientController(clientService);
                    ProjectController projectController = new ProjectController(projectService);
                    EmployeeController employeeController = new EmployeeController(employeeService);
                    MaterialController materialController = new MaterialController(materialService);

                    ConsoleApp app = new ConsoleApp(projectController, employeeController, clientController, materialController);
                    app.run();
                }
            }

        } else {
            System.out.println("Invalid input! Please enter an integer.");
            scan.close();
            exit(1);
        }
    }
}

