package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class tableButtonViewModel {

    private final Publisher publisher;

    private final BooleanProperty tourLogAddButton = new SimpleBooleanProperty(false);
    private final BooleanProperty tourLogDeleteButton = new SimpleBooleanProperty(true);

    public tableButtonViewModel(Publisher publisher) {
        this.publisher = publisher;

        publisher.subscribe(Event.DELETE_TOUR_LOG_BUTTON_VISIBILITY, (message) -> {
            changeDeleteButtonVisibility(Boolean.valueOf(message));
        });
    }

    public BooleanProperty TourLogAddButtonProperty() {
        return tourLogAddButton;
    }
    public BooleanProperty TourLogDeleteButtonProperty() {
        return tourLogDeleteButton;
    }

    public void deleteTourLogEvent() {
        publisher.publish(Event.DELETE_TOUR_LOG, "Delete Tour Log Button Clicked");
    }

    public void addTourLogEvent() {
        publisher.publish(Event.ADD_TOUR_LOG, "Add Tour Log Button Clicked");
    }

    public void changeDeleteButtonVisibility(Boolean visibility) {
        tourLogDeleteButton.set(visibility);
    }
}
