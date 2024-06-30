package org.shayvin.tourplanner.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.pdf.PdfBox;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.viewmodel.MenuBarViewModel;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarView implements Initializable {

    private final Publisher publisher;
    private final MenuBarViewModel viewModel;
    private final PdfBox pdfBox;
    private Scene currentScene;

    @FXML
    private MenuBar menuBar;

    public MenuBarView(Publisher publisher, PdfBox pdfBox, MenuBarViewModel viewModel) {
        this.publisher = publisher;
        this.pdfBox = pdfBox;
        this.viewModel = viewModel;
    }

    @FXML
    private void exitProgram(){
        viewModel.exitProgram();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void exportPdf(ActionEvent actionEvent) {
        viewModel.exportPdf();
    }

    @FXML
    public void fontArial(ActionEvent event){
        currentScene = getCurrentScene(event);
        viewModel.changeFont("Arial", currentScene);
    }

    @FXML
    public void fontVerdana(ActionEvent event){
        currentScene = getCurrentScene(event);
        viewModel.changeFont("Verdana", currentScene);
    }

    @FXML
    public void fontCourierNew(ActionEvent event){
        currentScene = getCurrentScene(event);
        viewModel.changeFont("CourierNew", currentScene);
    }

    private Scene getCurrentScene(ActionEvent event) {
        return menuBar.getScene();
    }

}
