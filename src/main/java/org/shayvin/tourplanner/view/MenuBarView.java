package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.MenuBarViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarView implements Initializable {

    private final Publisher publisher;
    private final MenuBarViewModel viewModel;

    public MenuBarView(Publisher publisher) {
        this.publisher = publisher;
        this.viewModel = new MenuBarViewModel(publisher);
    }

    @FXML
    private void exitProgram(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
