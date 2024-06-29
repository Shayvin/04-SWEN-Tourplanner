package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.TourLog;

import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.TourLogService;

public class TourLogViewModel {

    private final Publisher publisher;

    private TourLog selectedTourLog;

    private final TourLogService tourLogService;

    private ObservableList<TourLog> tourLogs
            = FXCollections.observableArrayList();

    public TourLogViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        this.publisher.subscribe(Event.TOURLOG_LIST_UPDATED, this::updateTourLogs);

        this.publisher.subscribe(Event.TOUR_SELECTED, this::updateTourLogs);

        publisher.subscribe(Event.DELETE_TOUR_LOG, (data) -> {
            tourLogService.removeTourLogFromRepository(selectedTourLog);
            updateTourLogs("Deleted");
        });

        publisher.subscribe(Event.SUBMIT_EDIT_TOUR_LOG, (data) -> {
            tourLogService.editTourLogData(selectedTourLog);
        });

        publisher.subscribe(Event.SELECT_TOUR_LOG, (data) -> {
            publisher.publish(Event.DELETE_TOUR_LOG_BUTTON_VISIBILITY, String.valueOf(false));
        });

        publisher.subscribe(Event.TOUR_SELECTED, (data) -> {
            publisher.publish(Event.ADD_TOUR_LOG_BUTTON_VISIBILITY, String.valueOf(false));
        });

        publisher.subscribe(Event.TOUR_UNSELECTED, (data) -> {
            publisher.publish(Event.ADD_TOUR_LOG_BUTTON_VISIBILITY, String.valueOf(true));
        });
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.selectedTourLog = selectedTourLog;
        tourLogService.setCurrentTourLog(selectedTourLog);
    }

    public void selectedTourEvent(TourLog selectedTourLog) {
        tourLogService.setSelectedTourLog(selectedTourLog);
        publisher.publish(Event.SELECT_TOUR_LOG, "Selected Tour Log: " + selectedTourLog);
    }

    private void updateTourLogs(String message) {
        tourLogs.setAll(tourLogService.getTourLogsByName());
        for (TourLog log : tourLogs) {
            log.setDate(log.getDate());
            log.setDuration(log.getDuration());
            log.setDistance(log.getDistance());
            log.setComment(log.getComment());
            log.setDifficulty(log.getDifficulty());
            log.setRating(log.getRating());
        }
    }
}
