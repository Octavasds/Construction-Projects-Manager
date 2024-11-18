package ModelLayer;

import Parser.ProjectParser;
import RepositoryLayer.FileRepository;
import RepositoryLayer.IRepository;

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
//        StringBuilder ids= new StringBuilder();
//        if(!this.getProjects().isEmpty()) {
//            IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
//            List<Project> allpr=projectIRepository.getAll();
//            for (Project pr : getProjects())
//                for(Project por:allpr)
//                    if(pr.getName().equals(por.getName()))
//                        ids.append(projectIRepository.getID(por)).append(',');
//            return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.experienceLevel+','+ids;
//        }
        return this.getLastName() +','+ this.getFirstName() +','+ this.getRole() +','+ this.getSalary() +','+this.experienceLevel;
    }

    public static Worker fromString(String line) {

          String[] parts = line.split(",");
          List<Project> projects=new ArrayList<>();
//        String aux;
//        int ct;
//        if(parts.length > 5 && parts[5] != null && !parts[5].isEmpty())
//        {
//            IRepository<Project> projectIRepository = new FileRepository<>("projects.txt", new ProjectParser());
//            ct=5;
//            while(ct<parts.length) {
//                projects.add(projectIRepository.getById(Integer.parseInt(parts[ct])));
//                ct++;
//            }
//            projectIRepository=null;
//        }
        return new Worker(parts[0], parts[1],parts[2],Float.parseFloat(parts[3]),projects,parts[4]);
    }
}
