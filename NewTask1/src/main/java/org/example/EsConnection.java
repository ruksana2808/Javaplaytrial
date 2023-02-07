package org.example;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

public class EsConnection {
    private static RestClient client;
    RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200, "http")
    ).build();



    public static void main(String[] args) throws IOException {
        EsConnection es = new EsConnection();
        es.addData();
        es.getData();
    }

    public void addData() throws IOException {
//        RestClient restClient = esConnect();
        System.out.println("connected to Elastic Search");
        String json = "{\"id\":\"1\",\"name\":\"Ruksana\"}";
        Request request = new Request("POST", "/student/_doc/1");
        request.setJsonEntity(json);
        Response response = restClient.performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }
    public void getData() throws IOException {
//        RestClient restClient = esConnect();
        Request request = new Request("GET", "/student/_doc/1");
        Response response = restClient.performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
