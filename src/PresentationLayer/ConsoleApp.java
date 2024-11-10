package PresentationLayer;
import ControllerLayer.*;
import ModelLayer.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private ProjectController projectController;
    private EmployeeController employeeController;
    private ContractController contractController;

    public ConsoleApp(ProjectController projectController, EmployeeController employeeController, ContractController contractController) {
        this.projectController = projectController;
        this.employeeController = employeeController;
        this.contractController = contractController;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Project\n2. Delete Project\n3. Show Unallocated Employees\n4. Create Contract\n5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //Add Project
                    break;
                case 2:
                    // Code to delete a project
                    break;
                case 3:
                    List<Employee> unallocatedEmployees = new ArrayList<>();
                    unallocatedEmployees= employeeController.getUnallocatedEmployees();
                    for(Employee em: unallocatedEmployees)
                        System.out.println(em.getLastName() + ' ' + em.getFirstName());
                    break;
                case 4:
                    // Code to create a contract
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

