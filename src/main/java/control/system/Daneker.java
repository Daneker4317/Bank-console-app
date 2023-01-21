package control.system;

import bank.system.controlMoney;
import bank.system.registerEmployee;
import user.Employee;
import java.sql.*;
import java.util.Scanner;
import java.util.function.Consumer;

public class Daneker  {
     private Scanner in = new Scanner (System.in);
    private controlMoney cm = new controlMoney();
    private registerEmployee registerEmployee = new registerEmployee();
    private Consumer<Object> out = i -> System.out.println(i);

    public Daneker() throws SQLException {}

    public void welcome() {
        out.accept("Welcome to bank system");
        out.accept("Firstly you have to add employee");
        out.accept("[1] - > to add client");
        out.accept("[2] - > manipulate with money");
        out.accept("[3] - > close app");
    }
    public void closeApp(){
        out.accept("App closing");
        System.exit(0);
    }

    private void calculateProfit(){
        out.accept("Enter the amount of the monthly installment");
        int sum = in.nextInt();
        out.accept("Enter quantity");
        int quantity = in.nextInt();
        out.accept("expected profit after " + quantity + " about"
                + 1.11 * sum  *quantity + "--" + sum * 1.18 * quantity);
    }
    public void sendMoney() throws Exception {
        cm.sendMoney();
    }

    public void cashWithdrawal() throws SQLException {
        cm.withdrawalMoney();
    }
    public void sendMoneyToDeposit() throws SQLException {
        cm.sendMoneyToDeposit();
    }
    public void manipulateWithMoney() throws Exception {
        manipulateWithMoneyInfo();
        while(true){
            out.accept("enter command number");
            int n = in.nextInt();
            switch (n){
                case 1 -> sendMoney();
                case 2 -> cashWithdrawal();
                case 3 -> sendMoneyToDeposit();
                case 4 -> calculateProfit();
                case 5 -> {welcome();return;}
                default -> out.accept("invalid number");
            }
        }
    }

    private void manipulateWithMoneyInfo(){
        out.accept("[1] -> send money be nomer");
        out.accept("[2] -> cash withdrawal");
        out.accept("[3] -> send money to deposit");
        out.accept("[4] -> calculate profit");
        out.accept("[5] -> main page");
    }

}
