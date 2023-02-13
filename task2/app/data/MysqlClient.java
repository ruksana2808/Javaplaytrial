package data;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.internalServerError;

public class MysqlClient {
    private Connection connection;
    Map<Integer, String> countryMap = new HashMap<>();

    //    @Inject
//    Database db;
    public MysqlClient() throws SQLException {
//        Connection connection = db.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student", "root", "password");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM country");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            countryMap.put(id, name);

            // process the result
        }


//        System.out.println(country);

    }

    public Connection getConnection() {
        return connection;
    }

    public Map<Integer, String> getCountryMap() {
        return countryMap;
    }

//    public Map<Integer, String> getCountryDetails(Connection connection) throws SQLException {
//            Map<Integer, String> countryDetails = new HashMap<>();
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM country");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                country.put(id, name);
//
//                // process the result
//            }
//            return country;
//        }
}
