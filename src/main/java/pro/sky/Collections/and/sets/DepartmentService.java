package pro.sky.Collections.and.sets;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static pro.sky.Collections.and.sets.EmployeeService.employees;

@Service
public class DepartmentService {
    public Employee getEmployeeWithMaxSalaryByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartment()==departmentId)
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public Employee getEmployeeWithMinSalaryByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartment()==departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> getEmployeeByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() == departmentId).collect(Collectors.toList());
    }
    public Map<Integer,List<Employee>> getAllEmployeeByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

}


