package org.shayvin.tourplanner.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
import org.shayvin.tourplanner.service.OpenRouteService;
import org.shayvin.tourplanner.viewmodel.TabViewModel;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class TabView implements Initializable {

    private final TabViewModel viewModel;

    private final OpenRouteService openRouteService;

    @FXML
    public TextField viewAddTourTextName;
    @FXML
    private TextField viewAddTourTextDescription;
    @FXML
    private TextField viewAddTourTextStart;
    @FXML
    private TextField viewAddTourTextDestination;
    @FXML
    private ComboBox<String> viewAddTourTextType;
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

    public TabView(TabViewModel viewModel, OpenRouteService openRouteService) {
        this.viewModel = viewModel;
        this.openRouteService = openRouteService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewAddTourTextName.textProperty().bindBidirectional(viewModel.addTourTextNameProperty());
        this.viewAddTourTextDescription.textProperty().bindBidirectional(viewModel.addTourTextDescriptionProperty());
        this.viewAddTourTextStart.textProperty().bindBidirectional(viewModel.addTourTextStartProperty());
        this.viewAddTourTextDestination.textProperty().bindBidirectional(viewModel.addTourTextDestinationProperty());
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

        this.viewAddTourTextType.setItems(FXCollections.observableArrayList("Walking", "Car", "Cycling"));
        this.viewAddTourTextType.valueProperty().bindBidirectional(viewModel.addTourTextType());

        this.picturesView.imageProperty().bindBidirectional(viewModel.pictures);
        // WebEngine webEngine = mapView.getEngine();
        // viewModel.mapContentProperty().addListener((obs, oldContent, newContent) -> webEngine.loadContent(newContent));
        webEngine = mapView.getEngine();
        String leafletMap = openRouteService.getLeafletMap();
        webEngine.loadContent(leafletMap);

        openRouteService.setOnSucceeded(event -> {
            String route = (String) event.getSource().getValue();
            System.out.println("Route JSON: " + route);
            openRouteService.displayRoute(route, webEngine);
        });
        openRouteService.start();
    }
}
