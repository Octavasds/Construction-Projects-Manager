package ControllerLayer;
import ModelLayer.Employee;
import ModelLayer.Material;
import ServiceLayer.*;
import ModelLayer.Project;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //Method for adding a new project
    public void addProject(Project project) {
        projectService.addProject(project);
    }

    // Method for deleting an existing project
    public  void deleteProject(int projectId) {
        projectService.deleteProject(projectId);
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

    public void allocateClientToProject(int projectId, int clientId){
        projectService.allocateClientToProject(projectId,clientId);
    }
    // Method to update material inventory
    public void updateMaterialInventory(String materialName, int quantity) {
        projectService.updateMaterialInventory(materialName, quantity);
    }

    // Method to generate a project report
    public void generateProjectReport(int projectId) {
        projectService.generateProjectReport(projectId);
    }

    public Map<Integer,Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}
