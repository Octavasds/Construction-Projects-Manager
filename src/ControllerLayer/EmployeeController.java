package ControllerLayer;
import ModelLayer.Employee;
import ServiceLayer.EmployeeService;

import java.util.List;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<Employee> getUnallocatedEmployees() {
        return employeeService.getUnallocatedEmployees();
    }
}

