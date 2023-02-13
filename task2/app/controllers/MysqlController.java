package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import data.MysqlClient;
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

    Map<Integer, String> mapOfCountries = new HashMap<>();

    public Map<Integer, String> getMapOfCountries() {
        return mapOfCountries;
    }

    public void setMapOfCountries(Map<Integer, String> mapOfCountries) {
        this.mapOfCountries = mapOfCountries;
    }

    public MysqlController() {
    }

//    API to print list of countries stored in hashmap countryMap
    public Result getCountry() {

        return ok(mysqlClient.getCountryMap().toString());
    }



//    API to fetch data from db initialisation

    public Result loadCountries() {
        mapOfCountries = mysqlClient.getCountryMap();
        setMapOfCountries(mapOfCountries);
        System.out.println(mapOfCountries.size());
        return ok("Countries fetched from dB and stored in hashMap");
    }
    public Result listOfCountries() {
        System.out.println(getMapOfCountries());
        return ok(getMapOfCountries().toString());
    }



    public Result connectionSql() throws IOException, SQLException {
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
            String sql = "INSERT INTO country (name) VALUES (?)";
            PreparedStatement stmt = mysqlClient.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            return ok("Data inserted successfully");
        } catch (SQLException e) {
            return internalServerError("Error inserting data: " + e.getMessage());
        }

    }

}
