package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entities.Student;
import play.mvc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import service.StudentService;
import utils.ApplicationUtil;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("success");
    }
//    private static final Logger logger = LoggerFactory.getLogger("controller");

//    public Result create() {
//
//        Student student = StudentService.getStudentService().addStudent(student);
//        JsonNode jsonObject = Json.toJson(student);
//        return created(ApplicationUtil.createResponse(jsonObject, true));
//    }

}
