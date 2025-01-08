package ServiceLayer;
import Exceptions.BusinessLogicException;
import Exceptions.EntityNotFoundException;
import ModelLayer.*;
import RepositoryLayer.DBRepository;
import RepositoryLayer.IRepository;
import ServiceLayer.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
    public ProjectService(IRepository<Project> projectRepository, IRepository<Employee> employeeRepository, IRepository<Material> materialRepository, IRepository<Client> clientRepository, Inventory inventory) {
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
        if (project == null) {
            throw new BusinessLogicException("Project cannot be null.");
        }
        if (validateEmployeeAllocation(project.getEmployees()) && validateMaterialAllocation(project.getMaterials())) {
            projectRepository.add(project);
        } else {
            throw new BusinessLogicException("Could not allocate employees or materials for this project.");
        }
    }

    /**
     * Description: Deletes an existent project
     * @param projectId
     */
    public void deleteProject(int projectId) {
        Project project = projectRepository.getById(projectId);
        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }
        projectRepository.delete(project);
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
                    throw new BusinessLogicException("Engineer " + employee.getFirstName() + " " + employee.getLastName() + " is already assigned to a project.");
                }
            } else if (employee instanceof Worker) {
                if (!employee.getProjects().isEmpty()) {
                    throw new BusinessLogicException("Worker " + employee.getFirstName() + " " + employee.getLastName() + " is already assigned to a project.");
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
                throw new BusinessLogicException("Insufficient material in inventory for " + material.getName() + ".");
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
        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }
        project.setName(name);
        project.setLocation(location);
        project.setBeginDate(beginDate);
        project.setFinalDate(finalDate);
        project.setBudget(budget);
        projectRepository.update(projectId, project);
    }

    /**
     * Description: Method to allocate an employee to a project
     * @param projectId
     * @param employeeId
     */
    public void allocateEmployeeToProject(int projectId, int employeeId) {
        Project project = projectRepository.getById(projectId);
        Employee employee = employeeRepository.getById(employeeId);

        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }
        if (employee == null) {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found.");
        }

        if (!employee.getProjects().isEmpty()) {
            throw new BusinessLogicException("Employee " + employee.getFirstName() + " " + employee.getLastName() + " is already assigned to a project.");
        }

        project.getEmployees().add(employee);
        employee.getProjects().add(project);
        employeeRepository.update(employeeId, employee);
        projectRepository.update(projectId, project);
    }

    /**
     * Description: Method to deallocate an employee from a project
     * @param projectId
     * @param employeeId
     */
    public void deallocateEmployeeFromProject(int projectId, int employeeId) {
        Project project = projectRepository.getById(projectId);
        Employee employee = employeeRepository.getById(employeeId);

        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }
        if (employee == null) {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found.");
        }

        if (!project.getEmployees().remove(employee)) {
            throw new BusinessLogicException("Employee with ID " + employeeId + " is not assigned to project " + projectId + ".");
        }

        employee.getProjects().remove(project);
        employeeRepository.update(employeeId, employee);
        projectRepository.update(projectId, project);
    }

    /**
     * Description: Method to allocate materials to a project
     * @param projectId
     * @param materials
     */
    public void allocateMaterialsToProject(int projectId, List<Material> materials) {
        Project project = projectRepository.getById(projectId);

        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }

        if (materials == null || materials.isEmpty()) {
            throw new BusinessLogicException("Materials list cannot be null or empty.");
        }

        for (Material material : materials) {
            if (!inventory.hasMaterial(material)) {
                throw new BusinessLogicException("Insufficient material in inventory for " + material.getName() + ".");
            }
            project.getMaterials().add(material);
            material.setQuantity(material.getQuantity() - 1); // Assuming quantity is reduced by 1 per allocation
        }

        projectRepository.update(projectRepository.getID(project), project);
    }

    /**
     * Description: Method to update material inventory
     * @param materialName
     * @param quantity
     */
    public void updateMaterialInventory(String materialName, int quantity) {
        if (materialName == null || materialName.trim().isEmpty()) {
            throw new BusinessLogicException("Material name cannot be null or empty.");
        }

        if (quantity <= 0) {
            throw new BusinessLogicException("Quantity must be greater than zero.");
        }

        boolean updated = false;
        for (Material material : inventory.getMaterials()) {
            if (material.getName().equals(materialName)) {
                material.setQuantity(material.getQuantity() + quantity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new EntityNotFoundException("Material " + materialName + " not found in inventory.");
        }
    }

    /**
     * Description: Generates all the information about a project
     * @param projectId
     */
    public void generateProjectReport(int projectId) {
        Project project = projectRepository.getById(projectId);
        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }

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
    }

    /**
     * Description: Gets all existent projects
     * @return Map with projects and their IDs
     */
    public Map<Integer, Project> getAllProjects() {
        Map<Integer, Project> projects = new HashMap<>();

        for (Project project : projectRepository.getAll()) {
            projects.put(projectRepository.getID(project), project);
        }

        return projects;
    }

    /**
     * Description: Allocates a client to a project
     * @param projectId
     * @param clientId
     */
    public void allocateClientToProject(int projectId, int clientId) throws SQLException {
        Project project = projectRepository.getById(projectId);
        Client client = clientRepository.getById(clientId);

        if (project == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " not found.");
        }
        if (client == null) {
            throw new EntityNotFoundException("Client with ID " + clientId + " not found.");
        }

        project.setClient(client);
        projectRepository.update(projectId, project);
    }

    /**
     * Sorts project by budget
     * @return List of projects sorted by budget
     */
    public List<Project> sortProjectsByBudget() {
        List<Project> projects = new ArrayList<>(projectRepository.getAll());
        projects.sort((p1, p2) -> Float.compare(p2.getBudget(), p1.getBudget()));
        return projects;
    }

    /**
     * Shows projects that have already begun from a specific date
     * @param minDate
     * @return
     */
    public List<Project> filterProjectsByStartDate(Date minDate) {
        if (minDate == null) {
            throw new BusinessLogicException("Minimum date cannot be null.");
        }

        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projectRepository.getAll()) {
            if (project.getBeginDate().after(minDate) || project.getBeginDate().equals(minDate)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    /**
     * It checks if a client and employee are on the same project
     * @param client
     * @param employee
     * @param project
     * @return
     */
    public boolean isClientAndEmployeeOnSameProject(Client client, Employee employee, Project project) {
        if (client == null) {
            throw new BusinessLogicException("Client cannot be null.");
        }
        if (employee == null) {
            throw new BusinessLogicException("Employee cannot be null.");
        }
        if (project == null) {
            throw new BusinessLogicException("Project cannot be null.");
        }

        boolean clientMatches = project.getClient() != null && project.getClient().equals(client);
        boolean employeeMatches = project.getEmployees().contains(employee);

        return clientMatches && employeeMatches;
    }
}
