package ServiceLayer;
import java.util.*;
import ModelLayer.Employee;
import RepositoryLayer.IRepository;

public class EmployeeService {
    private IRepository<Employee> employeeRepository;

    public EmployeeService(IRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getUnallocatedEmployees() {
        List<Employee> unallocatedEmployees = new ArrayList<>();

        for (Employee employee : employeeRepository.getAll()) {
            if (employee.getProjects().isEmpty()) {
                unallocatedEmployees.add(employee);
            }
        }
        return unallocatedEmployees;
    }

}

