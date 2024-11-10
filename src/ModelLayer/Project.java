package ModelLayer;

import java.util.Date;
import java.util.List;

public class Project {
    private String name;
    private String location;
    private Date beginDate;
    private Date finalDate;
    private float budget;
    private Client client;
    private List<Employee> employees;
    private List<Material> materials;

    public Project(String name, String location, Date beginDate, Date finalDate, float budget, Client client, List<Employee> employees, List<Material> materials) {
        this.name = name;
        this.location = location;
        this.beginDate = beginDate;
        this.finalDate = finalDate;
        this.budget = budget;
        this.client = client;
        this.employees = employees;
        this.materials = materials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}

