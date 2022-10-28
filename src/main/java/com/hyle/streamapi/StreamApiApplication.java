package com.hyle.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamApiApplication {

    static List<Employee> employees = new ArrayList<>();
    static {
        employees.add(
                new Employee("Tanel", "Kruus", 5000.0, List.of("Project 1", "Project 2")
        ));

        employees.add(
                new Employee("John", "Doe", 6000.0, List.of("Project 1", "Project 3")
                ));

        employees.add(
                new Employee("Peter", "Don", 6500.0, List.of("Project 4", "Project 6")
                ));

        employees.add(
                new Employee("Jake", "Holland", 5500.0, List.of("Project 7", "Project 8")
                ));
    }

    public static void main(String[] args) {
        //SpringApplication.run(StreamApiApplication.class, args);



        System.out.println("### foreach ###");
        //foreach (Terminal operator)
        employees.stream()
                .forEach(employee -> System.out.println(employee));
        System.out.println("_______________________________________");

        System.out.println("### map and collect ###");
        //map - convert one object to another
        //collect
        List<Employee> increasedSalary = employees.stream()
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.10,
                        employee.getProjects()
                ))
                .collect(Collectors.toList());
        System.out.println(increasedSalary);
        System.out.println("_______________________________________");

        System.out.println("### filter ###");
        //filter
        List<Employee> filterEmployee = employees.stream()
                .filter(employee -> employee.getSalary() > 5000.0)
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary(),
                        employee.getProjects()
                ))
                .collect(Collectors.toList());
        System.out.println(filterEmployee);
        System.out.println("_______________________________________");

        System.out.println("### findFirst ###");
        //findFirst
        Employee FirstEmployee = employees.stream()
                .filter(employee -> employee.getSalary() > 5000.0)
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary(),
                        employee.getProjects()
                ))
                .findFirst()
                .orElse(null); // also possible to .orElseGet or .orElseThrow
        System.out.println(FirstEmployee);
        System.out.println("_______________________________________");

        System.out.println("### flatMap ###");
        //flatMap
        String projects = employees.stream()
                .map(employee -> employee.getProjects())
                .flatMap(strings -> strings.stream())
                .collect(Collectors.joining(", "));
        System.out.println(projects);
        System.out.println("_______________________________________");

        System.out.println("### short Circuit operations ###");
        //short Circuit operations
        List<Employee> shortCircuit = employees.stream()
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());
        System.out.println(shortCircuit);
        System.out.println("_______________________________________");

        System.out.println("### Finite Data ###");
        //Finite Data
        Stream.generate(Math::random)
                .limit(5)
                .forEach(value -> System.out.println(value));
        System.out.println("_______________________________________");

        System.out.println("### sorting ###");
        //sorting
        List<Employee> sortedEmployees = employees.stream()
                .sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
                .collect(Collectors.toList());
        System.out.println(sortedEmployees);
        System.out.println("_______________________________________");

        System.out.println("### MIN or MAX ###");
        //min or max - example with max
        Employee maxSalaryEmployee = employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);
        System.out.println(maxSalaryEmployee);
        System.out.println("_______________________________________");

        System.out.println("### reduce ###");
        //reduce
        Double totalSalary = employees.stream()
                .map(employee -> employee.getSalary())
                .reduce(0.0, Double::sum);
        System.out.println(totalSalary);
        System.out.println("_______________________________________");
    }



}
