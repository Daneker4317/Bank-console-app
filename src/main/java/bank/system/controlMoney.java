package bank.system;

import java.sql.*;
import java.util.Scanner;

public class controlMoney implements bank {

    private final int limitForTransaction = 1000000; // лимит переводов
    private final int limitForWithdrawal = 300000; // лимит снятие наличных

    Scanner in = new Scanner(System.in);
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "qwerty");
    Statement statement = connection.createStatement();

    public controlMoney() throws SQLException {
    }


    public int findClient() throws SQLException {

        System.out.println("enter user name");
        String name = in.nextLine();


        System.out.println("enter login to entrance");
        String login = in.nextLine();


        ResultSet resultSet = statement.executeQuery("select * from client where  login ='" + login + "'");
        if (!resultSet.next() || !resultSet.getString("name").equals(name)) {
            System.out.println("Invalid name or login");
            return -1;
        } else {
            return resultSet.getInt("id");
        }

    }

    @Override
    public void sendMoney(int userId) throws Exception {

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
        String con = in.nextLine();
        if (!con.equals("1")) {
            System.out.println("transaction cancelled");
            return;
        }

        System.out.println("You can send not more 1 million every 5 minutes");
        System.out.println("Enter sum of transaction");
        int moneyToSend = in.nextInt();

        System.out.println("Enter 1 for confirm transaction or any symbol to cancel transaction");
        String confirm = in.next();

        if (!confirm.equals("1")) {
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

        // --------------------------->   обновление счета   <-----------------------------
        PreparedStatement preparedStatementUpdateCurrent = connection.prepareStatement("update client set currentaccount = ? where id= " + userId);
        preparedStatementUpdateCurrent.setInt(1, balance - moneyToSend);
        preparedStatementUpdateCurrent.executeUpdate();

        // ---------------------------> обновление счетчика  <------------------------------
        PreparedStatement preparedStatementUpdateSum = connection.prepareStatement("update client set summa = ? where id =" + userId);
        preparedStatementUpdateSum.setInt(1, sum + moneyToSend);
        preparedStatementUpdateSum.executeUpdate();

        //----------------------------> обновление счета получачтеля <----------------------
        PreparedStatement preparedStatementUpdateToMoney = connection.prepareStatement("update client set currentaccount = ?  where id=" + getterId);
        preparedStatementUpdateToMoney.setInt(1, moneyToSend + getterSum);
        preparedStatementUpdateToMoney.executeUpdate();

        System.out.println("Transaction accepted");
        System.out.println("Balance after transaction: " + (balance - moneyToSend));
    }


    @Override
    public void withdrawalMoney(int userId) throws SQLException {

        System.out.println("enter sum to withdrawal:");
        int moneyToWithdrawal = in.nextInt();

        PreparedStatement preparedStatementClient = connection.prepareStatement("select * from client where id=" + userId);
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
        // --------------------------> обновление счета после снятие денег <------------------------------
        PreparedStatement preparedStatementUpdateCurrent = connection.prepareStatement("update client set currentaccount = ? where id = " + userId);
        preparedStatementUpdateCurrent.setInt(1, balance - moneyToWithdrawal);
        preparedStatementUpdateCurrent.executeUpdate();

        //---------------------------> обновление счетчика <----------------------------------------------
        PreparedStatement preparedStatementUpdateWithdrawal = connection.prepareStatement("update client set withdrawal = ? where id =" + userId);

        preparedStatementUpdateWithdrawal.setInt(1, moneyToWithdrawal + withdrawal);
        preparedStatementUpdateWithdrawal.executeUpdate();


        System.out.println("Сash withdrawal completed successfully");
        System.out.println("Balance after withdrawal : " + (balance - moneyToWithdrawal));


    }


    @Override
    public void sendMoneyToDeposit(int userId) throws SQLException {

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


        PreparedStatement upDeposit = connection.prepareStatement("update client set savingaccount = ? where id =" + userId);
        upDeposit.setInt(1, (save+ sum));
        upDeposit.executeUpdate();


        PreparedStatement upCurr = connection.prepareStatement("update client set currentaccount = ? where id =" + userId);
        upCurr.setInt(1,(curr- sum));
        upCurr.executeUpdate();


        System.out.println("Successfully transaction to deposit : " + sum);
    }


    public ResultSet certainClient(int id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from client where id=" + id);
        return resultSet;
    }

    public void printToConsole() throws SQLException {
        print(statement.executeQuery("select * from client"));
    }

    private void print(ResultSet result) throws SQLException {
        while (result.next()) {
            System.out.println(
                    "name:" + result.getString("name") +
                            " |surname: " + result.getString("surname") +
                            " |age: " + result.getInt("age") +
                            " |gender: " + result.getString("gender") +
                            " |sum in current account: " + result.getInt("currentaccount") +
                            " |sum in saving account: " + result.getInt("savingaccount") +
                            " |login: " + result.getString("login"));
        }
    }

}


