package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    private Connection connection;

    public MysqlConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Product", "root", "password");
        System.out.println("Conneted");
    }

    public Connection getConnection() {
        return connection;
    }
}
