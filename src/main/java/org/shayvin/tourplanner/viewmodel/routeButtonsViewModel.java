package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class routeButtonsViewModel {

    private final Publisher publisher;

    private final BooleanProperty addDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty removeDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty editDisabled = new SimpleBooleanProperty(true);


    public routeButtonsViewModel(Publisher publisher){
        this.publisher = publisher;
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

    public BooleanProperty addDisabledProperty() {
        return addDisabled;
    }

    public boolean getAddDisabled() {
        return addDisabled.get();
    }

    public boolean isAddDisabled() {
        return addDisabled.get();
    }

    public BooleanProperty removeDisabledProperty() {
        return removeDisabled;
    }

    public boolean getRemoveDisabled() {
        return removeDisabled.get();
    }

    public boolean isRemoveDisabled() {
        return removeDisabled.get();
    }

    public BooleanProperty editDisabledProperty() {
        return editDisabled;
    }

    public boolean getEditDisabled() {
        return editDisabled.get();
    }

    public boolean isEditDisabled() {
        return editDisabled.get();
    }

}
