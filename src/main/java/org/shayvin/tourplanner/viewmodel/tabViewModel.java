package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.*;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.TourService;

import java.util.ArrayList;
import java.util.List;

public class tabViewModel {

    private final Publisher publisher;
    private final TourService tourService;

    private List<Event> eventList;

    private final StringProperty addTourTextName = new SimpleStringProperty("");
    private final StringProperty addTourTextDescription = new SimpleStringProperty("");
    private final StringProperty addTourTextStart = new SimpleStringProperty("");
    private final StringProperty addTourTextDestination = new SimpleStringProperty("");
    private final StringProperty addTourTextType = new SimpleStringProperty("");
    private final StringProperty addTourTextDistance = new SimpleStringProperty("");
    private final StringProperty addTourTextTime = new SimpleStringProperty("");
    private final StringProperty addTourTextInformation = new SimpleStringProperty("");

    public tabViewModel(Publisher publisher, TourService tourService) {
        this.publisher = publisher;
        this.tourService = tourService;
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


        this.publisher.subscribe(Event.ADD_TOUR, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_NAME_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_DESCRIPTION_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_START_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_DESTINATION_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_TYPE_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_DISTANCE_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_TIME_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.TOUR_INFORMATION_ADDED, message -> System.out.println("Received message: " + message));

        this.publisher.subscribe(Event.ENABLE_ADD_BUTTON, message -> System.out.println("Received message: " + message));

        //TODO add Data to TourRepo when ADD_TOUR is listened to
        publisher.subscribe(Event.ADD_TOUR, message -> {
            System.out.println("Received message: " + message);
            tourService.add(
                    addTourTextName.get(),
                    addTourTextDescription.get(),
                    addTourTextStart.get(),
                    addTourTextDestination.get(),
                    addTourTextType.get(),
                    addTourTextDistance.get(),
                    addTourTextTime.get(),
                    addTourTextInformation.get());
        });

    }

    public void addEventListEntry(Event event){
        System.out.println("Adding event list entry: " + event);
        if(!eventList.contains(event)){
            eventList.add(event);
        }
        if(eventList.size()==8){
            System.out.println("Event list contains more than 8 events");
            validateInputs();
        }
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

    public void validateInputs(){
        publisher.publish(Event.DISABLE_ADD_BUTTON, "Add Button disabled.");

        System.out.println("in validateInputs");
        int counter = 0;

        if(!validateInputString(addTourTextName.get())){
            System.out.println("Please enter a valid TourTextName");
            ++counter;
        }
        if(!validateInputString(addTourTextDescription.get())){
            System.out.println("Please enter a valid TourTextDescription");
            ++counter;
        }
        if(!validateInputString(addTourTextStart.get())){
            System.out.println("Please enter a valid TourTextStart");
            ++counter;
        }
        if(!validateInputString(addTourTextDestination.get())){
            System.out.println("Please enter a valid TourTextDestination");
            ++counter;
        }
        if(!validateInputString(addTourTextType.get())){
            System.out.println("Please enter a valid TourTextType");
            ++counter;
        }
        if(!validateInputString(addTourTextInformation.get())){
            System.out.println("Please enter a valid TourTextInformation");
            ++counter;
        }
        if(!validateInputInteger(addTourTextDistance.get())){
            System.out.println("Please enter a valid TourIntegerDistance");
            ++counter;
        }
        if(!validateInputInteger(addTourTextTime.get())){
            System.out.println("Please enter a valid TourIntegerTime");
            ++counter;
        }

        System.out.println(counter);

        if(counter == 0) {
            publisher.publish(Event.ENABLE_ADD_BUTTON, "Add button enabled.");
        }

        //TODO enable button
    }

    public boolean validateInputString(String input){
        return input.matches("[a-zA-Z]+");
    }

    public boolean validateInputInteger(String input){
        return input.matches("[0-9]+");
    }


}
