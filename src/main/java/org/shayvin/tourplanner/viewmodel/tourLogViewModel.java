package org.shayvin.tourplanner.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.TourLog;

import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;

public class tourLogViewModel {

    private final Publisher publisher;

    private TourLog selectedTourLog;


    public tourLogViewModel(Publisher publisher) {
        this.publisher = publisher;
        tourLogs = FXCollections.observableArrayList();
        initializeSampleData();

        publisher.subscribe(Event.ADD_TOUR_LOG, (data) -> {
            tourLogs.add(new TourLog("Sample", "Sample", "Sample", "Sample", "Sample", "Sample"));
        });

        publisher.subscribe(Event.DELETE_TOUR_LOG, (data) -> {
            tourLogs.remove(selectedTourLog);
        });

        publisher.subscribe(Event.SELECT_TOUR_LOG, (data) -> {
            publisher.publish(Event.DELETE_TOUR_LOG_BUTTON_VISIBILITY, String.valueOf(false));
            System.out.println(getTourLogs());
        });
    }
    private final ObservableList<TourLog> tourLogs;

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    // Method to populate tourLogs with sample data
    private void initializeSampleData() {
        tourLogs.add(new TourLog("11.11.1111", "1 h", "10 km", "Short", "easy", "1"));
        tourLogs.add(new TourLog("22.22.2222", "2 h", "8 km", "Medium", "medium", "2"));
        tourLogs.add(new TourLog("33.33.3333", "3 h", "15 km", "Long", "hard", "3"));
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.selectedTourLog = selectedTourLog;
    }

    public void selectedTourEvent(TourLog selectedTourLog) {
        publisher.publish(Event.SELECT_TOUR_LOG, "Selected Tour Log: " + selectedTourLog);
    }
}
