package ServiceLayer;
import java.util.*;
import ModelLayer.Employee;
import ModelLayer.*;
import RepositoryLayer.IRepository;

public class EmployeeService {
    private IRepository<Employee> employeeRepository;

    /**
     * Description: Constructor
     * @param employeeRepository
     */
    public EmployeeService(IRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Description: Gets all existent employees
     * @return Map with employees and their IDs
     */
    public Map<Integer,Employee> getAllEmployees() {
        Map<Integer,Employee> allEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
                allEmployees.put(employeeRepository.getID(employee),employee);
        }
        return allEmployees;
    }

    /**
     * Description: Gets all employees that are not allocated to a project
     * @return Map with employees and their IDs
     */
    public Map<Integer,Employee> getUnallocatedEmployees() {
        Map<Integer,Employee> unallocatedEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
            if (employee.getProjects().isEmpty()) {
                unallocatedEmployees.put(employeeRepository.getID(employee),employee);
            }
        }
        return unallocatedEmployees;
    }

    /**
     * Description: Creates a new engineer
     * @param lastName
     * @param firstName
     * @param role
     * @param salary
     * @param specialization
     */
    public void createEngineer(String lastName, String firstName, String role, float salary, String specialization) {
        Engineer newEngineer = new Engineer(lastName, firstName, role, salary, new ArrayList<>(), specialization);
        employeeRepository.add(newEngineer);
        System.out.println("Engineer created successfully: " + newEngineer.getFirstName() + " " + newEngineer.getLastName());
    }

    /**
     * Description: Creates a new worker
     * @param lastName
     * @param firstName
     * @param role
     * @param salary
     * @param experienceLevel
     */
    public void createWorker(String lastName, String firstName, String role, float salary, String experienceLevel) {
        Worker newWorker = new Worker(lastName, firstName, role, salary, new ArrayList<>(), experienceLevel);
        employeeRepository.add(newWorker);
        System.out.println("Worker created successfully: " + newWorker.getFirstName() + " " + newWorker.getLastName());
    }
    /**
     *
     * @return
    //Sorts the workes by the experience level
    */
    public List<Worker> sortEmployeesByExperience() {
        List<Worker> workers = new ArrayList<>();
        for (Employee employee : employeeRepository.getAll()) {
            if (employee instanceof Worker) {
                workers.add((Worker) employee);
            }
        }
        workers.sort((w1, w2) -> w2.getExperienceLevel().compareTo(w1.getExperienceLevel()));
        return workers;
    }

}




