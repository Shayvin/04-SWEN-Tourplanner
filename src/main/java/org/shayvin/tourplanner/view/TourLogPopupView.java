package org.shayvin.tourplanner.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.viewmodel.TourLogPopupViewModel;
import org.w3c.dom.Text;

public class TourLogPopupView {

    private final Publisher publisher;
    private final TourLogPopupViewModel tourLogPopupViewModel;

    @FXML
    private TextField distanceTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField commentTextArea;
    @FXML
    private TextField difficultyTextField;
    @FXML
    private RadioButton star1RadioButton;
    @FXML
    private RadioButton star2RadioButton;
    @FXML
    private RadioButton star3RadioButton;
    @FXML
    private RadioButton star4RadioButton;
    @FXML
    private RadioButton star5RadioButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button editButton;

    public TourLogPopupView(Publisher publisher, TourLogPopupViewModel tourLogPopupViewModel) {
        this.publisher = publisher;
        this.tourLogPopupViewModel = tourLogPopupViewModel;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void onSubmit() {
        String rating = "";
        if (star1RadioButton.isSelected()) {
            rating = "1";
        } else if (star2RadioButton.isSelected()) {
            rating = "2";
        } else if (star3RadioButton.isSelected()) {
            rating = "3";
        } else if (star4RadioButton.isSelected()) {
            rating = "4";
        } else if (star5RadioButton.isSelected()) {
            rating = "5";
        }

        TourLog tourLog = new TourLog(dateTextField.getText(), Double.parseDouble(durationTextField.getText()), Double.parseDouble(distanceTextField.getText()), commentTextArea.getText(), Double.parseDouble(difficultyTextField.getText()), Integer.parseInt(rating));
        tourLogPopupViewModel.submitTourLog(tourLog);

        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    public void onEdit() {
        String rating = "";
        if (star1RadioButton.isSelected()) {
            rating = "1";
        } else if (star2RadioButton.isSelected()) {
            rating = "2";
        } else if (star3RadioButton.isSelected()) {
            rating = "3";
        } else if (star4RadioButton.isSelected()) {
            rating = "4";
        } else if (star5RadioButton.isSelected()) {
            rating = "5";
        }

        TourLog tourLog = new TourLog(dateTextField.getText(), Double.parseDouble(durationTextField.getText()), Double.parseDouble(distanceTextField.getText()), commentTextArea.getText(), Double.parseDouble(difficultyTextField.getText()), Integer.parseInt(rating));

        tourLogPopupViewModel.editTourLog(tourLog);

        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }
}
