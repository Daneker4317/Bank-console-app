package controller;

import util.bank;
import database.Connect;
import model.Client;
import model.Gender;
import util.Builder;
import util.SingletonScanner;

import java.sql.*;
import java.util.function.Consumer;

/**
  @author Kaliaskaruly Daneker
* */
public class BankController implements bank, SingletonScanner {

    private final int limitForTransaction = 1000000; // fixed limit for transactions
    private final int limitForWithdrawal = 300000; // fixed limit for cashing

    private final Connect connection;

    public BankController(Connect connection) {
        this.connection = connection;
    }


    public int findClient() throws Exception {

        System.out.println("enter user name");
        String name = in.nextLine();


        System.out.println("enter login to entrance");
        String login = in.nextLine();

        Connection conn = connection.getConnection();
        Statement statement = conn.createStatement();

        int id;

        ResultSet resultSet = statement.executeQuery("select * from client where  login ='" + login + "'");
        if (!resultSet.next() || !resultSet.getString("name").equals(name)) {
            System.out.println("Invalid name or login");
            id = -1;
        } else {
            id =  resultSet.getInt("id");
        }


        resultSet.close();
        statement.close();
        conn.close();

        return id;

    }

    @Override
    public void sendMoney(int userId) throws Exception {

        Connection conn = connection.getConnection();
        Statement statement = conn.createStatement();

        System.out.println("Enter phone to send money ");
        String phone = in.nextLine();

        ResultSet checkNum = statement.executeQuery("select * from client where phone='" + phone + "'");

        if (!checkNum.next()) {
            System.out.println("incorrect phone");
            return;
        }

        int getterId = checkNum.getInt("id");
        int getterSum = checkNum.getInt("currentaccount");

        System.out.println("Getter user name: " + checkNum.getString("name") + "?" + "\n" + "enter 1 for confirm");
        String confirmUserName = in.nextLine();
        if (!confirmUserName.equals("1")) {
            System.out.println("transaction cancelled");
            return;
        }

        System.out.println("You can send not more 1 million every 5 minutes");
        System.out.println("Enter sum of transaction");
        int moneyToSend = in.nextInt();

        System.out.println("Enter 1 for confirm transaction or any symbol to cancel transaction");
        String confirmTransaction = in.next();

        if (!confirmTransaction.equals("1")) {
            System.out.println("Transaction cancelled");
            return;
        }

        ResultSet resultSetForGetCurrentAccount = statement.executeQuery("select  * from client where id = " + userId);

        int balance = 0, sum = 0;
        if (resultSetForGetCurrentAccount.next()) {
            sum = resultSetForGetCurrentAccount.getInt("summa");
            if (sum > limitForTransaction) {
                System.out.println("Ohhh no");
                System.out.println("You have limitForTransaction only " + limitForTransaction + " tenge for transactions");
                return;
            }
            balance = resultSetForGetCurrentAccount.getInt("currentaccount");
            if (balance < moneyToSend) {
                System.out.println("Lol not enough money");
                return;
            }
        }

        resultSetForGetCurrentAccount.close();

        PreparedStatement preparedStatementUpdateCurrent = conn.prepareStatement("update client set currentaccount = ? where id= " + userId);
        preparedStatementUpdateCurrent.setInt(1, balance - moneyToSend);
        preparedStatementUpdateCurrent.executeUpdate();
        preparedStatementUpdateCurrent.close();

        PreparedStatement preparedStatementUpdateSum = conn.prepareStatement("update client set summa = ? where id =" + userId);
        preparedStatementUpdateSum.setInt(1, sum + moneyToSend);
        preparedStatementUpdateSum.executeUpdate();
        preparedStatementUpdateSum.close();

        PreparedStatement preparedStatementUpdateToMoney = conn.prepareStatement("update client set currentaccount = ?  where id=" + getterId);
        preparedStatementUpdateToMoney.setInt(1, moneyToSend + getterSum);
        preparedStatementUpdateToMoney.executeUpdate();
        preparedStatementUpdateToMoney.close();


        conn.close();

        System.out.println("Transaction accepted");
        System.out.println("Balance after transaction: " + (balance - moneyToSend));
    }


