package sequelize;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class SQLExecutor {
    private static Connection connection;

    private static SQLExecutor ourInstance = new SQLExecutor();

    public static SQLExecutor getInstance() {
        return ourInstance;
    }

    private SQLExecutor() {
    }

    public static void setConnection(Connection connection) {
        SQLExecutor.connection = connection;
    }

    public static CompletableFuture<Boolean> execute(String query){
        System.out.println("Executing \"" + query +"\"");

        try {
            connection.createStatement().execute(query);
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }
    }

}
