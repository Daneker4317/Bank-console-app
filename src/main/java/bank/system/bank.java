package bank.system;

import java.sql.SQLException;

public interface bank {
    void sendMoney(int userId) throws Exception;
    void withdrawalMoney(int userId) throws SQLException;
    void sendMoneyToDeposit(int userId) throws SQLException;
}
