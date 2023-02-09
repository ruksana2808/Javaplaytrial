package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import data.MysqlClient;
import play.api.db.Database;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MysqlController extends Controller {

    @Inject
    private MysqlClient mysqlClient;
    Map<Integer, String>  initialMap = new HashMap<>();

    public MysqlController() {
    }

    public Result connectionSql() throws IOException, SQLException {
//        PreparedStatement ps = mysqlClient.getConnection().prepareStatement("INSERT INTO student_details " +
//                        "(id,name)" +
//                        "VALUES (?, ?)",
//                new String[]{"id"});
//        ps.setInt(1, 6);
//        ps.setString(2, "America");
//        System.out.println(ps);
//        ps.executeUpdate();
//        System.out.println("Success");
////        ps.close();
//        return ok("success");
        return ok(mysqlClient.getConnection().toString());

    }

    public Result fetchData() {
        try (

                PreparedStatement preparedStatement = mysqlClient.getConnection().prepareStatement("SELECT * FROM country");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id);
                System.out.println(name);
//                System.out.println(co);
                // process the result
            }
        } catch (Exception e) {
            return internalServerError("Failed");
            // handle the exception
        }
        return ok("Successfully connected");
    }

    public Result addData(Http.Request request) {
        JsonNode json = request.body().asJson();
        String name = json.get("name").asText();

        Connection conn = null;
        try {
            String sql = "INSERT INTO student_details (name) VALUES (?)";
            PreparedStatement stmt = mysqlClient.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            return ok("Data inserted successfully");
        } catch (SQLException e) {
            return internalServerError("Error inserting data: " + e.getMessage());
        }

    }
    public Result getCountry(){
        return ok(mysqlClient.getCountry().toString());
    }
}
