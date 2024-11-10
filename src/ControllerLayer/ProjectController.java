package ControllerLayer;
import ServiceLayer.*;
import ModelLayer.Project;

public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void addProject(Project project) {
        projectService.addProject(project);
    }

    public void deleteProject(int projectId) {
        projectService.deleteProject(projectId);
    }
}

