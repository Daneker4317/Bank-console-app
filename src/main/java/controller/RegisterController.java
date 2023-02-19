package controller;


import database.Connect;
import model.Employee;
import model.Gender;
import util.Builder;
import util.SingletonScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 @author Kaliaskaruly Daneker
  * */

public class RegisterController implements SingletonScanner { // - Single Responsibility Principle -> class which has only one function -> register entities to database

    private final Connect connect;

    public RegisterController(Connect connect) {
        this.connect = connect;
    }

    public void registerClient(Employee employee) throws  Exception{

        Connection connection = connect.getConnection();

        System.out.println("Enter name");
        String name = in.next();

        System.out.println("Enter surname");
        String surName = in.next();

        System.out.println("Enter age");
        int age = in.nextInt();

        System.out.println("Choose gender:" + "\n" + "[1] -> MALE" +
                "\n" + "[2] -> FEMALE");
        int gen = in.nextInt();
        String gender = (gen==1)?"Male":"Female";


        System.out.println("Enter login (be carefully you will use it for entrance)");
        String login = in.next();

        System.out.println("enter clients phone");
        String phone = in.next();

        System.out.println("Enter sum in currentAccount");
        int currentAccount = in.nextInt();


        System.out.println("Enter sum an initial fee");
        System.out.println("This wallet will be used for earn deposit with 15% for one year like at Kaspi");
        int savingAccount = in.nextInt();
        System.out.println("profit in a year about: " + savingAccount * 1.13 + "--" + savingAccount * 1.15);


        String sql = "insert into client(name, surName, age, gender, login, currentAccount, savingAccount , phone) values (? , ? , ? , ? , ? , ? , ? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surName);
        preparedStatement.setInt(3,age);
        preparedStatement.setString(4,gender);
        preparedStatement.setString(5,login);
        preparedStatement.setInt(6,currentAccount);
        preparedStatement.setInt(7,savingAccount);
        preparedStatement.setString(8,phone);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        System.out.println("Client added successfully");

    }
    public Employee registerEmployee() throws Exception {


        System.out.println("Enter employee's name:");
        String name = in.nextLine();


        System.out.println("Enter employee's surname:");
        String surName = in.nextLine();


        System.out.println("Enter employee's age:");
        int age = in.nextInt();


        System.out.println("Choose gender:" + "\n" + "[1] -> MALE" +
                "\n" + "[2] -> FEMALE");
        int gen = in.nextInt();
        String gender = (gen==1)?"Male":"Female";

        System.out.println("Enter employee's salary(be realist [0 - 100000])");
        int salary = in.nextInt();

        Connection connection =connect.getConnection();


        String sql = "insert into employee (name, surname, age, gender, salary) values (? , ? , ? ,? ,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surName);
        preparedStatement.setInt(3, age);
        preparedStatement.setString(4, gender);
        preparedStatement.setInt(5, salary);


        preparedStatement.executeUpdate();
        connection.close();

        System.out.println("employee added successfully");

        return new Employee(new Builder().
                setName(name).
                setAge(age).
                setGender(gen == 1 ? Gender.Male:Gender.Female).
                setSalary(salary).
                setSurname(surName).build() , connect);
    }
}

