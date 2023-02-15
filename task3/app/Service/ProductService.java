package Service;

import Config.EsConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import play.mvc.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.ok;

public class ProductService {
    @Inject
    private EsConnection esConnection;
    public int addProduct(Http.Request request) throws IOException {
        JsonNode body = request.body().asJson();
        int productId = body.get("product_id").asInt();
        String productName = body.get("product_name").asText();
        int specId = body.get("spec_id").asInt();
        String specDetails = body.get("spec_details").asText();
        Map<String, Object> productJsonMap = new HashMap<>();
        productJsonMap.put("product_id",productId);
        productJsonMap.put("product_name",productName);
        productJsonMap.put("spec_id",specId);
        IndexRequest indexRequest = new IndexRequest("product","_doc",String.valueOf(productId)).source(productJsonMap);
        esConnection.getRestHighLevelClient().index(indexRequest, RequestOptions.DEFAULT);
        Map<String, Object> specJsonMap = new HashMap<>();
        specJsonMap.put("spec_id", specId);
        specJsonMap.put("spec_details", specDetails);
        IndexRequest indexRequest1 = new IndexRequest("spec","_doc", String.valueOf(specId)).source(specJsonMap);
        esConnection.getRestHighLevelClient().index(indexRequest1, RequestOptions.DEFAULT);
        return productId;
    }
//    public String getProduct(Http.Request request) throws IOException {
//        JsonNode body = request.body().asJson();
//        String index = body.get("index").asText();
//        String type = body.get("type").asText();
//        String id = body.get("id").asText();
//        GetRequest getRequest = new GetRequest(index, type, id);
//        GetResponse response = esConnection.getRestHighLevelClient().get(getRequest, RequestOptions.DEFAULT);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(response.getSource());
//        return jsonString;
//    }
    public String getProductById(Integer productId) throws IOException {
        GetRequest request = new GetRequest("product", "_doc", String.valueOf(productId));
        GetResponse response = esConnection.getRestHighLevelClient().get(request, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonobject = objectMapper.writeValueAsString(response.getSource());
        return jsonobject;
    }

}
