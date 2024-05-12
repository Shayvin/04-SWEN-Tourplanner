package org.shayvin.tourplanner.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.TourLog;

import java.util.HashMap;

public class TourLogRepository {

    private final HashMap<String, ObservableList<TourLog>> tours = new HashMap<>();

    public TourLogRepository() { }

    public void save(String name, TourLog tour) {
        ObservableList<TourLog> tourLogs = tours.getOrDefault(name, FXCollections.observableArrayList());
        tourLogs.add(tour);
        tours.put(name, tourLogs);
    }

    public ObservableList<TourLog> findByTourName(String tourToFind) {
        return tours.getOrDefault(tourToFind, FXCollections.emptyObservableList());
    }

    public void removeTourLog(String currentSelectedTourName, TourLog tour) {
        ObservableList<TourLog> tourLogs = tours.getOrDefault(currentSelectedTourName, FXCollections.emptyObservableList());
        tourLogs.remove(tour);
        tours.put(currentSelectedTourName, tourLogs);
    }
}
