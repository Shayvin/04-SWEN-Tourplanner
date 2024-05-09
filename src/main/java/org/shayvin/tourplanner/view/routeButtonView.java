package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.routeButtonViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class routeButtonView implements Initializable {

    private final Publisher publisher;
    private final routeButtonViewModel viewModel;

    @FXML
    public Button addRouteButton;

    public routeButtonView(routeButtonViewModel routeViewModel, Publisher publisher) {
        this.viewModel = routeViewModel;
        this.publisher = publisher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.addRouteButton.disableProperty()
                .bind(viewModel.addRouteButtonDisabledProperty());

        this.addRouteButton.setOnAction(event -> viewModel.addButtonClickedEvent());
    }
}
