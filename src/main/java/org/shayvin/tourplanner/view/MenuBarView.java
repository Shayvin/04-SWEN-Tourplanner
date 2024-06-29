package org.shayvin.tourplanner.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.pdf.PdfBox;
import org.shayvin.tourplanner.viewmodel.MenuBarViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarView implements Initializable {

    private final Publisher publisher;
    private final MenuBarViewModel viewModel;
    private final PdfBox pdfBox;

    public MenuBarView(Publisher publisher, PdfBox pdfBox) {
        this.publisher = publisher;
        this.pdfBox = pdfBox;
        this.viewModel = new MenuBarViewModel(publisher, pdfBox);
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
}
