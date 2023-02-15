package Config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EsConnection {
    private RestHighLevelClient restHighLevelClient;

    public EsConnection() {
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        System.out.println("connected");
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }
}
