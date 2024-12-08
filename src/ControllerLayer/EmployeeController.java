package ControllerLayer;
import Exceptions.ValidationException;
import ModelLayer.Employee;
import ModelLayer.Worker;
import ServiceLayer.EmployeeService;

import java.util.List;
import java.util.Map;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Map<Integer, Employee> getUnallocatedEmployees() {
        return employeeService.getUnallocatedEmployees();
    }

    public void createEngineer(String lastName, String firstName, String role, float salary, String specialization) {
        // Validate input parameters
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ValidationException("Last name cannot be null or empty.");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException("First name cannot be null or empty.");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new ValidationException("Role cannot be null or empty.");
        }
        if (salary <= 0) {
            throw new ValidationException("Salary must be greater than zero.");
        }
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new ValidationException("Specialization cannot be null or empty.");
        }

        employeeService.createEngineer(lastName, firstName, role, salary, specialization);
    }

    public void createWorker(String lastName, String firstName, String role, float salary, String experienceLevel) {
        // Validate input parameters
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ValidationException("Last name cannot be null or empty.");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException("First name cannot be null or empty.");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new ValidationException("Role cannot be null or empty.");
        }
        if (salary <= 0) {
            throw new ValidationException("Salary must be greater than zero.");
        }
        if (experienceLevel == null || experienceLevel.trim().isEmpty()) {
            throw new ValidationException("Experience level cannot be null or empty.");
        }

        employeeService.createWorker(lastName, firstName, role, salary, experienceLevel);
    }

    public Map<Integer, Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public List<Worker> sortEmployeesByExperience() {
        return employeeService.sortEmployeesByExperience();
    }
}