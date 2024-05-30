package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class RouteButtonsViewModel {

    private final Publisher publisher;

    private final BooleanProperty addDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty removeDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty editDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty saveDisabled = new SimpleBooleanProperty(true);


    public RouteButtonsViewModel(Publisher publisher){
        this.publisher = publisher;

        publisher.subscribe(Event.ENABLE_ADD_BUTTON, message -> {
            System.out.println("Subscribe message received for add button:" + message);
            addDisabled.set(false);
        });
        publisher.subscribe(Event.DISABLE_ADD_BUTTON, message -> {
            System.out.println("Subscribe message received for add button:" + message);
            addDisabled.set(true);
        });
        publisher.subscribe(Event.DISABLE_REMOVE_BUTTON, message -> {
            System.out.println("Subscribe message received for remove button:" + message);
            removeDisabled.set(true);
        });
        publisher.subscribe(Event.DISABLE_EDIT_BUTTON, message -> {
            System.out.println("Subscribe message received for edit button:" + message);
            editDisabled.set(true);
        });
        publisher.subscribe(Event.TOUR_SELECTED, message -> {
            System.out.println("Subscribe message received for edit button:" + message);
            editDisabled.set(false);
            removeDisabled.set(false);
        });
        publisher.subscribe(Event.EDIT_TOUR, message -> {
            System.out.println("Subscribe message received for save button:" + message);
            saveDisabled.set(false);
            addDisabled.set(true);
        });
        publisher.subscribe(Event.LIST_UPDATED, message -> {
            System.out.println("Subscribe message received for update:" + message);
            addDisabled.set(true);
            editDisabled.set(true);
            removeDisabled.set(true);
            saveDisabled.set(true);
        });
        publisher.subscribe(Event.TOUR_UNSELECTED, message -> {
            System.out.println("Subscribe message received for save button:" + message);
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

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }

    public boolean getSaveDisabled() {
        return saveDisabled.get();
    }

    public boolean isSaveDisabled() {
        return saveDisabled.get();
    }
}
