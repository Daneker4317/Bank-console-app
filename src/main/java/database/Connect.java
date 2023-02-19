package database;

import java.sql.Connection;
/**
 @author Kaliaskaruly Daneker
  * */

public interface Connect { // // Interface Segregation -> divide large interfaces to smaller interfaces grouping by relevant functions
    Connection getConnection() throws Exception;
}
