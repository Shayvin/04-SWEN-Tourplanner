package org.shayvin.tourplanner.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.event.Subscriber;

public class routeButtonViewModel {

    private final Publisher publisher;

    private final BooleanProperty addRouteButtonDisabled = new SimpleBooleanProperty(true);
    private final tabViewModel tabViewModel;

    public routeButtonViewModel(Publisher publisher, tabViewModel tabViewModel) {
        this.publisher = publisher;
        this.tabViewModel = tabViewModel;

        this.publisher.subscribe(Event.ADD_TOUR, message -> {
            System.out.println("Received message: " + message);
            addRouteButtonDisabled.set(false);
        });
    }

    public BooleanProperty addRouteButtonDisabledProperty() {
        return addRouteButtonDisabled;
    }

    public void addButtonClickedEvent() {
        publisher.publish(Event.ADD_ROUTE_BUTTON_CLICKED, "Add Route Button clicked");
    }
}
