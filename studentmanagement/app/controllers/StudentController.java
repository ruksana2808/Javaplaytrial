package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Employee;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.EmployeeService;

import javax.inject.Inject;
import java.sql.*;

public class StudentController extends Controller {
    private EmployeeService studentService;
    public final FormFactory formFactory;

    @Inject
    public StudentController(EmployeeService studentService, FormFactory formFactory) {
        this.studentService = studentService;
        this.formFactory = formFactory;
    }

    @Inject
    play.api.db.Database db;


//public Statement sqlConnect()throws Exception{
//    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee", "root", "password");
//    Statement statement = connection.createStatement();
//    return statement;
//}

    //    public Result getStudent(int id){
//        return ok(studentService.getStudent(id).toString());
//    }
//    public Result addStudent(Http.Request Employee){
//        Form<model.Employee> form = formFactory.form(Employee.class).bindFromRequest(employee);
//        return ok(studentService.addStudent(form.get()).toString());
//    }
    public Result fetchDataFromDatabase() {
        try (Connection connection = db.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("empName");
                System.out.println(name);
                // process the result
            }
        } catch (Exception e) {
            return internalServerError("Failed");
            // handle the exception
        }
        return ok("Successfully connected");
    }

    public Result getEmployeebyId(int empId) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from employee where empId = ?");
        statement.setInt(1, empId);
        ResultSet resultSet = statement.executeQuery();
        ObjectNode result = Json.newObject();
        while (resultSet.next()) {
            int id = resultSet.getInt("empId");
            String empName = resultSet.getString("empName");
            String phoneNum = resultSet.getString("phoneNum");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String gender = resultSet.getString("gender");
            result.put("empId", empId);
            result.put("empName", empName);
            result.put("phoneNum", phoneNum);
            result.put("address", address);
            result.put("email", email);
            result.put("gender", gender);

        }

        return ok(result);
    }

    //    public Result addEmployee(Http.Request request) throws SQLException, JsonProcessingException {
//        Connection connection = db.getConnection();
//        PreparedStatement statement1 = connection.prepareStatement("insert into employee (empId,empName,address,gender,email) values(?,?,?,?,?)");
//        statement1.setString(1,jsonData);
//        statement1.executeUpdate();
//        return ok(jsonData);
//    }
    public Result addData(Http.Request request) {
        JsonNode json = request.body().asJson();
        String name = json.get("empName").asText();

        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO employee (empName) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            return ok("Data inserted successfully");
        } catch (SQLException e) {
            return internalServerError("Error inserting data: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }
    public Result updateRecord(Http.Request request) {
        JsonNode json = request.body().asJson();
        Employee employee = Json.fromJson(json, Employee.class);

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection =db.getConnection();
            statement = connection.prepareStatement("UPDATE employee SET empName=?,  address=? WHERE empId=?");
            statement.setString(1, employee.getEmpName());
            statement.setString(2, employee.getAddress());
            statement.setInt(3, employee.getEmpId());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ok("Data successfully updated");
    }
    public Result deleteEmployee(int id) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String checkSql = "SELECT * FROM employee WHERE empId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return notFound("Data not found with id " + id);
            }
            String sql = "DELETE FROM employee WHERE empId=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return ok("Data deleted successfully");
        } catch (SQLException e) {
            return internalServerError("Error deleting data: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {

            }
        }
    }


}
