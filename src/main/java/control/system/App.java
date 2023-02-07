package control.system;

import java.sql.SQLException;

public interface App {
    void start() throws Exception;
    default void exit(){
        System.out.println("app closing");
        System.exit(0);
    }
}
