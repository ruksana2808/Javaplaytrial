package Service;

import Config.EsConnection;
import Config.MysqlConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import play.libs.Json;
import play.mvc.Http;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.ok;

public class ProductService {
    @Inject
    private EsConnection esConnection;
    @Inject
    private MysqlConnection mysqlConnection;
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

    public String getProductById(Integer productId) throws IOException {
        GetRequest request = new GetRequest("product", "_doc", String.valueOf(productId));
        GetResponse response = esConnection.getRestHighLevelClient().get(request, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonobject = objectMapper.writeValueAsString(response.getSource());
        return jsonobject;
    }

    public int  addProducToMysql(Http.Request request) throws SQLException {
        JsonNode json = request.body().asJson();
        int productid = json.get("productid").asInt();
        String productname = json.get("productname").asText();
        int specid = json.get("specid").asInt();
        String productspec = json.get("productspec").asText();
        PreparedStatement preparedStatement = mysqlConnection.getConnection().prepareStatement("insert into product(productid,productname,specid) values (?,?,?)");
        preparedStatement.setInt(1,productid);
        preparedStatement.setString(2,productname);
        preparedStatement.setInt(3,specid);
        preparedStatement.executeUpdate();
        System.out.println("data inserted to product table");
        PreparedStatement statement = mysqlConnection.getConnection().prepareStatement("insert into productspec(specid,productspec) values (?,?)");
        statement.setInt(1,specid);
        statement.setString(2,productspec);
        statement.executeUpdate();
        System.out.println("data inserted to productspec table");
        return productid;
    }

    public String getBySpecId(int specid) throws SQLException {
        PreparedStatement preparedStatement1 = mysqlConnection.getConnection().prepareStatement("select * from product where specid =? ");

        ObjectNode result = Json.newObject();
        preparedStatement1.setInt(1,specid);
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()){

            int id = resultSet.getInt("productid");
            String pname = resultSet.getString("productname");
            int sid = resultSet.getInt("specid");
            result.put("productid",id);
            result.put("productname",pname);
            result.put("specid",sid);

        }
        return result.toString();

    }
}
