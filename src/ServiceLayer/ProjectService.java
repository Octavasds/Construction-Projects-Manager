package ServiceLayer;
import ModelLayer.*;
import RepositoryLayer.IRepository;
import ServiceLayer.*;

import java.util.*;

public class ProjectService {
    // Existing attributes
    private IRepository<Project> projectRepository;
    private IRepository<Employee> employeeRepository;
    private IRepository<Material> materialRepository;
    private IRepository<Client> clientRepository;
    private Inventory inventory;

    /**
     * Description: Constructor
     * @param projectRepository
     * @param employeeRepository
     * @param materialRepository
     * @param clientRepository
     * @param inventory
     */
    public ProjectService(IRepository<Project> projectRepository, IRepository<Employee> employeeRepository, IRepository<Material> materialRepository, IRepository<Client> clientRepository,Inventory inventory) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.materialRepository = materialRepository;
        this.clientRepository = clientRepository;
        this.inventory = inventory;
    }

    /**
     * Description: Adds a new project
     * @param project
     */
    public void addProject(Project project) {
        if (validateEmployeeAllocation(project.getEmployees()) && validateMaterialAllocation(project.getMaterials())) {
            projectRepository.add(project);
        } else {
            System.out.println("Error: Could not allocate employees or materials for this project.");
        }
    }

    /**
     * Description: Deletes an existent project
     * @param projectId
     */
    public void deleteProject(int projectId) {
        Project project = projectRepository.getById(projectId);
        if (project != null) {
            projectRepository.delete(project);
        }
    }

    /**
     * Description: Validation method for employee allocation
     * @param employees
     * @return
     */
    private boolean validateEmployeeAllocation(List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee instanceof Engineer) {
                if (!employee.getProjects().isEmpty()) {
                    System.out.println("Error: Engineer already assigned to a project.");
                    return false;
                }
            } else if (employee instanceof Worker) {
                if (!employee.getProjects().isEmpty()) {
                    System.out.println("Error: Worker already assigned to a project.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Description: Validation method for material allocation
     * @param materials
     * @return
     */
    private boolean validateMaterialAllocation(List<Material> materials) {
        for (Material material : materials) {
            if (!inventory.hasMaterial(material)) {
                System.out.println("Error: Insufficient material in inventory.");
                return false;
            }
        }
        return true;
    }

    /**
     * Description: Method to update an existing project
     * @param projectId
     * @param name
     * @param location
     * @param beginDate
     * @param finalDate
     * @param budget
     */
    public void updateProject(int projectId, String name, String location, Date beginDate, Date finalDate, float budget) {
        Project project = projectRepository.getById(projectId);
        if (project != null) {
            project.setName(name);
            project.setLocation(location);
            project.setBeginDate(beginDate);
            project.setFinalDate(finalDate);
            project.setBudget(budget);
            projectRepository.update(projectId, project);
            System.out.println("Project updated successfully.");
        } else {
            System.out.println("Error: Project with ID " + projectId + " not found.");
        }
    }

    /**
     * Description: Method to allocate an employee to a project
     * @param projectId
     * @param employeeId
     */
    public void allocateEmployeeToProject(int projectId, int employeeId) {
        Project project = projectRepository.getById(projectId);
        Employee employee = employeeRepository.getById(employeeId);

        if (project != null && employee != null) {
            if (employee.getProjects().isEmpty()) {
                project.getEmployees().add(employee);
                employee.getProjects().add(project);
                System.out.println("Employee allocated successfully.");
            } else {
                System.out.println("Error: Employee is already assigned to another project.");
            }
        } else {
            System.out.println("Error: Project or Employee not found.");
        }
    }

    /**
     * Description: Method to deallocate an employee from a project
     * @param projectId
     * @param employeeId
     */
    public void deallocateEmployeeFromProject(int projectId, int employeeId) {
        Project project = projectRepository.getById(projectId);
        Employee employee = employeeRepository.getById(employeeId);

        if (project != null && employee != null) {
            if (project.getEmployees().remove(employee)) {
                employee.getProjects().remove(project);
                System.out.println("Employee deallocated successfully.");
            } else {
                System.out.println("Error: Employee not assigned to this project.");
            }
        } else {
            System.out.println("Error: Project or Employee not found.");
        }
    }

    /**
     * Description: Method to allocate materials to a project
     * @param projectId
     * @param materials
     */
    public void allocateMaterialsToProject(int projectId, List<Material> materials) {
        Project project = projectRepository.getById(projectId);

        if (project != null) {
            for (Material material : materials) {
                if (inventory.hasMaterial(material)) {
                    project.getMaterials().add(material);
                    material.setQuantity(material.getQuantity() - 1); // Assuming quantity is reduced by 1 per allocation
                    System.out.println("Material allocated: " + material.getName());
                } else {
                    System.out.println("Error: Insufficient material in inventory for " + material.getName());
                }
            }
        } else {
            System.out.println("Error: Project not found.");
        }
    }

    /**
     * Description: Method to update material inventory
     * @param materialName
     * @param quantity
     */
    public void updateMaterialInventory(String materialName, int quantity) {
        for (Material material : inventory.getMaterials()) {
            if (material.getName().equals(materialName)) {
                material.setQuantity(material.getQuantity() + quantity);
                System.out.println("Material inventory updated: " + materialName + " now has " + material.getQuantity() + " units.");
                return;
            }
        }
        System.out.println("Error: Material not found in inventory.");
    }

    /**
     * Description: Generates all the information about a project
     * @param projectId
     */
    public void generateProjectReport(int projectId) {
        Project project = projectRepository.getById(projectId);
        if (project != null) {
            System.out.println("Project Report: ");
            System.out.println("Name: " + project.getName());
            System.out.println("Location: " + project.getLocation());
            System.out.println("Budget: " + project.getBudget());
            System.out.println("Begin Date: " + project.getBeginDate());
            System.out.println("Final Date: " + project.getFinalDate());
            System.out.println("Client: " + project.getClient().getName());
            System.out.println("Employees: ");
            for (Employee employee : project.getEmployees()) {
                System.out.println("- " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getRole() + ")");
            }
            System.out.println("Materials: ");
            for (Material material : project.getMaterials()) {
                System.out.println("- " + material.getName() + " (Quantity: " + material.getQuantity() + ")");
            }
        } else {
            System.out.println("Error: Project not found.");
        }
    }

    /**
     * Description: Gets all existent projects
     * @return Map with projects and their IDs
     */
    public Map<Integer, Project> getAllProjects() {
        Map<Integer,Project> projects = new HashMap<>();

        for (Project project : projectRepository.getAll()) {
                projects.put(projectRepository.getID(project),project);
        }
        return projects;
    }

    /**
     * Description: Allocates a client to a project
     * @param projectId
     * @param clientId
     */
    public void allocateClientToProject(int projectId, int clientId) {
        Project project = projectRepository.getById(projectId);
        Client client = clientRepository.getById(clientId);

        if (project != null && client != null) {
            project.setClient(client);
            projectRepository.update(projectId, project);
            System.out.println("Client " + client.getName() + " has been successfully allocated to project " + project.getName());
        } else {
            System.out.println("Error: Project or Client not found.");
        }
    }
    //Sorts project by budget
    public List<Project> sortProjectsByBudget() {
        List<Project> projects = new ArrayList<>(projectRepository.getAll());
        projects.sort((p1, p2) -> Float.compare(p2.getBudget(), p1.getBudget()));
        return projects;
    }
    //Shows projects that have already begun from a specific date
    public List<Project> filterProjectsByStartDate(Date minDate) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projectRepository.getAll()) {
            if (project.getBeginDate().after(minDate) || project.getBeginDate().equals(minDate)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

}

