package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.shayvin.tourplanner.viewmodel.AddTourButtonViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTourButtonView implements Initializable {

    private final AddTourButtonViewModel viewModel;

    // Add Tour Dialog
    @FXML
    private TextField addTourTextName;
    @FXML
    private TextField addTourTextDescription;
    @FXML
    private TextField addTourTextStart;
    @FXML
    private TextField addTourTextDestination;
    @FXML
    private TextField addTourTextType;
    @FXML
    private TextField addTourTextDistance;
    @FXML
    private TextField addTourTextTime;
    @FXML
    private TextField addTourTextInformation;

    // Add Tour Button
    @FXML
    private Button addTourButton;
    public AddTourButtonView() {
        this.viewModel = new AddTourButtonViewModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTourButton.setOnAction(actionEvent -> viewModel.onAddTourButtonClicked());
        this.addTourTextName.textProperty().bindBidirectional(viewModel.addTourTextNameProperty());
        this.addTourTextDescription.textProperty().bindBidirectional(viewModel.addTourTextDescriptionProperty());
        this.addTourTextStart.textProperty().bindBidirectional(viewModel.addTourTextStartProperty());
        this.addTourTextDestination.textProperty().bindBidirectional(viewModel.addTourTextDestinationProperty());
        this.addTourTextType.textProperty().bindBidirectional(viewModel.addTourTextTypeProperty());
        this.addTourTextDistance.textProperty().bindBidirectional(viewModel.addTourTextDistanceProperty());
        this.addTourTextTime.textProperty().bindBidirectional(viewModel.addTourTextTimeProperty());
        this.addTourTextInformation.textProperty().bindBidirectional(viewModel.addTourTextInformationProperty());
    }
}

