package controllers;

import Service.ProductService;
import com.google.inject.Inject;
import play.mvc.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ProductController extends Controller {

    @Inject private ProductService productService;

    public Result index() {
        return ok("Success");
    }

    public Result addProduct (Http.Request request) throws IOException {
        productService.addProduct(request);
        return ok("Data inserted");
    }
//    public Result getProduct (Http.Request request) throws IOException {
//        return ok(productService.getProduct(request));
//    }
    public Result getProductById(Integer productId) throws IOException {
        return  ok(productService.getProductById(productId).toString());
    }
    public Result addProducToMysql(Http.Request request) throws SQLException {
        productService.addProducToMysql(request);
        return ok("Data inserted");
    }
    public Result getBySpecId(Integer specId) throws SQLException {
        return ok(productService.getBySpecId(specId));
    }

}
