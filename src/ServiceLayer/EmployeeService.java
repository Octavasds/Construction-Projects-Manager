package ServiceLayer;
import java.util.*;
import ModelLayer.Employee;
import ModelLayer.*;
import RepositoryLayer.IRepository;

public class EmployeeService {
    private IRepository<Employee> employeeRepository;

    public EmployeeService(IRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Map<Integer,Employee> getAllEmployees() {
        Map<Integer,Employee> allEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
                allEmployees.put(employeeRepository.getID(employee),employee);
        }
        return allEmployees;
    }

    public Map<Integer,Employee> getUnallocatedEmployees() {
        Map<Integer,Employee> unallocatedEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
            if (employee.getProjects().isEmpty()) {
                unallocatedEmployees.put(employeeRepository.getID(employee),employee);
            }
        }
        return unallocatedEmployees;
    }

    public void createEngineer(String lastName, String firstName, String role, float salary, String specialization) {
        Engineer newEngineer = new Engineer(lastName, firstName, role, salary, new ArrayList<>(), specialization);
        employeeRepository.add(newEngineer);
        System.out.println("Engineer created successfully: " + newEngineer.getFirstName() + " " + newEngineer.getLastName());
    }

    public void createWorker(String lastName, String firstName, String role, float salary, String experienceLevel) {
        Worker newWorker = new Worker(lastName, firstName, role, salary, new ArrayList<>(), experienceLevel);
        employeeRepository.add(newWorker);
        System.out.println("Worker created successfully: " + newWorker.getFirstName() + " " + newWorker.getLastName());
    }
}

