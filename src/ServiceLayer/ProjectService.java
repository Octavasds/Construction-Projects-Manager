package ServiceLayer;

import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Exceptions.BusinessLogicException;
import ModelLayer.*;
import RepositoryLayer.DBRepository;
import RepositoryLayer.IRepository;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ProjectService {
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
            throw new ValidationException("Project cannot be null.");
        }
        if (!validateEmployeeAllocation(project.getEmployees())) {
            throw new ValidationException("Employee allocation failed for the project.");
        }
        if (!validateMaterialAllocation(project.getMaterials())) {
            throw new ValidationException("Material allocation failed for the project.");
        }

        projectRepository.add(project);
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
                    throw new BusinessLogicException("Engineer already assigned to a project.");
                }
            } else if (employee instanceof Worker) {
                if (!employee.getProjects().isEmpty()) {
                    throw new BusinessLogicException("Worker already assigned to a project.");
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
                throw new BusinessLogicException("Insufficient material in inventory.");
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
            throw new BusinessLogicException("Employee is already assigned to another project.");
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

        if (project == null || employee == null) {
            throw new EntityNotFoundException("Project or Employee not found.");
        }

        if (project.getEmployees().remove(employee)) {
            employee.getProjects().remove(project);
            employeeRepository.update(employeeId, employee);
            projectRepository.update(projectId, project);
        } else {
            throw new BusinessLogicException("Employee is not assigned to this project.");
        }
    }

    /**
     * Description: Method to allocate materials to a project
     * @param projectId
     * @param materials
     */
    public void allocateMaterialsToProject(int projectId, List<Material> materials) {
        Project project = projectRepository.getById(projectId);

        if (project == null) {
            throw new EntityNotFoundException("Project not found.");
        }

        for (Material material : materials) {
            if (inventory.hasMaterial(material)) {
                project.getMaterials().add(material);
                material.setQuantity(material.getQuantity() - 1); // Assuming quantity is reduced by 1 per allocation
            } else {
                throw new BusinessLogicException("Insufficient material in inventory for " + material.getName());
            }
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
                return;
            }
        }
        throw new EntityNotFoundException("Material not found in inventory.");
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

        System.out.println("Project Report:");
        System.out.println("Name: " + project.getName());
        System.out.println("Location: " + project.getLocation());
        System.out.println("Budget: " + project.getBudget());
        System.out.println("Begin Date: " + project.getBeginDate());
        System.out.println("Final Date: " + project.getFinalDate());
        System.out.println("Client: " + project.getClient().getName());
        System.out.println("Employees: ");
        for (Employee employee : project.getEmployees()) {
            System.out.println("- " + employee.getFirstName() + " " + employee.getLastName());
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

        if (project == null || client == null) {
            throw new EntityNotFoundException("Project or Client not found.");
        }

        project.setClient(client);
        clientRepository.update(clientId, client);

        if (projectRepository instanceof DBRepository<Project>) {
            ((DBRepository<Project>) projectRepository).updateProject(projectId, project, clientId);
        } else {
            projectRepository.update(projectId, project);
        }
    }

    /**
     * Sorts projects by budget
     * @return Sorted list of projects
     */
    public List<Project> sortProjectsByBudget() {
        List<Project> projects = new ArrayList<>(projectRepository.getAll());
        projects.sort((p1, p2) -> Float.compare(p2.getBudget(), p1.getBudget()));
        return projects;
    }

    /**
     * Filters projects by start date
     * @param minDate
     * @return List of filtered projects
     */
    public List<Project> filterProjectsByStartDate(Date minDate) {
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
     * @return true if client and employee are on the same project
     */
    public boolean isClientAndEmployeeOnSameProject(Client client, Employee employee, Project project) {
        boolean clientMatches = project.getClient() != null && project.getClient().equals(client);
        boolean employeeMatches = project.getEmployees().contains(employee);

        return clientMatches && employeeMatches;
    }
}
