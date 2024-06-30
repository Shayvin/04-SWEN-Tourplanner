package org.shayvin.tourplanner.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.FXMLDependencyInjector;
import org.shayvin.tourplanner.viewmodel.TabViewModel;

import java.util.Locale;

public class CreatePopupService {

    private static final Logger logger = LogManager.getLogger(CreatePopupService.class);

    public CreatePopupService() {
    }

    public void createPopup(String name, double width, double height) {
        try {
            Parent popupView = FXMLDependencyInjector.load(name, Locale.ENGLISH);
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(popupView));
            popupStage.setWidth(width);
            popupStage.setHeight(height);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
            logger.info("Popup created: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
