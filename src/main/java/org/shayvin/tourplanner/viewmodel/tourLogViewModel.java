package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.TourLog;

import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.TourLogService;

public class tourLogViewModel {

    private final Publisher publisher;

    private TourLog selectedTourLog;

    private final TourLogService tourLogService;

    public tourLogViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        publisher.subscribe(Event.ADD_TOUR_LOG, (data) -> {
            tourLogService.addTourLogData(tourLogService.currentSelectedTourName, new TourLog("01/02/1234", "1", "2", "Sample", "Sample", "3"));
        });

        publisher.subscribe(Event.DELETE_TOUR_LOG, (data) -> {
            tourLogService.removeTourLogFromRepository(tourLogService.currentSelectedTourName, selectedTourLog);
        });

        publisher.subscribe(Event.SELECT_TOUR_LOG, (data) -> {
            publisher.publish(Event.DELETE_TOUR_LOG_BUTTON_VISIBILITY, String.valueOf(false));
        });
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogService.getTourLogsByName();
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.selectedTourLog = selectedTourLog;
    }

    public void selectedTourEvent(TourLog selectedTourLog) {
        publisher.publish(Event.SELECT_TOUR_LOG, "Selected Tour Log: " + selectedTourLog);
    }
}
