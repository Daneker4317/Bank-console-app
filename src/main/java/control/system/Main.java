package control.system;
import user.Employee;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Daneker daneker = new Daneker();
        Scanner in = new Scanner(System.in);
        daneker.welcome();
        while (true){
            System.err.println("Enter key number");
            int number = in.nextInt();
            switch (number){
                 case 1 -> Employee.registerClient();
                 case 2 -> daneker.manipulateWithMoney();
                 case 3 -> daneker.printAllClients();
                 case 4 -> daneker.closeApp();
                 default -> System.err.println("INCORRECT NUMBER");
            }
        }
    }
}
