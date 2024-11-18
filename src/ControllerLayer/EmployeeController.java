package ControllerLayer;
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

    public Map<Integer,Employee> getUnallocatedEmployees() {
        return employeeService.getUnallocatedEmployees();
    }
    public void createEngineer(String lastName, String firstName, String role, float salary, String specialization) {
        employeeService.createEngineer(lastName, firstName, role, salary, specialization);
    }

    public void createWorker(String lastName, String firstName, String role, float salary, String experienceLevel) {
        employeeService.createWorker(lastName, firstName, role, salary, experienceLevel);
    }

    public Map<Integer,Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public List<Worker> sortEmployeesByExperience() {
        return employeeService.sortEmployeesByExperience();
    }
}

