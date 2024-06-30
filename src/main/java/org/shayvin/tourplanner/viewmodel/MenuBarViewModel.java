package org.shayvin.tourplanner.viewmodel;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.pdf.PdfBox;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.view.RouteButtonsView;

public class MenuBarViewModel {

    private static final Logger logger = LogManager.getLogger(MenuBarViewModel.class);

    private Stage stage;
    private final Publisher publisher;
    private final CreatePopupService createPopupService;
    private final PdfBox pdfBox;

    public MenuBarViewModel(Publisher publisher, PdfBox pdfBox, CreatePopupService createPopupService) {
        this.publisher = publisher;
        this.pdfBox = pdfBox;
        this.createPopupService = createPopupService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void exitProgram() {
        logger.info("Exiting program");
        Platform.exit();
    }

    public void exportPdf() {
        logger.info("Exporting pdf");
        try{
            pdfBox.exportToPDF("tour.pdf");
            createPopup("successful");
        }catch(Exception e){
            createPopup("failure");
            logger.error(e);
        }
    }

    public void changeFont(String font, Scene currentScene){
        if (currentScene != null) {
            logger.info("changing font to {}", font);
            currentScene.getRoot().setStyle("-fx-font-family: '" + font + "'; -fx-font-size: 12px;");
        }
    }

    public void createPopup(String message) {
        if(message.equals("successful")){
            createPopupService.createPopup("notification-popup-successful-view.fxml", 300, 100);
        }else{
            createPopupService.createPopup("notification-popup-failure-view.fxml", 300, 100);
        }
    }
}
