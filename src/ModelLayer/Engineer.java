package ModelLayer;

import java.util.List;

public class Engineer extends Employee {
    private String specialization;

    public Engineer(String lastName, String firstName, String role, float salary, List<Project> projects, String specialization) {
        super(lastName, firstName, role, salary, projects);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
