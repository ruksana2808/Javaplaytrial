package Service;

import Config.EsConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
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
        IndexRequest indexRequest = new IndexRequest("product","_doc").source(productJsonMap);
        esConnection.getRestHighLevelClient().index(indexRequest, RequestOptions.DEFAULT);
        Map<String, Object> specJsonMap = new HashMap<>();
        specJsonMap.put("spec_id", specId);
        specJsonMap.put("spec_details", specDetails);
        IndexRequest indexRequest1 = new IndexRequest("spec","_doc", "spec_id").source(specJsonMap);
        esConnection.getRestHighLevelClient().index(indexRequest1, RequestOptions.DEFAULT);
        return productId;
    }
}
