package org.shayvin.tourplanner.view;

import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.mainViewModel;
import org.shayvin.tourplanner.viewmodel.tabViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class mainView implements Initializable {

    private final mainViewModel viewModel;

    public mainView(Publisher publisher) {
        this.viewModel = new mainViewModel(publisher);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
