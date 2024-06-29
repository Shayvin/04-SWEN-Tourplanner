package org.shayvin.tourplanner.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.shayvin.tourplanner.service.OpenRouteService;
import org.shayvin.tourplanner.viewmodel.TabViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabView implements Initializable {

    private final TabViewModel viewModel;
    private final OpenRouteService openRouteService;

    @FXML
    private TextField viewAddTourTextName;
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
        viewAddTourTextName.textProperty().bindBidirectional(viewModel.addTourTextNameProperty());
        viewAddTourTextDescription.textProperty().bindBidirectional(viewModel.addTourTextDescriptionProperty());
        viewAddTourTextStart.textProperty().bindBidirectional(viewModel.addTourTextStartProperty());
        viewAddTourTextDestination.textProperty().bindBidirectional(viewModel.addTourTextDestinationProperty());
        viewAddTourTextDistance.textProperty().bindBidirectional(viewModel.addTourTextDistanceProperty());
        viewAddTourTextTime.textProperty().bindBidirectional(viewModel.addTourTextTimeProperty());
        viewAddTourTextInformation.textProperty().bindBidirectional(viewModel.addTourTextInformationProperty());

        viewAddTourTextName.disableProperty().bind(viewModel.readOnlyTextNameProperty());
        viewAddTourTextDescription.disableProperty().bind(viewModel.readOnlyTextDescriptionProperty());
        viewAddTourTextStart.disableProperty().bind(viewModel.readOnlyTextStartProperty());
        viewAddTourTextDestination.disableProperty().bind(viewModel.readOnlyTextDestinationProperty());
        viewAddTourTextType.disableProperty().bind(viewModel.readOnlyTextTypeProperty());
        viewAddTourTextDistance.disableProperty().bind(viewModel.readOnlyTextDistanceProperty());
        viewAddTourTextTime.disableProperty().bind(viewModel.readOnlyTextTimeProperty());
        viewAddTourTextInformation.disableProperty().bind(viewModel.readOnlyTextInformationProperty());

        viewAddTourTextType.setItems(FXCollections.observableArrayList("Walking", "Car", "Cycling"));
        viewAddTourTextType.valueProperty().bindBidirectional(viewModel.addTourTextType());

        picturesView.imageProperty().bindBidirectional(viewModel.pictures);

        webEngine = mapView.getEngine();
        String leafletMap = openRouteService.getLeafletMap();
        webEngine.loadContent(leafletMap);

        ChangeListener<String> addressListener = (observable, oldValue, newValue) -> {
            if (!newValue.isBlank()) {
                viewModel.updateMap(webEngine);
                viewModel.updateTextField();
            }
        };

        viewModel.addTourTextStartProperty().addListener(addressListener);
        viewModel.addTourTextDestinationProperty().addListener(addressListener);
        viewModel.addTourTextType().addListener(addressListener);

        openRouteService.start();
    }
}
