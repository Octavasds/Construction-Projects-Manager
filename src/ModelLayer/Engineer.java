package ModelLayer;

import java.util.ArrayList;
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

    public String toString() {
        return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.specialization;
    }

    public static Engineer fromString(String line) {
        String[] parts = line.split(",");
        List<Project> projects=new ArrayList<>();
        return new Engineer(parts[0], parts[1],parts[2],Float.parseFloat(parts[3]),projects,parts[4]);
    }
}
