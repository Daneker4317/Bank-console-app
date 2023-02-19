package model;

import database.Connect;
import util.Builder;

public class Employee extends Human {
    private double salary;
    private final Connect connect;

    public Employee(Builder builder , Connect connect) { // implementing builder design pattern
        super(builder.getName(),builder.getSurName(), builder.getAge(),builder.getGender());
        this.salary = builder.getSalary();
        this.connect = connect;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
