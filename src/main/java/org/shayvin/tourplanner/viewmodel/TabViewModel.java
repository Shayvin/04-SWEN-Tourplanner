package org.shayvin.tourplanner.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.OpenRouteService;
import org.shayvin.tourplanner.service.TourService;
import org.shayvin.tourplanner.service.ValidateInputService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabViewModel {

    private static final Logger logger = LogManager.getLogger(TabViewModel.class);

    private final Publisher publisher;
    private final TourService tourService;
    private final OpenRouteService openRouteService;

    private final ValidateInputService validateInputService;

    public Property<Image> pictures = new SimpleObjectProperty<>();
    public Property<Image> map = new SimpleObjectProperty<>();
    private final StringProperty mapContent = new SimpleStringProperty();

    public List<Event> eventList;

    private boolean editMode = false;

    private final StringProperty addTourTextName = new SimpleStringProperty("");
    private final StringProperty addTourTextDescription = new SimpleStringProperty("");
    private final StringProperty addTourTextStart = new SimpleStringProperty("");
    private final StringProperty addTourTextDestination = new SimpleStringProperty("");
    private final StringProperty addTourTextType = new SimpleStringProperty("");
    private final StringProperty addTourTextDistance = new SimpleStringProperty("");
    private final StringProperty addTourTextTime = new SimpleStringProperty("");
    private final StringProperty addTourTextInformation = new SimpleStringProperty("");

    public StringProperty mapContentProperty() { return mapContent; }

    private final BooleanProperty readOnlyTextName = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextDescription = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextStart = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextDestination = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextType = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextDistance = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextTime = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyTextInformation = new SimpleBooleanProperty(false);

    public TabViewModel(Publisher publisher, TourService tourService, ValidateInputService validateInputService, OpenRouteService openRouteService) {
        this.publisher = publisher;
        this.tourService = tourService;
        this.validateInputService = validateInputService;
        this.eventList = new ArrayList<>();
        this.openRouteService = openRouteService;

        // set placeholder for map
        String mapPath = "/org/shayvin/tourplanner/img/street-map.png";
        Image mapImage = new Image(Objects.requireNonNull(getClass().getResource(mapPath)).toExternalForm());
        this.map.setValue(mapImage);

        // listeners for textfield inputs
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

        // add data to repo when ADD_TOUR is received
        publisher.subscribe(Event.ADD_TOUR, message -> {
            tourService.add(
                    addTourTextName.get(),
                    addTourTextDescription.get(),
                    addTourTextStart.get(),
                    addTourTextDestination.get(),
                    addTourTextType.get(),
                    addTourTextDistance.get(),
                    addTourTextTime.get(),
                    addTourTextInformation.get()
            );

            try {
                logger.info("Clear input fields");
                clearInputFields();
            }catch (IllegalAccessException e){
                logger.error(e.getMessage());
            }

            publisher.publish(Event.TOUR_ADDED, "added new tour");
        });

        // update data in repo when SAVE_EDITED_TOUR is received
        publisher.subscribe(Event.SAVE_EDITED_TOUR, message -> {
            System.out.println("Received message: " + message);
            tourService.updateTour(
                    addTourTextName.get(),
                    addTourTextDescription.get(),
                    addTourTextStart.get(),
                    addTourTextDestination.get(),
                    addTourTextType.get(),
                    addTourTextDistance.get(),
                    addTourTextTime.get(),
                    addTourTextInformation.get()
            );
            try {
                clearInputFields();
            }catch (IllegalAccessException e){
                System.out.println(e);
            }
            publisher.publish(Event.TOUR_UPDATED, "Tour updated");
            editMode = false;
        });

        // get data from repo when TOUR_SELECTED is received
        publisher.subscribe(Event.TOUR_SELECTED, message -> {
            System.out.println("Received tour selected: " + message);
            fillInElements(tourService.getTourWithName());
            setReadOnly(true);
        });

        // clear data from textfields when TOUR_UNSELECTED is received
        publisher.subscribe(Event.TOUR_UNSELECTED, message -> {
            System.out.println("Received tour unselected: " + message);
            try {
                clearInputFields();
            }catch (IllegalAccessException e){
                System.out.println(e);
            }
            setReadOnly(false);
        });

        // get route info and add to textfields, make it editable
        publisher.subscribe(Event.EDIT_TOUR, message -> {
            System.out.println("Received message: " + message);
            fillInElements(tourService.getTourWithName());
            setReadOnly(false);
            editMode = true;
            publisher.publish(Event.DISABLE_EDIT_BUTTON, "Edit button disabled");
            publisher.publish(Event.DISABLE_REMOVE_BUTTON, "Remove button enabled");
        });

        // on LIST_UPDATED clear textfields and set to not editable
        publisher.subscribe(Event.LIST_UPDATED, message -> {
            System.out.println("Received message: " + message);
            try {
                clearInputFields();
            }catch (IllegalAccessException e){
                System.out.println(e);
            }
            setReadOnly(false);
        });
    }

    // add event to event list
    public void addEventListEntry(Event event){
        System.out.println("Adding event list entry: " + event);
        if(!eventList.contains(event)){
            eventList.add(event);
        }
        if(eventList.size()==6){
            System.out.println("Event list contains 6 events");
            validateInputs();
        }
    }

    // iterate through all StringProperty elements and sets them to an empty string, clear pictures tab
    public void clearInputFields() throws IllegalAccessException {
        Class<?> myClass = getClass();
        Field[] fields = myClass.getDeclaredFields();

        for(Field field : fields){
            if(StringProperty.class.isAssignableFrom(field.getType())){
                try{
                    StringProperty stringProperty = (StringProperty) field.get(this);
                    stringProperty.set("");
                }
                catch (IllegalAccessException e){
                    logger.error(e.getMessage());
                }
            }
        }
        this.pictures.setValue(null);

    }

    // ugly af but it adds the elements from the tour back to the tabView
    private void fillInElements(List<String> tourDetails){
        if(tourDetails == null){
            return;
        }

        addTourTextName.set(tourDetails.getFirst());
        addTourTextDescription.set(tourDetails.get(1));
        addTourTextStart.set(tourDetails.get(2));
        addTourTextDestination.set(tourDetails.get(3));
        addTourTextType.set(tourDetails.get(4));
        addTourTextDistance.set(tourDetails.get(5));
        addTourTextTime.set(tourDetails.get(6));
        addTourTextInformation.set(tourDetails.get(7));

        String imagePath = tourDetails.get(8);
        Image currentImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        this.pictures.setValue(currentImage);

    }

    // checks if the input fields are valid
    public void validateInputs(){
        publisher.publish(Event.DISABLE_ADD_BUTTON, "Add Button disabled.");

        System.out.println("in validateInputs");
        int counter = 0;

        if(!validateInputService.validateInput(addTourTextName.get())){
            System.out.println("Please enter a valid TourTextName");
            ++counter;
        }
        if(!validateInputService.validateInput((addTourTextDescription.get()))){
            System.out.println("Please enter a valid TourTextDescription");
            ++counter;
        }
        if(!validateInputService.validateInput((addTourTextStart.get()))){
            System.out.println("Please enter a valid TourTextStart");
            ++counter;
        }
        if(!validateInputService.validateInput((addTourTextDestination.get()))){
            System.out.println("Please enter a valid TourTextDestination");
            ++counter;
        }
        if(!validateInputService.validateInput((addTourTextType.get()))){
            System.out.println("Please enter a valid TourTextType");
            ++counter;
        }
        if(!validateInputService.validateInput((addTourTextInformation.get()))){
            System.out.println("Please enter a valid TourTextInformation");
            ++counter;
        }

        System.out.println(counter);

        if(counter == 0) {
            if(editMode){
                publisher.publish(Event.DISABLE_ADD_BUTTON, "Add Button disabled.");
            }else{
                publisher.publish(Event.ENABLE_ADD_BUTTON, "Add button enabled.");
            }
        }
    }

    // sets the input fields to read only or enables them
    private void setReadOnly(boolean value){
        readOnlyTextName.set(value);
        readOnlyTextDescription.set(value);
        readOnlyTextStart.set(value);
        readOnlyTextDestination.set(value);
        readOnlyTextType.set(value);
        readOnlyTextDistance.set(value);
        readOnlyTextTime.set(value);
        readOnlyTextInformation.set(value);
    }

    public void updateMap(WebEngine webEngine) {

        if(addTourTextStart.get().isBlank() || addTourTextDestination.get().isBlank() || addTourTextType.get().isBlank()) {
            System.out.println("Fill all fields to calculate route.");

        } else {
            openRouteService.setStartAddress(addTourTextStart.get());
            openRouteService.setEndAddress(addTourTextDestination.get());
            openRouteService.setTransportType(calculateTransportType(addTourTextType.get()));
            if(!editMode) {
                setReadOnly(true);
            }
            openRouteService.restart();
        }

        openRouteService.setOnSucceeded(event -> {
            String route = (String) event.getSource().getValue();
            System.out.println("Route JSON: " + route);
            openRouteService.displayRoute(route, webEngine);
            addTourTextDistance.set(openRouteService.getDistance());
            addTourTextTime.set(openRouteService.getDuration());
            openRouteService.clearRouteProperties();
            setReadOnly(false);
            if(!editMode) {
                setReadOnly(true);
            }
            if(tourService.getCurrentSelectedTour().isEmpty()) {
                setReadOnly(false);
            }
        });

        openRouteService.setOnFailed(event -> {
            System.out.println("Failed to calculate route.");
            addTourTextDistance.set("");
            addTourTextTime.set("");
            openRouteService.clearExistingRoute(webEngine);
            openRouteService.clearRouteProperties();
            setReadOnly(!tourService.getCurrentSelectedTour().isEmpty());

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Route Calculation Error");
                alert.setHeaderText("Failed to calculate route.");
                alert.setContentText("Fill in correct start and end destinations.");
                alert.showAndWait();
            });
        });
    }

    public void updateTextField() {
        addTourTextTime.set(openRouteService.getDuration());
        addTourTextDistance.set(openRouteService.getDistance());
    }

    public void clearTypeField(){
        addTourTextType.set("");
    }

    private String calculateTransportType(String transportType) {
        switch (transportType) {
            case "Walking":
                return "foot-walking";
            case "Car":
                return "driving-car";
            case "Cycling":
                return "cycling-regular";
            default:
                return "driving-car";
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

    public Property<String> addTourTextType() {
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

    public boolean isReadOnlyTextName() {
        return readOnlyTextName.get();
    }

    public BooleanProperty readOnlyTextNameProperty() {
        return readOnlyTextName;
    }

    public boolean isReadOnlyTextDescription() {
        return readOnlyTextDescription.get();
    }

    public BooleanProperty readOnlyTextDescriptionProperty() {
        return readOnlyTextDescription;
    }

    public boolean isReadOnlyTextStart() {
        return readOnlyTextStart.get();
    }

    public BooleanProperty readOnlyTextStartProperty() {
        return readOnlyTextStart;
    }

    public boolean isReadOnlyTextDestination() {
        return readOnlyTextDestination.get();
    }

    public BooleanProperty readOnlyTextDestinationProperty() {
        return readOnlyTextDestination;
    }

    public boolean isReadOnlyTextType() {
        return readOnlyTextType.get();
    }

    public BooleanProperty readOnlyTextTypeProperty() {
        return readOnlyTextType;
    }

    public boolean isReadOnlyTextDistance() {
        return readOnlyTextDistance.get();
    }

    public BooleanProperty readOnlyTextDistanceProperty() {
        return readOnlyTextDistance;
    }

    public boolean isReadOnlyTextTime() {
        return readOnlyTextTime.get();
    }

    public BooleanProperty readOnlyTextTimeProperty() {
        return readOnlyTextTime;
    }

    public boolean isReadOnlyTextInformation() {
        return readOnlyTextInformation.get();
    }

    public BooleanProperty readOnlyTextInformationProperty() {
        return readOnlyTextInformation;
    }

    public void setAddTourTextName(String value) {
        addTourTextName.set(value);
    }

    public void setAddTourTextDescription(String value) {
        addTourTextDescription.set(value);
    }

    public void setAddTourTextStart(String value) {
        addTourTextStart.set(value);
    }

    public void setAddTourTextDestination(String value) {
        addTourTextDestination.set(value);
    }

    public void setAddTourTextType(String value) {
        addTourTextType.set(value);
    }

    public void setAddTourTextDistance(String value) {
        addTourTextDistance.set(value);
    }

    public void setAddTourTextTime(String value) {
        addTourTextTime.set(value);
    }

    public void setAddTourTextInformation(String value) {
        addTourTextInformation.set(value);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