    @Override
    public void withdrawalMoney(int userId) throws Exception {

        Connection conn = connection.getConnection();

        System.out.println("enter sum to withdrawal:");
        int moneyToWithdrawal = in.nextInt();

        PreparedStatement preparedStatementClient = conn.prepareStatement("select * from client where id=" + userId);
        ResultSet resultSetClient = preparedStatementClient.executeQuery();

        int withdrawal = 0;
        if (resultSetClient.next()) {
            withdrawal = resultSetClient.getInt("withdrawal");
            if ((withdrawal + moneyToWithdrawal) > limitForWithdrawal) {
                System.out.println("Ohhh no");
                System.out.println("You have limit for withdrawal only " + limitForWithdrawal + " tenge ");
                return;
            }
        }
        int balance = resultSetClient.getInt("currentaccount");
        if (balance < moneyToWithdrawal) {
            System.out.println("Lol not enough money");
            return;
        }

        resultSetClient.close();
        preparedStatementClient.close();

        PreparedStatement preparedStatementUpdateCurrent = conn.prepareStatement("update client set currentaccount = ? where id = " + userId);
        preparedStatementUpdateCurrent.setInt(1, balance - moneyToWithdrawal);
        preparedStatementUpdateCurrent.executeUpdate();
        preparedStatementUpdateCurrent.close();

        PreparedStatement preparedStatementUpdateWithdrawal = conn.prepareStatement("update client set withdrawal = ? where id =" + userId);
        preparedStatementUpdateWithdrawal.setInt(1, moneyToWithdrawal + withdrawal);
        preparedStatementUpdateWithdrawal.executeUpdate();
        preparedStatementUpdateWithdrawal.close();


        conn.close();

        System.out.println("Сash withdrawal completed successfully");
        System.out.println("Balance after withdrawal : " + (balance - moneyToWithdrawal));


    }


    @Override
    public void sendMoneyToDeposit(int userId) throws Exception {

        Connection conn = connection.getConnection();

        ResultSet resultSet = certainClient(userId);
        int curr = 0;
        int save = 0;
        if(resultSet.next()){
            curr = resultSet.getInt("currentaccount");
            save = resultSet.getInt("savingaccount");
        }

        System.out.println("Enter sum for transaction to deposit account");
        int sum = in.nextInt();

        if (curr < sum) {
            System.out.println("Not enough money");
            return;
        }

        System.out.println("Enter 1 for confirm transaction");
        String confirm = in.next();

        if (!confirm.equals("1")) {
            System.out.println("Transaction canceled");
            return;
        }


        PreparedStatement upDeposit = conn.prepareStatement("update client set savingaccount = ? where id =" + userId);
        upDeposit.setInt(1, (save+ sum));
        upDeposit.executeUpdate();


        PreparedStatement upCurr = conn.prepareStatement("update client set currentaccount = ? where id =" + userId);
        upCurr.setInt(1,(curr- sum));
        upCurr.executeUpdate();


        System.out.println("Successfully transaction to deposit : " + sum);
    }


    public ResultSet certainClient(int id) throws Exception {
        Connection conn = connection.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from client where id=" + id);

        statement.close();
        conn.close();

        return resultSet;
    }

    public void printToConsole() throws Exception {
        Connection conn = connection.getConnection();
        Statement statement = conn.createStatement();

        print(statement.executeQuery("select * from client"));

        statement.close();
        conn.close();
    }

    private void print(ResultSet result) throws SQLException {
        Builder clientBuilder = new Builder();
        Consumer<Client> rabbitMQ = Client::info;
        while (result.next()) {
            Client client = new Client(clientBuilder.
                    setName(result.getString("name")).
                    setSurname(result.getString("surname")).
                    setAge(result.getInt("age")).
                    setGender(String.valueOf(result.getString("gender")).equals("Male")? Gender.Male:Gender.Female).
                    setCurrentAccount(result.getInt("currentaccount")).
                    setSavingAccount(result.getInt("savingaccount")).build());
            rabbitMQ.accept(client);
        }
    }

    public void calculateProfit() {
        System.out.println("Enter the amount of the monthly installment");
        int sum = in.nextInt();
        System.out.println("Enter quantity");
        int quantity = in.nextInt();
        System.out.println("expected profit after " + quantity + " nearly"
                + 1.11 * sum * quantity + "--" + sum * 1.18 * quantity);
    }

}


