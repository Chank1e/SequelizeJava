package sequelize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionDB {
    public ConnectionDB DBConnect() throws SQLException {
        return this;
    }

    public Connection connect(String url, String username, String password) throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            ErrorHandler.handle(e);
        }
        return DriverManager.getConnection(url, username, password);
    }
}
