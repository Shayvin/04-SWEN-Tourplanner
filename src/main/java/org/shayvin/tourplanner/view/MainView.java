package org.shayvin.tourplanner.view;

import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.MainViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable {

    private final MainViewModel viewModel;

    public MainView(Publisher publisher) {
        this.viewModel = new MainViewModel(publisher);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
