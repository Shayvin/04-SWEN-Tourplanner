package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddTourButtonViewModel {

    private final StringProperty addTourTextName = new SimpleStringProperty("");
    private final StringProperty addTourTextDescription = new SimpleStringProperty("");
    private final StringProperty addTourTextStart = new SimpleStringProperty("");
    private final StringProperty addTourTextDestination = new SimpleStringProperty("");
    private final StringProperty addTourTextType = new SimpleStringProperty("");
    private final StringProperty addTourTextDistance = new SimpleStringProperty("");
    private final StringProperty addTourTextTime = new SimpleStringProperty("");
    private final StringProperty addTourTextInformation = new SimpleStringProperty("");

    public void onAddTourButtonClicked() {
        System.out.println("Add tour button clicked");
    }

    public String getAddTourTextName() {
        return addTourTextName.get();
    }

    public StringProperty addTourTextNameProperty() {
        return addTourTextName;
    }

    public String getAddTourTextDescription() {
        return addTourTextDescription.get();
    }

    public StringProperty addTourTextDescriptionProperty() {
        return addTourTextDescription;
    }

    public String getAddTourTextStart() {
        return addTourTextStart.get();
    }

    public StringProperty addTourTextStartProperty() {
        return addTourTextStart;
    }

    public String getAddTourTextDestination() {
        return addTourTextDestination.get();
    }

    public StringProperty addTourTextDestinationProperty() {
        return addTourTextDestination;
    }

    public String getAddTourTextType() {
        return addTourTextType.get();
    }

    public StringProperty addTourTextTypeProperty() {
        return addTourTextType;
    }

    public String getAddTourTextDistance() {
        return addTourTextDistance.get();
    }

    public StringProperty addTourTextDistanceProperty() {
        return addTourTextDistance;
    }

    public String getAddTourTextTime() {
        return addTourTextTime.get();
    }

    public StringProperty addTourTextTimeProperty() {
        return addTourTextTime;
    }

    public String getAddTourTextInformation() {
        return addTourTextInformation.get();
    }

    public StringProperty addTourTextInformationProperty() {
        return addTourTextInformation;
    }
}
