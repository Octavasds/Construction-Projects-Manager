package Parser;

import ModelLayer.Employee;
import ModelLayer.Worker;

public class EmployeeParser implements EntityParser<Employee> {

    @Override
    public String toString(Employee obj) {
        return obj.toString();
    }

    @Override
    public Worker fromString(String data) {
        return Worker.fromString(data);
    }
}
