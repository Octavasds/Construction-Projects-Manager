package ControllerLayer;
import ModelLayer.Material;
import ServiceLayer.*;
import ModelLayer.Project;
import java.util.Date;
import java.util.List;

public class ProjectController {
    private ControllerLayer.ProjectService projectService;

    public ProjectController(ControllerLayer.ProjectService projectService) {
        this.projectService = projectService;
    }

    // Method to update an existing project
    public void updateProject(int projectId, String name, String location, Date beginDate, Date finalDate, float budget) {
        projectService.updateProject(projectId, name, location, beginDate, finalDate, budget);
    }

    // Method to allocate an employee to a project
    public void allocateEmployeeToProject(int projectId, int employeeId) {
        projectService.allocateEmployeeToProject(projectId, employeeId);
    }

    // Method to deallocate an employee from a project
    public void deallocateEmployeeFromProject(int projectId, int employeeId) {
        projectService.deallocateEmployeeFromProject(projectId, employeeId);
    }

    // Method to allocate materials to a project
    public void allocateMaterialsToProject(int projectId, List<Material> materials) {
        projectService.allocateMaterialsToProject(projectId, materials);
    }

    // Method to update material inventory
    public void updateMaterialInventory(String materialName, int quantity) {
        projectService.updateMaterialInventory(materialName, quantity);
    }

    // Method to generate a project report
    public void generateProjectReport(int projectId) {
        projectService.generateProjectReport(projectId);
    }
}
