package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class tabViewModel {

    private final Publisher publisher;

    private final StringProperty addTourTextName = new SimpleStringProperty("");
    private final StringProperty addTourTextDescription = new SimpleStringProperty("");
    private final StringProperty addTourTextStart = new SimpleStringProperty("");
    private final StringProperty addTourTextDestination = new SimpleStringProperty("");
    private final StringProperty addTourTextType = new SimpleStringProperty("");
    private final StringProperty addTourTextDistance = new SimpleStringProperty("");
    private final StringProperty addTourTextTime = new SimpleStringProperty("");
    private final StringProperty addTourTextInformation = new SimpleStringProperty("");

    public tabViewModel(Publisher publisher) {
        this.publisher = publisher;

        addListenerToProperty(addTourTextName);
        addListenerToProperty(addTourTextDescription);
        addListenerToProperty(addTourTextStart);
        addListenerToProperty(addTourTextDestination);
        addListenerToProperty(addTourTextType);
        addListenerToProperty(addTourTextDistance);
        addListenerToProperty(addTourTextTime);
        addListenerToProperty(addTourTextInformation);

        this.publisher.subscribe(Event.ADD_TOUR, message -> {
            System.out.println("Received message: " + message);
        });
    }

    private void addListenerToProperty(StringProperty property) {
        property.addListener((obs, oldValue, newValue) -> {
            addTour();
        });
    }

    public void addTour() {
        publisher.publish(Event.ADD_TOUR, "text filled");
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
