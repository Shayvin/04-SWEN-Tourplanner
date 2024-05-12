package org.shayvin.tourplanner.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.tableButtonViewModel;
import org.shayvin.tourplanner.viewmodel.tourLogViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

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

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 0) {
                tableView.setItems(viewModel.getTourLogs());
            }
        });

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TourLog>) change -> {
            handleTourLogSelectionChanged();
        });
        setupCellFactories();
    }

    @FXML
    public void handleTourLogSelectionChanged() {
        TourLog selectedTourLog = tableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            viewModel.selectedTourEvent(selectedTourLog);
            viewModel.setSelectedTourLog(selectedTourLog);
        }
    }

    private void setupCellFactories() {
        StringConverter<String> stringConverter = new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        };

        dateColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidDate(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });

        durationColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidInt(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });

        distanceColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidInt(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });

        commentColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidString(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });

        difficultyColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidString(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });

        ratingColumn.setCellFactory(column -> new TextFieldTableCell<>(stringConverter) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item == null || item.isEmpty() || !isValidInt(item)) {
                    if(!empty){
                        setText("");
                    }
                } else {
                    setStyle(""); // Reset to default style
                }
            }
        });
    }



    private boolean isValidString(String input) {
        return input.matches("[a-zA-Z]+");
    }

    private boolean isValidInt(String input) {
        return input.matches("[0-9]+");
    }

    private boolean isValidDate(String input) {
        return input.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
    }

}
