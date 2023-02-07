package control.system;

import DAO.registerEmployee;
import bank.system.controlMoney;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Consumer;

public class Daneker {
    private final Scanner in = new Scanner(System.in);
    private final controlMoney cm = new controlMoney();
    private final registerEmployee registerEmployee = new registerEmployee();
    private final Consumer<Object> out = System.out::println;

    public Daneker() throws SQLException {
    }

    public void welcome() throws Exception {
        out.accept("Welcome to bank system");
        out.accept("Firstly you have to add employee");
        out.accept("[1] - > to add client");
        out.accept("[2] - > manipulate with money");
        out.accept("[3] - > print all clients to console");
        out.accept("[4] - > close app");
        registerEmployee.add();
    }

    public void manipulateWithMoney() throws Exception {
        System.out.println("firstly you have to entered to system with name and login");
        int userId = cm.findClient();
        if (userId < 0) {
            return;
        }
        manipulateWithMoneyInfo();
        while (true) {
            out.accept("enter command number");
            int n = in.nextInt();
            switch (n) {
                case 1 -> cm.sendMoney(userId);
                case 2 -> cm.withdrawalMoney(userId);
                case 3 -> cm.sendMoneyToDeposit(userId);
                case 4 -> calculateProfit();
                case 5 -> {
                    out.accept("u r in main page");
                    return;
                }
                default -> out.accept("invalid number");
            }
        }
    }

    public void printAllClients() throws SQLException {
        cm.printToConsole();
    }

    private void calculateProfit() {
        out.accept("Enter the amount of the monthly installment");
        int sum = in.nextInt();
        out.accept("Enter quantity");
        int quantity = in.nextInt();
        out.accept("expected profit after " + quantity + " about"
                + 1.11 * sum * quantity + "--" + sum * 1.18 * quantity);
    }

    private void manipulateWithMoneyInfo() {
        out.accept("[1] -> send money be nomer");
        out.accept("[2] -> cash withdrawal");
        out.accept("[3] -> send money to deposit");
        out.accept("[4] -> calculate profit");
        out.accept("[5] -> main page");
    }

    public void closeApp() {
        out.accept("App closing");
        System.exit(0);
    }

}
