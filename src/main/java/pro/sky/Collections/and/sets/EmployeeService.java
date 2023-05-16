package pro.sky.Collections.and.sets;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmployeeService {
    public static final List<Employee> employees = List.of(
            new Employee("Ольга", "Лукьянова", 10000.00, 5),
            new Employee("Бирмова", "Анна", 56_000.0, 3),
            new Employee("Нефёдова ", "Алена", 90_000.0, 22),
            new Employee("Бурилова ", "Юлия", 78_000.0, 26),
            new Employee("Гуцко", "Татьяна", 30_000.0, 22));


    private static void printEmployees() {
        employees.stream().filter(Objects::nonNull).forEach(System.out::println);
    }


    private static void printSum() {
        double sum = employees.stream().filter(Objects::nonNull).mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("Salary sum:" + sum);

    }


    private static void printEmployeeWithMinSalary() {
        Employee employeeWithMinSalary = employees.stream().filter(Objects::nonNull).min(Comparator.comparingDouble(Employee::getSalary)).orElse(null);
        System.out.println("employee with min salary:" + employeeWithMinSalary);
    }


    private static void printEmployeeWithMaxSalary() {
        Employee employeeWithMaxSalary = employees.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
        System.out.println("employee with max salary:" + employeeWithMaxSalary);
    }

    private static void printSalaryAverage() {
        double avg = employees.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.println("Average salary " + avg);
    }

    private static void printNames() {
        employees.stream().filter(Objects::nonNull)
                .forEach(employee -> System.out.println("Имя сотрудника: "
                        + employee.getFirstName() + "\nФамилия сотрудника: " + employee.getLastName()));
    }


    private final int employeesCount = 2;

    private final Map<Integer, Employee> employeesByHashCode = new HashMap<>();

    public Employee add(String firstName, String lastName, double salary, int department) {

        if (employeesByHashCode.size() == employeesCount) {
            throw new EmployeeStorageIsFullException(" Хранилищетрудников переполнен");
        }

        Employee employee = new Employee(firstName, lastName, salary, department );

        int employeeHashCode = employee.hashCode();

        if (employeesByHashCode.containsKey(employeeHashCode)) {
            throw new EmployeeAlreadyAddedException("В хранилище уже есть такой сотрудник");
        }
        employeesByHashCode.put(employeeHashCode, employee);
                return employee;

    }

    public Employee find(String firstName, String lastName) {
        int employeeHashCode = getEmployeeKey(firstName, lastName);
        return employeesByHashCode.values().stream()
                .filter(e -> e.hashCode() == employeeHashCode)
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public Employee remove(String firstName, String lastName) {
        int employeeHashCode = getEmployeeKey(firstName, lastName);
        return employeesByHashCode.entrySet().stream()
                .filter(entry -> entry.getKey() == employeeHashCode)
                .map(Map.Entry::getValue)
                .findFirst()
                .map(employee -> employeesByHashCode.remove(employeeHashCode))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));

    }

    public List<Employee> getAll() {
        return employeesByHashCode.values().stream().toList();
    }

    private int getEmployeeKey(String firstName, String lastName) {
        return Objects.hash(firstName, lastName);
    }
}


