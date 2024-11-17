package ModelLayer;

import java.util.ArrayList;
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

    public String toString() {
        return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.experienceLevel;
    }

    public static Worker fromString(String line) {
        String[] parts = line.split(",");
        List<Project> projects=new ArrayList<>();
        return new Worker(parts[0], parts[1],parts[2],Float.parseFloat(parts[3]),projects,parts[4]);
    }
}
