package application;

import controller.BankController;
import controller.RegisterController;
import database.Connect;
import model.Employee;
import util.App;
import util.SingletonScanner;

import java.util.function.Consumer;

public class BankApplication implements App, SingletonScanner {

    private final Connect connect;
    private final RegisterController registerController;
    private final BankController bankController;

    public BankApplication(Connect connect) {
        this.connect = connect;
        this.bankController = new BankController(connect);
        this.registerController = new RegisterController(connect);
    }

    @Override
    public void start() throws Exception {
        Employee employee = welcome();
        menu();
        while (true) {
            out.accept("Enter key number");
            int number = in.nextInt();
            switch (number) {
                case 1 -> registerController.registerClient(employee);
                case 2 -> manipulateWithMoney();
                case 3 -> bankController.printToConsole();
                case 4 -> exit();
                default -> out.accept("INCORRECT ENTERED NUMBER");
            }
        }
    }

    public void manipulateWithMoney() throws Exception {

        System.out.println("firstly you have to entered to system with name and login");
        int userId = bankController.findClient();
        if (userId < 0) {
            return;
        }
        manipulateWithMoneyInfo();
        while (true) {
            out.accept("enter command number");
            int n = in.nextInt();
            switch (n) {
                case 1 -> bankController.sendMoney(userId);
                case 2 -> bankController.withdrawalMoney(userId);
                case 3 -> bankController.sendMoneyToDeposit(userId);
                case 4 -> bankController.calculateProfit();
                case 5 -> {out.accept("u r in main page");return;}
                case 6 -> exit();
                default -> out.accept("invalid number");
            }
        }
    }


    public Employee welcome() throws Exception {
        out.accept("Welcome to bank system");
        out.accept("Firstly you have to add employee");
        return registerController.registerEmployee();
    }

    private void menu() {
        out.accept("[1] - > to add client");
        out.accept("[2] - > manipulate with money");
        out.accept("[3] - > print all clients to console");
        out.accept("[4] - > close app");
    }

    private void manipulateWithMoneyInfo() {
        out.accept("[1] -> send money be nomer");
        out.accept("[2] -> cash withdrawal");
        out.accept("[3] -> send money to deposit");
        out.accept("[4] -> calculate profit");
        out.accept("[5] -> main page");
        out.accept("[6] -> close app");
    }

    private final Consumer<Object> out = System.out::println;
}
