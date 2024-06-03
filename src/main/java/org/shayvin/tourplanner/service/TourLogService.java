package org.shayvin.tourplanner.service;

import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourLogRepository;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourService tourService;
    public String currentSelectedTourName;
    public TourLog selectedTourLog;
    public TourLog newTourLog;

    public TourLogService(TourLogRepository tourLogRepository, TourService tourService) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
    }

    public void addTourLogData(String name, TourLog tourlog) {
        tourLogRepository.save(tourlog);
    }

    public void removeTourLogFromRepository(String currentSelectedTourName, TourLog tour) {
        tourLogRepository.remove(tour);
    }

    public ObservableList<TourLog> getTourLogsByName() {
        currentSelectedTourName = tourService.currentSelectedTourName;
        return (ObservableList<TourLog>) tourLogRepository.findByTourName(currentSelectedTourName);
    }

    public void editTourLogData(String currentSelectedTourName, TourLog newTourLog, TourLog oldTourLog) {
        tourLogRepository.edit(newTourLog);
    }

    public TourLog getSelectedTourLog() {
        return selectedTourLog;
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.selectedTourLog = selectedTourLog;
    }

    public void setNewTourLog(TourLog newTourLog) {
        this.newTourLog = newTourLog;
    }

    public TourLog getNewTourLog() {
        return newTourLog;
    }
}
