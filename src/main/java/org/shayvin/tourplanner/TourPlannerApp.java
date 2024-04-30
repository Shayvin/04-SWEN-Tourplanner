package org.shayvin.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class TourPlannerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainView = FXMLDependencyInjector.load("main-view.fxml", Locale.ENGLISH);
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}