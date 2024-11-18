package PresentationLayer;
import ControllerLayer.*;
import ModelLayer.*;

import java.util.Map;
import java.util.Scanner;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class ConsoleApp {
    private ProjectController projectController;
    private EmployeeController employeeController;
    private ClientController clientController;
    private MaterialController materialController;

    public ConsoleApp(ProjectController projectController, EmployeeController employeeController, ClientController clientController, MaterialController materialController) {
        this.projectController = projectController;
        this.employeeController = employeeController;
        this.clientController = clientController;
        this.materialController = materialController;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Project\n2. Delete Project\n3. Update Project\n4. Allocate Employee\n5. Deallocate Employee\n6. Allocate Materials\n7. Update Material Inventory\n8. Generate Project Report\n9. Show Unallocated Employees\n10. Create Contract\n11. Create Client\n12. Create Material\n13. Create Engineer\n14. Create Worker\n15. Show All Projects\n16. Show All Employees\n17. Allocate Client\n18. Show Clients\n19. Sort projects by price\n20. Sort workers by experience level\n21. Filter project by date\n22. Check if client and employee are on the same project \n23. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add Project
                    scanner.nextLine();
                    System.out.println("Enter project name:");
                    String projectName = scanner.nextLine();
                    System.out.println("Enter project location:");
                    String projectLocation = scanner.nextLine();
                    System.out.println("Enter begin date (yyyy-mm-dd):");
                    Date beginDate = Date.valueOf(scanner.nextLine());
                    System.out.println("Enter final date (yyyy-mm-dd):");
                    Date finalDate = Date.valueOf(scanner.nextLine());
                    System.out.println("Enter budget:");
                    float budget = scanner.nextFloat();
                    List<Employee> employees = new ArrayList<>();
                    List<Material> materials = new ArrayList<>();
                    Project newProject = new Project(projectName, projectLocation, beginDate, finalDate, budget, null, employees, materials);
                    projectController.addProject(newProject);
                    break;

                case 2:
                    // Delete Project
                    System.out.println("Enter project ID to delete:");
                    int projectIdToDelete = scanner.nextInt();
                    projectController.deleteProject(projectIdToDelete);
                    break;

                case 3:
                    // Update Project
                    System.out.println("Enter project ID to update:");
                    int projectIdToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new project name:");
                    String updatedName = scanner.nextLine();
                    System.out.println("Enter new project location:");
                    String updatedLocation = scanner.nextLine();
                    System.out.println("Enter new begin date (yyyy-mm-dd):");
                    Date updatedBeginDate = Date.valueOf(scanner.nextLine());
                    System.out.println("Enter new final date (yyyy-mm-dd):");
                    Date updatedFinalDate = Date.valueOf(scanner.nextLine());
                    System.out.println("Enter new budget:");
                    float updatedBudget = scanner.nextFloat();
                    projectController.updateProject(projectIdToUpdate, updatedName, updatedLocation, updatedBeginDate, updatedFinalDate, updatedBudget);
                    break;

                case 4:
                    // Allocate Employee
                    System.out.println("Enter project ID:");
                    int projectIdForEmp = scanner.nextInt();
                    System.out.println("Enter employee ID:");
                    int employeeIdToAllocate = scanner.nextInt();
                    projectController.allocateEmployeeToProject(projectIdForEmp, employeeIdToAllocate);
                    break;

                case 5:
                    // Deallocate Employee
                    System.out.println("Enter project ID:");
                    int projectIdToDeallocate = scanner.nextInt();
                    System.out.println("Enter employee ID:");
                    int employeeIdToDeallocate = scanner.nextInt();
                    projectController.deallocateEmployeeFromProject(projectIdToDeallocate, employeeIdToDeallocate);
                    break;

                case 6:
                    // Allocate Materials
                    System.out.println("Enter project ID:");
                    int projectIdForMaterials = scanner.nextInt();
                    scanner.nextLine();
                    List<Material> materialsToAllocate = new ArrayList<>();
                    projectController.allocateMaterialsToProject(projectIdForMaterials, materialsToAllocate);
                    break;

                case 7:
                    // Update Material Inventory
                    scanner.nextLine();
                    System.out.println("Enter material name:");
                    String materialNameToUpdate = scanner.nextLine();
                    System.out.println("Enter quantity to add:");
                    int quantityToAdd = scanner.nextInt();
                    projectController.updateMaterialInventory(materialNameToUpdate, quantityToAdd);
                    break;

                case 8:
                    // Generate Project Report
                    System.out.println("Enter project ID to generate report:");
                    int projectIdForReport = scanner.nextInt();
                    projectController.generateProjectReport(projectIdForReport);
                    break;

                case 9:
                    // Show Unallocated Employees
                    Map<Integer,Employee> unallocatedEmployees = employeeController.getUnallocatedEmployees();
                    System.out.println("Unallocated Employees:");
                    for (Map.Entry<Integer, Employee> employee : unallocatedEmployees.entrySet()) {
                        System.out.println("- " + employee.getKey() + " " + employee.getValue().getFirstName() + " " + employee.getValue().getLastName() + " (" + employee.getValue().getRole() + ")");
                    }
                    break;

                case 10:
                    // Create Contract
                    break;

                case 11:
                    // Create Client
                    scanner.nextLine();
                    System.out.println("Enter client name:");
                    String clientName = scanner.nextLine();
                    System.out.println("Enter client address:");
                    String clientAddress = scanner.nextLine();
                    System.out.println("Enter client phone:");
                    String clientPhone = scanner.nextLine();
                    System.out.println("Enter client email:");
                    String clientEmail = scanner.nextLine();
                    clientController.createClient(clientName, clientAddress, clientPhone, clientEmail);
                    break;

                case 12:
                    // Create Material
                    scanner.nextLine();
                    System.out.println("Enter material name:");
                    String materialName = scanner.nextLine();
                    System.out.println("Enter provider:");
                    String materialProvider = scanner.nextLine();
                    System.out.println("Enter quantity:");
                    int materialQuantity = scanner.nextInt();
                    System.out.println("Enter unit price:");
                    float materialPrice = scanner.nextFloat();
                    materialController.createMaterial(materialName, materialProvider, materialQuantity, materialPrice);
                    break;

                case 13:
                    // Create Engineer
                    scanner.nextLine();
                    System.out.println("Enter engineer last name:");
                    String engineerLastName = scanner.nextLine();
                    System.out.println("Enter engineer first name:");
                    String engineerFirstName = scanner.nextLine();
                    System.out.println("Enter role:");
                    String engineerRole = scanner.nextLine();
                    System.out.println("Enter salary:");
                    float engineerSalary = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.println("Enter specialization:");
                    String specialization = scanner.nextLine();
                    employeeController.createEngineer(engineerLastName, engineerFirstName, engineerRole, engineerSalary, specialization);
                    break;

                case 14:
                    // Create Worker
                    scanner.nextLine();
                    System.out.println("Enter worker last name:");
                    String workerLastName = scanner.nextLine();
                    System.out.println("Enter worker first name:");
                    String workerFirstName = scanner.nextLine();
                    System.out.println("Enter role:");
                    String workerRole = scanner.nextLine();
                    System.out.println("Enter salary:");
                    float workerSalary = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.println("Enter experience level:");
                    String experienceLevel = scanner.nextLine();
                    employeeController.createWorker(workerLastName, workerFirstName, workerRole, workerSalary, experienceLevel);
                    break;

                case 15:
                    //Show All Projects
                    Map<Integer,Project> projects = projectController.getAllProjects();
                    System.out.println("Projects: ");
                    for (Map.Entry<Integer, Project> project: projects.entrySet()) {
                        System.out.println("- " + project.getKey() + " " + project.getValue().getName());
                    }
                    break;
                case 16:
                    // Show All Employees
                    Map<Integer,Employee> allEmployees = employeeController.getAllEmployees();
                    System.out.println("Unallocated Employees:");
                    for (Map.Entry<Integer, Employee> employee : allEmployees.entrySet()) {
                        System.out.println("- " + employee.getKey() + " " + employee.getValue().getFirstName() + " " + employee.getValue().getLastName() + " (" + employee.getValue().getRole() + ")");
                    }
                    break;
                case 17:
                    // Allocate Client
                    System.out.println("Enter project ID:");
                    int projectIdForCl = scanner.nextInt();
                    System.out.println("Enter client ID:");
                    int clientIdToAllocate = scanner.nextInt();
                    projectController.allocateClientToProject(projectIdForCl, clientIdToAllocate);
                    break;
                case 18:
                    // Show All Clients
                    Map<Integer,Client> allClients = clientController.getAllClients();
                    System.out.println("Clients:");
                    for (Map.Entry<Integer, Client> client : allClients.entrySet()) {
                        System.out.println("- " + client.getKey() + " " + client.getValue().getName());
                    }
                    break;
                case 19:
                    // Sort Projects by Budget
                    List<Project> sortedProjectsByBudget = projectController.sortProjectsByBudget();
                    System.out.println("Projects sorted by Budget:");
                    for (Project project : sortedProjectsByBudget) {
                        System.out.println("- " + project.getName() + " (Budget: " + project.getBudget() + ")");
                    }
                    break;
                case 20:
                    // Sort Employees by Experience
                    List<Worker> sortedWorkers = employeeController.sortEmployeesByExperience();
                    System.out.println("Workers sorted by Experience:");
                    for (Worker worker : sortedWorkers) {
                        System.out.println("- " + worker.getFirstName() + " " + worker.getLastName() + " (Experience Level: " + worker.getExperienceLevel() + ")");
                    }
                    break;
                case 21:
                    Date minDate=new Date(125,1,2);
                    System.out.println(minDate);
                    List<Project> prs= projectController.filterProjectsByStartDate(minDate);
                    for(Project pr:prs)
                        System.out.println(pr.getName()+' '+pr.getBeginDate());
                    break;
                case 22:
                    System.out.println("Enter project ID:");
                    int projectId = scanner.nextInt();
                    System.out.println("Enter client ID:");
                    int clientId= scanner.nextInt();
                    System.out.println("Enter client ID:");
                    int workerId= scanner.nextInt();
                    Project auxP=null;
                    Client auxCl=null;
                    Employee auxW=null;
                    Map<Integer,Project> allP= projectController.getAllProjects();
                    for(int id:allP.keySet())
                        if(id==projectId)
                            auxP=allP.get(id);
                    Map<Integer,Client> allCl=clientController.getAllClients();
                    for(int id:allCl.keySet())
                        if(id==clientId)
                            auxCl=allCl.get(id);
                    Map<Integer,Employee> allW=employeeController.getAllEmployees();
                    for(int id:allW.keySet())
                        if(id==workerId)
                            auxW=allW.get(id);
                    System.out.println(projectController.isClientAndEmployeeOnSameProject(auxCl,auxW,auxP));
                    break;
                case 23:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}