package org.shayvin.tourplanner.viewmodel;

import javafx.stage.Stage;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.pdf.PdfBox;

public class MenuBarViewModel {
    private Stage stage;
    private final Publisher publisher;
    private final PdfBox pdfBox;

    public MenuBarViewModel(Publisher publisher, PdfBox pdfBox) {
        this.publisher = publisher;
        this.pdfBox = pdfBox;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void exitProgram() {
        stage.close();
    }

    public void exportPdf() {
        pdfBox.exportToPDF("tour.pdf");
    }
}
