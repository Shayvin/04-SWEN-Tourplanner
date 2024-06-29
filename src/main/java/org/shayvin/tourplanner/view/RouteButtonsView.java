package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.viewmodel.RouteButtonsViewModel;
import org.shayvin.tourplanner.viewmodel.TourLogPopupViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteButtonsView implements Initializable {

    private static final Logger logger = LogManager.getLogger(RouteButtonsView.class);

    private final RouteButtonsViewModel viewModel;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private Button saveButton;


    public RouteButtonsView(RouteButtonsViewModel viewModel) {
        this.viewModel = viewModel;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.addButton.disableProperty().bind(viewModel.addDisabledProperty());
        this.removeButton.disableProperty().bind(viewModel.removeDisabledProperty());
        this.editButton.disableProperty().bind(viewModel.editDisabledProperty());
        this.saveButton.disableProperty().bind(viewModel.saveDisabledProperty());
    }

    public void onAdd(){
        logger.info("Add route button pressed");
        this.viewModel.add();
    }

    public void onRemove(){
        logger.info("Remove route button pressed");
        this.viewModel.remove();
    }

    public void onEdit(){
        logger.info("Edit route button pressed");
        this.viewModel.edit();
    }

    public void onSave(){
        logger.info("Save route button pressed");
        this.viewModel.save();
    }
}

