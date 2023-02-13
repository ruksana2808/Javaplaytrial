package Config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlConnection extends AbstractModule {
    @Provides
    private Connection sqlConnect() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student", "root", "password");
        return connection;

    }

}
