package bank.system;

import java.sql.SQLException;

public interface bank {
    void sendMoney(int userId) throws Exception;
    void withdrawalMoney() throws SQLException;
    void sendMoneyToDeposit() throws SQLException;
}
