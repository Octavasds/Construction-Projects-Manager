package ServiceLayer;
import ModelLayer.*;
import RepositoryLayer.IRepository;

import java.util.List;

public class ProjectService {
    private IRepository<Project> projectRepository;
    private IRepository<Employee> employeeRepository;
    private IRepository<Material> materialRepository;
    private Inventory inventory;

    public ProjectService(IRepository<Project> projectRepository, IRepository<Employee> employeeRepository, IRepository<Material> materialRepository, Inventory inventory) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.materialRepository = materialRepository;
        this.inventory = inventory;
    }

    public void addProject(Project project) {
        if (validateEmployeeAllocation(project.getEmployees()) && validateMaterialAllocation(project.getMaterials())) {
            projectRepository.add(project);
        } else {
            System.out.println("Error: Could not allocate employees or materials for this project.");
        }
    }

    public void deleteProject(int projectId) {
        Project project = projectRepository.getById(projectId);
        if (project != null) {
            projectRepository.delete(project);
        }
    }

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

    private boolean validateMaterialAllocation(List<Material> materials) {
        for (Material material : materials) {
            if (!inventory.hasMaterial(material)) {
                System.out.println("Error: Insufficient material in inventory.");
                return false;
            }
        }
        return true;
    }
}

