package ControllerLayer;
import Exceptions.ValidationException;
import ModelLayer.Client;
import ModelLayer.Employee;
import ModelLayer.Material;
import ServiceLayer.*;
import ModelLayer.Project;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Method for adding a new project
    public void addProject(Project project) {
        if (project == null) {
            throw new ValidationException("Project cannot be null.");
        }
        projectService.addProject(project);
    }

    public void deleteProject(int projectId) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        projectService.deleteProject(projectId);
    }

    public void updateProject(int projectId, String name, String location, Date beginDate, Date finalDate, float budget) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Project name cannot be null or empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new ValidationException("Project location cannot be null or empty.");
        }
        if (beginDate == null || finalDate == null) {
            throw new ValidationException("Begin date and final date cannot be null.");
        }
        if (beginDate.after(finalDate)) {
            throw new ValidationException("Begin date must be before or equal to the final date.");
        }
        if (budget <= 0) {
            throw new ValidationException("Budget must be greater than zero.");
        }

        projectService.updateProject(projectId, name, location, beginDate, finalDate, budget);
    }

    public void allocateEmployeeToProject(int projectId, int employeeId) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        if (employeeId <= 0) {
            throw new ValidationException("Employee ID must be greater than zero.");
        }

        projectService.allocateEmployeeToProject(projectId, employeeId);
    }

    public void deallocateEmployeeFromProject(int projectId, int employeeId) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        if (employeeId <= 0) {
            throw new ValidationException("Employee ID must be greater than zero.");
        }

        projectService.deallocateEmployeeFromProject(projectId, employeeId);
    }

    public void allocateMaterialsToProject(int projectId, List<Material> materials) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        if (materials == null || materials.isEmpty()) {
            throw new ValidationException("Materials list cannot be null or empty.");
        }

        projectService.allocateMaterialsToProject(projectId, materials);
    }

    public void allocateClientToProject(int projectId, int clientId) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }
        if (clientId <= 0) {
            throw new ValidationException("Client ID must be greater than zero.");
        }

        try {
            projectService.allocateClientToProject(projectId, clientId);
        } catch (SQLException e) {
            throw new RuntimeException("Error allocating client to project: " + e.getMessage(), e);
        }
    }

    public void updateMaterialInventory(String materialName, int quantity) {
        if (materialName == null || materialName.trim().isEmpty()) {
            throw new ValidationException("Material name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than zero.");
        }

        projectService.updateMaterialInventory(materialName, quantity);
    }

    public void generateProjectReport(int projectId) {
        if (projectId <= 0) {
            throw new ValidationException("Project ID must be greater than zero.");
        }

        projectService.generateProjectReport(projectId);
    }

    public Map<Integer, Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public List<Project> sortProjectsByBudget() {
        return projectService.sortProjectsByBudget();
    }

    public List<Project> filterProjectsByStartDate(Date minDate) {
        if (minDate == null) {
            throw new ValidationException("Minimum date cannot be null.");
        }

        return projectService.filterProjectsByStartDate(minDate);
    }

    public boolean isClientAndEmployeeOnSameProject(Client client, Employee employee, Project project) {
        if (client == null) {
            throw new ValidationException("Client cannot be null.");
        }
        if (employee == null) {
            throw new ValidationException("Employee cannot be null.");
        }
        if (project == null) {
            throw new ValidationException("Project cannot be null.");
        }

        return projectService.isClientAndEmployeeOnSameProject(client, employee, project);
    }
}