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
        StringBuilder ids= new StringBuilder();
//        if(!this.getProjects().isEmpty()) {
//            IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
//            List<Project> allpr=projectIRepository.getAll();
//            for (Project pr : getProjects())
//                for(Project por:allpr)
//                    if(pr.getName().equals(por.getName()))
//                        ids.append(projectIRepository.getID(por)).append(',');
//            projectIRepository=null;
//            return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.specialization+','+ids;
//        }
        return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.specialization;
    }

    public static Engineer fromString(String line) {
        String[] parts = line.split(",");
        List<Project> projectss=new ArrayList<>();
//        String aux;
//        int ct;
//        if(parts.length > 5 && parts[5] != null && !parts[5].isEmpty())
//        {
//            IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
//            ct=5;
//            while(ct<parts.length) {
//                projectss.add(projectIRepository.getById(Integer.parseInt(parts[ct])));
//                ct++;
//            }
//            projectIRepository=null;
//        }
        return new Engineer(parts[0], parts[1],parts[2],Float.parseFloat(parts[3]),projectss,parts[4]);
    }
}
