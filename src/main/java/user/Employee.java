package user;

import bank.system.registerClientToDataBase;

public class Employee extends Human {
    private double salary;

    public Employee(String name, String surName, int age, Gender gender, double salary) {
        super(name, surName, age, gender);
        this.salary = salary;
    }

    public static void registerClient() throws Exception {
        new registerClientToDataBase().addClientToDataBase();
    }

}
