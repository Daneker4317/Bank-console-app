package control.system;

import bank.system.controlMoney;
import bank.system.registerEmployee;
import user.Employee;
import java.sql.*;
import java.util.Scanner;

public class Daneker {
    Scanner in = new Scanner (System.in);
    controlMoney cm = new controlMoney();
    registerEmployee registerEmployee = new registerEmployee();

    public Daneker() throws SQLException {}

    public void welcome() {
        System.out.println("Welcome to bank system");
        System.out.println("Firstly you have to add employee");
        System.out.println("[1] - > to add client");
        System.out.println("[2] - > send money by nomer");
        System.out.println("[3] - > cash withdrawal");
        System.out.println("[4] - > print full info about clients");
        System.out.println("[5] - > calculate profit");
        System.out.println("[6] - > close app");
    }
    public void closeApp(){
        System.out.println("App closing");
        System.exit(0);
    }

    public void calculateProfit(){
        System.out.println("Enter the amount of the monthly installment");
        int sum = in.nextInt();
        System.out.println("Enter quantity");
        int quantity = in.nextInt();
        System.out.println("expected profit after " + quantity + " about"
                + 1.11 * sum  *quantity + "--" + sum * 1.18 * quantity);
    }

}
