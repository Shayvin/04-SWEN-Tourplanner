package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class TableButtonViewModel {

    private final Publisher publisher;

    private final BooleanProperty tourLogAddButton = new SimpleBooleanProperty(true);
    private final BooleanProperty tourLogDeleteButton = new SimpleBooleanProperty(true);
    private final BooleanProperty tourLogEditButton = new SimpleBooleanProperty(true);

    public TableButtonViewModel(Publisher publisher) {
        this.publisher = publisher;

        publisher.subscribe(Event.DELETE_TOUR_LOG_BUTTON_VISIBILITY, (message) -> {
            changeDeleteButtonVisibility(Boolean.valueOf(message));
        });

        publisher.subscribe(Event.ADD_TOUR_LOG_BUTTON_VISIBILITY, (message) -> {
            changeAddButtonVisibility(Boolean.valueOf(message));
        });

        publisher.subscribe(Event.EDIT_TOUR_LOG_BUTTON_VISIBILITY, (message) -> {
            changeEditButtonVisibility(Boolean.valueOf(message));
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

    public void changeAddButtonVisibility(Boolean visibility) { tourLogAddButton.set(visibility); }

    public void changeEditButtonVisibility(Boolean visibility) { tourLogEditButton.set(visibility); }

    public void editTourLogEvent() { publisher.publish(Event.EDIT_TOUR_LOG, "Edit Tour Log Button Clicked"); }


}
