package org.shayvin.tourplanner.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.shayvin.tourplanner.FXMLDependencyInjector;

import java.util.Locale;

public class CreatePopupService {

    public void createPopup(String name, double width, double height) {
        try {
            Parent popupView = FXMLDependencyInjector.load(name, Locale.ENGLISH);
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(popupView));
            popupStage.setWidth(width);
            popupStage.setHeight(height);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Think about this, currently hard to add a popup as the validation starts on the first input
    //      maybe with a timer (wait 2 seconds)?

}
