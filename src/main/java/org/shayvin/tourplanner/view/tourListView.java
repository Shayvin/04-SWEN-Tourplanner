package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.shayvin.tourplanner.viewmodel.tourListViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class tourListView implements Initializable {

    private final tourListViewModel tourListViewModel;

    @FXML
    private ListView<String> tourList;


    public tourListView(tourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tourList.setItems(tourListViewModel.getTourList());
        this.tourListViewModel.selectedTourIndexProperty().bind(tourList.getSelectionModel().selectedIndexProperty());

        // unselect tour if mouse click is registered in list without an element underneath
        tourList.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!(event.getTarget() instanceof ListCell<?>)) {
                tourList.getSelectionModel().clearSelection();
            } else {
                ListCell<?> cell = (ListCell<?>) event.getTarget();
                if (cell.isEmpty()) {
                    tourList.getSelectionModel().clearSelection();
                    tourListViewModel.unselectTour();
                }
            }
        });

    }
}
