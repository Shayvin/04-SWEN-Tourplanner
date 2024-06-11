package org.shayvin.tourplanner.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class OpenRouteService {
    private static final String apiKey = "5b3ce3597851110001cf62488cca42832d6f48468b34975a5a4793a3";
    private static final String baseUrl = "https://api.openrouteservice.org/v2/directions/";


    public OpenRouteService() {

    }

    public static String getRoute(double startLon, double startLat, double endLon, double endLat) throws IOException {
        String url = String.format("%s?api_key=%s&start=%f,%f&end=%f,%f",
                baseUrl, apiKey, startLon, startLat, endLon, endLat);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());
                    return parseRoute(json);
                } else {
                    throw new IOException("Failed to get route: " + response.getStatusLine().getStatusCode());
                }
            }
        }
    }

    private static String parseRoute(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode routesNode = rootNode.path("routes");

        if (routesNode.isArray() && routesNode.size() > 0) {
            JsonNode summaryNode = routesNode.get(0).path("summary");
            double distance = summaryNode.path("distance").asDouble();
            double duration = summaryNode.path("duration").asDouble();
            return String.format("Distance: %.2f meters, Duration: %.2f seconds", distance, duration);
        } else {
            throw new IOException("No route found");
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
