package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.routeButtonsViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class routeButtonsView implements Initializable {

    private final routeButtonsViewModel viewModel;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private Button saveButton;


    public routeButtonsView(routeButtonsViewModel viewModel) {
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
        this.viewModel.add();
    }

    public void onRemove(){
        this.viewModel.remove();
    }

    public void onEdit(){
        this.viewModel.edit();
    }

    public void onSave(){
        this.viewModel.save();
    }
}
