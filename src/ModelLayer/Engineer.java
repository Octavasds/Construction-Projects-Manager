package ModelLayer;

import Parser.ClientParser;
import Parser.ProjectParser;
import RepositoryLayer.FileRepository;
import RepositoryLayer.IRepository;
import RepositoryLayer.InMemoryRepository;

import java.util.ArrayList;
import java.util.Collections;
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
        IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
        StringBuilder ids= new StringBuilder();
        if(!this.getProjects().isEmpty()) {
            for (Project pr : getProjects())
                ids.append(projectIRepository.getID(pr)).append(',');
            return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.specialization+','+ids;
        }
        return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.specialization;
    }

    public static Engineer fromString(String line) {
        IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
        String[] parts = line.split(",");
        List<Project> projectss=new ArrayList<>();
        String aux;
        int ct;
        if(parts.length > 5 && parts[5] != null && !parts[5].isEmpty())
        {
            ct=5;
            while(ct<parts.length) {
                projectss.add(projectIRepository.getById(Integer.parseInt(parts[ct])));
                ct++;
            }
        }
        return new Engineer(parts[0], parts[1],parts[2],Float.parseFloat(parts[3]),projectss,parts[4]);
    }
}
