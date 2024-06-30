package org.shayvin.tourplanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.TourPlannerApp;
import org.shayvin.tourplanner.viewmodel.TourLogPopupViewModel;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class OpenRouteService extends Service<String> {

    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);

    private CloseableHttpClient httpClient;

    private final ConfigService configService;

    private final String apiKey;
    private static final String baseUrl = "https://api.openrouteservice.org/v2/directions/";
    private static final String geocodeUrl = "https://api.openrouteservice.org/geocode/search";
    private String startAddress = "";
    private String endAddress = "";
    private String transportType = "";
    private String distance = "";
    private String duration = "";


    public OpenRouteService(ConfigService configService) {
        this.configService = configService;
        this.httpClient = HttpClients.createDefault();
        apiKey = ConfigService.loadKeyValuePair();
    }

    /*public static String getRoute(double startLon, double startLat, double endLon, double endLat) throws IOException {
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
    }*/

    public double[] getCoordinates(String address) throws IOException {
        String url = String.format("%s?api_key=%s&text=%s", geocodeUrl, apiKey, address);

        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                return parseCoordinates(json);
            } else {
                throw new IOException("Failed to get coordinates: " + response.getStatusLine().getStatusCode());
            }
        }
    }

    private static double[] parseCoordinates(String json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode featuresNode = rootNode.path("features");

        if (featuresNode.isArray() && featuresNode.size() > 0) {
            JsonNode geometryNode = featuresNode.get(0).path("geometry");
            JsonNode coordinatesNode = geometryNode.path("coordinates");

            if (coordinatesNode.isArray() && coordinatesNode.size() == 2) {
                double lon = coordinatesNode.get(0).asDouble();
                double lat = coordinatesNode.get(1).asDouble();
                return new double[]{lon, lat};
            } else {
                throw new IOException("Invalid coordinates format");
            }
        } else {
            throw new IOException("No features found");
        }
    }

    /*private static String parseRoute(String json) throws IOException {
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
    }*/

    @Override
    public Task<String> createTask() {
        return new Task<>() {
            @Override
            public String call() throws Exception {
                double[] startCoords = getCoordinates(startAddress);
                double[] endCoords = getCoordinates(endAddress);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode requestJson = mapper.createObjectNode()
                        .set("coordinates", mapper.createArrayNode()
                                .add(mapper.createArrayNode().add(startCoords[0]).add(startCoords[1]))
                                .add(mapper.createArrayNode().add(endCoords[0]).add(endCoords[1]))
                        );

                String url = baseUrl + transportType + "/geojson";

                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setHeader("Authorization", apiKey);
                    httpPost.setHeader("Content-Type", "application/json");
                    httpPost.setEntity(new StringEntity(requestJson.toString(), StandardCharsets.UTF_8));

                    try (CloseableHttpResponse response = client.execute(httpPost)) {
                        String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                        JsonNode jsonObject = mapper.readTree(jsonResponse);
                        calculateDistance(jsonResponse);
                        calculateDuration(jsonResponse);
                        return jsonObject.toString();
                    }
                }
            }
        };
    }

    public void displayRoute(String route, WebEngine webEngine) {
        route = route.replace("\\", "\\\\").replace("'", "\\'");
        webEngine.executeScript("window.route = '" + route + "';");
        webEngine.executeScript("setTimeout(function() { window.tryDisplayRoute(); }, 100);"); // Without Timeout it doesn't work. Because the function is not ready at this point
        if(!startAddress.isEmpty() || !endAddress.isEmpty()){
            clearExistingRoute(webEngine);
        }
    }

    public void clearExistingRoute(WebEngine webEngine) {
        webEngine.executeScript("clearRouteOnMap();");
    }

    private String generateLeafletMapHTML() {
        return """
    <!DOCTYPE html>
    <html>
    <head>
        <title>Leaflet Map</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
        <style>
            #map { height: 100vh; width: 100%; }
            .leaflet-container { height: 100%; width: 100%; }
        </style>
    </head>
    <body>
        <div id="map"></div>
        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <script>
            var map = L.map('map').setView([48.2083, 16.3731], 6);
            var routeLayer; // Define a variable to hold the route layer

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            // For webview resizing
            window.addEventListener('resize', function() {
                map.invalidateSize();
            });

            function displayRoute(route) {
                console.log("Displaying Route: ", route);  // Debugging line
                var geojson = JSON.parse(route);
                if (routeLayer) {
                    map.removeLayer(routeLayer); // Remove the existing route layer if present
                }
                routeLayer = L.geoJSON(geojson).addTo(map); // Add the new route layer
            }

            window.displayRoute = displayRoute;

            function tryDisplayRoute() {
                if (window.displayRoute) {
                    window.displayRoute(window.route);
                } else {
                    setTimeout(tryDisplayRoute, 100);  // Check again after 100 milliseconds
                }
            }

            window.tryDisplayRoute = tryDisplayRoute;

            document.addEventListener("DOMContentLoaded", function() {
                tryDisplayRoute();
            });

            // Clear exising route on map
            function clearRouteOnMap() {
                if (routeLayer) {
                    map.removeLayer(routeLayer); // Remove the route layer from the map
                    routeLayer = null; // Reset the routeLayer variable
                }
            }

            window.clearRouteOnMap = clearRouteOnMap;
        </script>
    </body>
    </html>
    """;
    }

    public String calculateDistance(String json) {
        String distance = "";
        try {
            logger.info("Calculating distance...");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            distance = root
                    .path("features").get(0)
                    .path("properties").path("summary")
                    .path("distance").asText();

            System.out.println("Distance: " + distance);
            setDistance(distance);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        logger.info("Success calculating distance: {}", distance);
        return distance;
    }

    public String calculateDuration(String json) {
        String duration = "";
        try {
            logger.info("Calculating duration...");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            duration = root
                    .path("features").get(0)
                    .path("properties").path("summary")
                    .path("duration").asText();

            System.out.println("Duration: " + duration);
            setDuration(duration);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        logger.info("Success calculating duration: {}", duration);
        return duration;
    }

    public void clearRouteProperties() {
        setStartAddress("");
        setEndAddress("");
        setDistance("");
        setDuration("");
    }


    public String getLeafletMap() {
        return generateLeafletMapHTML();
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
