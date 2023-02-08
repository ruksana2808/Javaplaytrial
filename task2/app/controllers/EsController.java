package controllers;

import com.google.inject.Inject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.*;

import play.mvc.Result;

import java.io.IOException;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

public class EsController {
    @Inject
    private RestClient restClient;

    public EsController(RestClient restClient) {
        this.restClient = restClient;
    }

    public Result connection() {
        return ok("Success");
    }
//    public void getData() throws IOException {
//        Request request = new Request("GET", "/employee/_doc/1");
//        Response response = restClient.performRequest(request);
//        System.out.println(EntityUtils.toString(response.getEntity()));
//    }

}
