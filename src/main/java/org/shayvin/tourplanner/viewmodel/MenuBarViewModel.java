package org.shayvin.tourplanner.viewmodel;

import javafx.stage.Stage;
import org.shayvin.tourplanner.event.Publisher;

public class MenuBarViewModel {
    private Stage stage;
    private final Publisher publisher;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MenuBarViewModel(Publisher publisher) {
        this.publisher = publisher;
    }

    public void exitProgram() {
        stage.close();
    }
}
