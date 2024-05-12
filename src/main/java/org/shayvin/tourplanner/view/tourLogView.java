package org.shayvin.tourplanner.view;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.tableButtonViewModel;
import org.shayvin.tourplanner.viewmodel.tourLogViewModel;

import java.awt.event.ActionEvent;

public class tourLogView {

    private final tourLogViewModel viewModel;
    private final tableButtonViewModel tableButtonViewModel;
    private final Publisher publisher;

    @FXML
    private TableView<TourLog> tableView;

    @FXML
    private TableColumn<TourLog, String> dateColumn;

    @FXML
    private TableColumn<TourLog, String> durationColumn;

    @FXML
    private TableColumn<TourLog, String> distanceColumn;

    @FXML
    private TableColumn<TourLog, String> commentColumn;

    @FXML
    private TableColumn<TourLog, String> difficultyColumn;

    @FXML
    private TableColumn<TourLog, String> ratingColumn;

    public tourLogView(tourLogViewModel tourLogViewModel, tableButtonViewModel tourButtonViewModel, Publisher publisher) {
        this.viewModel = tourLogViewModel;
        this.tableButtonViewModel = tourButtonViewModel;
        this.publisher = publisher;
    }

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableView.setItems(viewModel.getTourLogs());

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TourLog>) change -> {
            handleTourLogSelectionChanged();
        });
    }

    @FXML
    public void handleTourLogSelectionChanged() {
        TourLog selectedTourLog = tableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            viewModel.selectedTourEvent(selectedTourLog);
            viewModel.setSelectedTourLog(selectedTourLog);
        }
    }

}
