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


    private int findClient() throws SQLException {

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
    public void sendMoney() throws Exception {


        int userId = findClient();
        if (userId < 0) {
            return;
        }


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
       // ResultSet resultSetForGetCurrentAccount = preparedStatementUpdateMoney.executeQuery();

        int balance = 0 , sum = 0;
        if(resultSetForGetCurrentAccount.next()) {
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
    public void withdrawalMoney() throws SQLException {


        System.out.println("enter user name");
        String name = in.nextLine();

        System.out.println("enter login to entrance");
        String login = in.nextLine();


        ResultSet resultSet = statement.executeQuery("select * from client where  login ='" + login + "'");


        if (!resultSet.next() || !resultSet.getString("name").equals(name)) {
            System.out.println("Invalid name or login");
            return;
        }


        System.out.println("enter sum to withdrawal:");
        int moneyToWithdrawal = in.nextInt();


        PreparedStatement preparedStatementUpdateMoney = connection.prepareStatement("select currentaccount from client where  login ='" + login + "'");
        PreparedStatement preparedStatementSqlQuery = connection.prepareStatement("select withdrawal from client where  login ='" + login + "'");


        ResultSet resultSetForGetCurrentAccount = preparedStatementUpdateMoney.executeQuery();
        ResultSet resultSetSqlQuery = preparedStatementSqlQuery.executeQuery();


        int withdrawal = 0;
        if (resultSetForGetCurrentAccount.next()) {
            if (resultSetSqlQuery.next()) {
                withdrawal = resultSetSqlQuery.getInt("withdrawal");
                if ((withdrawal + moneyToWithdrawal) > limitForWithdrawal) {
                    System.out.println("Ohhh no");
                    System.out.println("You have limit for withdrawal only " + limitForWithdrawal + " tenge ");
                    return;
                }
            }
            int balance = resultSetForGetCurrentAccount.getInt("currentaccount");
            if (balance < moneyToWithdrawal) {
                System.out.println("Lol not enough money");
                return;
            }


            resultSet.close();
            resultSetSqlQuery.close();
            resultSetForGetCurrentAccount.close();


            // --------------------------> обновление счета после снятие денег <------------------------------
            PreparedStatement preparedStatementUpdateCurrent = connection.prepareStatement("update client set currentaccount = ? where login ='" + login + "'");
            preparedStatementUpdateCurrent.setInt(1, balance - moneyToWithdrawal);
            preparedStatementUpdateCurrent.executeUpdate();

            //---------------------------> обновление счетчика <----------------------------------------------
            PreparedStatement preparedStatementUpdateWithdrawal = connection.prepareStatement("update client set withdrawal = ? where login  ='" + login + "'");
            preparedStatementUpdateWithdrawal.setInt(1, moneyToWithdrawal + withdrawal);
            preparedStatementUpdateWithdrawal.executeUpdate();


            System.out.println("Сash withdrawal completed successfully");
            System.out.println("Balance after withdrawal : " + (balance - moneyToWithdrawal));


        }

    }

    @Override
    public void sendMoneyToDeposit() throws SQLException {

        System.out.println("Enter user name");
        String name = in.nextLine();

        System.out.println("Enter login for entrance");
        String login = in.nextLine();

        ResultSet resultSet = statement.executeQuery("select * from client where  login ='" + login + "'");

        if (!resultSet.next()) {
            System.out.println("Incorrect name or password");
            return;
        }


        System.out.println("Enter sum for transaction to deposit account");
        int sum = in.nextInt();

        if (resultSet.getInt("currentaccount") < sum) {
            System.out.println("Not enough money");
            return;
        }

        System.out.println("Enter 1 for confirm transaction");
        String one = in.next();

        if (!one.equals("1")) {
            System.out.println("Transaction canceled");
            return;
        }


        PreparedStatement upDeposit = connection.prepareStatement("update client set savingaccount = ? where login ='" + login + "'");
        upDeposit.setInt(1, resultSet.getInt("savingaccount") + sum);
        upDeposit.executeUpdate();


        PreparedStatement upCurr = connection.prepareStatement("update client set currentaccount = ? where login ='" + login + "'");
        upCurr.setInt(1, resultSet.getInt("currentaccount") - sum);
        upCurr.executeUpdate();


        System.out.println("Successfully transaction to deposit : " + sum);
    }


    public void infoAboutCertainClient(int id) throws SQLException {
        print(statement.executeQuery("select from client where id=" + id));
    }

    public void printToConsole() throws SQLException {
        print(statement.executeQuery("select * from client"));
    }

    private void print(ResultSet result) throws SQLException {
        while (result.next()) {
            System.out.println(result.getString("name") + " " + result.getString("surname") + " " + result.getInt("age") + " " + result.getString("gender") + " " + result.getInt("currentaccount") + " " + result.getInt("savingaccount") + " " + result.getString("login"));
        }
    }

}


