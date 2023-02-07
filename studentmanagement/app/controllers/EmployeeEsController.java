package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.request;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

public class EmployeeEsController {
    private RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            )
    );
    public Result createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
            return ok(String.format("Index '%s' created successfully.", index));
        } catch (IOException e) {
            return internalServerError(e.getMessage());
        }
    }
    public Result addDocument(Http.Request request, String employee, String id) {
        JsonNode requestBody = request.body().asJson();
        IndexRequest indexRequest = new IndexRequest(employee, "_doc", id)
                .source(requestBody);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
            return ok(String.format("Document with id '%s' added to index '%s'.", id, employee));
        } catch (IOException e) {
            return internalServerError(e.getMessage());
        }
    }
//public Result addDocument(String index, String id) {
//    JsonNode requestBody = request().body().asJson();
//    IndexRequest indexRequest = new IndexRequest(index, "_doc", id)
//            .source(requestBody);
//    try {
//        client.index(indexRequest, RequestOptions.DEFAULT);
//        return ok(String.format("Document with id '%s' added to index '%s'.", id, index));
//    } catch (IOException e) {
//        return internalServerError(e.getMessage());
//    }

}
