package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.TableButtonViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TableButtonView implements Initializable {

    private final Publisher publisher;
    private final TableButtonViewModel viewModel;

    @FXML
    private Button tourLogAddButton;

    @FXML
    private Button tourLogDeleteButton;

    @FXML
    private Button tourLogEditButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tourLogAddButton.disableProperty().bind(viewModel.TourLogAddButtonProperty());
        this.tourLogDeleteButton.disableProperty().bind(viewModel.TourLogDeleteButtonProperty());
        this.tourLogEditButton.disableProperty().bind(viewModel.TourLogDeleteButtonProperty());

    }

    public TableButtonView(Publisher publisher, TableButtonViewModel tableButtonViewModel) {
        this.publisher = publisher;
        this.viewModel = tableButtonViewModel;
    }

    public void onAdd() {
        System.out.println("in onAdd");
        this.viewModel.addTourLogEvent();
    }

    public void onDelete() {
        this.viewModel.deleteTourLogEvent();
    }

    public void onEdit() {
        this.viewModel.editTourLogEvent();
    }
}
