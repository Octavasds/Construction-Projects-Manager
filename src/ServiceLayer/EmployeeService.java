package ServiceLayer;
import java.util.*;

import Exceptions.BusinessLogicException;
import Exceptions.EntityNotFoundException;
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
     * @throws EntityNotFoundException if no employees exist
     */
    public Map<Integer, Employee> getAllEmployees() {
        Map<Integer, Employee> allEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
            allEmployees.put(employeeRepository.getID(employee), employee);
        }

        if (allEmployees.isEmpty()) {
            throw new EntityNotFoundException("No employees found.");
        }

        return allEmployees;
    }

    /**
     * Description: Gets all employees that are not allocated to a project
     * @return Map with employees and their IDs
     * @throws EntityNotFoundException if no unallocated employees exist
     */
    public Map<Integer, Employee> getUnallocatedEmployees() {
        Map<Integer, Employee> unallocatedEmployees = new HashMap<>();

        for (Employee employee : employeeRepository.getAll()) {
            if (employee.getProjects().isEmpty()) {
                unallocatedEmployees.put(employeeRepository.getID(employee), employee);
            }
        }

        if (unallocatedEmployees.isEmpty()) {
            throw new EntityNotFoundException("No unallocated employees found.");
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
    }

    /**
     * Sorts the workers by their experience level
     * @return List of workers sorted by experience level
     * @throws BusinessLogicException if no workers are available to sort
     */
    public List<Worker> sortEmployeesByExperience() {
        List<Worker> workers = new ArrayList<>();
        for (Employee employee : employeeRepository.getAll()) {
            if (employee instanceof Worker) {
                workers.add((Worker) employee);
            }
        }

        if (workers.isEmpty()) {
            throw new BusinessLogicException("No workers available to sort by experience level.");
        }

        workers.sort((w1, w2) -> w2.getExperienceLevel().compareTo(w1.getExperienceLevel()));
        return workers;
    }
}