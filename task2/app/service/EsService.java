package service;

import com.google.inject.Inject;
import data.EsClient;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import play.libs.Json;

import java.io.IOException;

public class EsService {

    private EsClient esClient;

    @Inject
    public EsService(EsClient esClient) {
        this.esClient = esClient;
    }

    public void addDocument(String index, String id, String json) throws IOException {
        Request request = new Request("POST", "/" + index + "/" + id);
        request.setJsonEntity(json);
        System.out.println(request.toString());
        Response response = esClient.getRestClient().performRequest(request);
    }
}
