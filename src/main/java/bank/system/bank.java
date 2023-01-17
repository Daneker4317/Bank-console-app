package bank.system;

import java.sql.SQLException;

public interface bank {
    void sendMoney() throws Exception;
    void withdrawalMoney() throws SQLException;
    void sendMoneyToDeposit() throws SQLException;
}
