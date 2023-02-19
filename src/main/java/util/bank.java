package util;

// Interface Segregation -> divide large interfaces to smaller interfaces grouping by relevant functions
public interface bank {
    void sendMoney(int userId) throws Exception;
    void withdrawalMoney(int userId) throws Exception;
    void sendMoneyToDeposit(int userId) throws Exception;
}
