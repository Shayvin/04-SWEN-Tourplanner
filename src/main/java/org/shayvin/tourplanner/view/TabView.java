package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.shayvin.tourplanner.viewmodel.TabViewModel;


import java.net.URL;
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
    private ImageView mapView;


    public TabView(TabViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        this.mapView.imageProperty().bindBidirectional(viewModel.map);


    }
}