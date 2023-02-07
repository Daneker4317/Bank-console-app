package control.system;

import user.Employee;

import java.sql.SQLException;
import java.util.Scanner;

public class Application implements App{
    @Override
    public void start() throws Exception {
        Daneker daneker = new Daneker();
        Scanner in = new Scanner(System.in);
        daneker.welcome(true);
        while (true){
            System.err.println("Enter key number");
            int number = in.nextInt();
            switch (number){
                case 1 -> Employee.registerClient();
                case 2 -> daneker.manipulateWithMoney();
                case 3 -> daneker.printAllClients();
                case 4 -> exit();
                default -> System.err.println("INCORRECT ENTERED NUMBER");
            }
        }
    }
}
