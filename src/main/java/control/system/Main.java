package control.system;


import user.Employee;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {


        Daneker daneker = new Daneker();
        Scanner in = new Scanner(System.in);
        daneker.welcome();
        daneker.registerEmployee.add();


        while (true){
            System.err.println("Enter key number");
            int number = in.nextInt();
            switch (number){
                case 1 -> Employee.registerClient();
                case 2 -> daneker.cm.sendMoney();
                case 3 -> daneker.cm.withdrawalMoney();
                case 4 ->  daneker.cm.printToConsole();
                case 5 -> daneker.calculateProfit();
                case 6 -> daneker.closeApp();
                default -> System.err.println("INCORRECT NUMBER");
            }
        }
    }
}
