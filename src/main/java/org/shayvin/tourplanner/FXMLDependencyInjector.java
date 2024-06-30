package org.shayvin.tourplanner;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjector {

    public static Parent load(String location, Locale locale) throws IOException {
        FXMLLoader loader = loader(location, locale);

        return loader.load();
    }

    // This method is used to create a new FXMLLoader instance with the given location and locale
    public static FXMLLoader loader(String location, Locale locale) {
        return new FXMLLoader(
                FXMLDependencyInjector.class.getResource("/org/shayvin/tourplanner/" + location),
                ResourceBundle.getBundle("org.shayvin.tourplanner." + "gui_strings", locale),
                new JavaFXBuilderFactory(),
                viewClass -> ViewFactory.getInstance().create(viewClass)
        );
    }
}
