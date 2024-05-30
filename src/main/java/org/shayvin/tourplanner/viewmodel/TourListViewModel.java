package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.TourService;

public class TourListViewModel {
    private final Publisher publisher;

    private final TourService tourService;


    private final ObservableList<String> tourList
            = FXCollections.observableArrayList();
    private final IntegerProperty selectedTourIndex
            = new SimpleIntegerProperty();
    private String selectedTourName = "";


    public TourListViewModel(Publisher publisher, TourService tourService) {
        this.publisher = publisher;
        this.tourService = tourService;

        this.publisher.subscribe(Event.ADD_TOUR, this::updateTourList);

        this.selectedTourIndex.addListener(
                observable -> selectTourList()
        );

        publisher.subscribe(Event.REMOVE_TOUR, message -> {
            System.out.println("Received message: " + message);
            removeItemTourList(tourList.get(selectedTourIndex.get()));
        });

        publisher.subscribe(Event.TOUR_UPDATED, message -> {
            System.out.println("Received message: " + message);
            updateTourList("Update");
        });

    }

    public void unselectTour() {
        publisher.publish(Event.TOUR_UNSELECTED, "Unselect tour button clicked");
    }

    // refresh tourList
    private void updateTourList(String message){
        tourList.setAll(tourService.updateFullList());
        publisher.publish(Event.LIST_UPDATED, "Updated TourList");
    }

    // check which tour is selected and set it in the tourService variable
    public void selectTourList(){
        if(selectedTourIndex.get() == -1){
            return;
        }

        tourService.setCurrentSelectedTourName(getTourList().get(selectedTourIndex.get()));
        publisher.publish(Event.TOUR_SELECTED, getTourList().get(selectedTourIndex.get()));
        publisher.publish(Event.DISABLE_ADD_BUTTON, "Disable add button");
    }

    public ObservableList<String> getTourList() {
        return tourList;
    }

    // get index of selected tour
    public IntegerProperty selectedTourIndexProperty() {
        System.out.println("Index: " + selectedTourIndex);
        return selectedTourIndex;
    }

    public void removeItemTourList(String tourToRemove){
        tourService.removeTourFromRepository(tourToRemove);
        updateTourList("Update");
    }
}