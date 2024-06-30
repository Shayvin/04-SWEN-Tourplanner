package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class RouteButtonsViewModel {

    private static final Logger logger = LogManager.getLogger(RouteButtonsViewModel.class);

    private final Publisher publisher;

    private final BooleanProperty addDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty removeDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty editDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty saveDisabled = new SimpleBooleanProperty(true);


    public RouteButtonsViewModel(Publisher publisher){
        this.publisher = publisher;

        publisher.subscribe(Event.ENABLE_ADD_BUTTON, message -> {
            addDisabled.set(false);
        });
        publisher.subscribe(Event.DISABLE_ADD_BUTTON, message -> {
            addDisabled.set(true);
        });
        publisher.subscribe(Event.DISABLE_REMOVE_BUTTON, message -> {
            removeDisabled.set(true);
        });
        publisher.subscribe(Event.DISABLE_EDIT_BUTTON, message -> {
            editDisabled.set(true);
        });
        publisher.subscribe(Event.TOUR_SELECTED, message -> {
            editDisabled.set(false);
            removeDisabled.set(false);
        });
        publisher.subscribe(Event.EDIT_TOUR, message -> {
            saveDisabled.set(false);
            addDisabled.set(true);
        });
        publisher.subscribe(Event.LIST_UPDATED, message -> {
            logger.info("Updating tour list");
            addDisabled.set(true);
            editDisabled.set(true);
            removeDisabled.set(true);
            saveDisabled.set(true);
        });
        publisher.subscribe(Event.TOUR_UNSELECTED, message -> {
            logger.info("Unselected tour list");
            saveDisabled.set(true);
            editDisabled.set(true);
            removeDisabled.set(true);
        });
    }

    public void add(){
        publisher.publish(Event.ADD_TOUR, "Add tour button clicked");
    }

    public void remove(){
        publisher.publish(Event.REMOVE_TOUR, "Remove tour button clicked");
    }

    public void edit(){
        publisher.publish(Event.EDIT_TOUR, "Edit tour button clicked");
    }

    public void save(){
        publisher.publish(Event.SAVE_EDITED_TOUR, "Save tour button clicked");
    }


    public BooleanProperty addDisabledProperty() {
        return addDisabled;
    }

    public BooleanProperty removeDisabledProperty() {
        return removeDisabled;
    }

    public BooleanProperty editDisabledProperty() {
        return editDisabled;
    }

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }
}
