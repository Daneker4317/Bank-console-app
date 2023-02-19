package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 @author Kaliaskaruly Daneker
  * */

public class PSQL implements Connect {
    private final String USERNAME = "postgres";
    private final String PASSWORD = "qwerty";
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";

    @Override
    public Connection getConnection() throws Exception {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

}
