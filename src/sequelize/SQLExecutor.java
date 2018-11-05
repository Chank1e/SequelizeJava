package sequelize;

import sequelize.logger.AwesomeLogger;
import sequelize.logger.AwesomeLoggerTypes;

import java.sql.Connection;
import java.sql.ResultSet;
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
        AwesomeLogger.getInstance().setMessage("Executing without result \"" + query +"\"").setType(AwesomeLoggerTypes.INFO).log();

        try {
            connection.createStatement().execute(query);
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            ErrorHandler.handle(e);
            return CompletableFuture.completedFuture(false);
        }
    }
    public static CompletableFuture<ResultSet> executeWithResult(String query){
        AwesomeLogger.getInstance().setMessage("Executing with result \"" + query +"\"").setType(AwesomeLoggerTypes.INFO).log();

        try {
            ResultSet res = connection.createStatement().executeQuery(query);
            return CompletableFuture.completedFuture(res);
        } catch (SQLException e) {
            ErrorHandler.handle(e);
            return CompletableFuture.completedFuture(null);
        }
    }

}
