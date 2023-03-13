package pro.sky.Collections.and.sets;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmployeeService {

    private final int employeesCount = 2;

    private final Map<Integer,Employee> employeesByHashCode = new HashMap<>();

    public Employee add (String firstName, String lastName) {

        if (employeesByHashCode.size() == employeesCount) {
            throw new EmployeeStorageIsFullException("Хранилище сотрудников переполнен");
        }

        Employee employee = new Employee(firstName, lastName);
        int employeeHashCode = employee.hashCode();

        if (employeesByHashCode.containsKey(employeeHashCode)) {
            throw new EmployeeAlreadyAddedException("В хранилище уже есть такой сотрудник");
        }
        employeesByHashCode.put(employeeHashCode, employee);

        return employee;

        }

    public Employee find(String firstName, String lastName){
        int employeeHashCode = getEmployeeKey(firstName, lastName);
        Employee employee = employeesByHashCode.get(employeeHashCode);

        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }

        return employee;
    }

    public Employee remove(String firstName, String lastName) {
        int employeeHashcode = getEmployeeKey(firstName, lastName);
        Employee employee = employeesByHashCode.remove(employeeHashcode);

        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }

        return employee;
    }

    public List<Employee> getAll() {
        return employeesByHashCode.values().stream().toList() ;
    }

    private int getEmployeeKey(String firstName, String lastName) {
        return Objects.hash(firstName, lastName);
    }



}
