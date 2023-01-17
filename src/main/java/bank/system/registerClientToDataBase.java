package bank.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class registerClientToDataBase {
        private static final String DB_USERNAME = "postgres";
        private static final String DB_PASSWORD = "qwerty";
        private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    Scanner in = new Scanner(System.in);



    public void addClientToDataBase() throws  Exception{


        Connection connection = DriverManager.getConnection(DB_URL , DB_USERNAME , DB_PASSWORD);
        System.out.println("Enter name");
        String name = in.nextLine();
        System.out.println("Enter surname");
        String surName = in.nextLine();
        System.out.println("Enter age");
        int age = in.nextInt();
        System.out.println("Choose gender:" + "\n" + "[1] -> MALE" +
                "\n" + "[2] -> FEMALE");
        int gen = in.nextInt();
        String gender = (gen==1)?"Male":"Female";
        System.out.println("Enter login (be carefully you will use it for entrance)");
        String login = in.nextLine();
        login = in.nextLine();
        System.out.println("Enter sum in currentAccount");
        int currentAccount = in.nextInt();
        System.out.println("Enter sum an initial fee"); // первоначальный взнос
        System.out.println("This wallet will be used for earn deposit with 15% for one year like at Kaspi");
        int savingAccount = in.nextInt();
        System.out.println("profit in a year about: " + savingAccount * 1.11 + "--" + savingAccount * 1.16);// дефолт кейс




        String sql = "insert into client(name, surName, age, gender, login, currentAccount, savingAccount) values (? , ? , ? , ? , ? , ? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surName);
        preparedStatement.setInt(3,age);
        preparedStatement.setString(4,gender);
        preparedStatement.setString(5,login);
        preparedStatement.setInt(6,currentAccount);
        preparedStatement.setInt(7,savingAccount);


        preparedStatement.executeUpdate();

        System.out.println("Client added successfully");

    }
}
