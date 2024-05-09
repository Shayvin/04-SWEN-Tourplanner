package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.TourService;

public class tourListViewModel {
    private final Publisher publisher;

    private final TourService tourService;


    private final ObservableList<String> tourList
            = FXCollections.observableArrayList();
    private final IntegerProperty selectedTourIndex
            = new SimpleIntegerProperty();


    public tourListViewModel(Publisher publisher, TourService tourService) {
        this.publisher = publisher;
        this.tourService = tourService;

        this.publisher.subscribe(Event.ADD_TOUR, this::updateTourList);

        this.selectedTourIndex.addListener(
                observable -> selectTourList()
        );

    }

    private void updateTourList(String message){
        tourList.setAll(tourService.updateFullList());
    }

    public void selectTourList(){
        if(selectedTourIndex.get() == -1){
            return;
        }

        publisher.publish(Event.TOUR_SELECTED, getTourList().get(selectedTourIndex.get()));

        //TODO HIER WEITER MACHEN -> es wird hier der string mitgegeben, kann man den im weitern verlauf weitergeben und benutzen? dann muss nachdem der Button gedrückt wurde nicht wieder alles neu gesucht werden.. maybe über den service?
    }

    public ObservableList<String> getTourList() {
        return tourList;
    }

    public IntegerProperty selectedTourIndexProperty() {
        System.out.println("Index: " + selectedTourIndex);
        return selectedTourIndex;
    }
}
