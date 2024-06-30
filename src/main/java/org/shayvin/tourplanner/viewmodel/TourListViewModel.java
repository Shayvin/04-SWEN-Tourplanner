package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.FullTextService;
import org.shayvin.tourplanner.service.TourService;

public class TourListViewModel {
    private final Publisher publisher;

    private final TourService tourService;
    private final FullTextService fullTextService;


    private final ObservableList<String> tourList
            = FXCollections.observableArrayList();
    private final IntegerProperty selectedTourIndex
            = new SimpleIntegerProperty();
    private String selectedTourName = "";


    public TourListViewModel(Publisher publisher, TourService tourService, FullTextService fullTextService) {
        this.publisher = publisher;
        this.tourService = tourService;
        this.fullTextService = fullTextService;

        this.publisher.subscribe(Event.TOUR_ADDED, this::updateTourList);

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

        publisher.subscribe(Event.FIND_SEARCHED_TOURS, message -> {
            updateTourListAfterFullTextSearch("search in db");
        });

        publisher.subscribe(Event.RESET_ALL, message -> {
            System.out.println("Received message: " + message);
            updateTourList("Reset");
        });


        updateTourList("Initial loading of tours");
    }

    public void unselectTour() {
        publisher.publish(Event.TOUR_UNSELECTED, "Unselect tour button clicked");
        tourService.clearCurrentTour();
    }

    // refresh tourList
    private void updateTourList(String message){
        tourList.setAll(tourService.updateFullList());
        publisher.publish(Event.LIST_UPDATED, "Updated TourList");
    }

    // refresh tourList with searchterm
    private void updateTourListAfterFullTextSearch(String message){
        tourList.setAll(fullTextService.search());
        publisher.publish(Event.LIST_UPDATED, "Updated TourList");
    }

    // check which tour is selected and set it in the tourService variable
    public void selectTourList(){
        if(selectedTourIndex.get() == -1){
            return;
        }

        tourService.setCurrentTourName(getTourList().get(selectedTourIndex.get()));

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
        tourService.clearCurrentTour();
        updateTourList("Update");
    }
}
