package ModelLayer;

import java.util.List;

public class Worker extends Employee {
    private String experienceLevel;

    public Worker(String lastName, String firstName, String role, float salary, List<Project> projects, String experienceLevel) {
        super(lastName, firstName, role, salary, projects);
        this.experienceLevel = experienceLevel;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}
