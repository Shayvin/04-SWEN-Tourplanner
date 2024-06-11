package org.shayvin.tourplanner.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.shayvin.tourplanner.viewmodel.TabViewModel;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class TabView implements Initializable {

    private final TabViewModel viewModel;

    @FXML
    public TextField viewAddTourTextName;
    @FXML
    private TextField viewAddTourTextDescription;
    @FXML
    private TextField viewAddTourTextStart;
    @FXML
    private TextField viewAddTourTextDestination;
    @FXML
    private TextField viewAddTourTextType;
    @FXML
    private TextField viewAddTourTextDistance;
    @FXML
    private TextField viewAddTourTextTime;
    @FXML
    private TextField viewAddTourTextInformation;
    @FXML
    private ImageView picturesView;
    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    public TabView(TabViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double[] startCoords = {16.3738, 48.2082};  // Stephansplatz
        double[] endCoords = {16.3122, 48.1845};
        this.viewAddTourTextName.textProperty().bindBidirectional(viewModel.addTourTextNameProperty());
        this.viewAddTourTextDescription.textProperty().bindBidirectional(viewModel.addTourTextDescriptionProperty());
        this.viewAddTourTextStart.textProperty().bindBidirectional(viewModel.addTourTextStartProperty());
        this.viewAddTourTextDestination.textProperty().bindBidirectional(viewModel.addTourTextDestinationProperty());
        this.viewAddTourTextType.textProperty().bindBidirectional(viewModel.addTourTextTypeProperty());
        this.viewAddTourTextDistance.textProperty().bindBidirectional(viewModel.addTourTextDistanceProperty());
        this.viewAddTourTextTime.textProperty().bindBidirectional(viewModel.addTourTextTimeProperty());
        this.viewAddTourTextInformation.textProperty().bindBidirectional(viewModel.addTourTextInformationProperty());

        this.viewAddTourTextName.disableProperty().bind(viewModel.readOnlyTextNameProperty());
        this.viewAddTourTextDescription.disableProperty().bind(viewModel.readOnlyTextDescriptionProperty());
        this.viewAddTourTextStart.disableProperty().bind(viewModel.readOnlyTextStartProperty());
        this.viewAddTourTextDestination.disableProperty().bind(viewModel.readOnlyTextDestinationProperty());
        this.viewAddTourTextType.disableProperty().bind(viewModel.readOnlyTextTypeProperty());
        this.viewAddTourTextDistance.disableProperty().bind(viewModel.readOnlyTextDistanceProperty());
        this.viewAddTourTextTime.disableProperty().bind(viewModel.readOnlyTextTimeProperty());
        this.viewAddTourTextInformation.disableProperty().bind(viewModel.readOnlyTextInformationProperty());

        this.picturesView.imageProperty().bindBidirectional(viewModel.pictures);
        // WebEngine webEngine = mapView.getEngine();
        // viewModel.mapContentProperty().addListener((obs, oldContent, newContent) -> webEngine.loadContent(newContent));
        webEngine = mapView.getEngine();
        String leafletMap = generateLeafletMapHTML();
        webEngine.loadContent(leafletMap);

        RouteService routeService = new RouteService(startCoords, endCoords);
        routeService.setOnSucceeded(event -> {
            String route = (String) event.getSource().getValue();
            System.out.println("Route JSON: " + route);
            displayRoute(route);
        });
        routeService.start();
    }

    private void displayRoute(String route) {
        route = route.replace("\\", "\\\\").replace("'", "\\'");
        webEngine.executeScript("window.route = '" + route + "';");
        webEngine.executeScript("setTimeout(function() { window.tryDisplayRoute(); }, 100);"); // Without Timeout it doesn't work. Because the function is not ready at this point
    }




    private class RouteService extends Service<String> {
        private final double[] startCoords;
        private final double[] endCoords;

        public RouteService(double[] startCoords, double[] endCoords) {
            this.startCoords = startCoords;
            this.endCoords = endCoords;
        }

        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                @Override
                protected String call() throws Exception {
                    String url = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";
                    ObjectMapper mapper = new ObjectMapper();

                    JsonNode requestJson = mapper.createObjectNode()
                            .set("coordinates", mapper.createArrayNode()
                                    .add(mapper.createArrayNode().add(startCoords[0]).add(startCoords[1]))
                                    .add(mapper.createArrayNode().add(endCoords[0]).add(endCoords[1]))
                            );

                    try (CloseableHttpClient client = HttpClients.createDefault()) {
                        HttpPost httpPost = new HttpPost(url);
                        httpPost.setHeader("Authorization", "5b3ce3597851110001cf62488cca42832d6f48468b34975a5a4793a3");
                        httpPost.setHeader("Content-Type", "application/json");
                        httpPost.setEntity(new StringEntity(requestJson.toString(), StandardCharsets.UTF_8));

                        try (CloseableHttpResponse response = client.execute(httpPost)) {
                            String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                            JsonNode jsonObject = mapper.readTree(jsonResponse);
                            return jsonObject.toString();
                        }
                    }
                }
            };
        }
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
                var map = L.map('map').setView([48.2083, 16.3731], 13);

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
                    L.geoJSON(geojson).addTo(map);
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
            </script>
        </body>
        </html>
    """;
    }
}
