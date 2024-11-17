package ModelLayer;

import java.util.List;

public class Employee {
    private String lastName;
    private String firstName;
    private String role;
    private float salary;
    private List<Project> projects;

    public Employee(String lastName, String firstName, String role, float salary, List<Project> projects) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.salary = salary;
        this.projects = projects;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}

