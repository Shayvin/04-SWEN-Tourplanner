package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.tableButtonViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class tableButtonView implements Initializable {

    private final Publisher publisher;
    private final tableButtonViewModel viewModel;

    @FXML
    private Button tourLogAddButton;

    @FXML
    private Button tourLogDeleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tourLogAddButton.disableProperty().bind(viewModel.TourLogAddButtonProperty());
        this.tourLogDeleteButton.disableProperty().bind(viewModel.TourLogDeleteButtonProperty());
    }

    public tableButtonView(Publisher publisher, tableButtonViewModel tableButtonViewModel) {
        this.publisher = publisher;
        this.viewModel = tableButtonViewModel;
    }

    public void onAdd() {
        this.viewModel.addTourLogEvent();
    }

    public void onDelete() {
        this.viewModel.deleteTourLogEvent();
    }
}
