package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.menuBarViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class menuBarView implements Initializable {

    private final Publisher publisher;
    private final menuBarViewModel viewModel;

    public menuBarView(Publisher publisher) {
        this.publisher = publisher;
        this.viewModel = new menuBarViewModel(publisher);
    }

    @FXML
    private void exitProgram(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
