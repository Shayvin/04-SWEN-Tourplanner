package org.shayvin.tourplanner.view;

import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;

import java.net.URL;
import java.util.ResourceBundle;

public class tourListView implements Initializable {

    private final Publisher publisher;

    public tourListView(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
