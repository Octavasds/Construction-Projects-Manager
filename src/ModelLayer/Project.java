package ModelLayer;

import Parser.ClientParser;
import Parser.EmployeeParser;
import Parser.EntityParser;
import RepositoryLayer.FileRepository;
import RepositoryLayer.IRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public String toString() {
        if (this.client==null)
            return this.name+','+this.location+','+this.beginDate+','+this.finalDate+','+this.budget;
        else
        {
            IRepository<Client> clientRepository= new FileRepository<>("clients.txt", new ClientParser());
            List<Client> cls= clientRepository.getAll();
            int id=1;
            for(Client cl:cls)
                if(Objects.equals(cl.getName(), client.getName()))
                    id=clientRepository.getID(cl);
            clientRepository=null;
            if(!this.employees.isEmpty())
            {
                IRepository<Employee> employeeIRepository = new FileRepository<>("employees.txt",new EmployeeParser());
                List<Employee> allem = employeeIRepository.getAll();
                StringBuilder ids = new StringBuilder();
                for(Employee emp:this.getEmployees())
                    for(Employee e:allem)
                        if(emp.getFirstName().equals(e.getFirstName()) && emp.getLastName().equals(e.getLastName()))
                            ids.append(employeeIRepository.getID(e)).append(',');
                employeeIRepository=null;
                return this.name+','+this.location+','+this.beginDate+','+this.finalDate+','+this.budget+','+ id+','+ids;
            }
            return this.name+','+this.location+','+this.beginDate+','+this.finalDate+','+this.budget+','+ id;
        }
    }

    public static Project fromString(String line) {

        List<Employee> allem = new ArrayList<>();
        String[] parts = line.split(",");
        Client cl=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(0,0,0);
        Date date2 = new Date(0,0,0);
        try {
            date1 = dateFormat.parse(parts[2]);
            date2 = dateFormat.parse(parts[3]);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());}
        if(parts.length > 5 && parts[5] != null && !parts[5].isEmpty())
        {
            IRepository<Client> clientRepository= new FileRepository<>("clients.txt", new ClientParser());
            cl= clientRepository.getById(Integer.parseInt(parts[5]));
            clientRepository=null;
        }
        if(parts.length > 6 && parts[6] != null && !parts[6].isEmpty())
        {
            IRepository<Employee> employeeIRepository = new FileRepository<>("employees.txt",new EmployeeParser());
            int ct=6;
            while(ct<parts.length && !parts[ct].isEmpty())
            {
                allem.add(employeeIRepository.getById(Integer.parseInt(parts[ct])));
                ct++;
            }
        }
        List<Material> mats=new ArrayList<>();
        return new Project(parts[0], parts[1],date1,date2,Float.parseFloat(parts[4]),cl,allem,mats);
    }
}

