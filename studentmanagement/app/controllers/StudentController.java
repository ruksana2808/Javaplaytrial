package controllers;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Student;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import service.StudentService;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import play.db.Database;
import javax.inject.Inject;

import play.db.Database;

public class StudentController extends Controller {
    private StudentService studentService;
    public final FormFactory formFactory;
    @Inject
    public StudentController(StudentService studentService, FormFactory formFactory) {
        this.studentService = studentService;
        this.formFactory = formFactory;
    }
    @Inject
    play.api.db.Database db;

//    private RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(
//                    new HttpHost("localhost", 9200, "http")
//            )
//    );
//public Statement sqlConnect()throws Exception{
//    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management", "root", "password");
//    Statement statement = connection.createStatement();
//    return statement;
//}

    public Result getStudent(int id){
        return ok(studentService.getStudent(id).toString());
    }
    public Result addStudent(Http.Request student){
        Form<Student> form = formFactory.form(Student.class).bindFromRequest(student);
        return ok(studentService.addStudent(form.get()).toString());
    }
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
    public  Result getEmployeebyId(int empId) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from employee where empId = ?");
        statement.setInt(1,empId);
        ResultSet resultSet = statement.executeQuery();
        ObjectNode result = Json.newObject();
        while(resultSet.next()){
            int id = resultSet.getInt("empId");
            String empName = resultSet.getString("empName");
            String phoneNum = resultSet.getString("phoneNum");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            result.put("empId", empId);
            result.put("empName", empName);
            result.put("phoneNum",phoneNum);
            result.put("address",address);
            result.put("email",email);

        }

        return ok(result);
    }

}
