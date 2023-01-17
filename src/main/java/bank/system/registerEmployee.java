package bank.system;

import user.Employee;
import user.Gender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class registerEmployee {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwerty";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    Scanner in = new Scanner(System.in);
    public void add() throws Exception {


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

        Connection connection = DriverManager.getConnection(DB_URL , DB_USERNAME , DB_PASSWORD);
        Statement statement = connection.createStatement();


        String sql = "insert into employee (name, surname, age, gender, salary) values (? , ? , ? ,? ,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surName);
        preparedStatement.setInt(3,age);
        preparedStatement.setString(4,gender);
        preparedStatement.setInt(5,salary);



        preparedStatement.executeUpdate();


        System.out.println("employee added successfully");

    }
}
