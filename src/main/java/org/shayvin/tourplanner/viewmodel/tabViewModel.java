package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.*;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

import java.util.ArrayList;
import java.util.List;

public class tabViewModel {

    private final Publisher publisher;

    private List<Event> eventList;

    private final StringProperty addTourTextName = new SimpleStringProperty("");
    private final StringProperty addTourTextDescription = new SimpleStringProperty("");
    private final StringProperty addTourTextStart = new SimpleStringProperty("");
    private final StringProperty addTourTextDestination = new SimpleStringProperty("");
    private final StringProperty addTourTextType = new SimpleStringProperty("");
    private final IntegerProperty addTourTextDistance = new SimpleIntegerProperty();
    private final IntegerProperty addTourTextTime = new SimpleIntegerProperty();
    private final StringProperty addTourTextInformation = new SimpleStringProperty("");

    public tabViewModel(Publisher publisher) {
        this.publisher = publisher;
        this.eventList = new ArrayList<>();

        this.addTourTextName.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_NAME_ADDED, "TourTextName added");
                    addEventListEntry(Event.TOUR_NAME_ADDED);
                }
        );

        this.addTourTextDescription.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_DESCRIPTION_ADDED, "TourTextDescription added");
                    addEventListEntry(Event.TOUR_DESCRIPTION_ADDED);
                }
        );

        this.addTourTextStart.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_START_ADDED, "TourTextStart added");
                    addEventListEntry(Event.TOUR_START_ADDED);
                }
        );

        this.addTourTextDestination.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_DESTINATION_ADDED, "TourTextDestination added");
                    addEventListEntry(Event.TOUR_DESTINATION_ADDED);
                }
        );

        this.addTourTextType.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_TYPE_ADDED, "TourTextType added");
                    addEventListEntry(Event.TOUR_TYPE_ADDED);
                }
        );

        this.addTourTextDistance.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_DISTANCE_ADDED, "TourTextDistance added");
                    addEventListEntry(Event.TOUR_DISTANCE_ADDED);
                }
        );

        this.addTourTextTime.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_TIME_ADDED, "TourTextTime added");
                    addEventListEntry(Event.TOUR_TIME_ADDED);
                }
        );

        this.addTourTextInformation.addListener(
                observable -> {
                    publisher.publish(Event.TOUR_INFORMATION_ADDED, "TourTextInformation added");
                    addEventListEntry(Event.TOUR_INFORMATION_ADDED);
                }
        );


        this.publisher.subscribe(Event.ADD_TOUR, message -> {
            System.out.println("Received message: " + message);
        });

        //TODO add Data to TourRepo when ADD_TOUR is listened to


    }

    public void addEventListEntry(Event event){
        if(!eventList.contains(event)){
            eventList.add(event);
        }
        if(eventList.size()==8){
            validateInputs();
        }
    }

    public void addTour() {
        publisher.publish(Event.ADD_TOUR, "aaaaa");
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

    public Integer getAddTourTextDistance() {
        return addTourTextDistance.get();
    }

    public IntegerProperty addTourTextDistanceProperty() {
        return addTourTextDistance;
    }

    public Integer getAddTourTextTime() {
        return addTourTextTime.get();
    }

    public IntegerProperty addTourTextTimeProperty() {
        return addTourTextTime;
    }

    public String getAddTourTextInformation() {
        return addTourTextInformation.get();
    }

    public StringProperty addTourTextInformationProperty() {
        return addTourTextInformation;
    }

    public void validateInputs(){

        validateInputString(addTourTextName.get());
        validateInputString(addTourTextDescription.get());
        validateInputString(addTourTextStart.get());
        validateInputString(addTourTextDestination.get());
        validateInputString(addTourTextType.get());
        validateInputString(addTourTextInformation.get());
        validateInputInteger(addTourTextDistance.get());
        validateInputString(addTourTextTime.get());




    }

    public boolean validateInputString(String input){
        return false;
    }

    public boolean validateInputInteger(Integer input){
        return false;
    }


}
