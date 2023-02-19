import application.Application;
import database.Connect;
import database.PSQL;

public class Main {

    public static void main(String[] args) throws Exception {
        Connect connect = new PSQL(); // singleton design pattern we will use only one connection for JDBC
        // also implemented LSP -> we can swap them and the client will not know about it
        Application application = new Application(connect);
        application.start();
    }
}
