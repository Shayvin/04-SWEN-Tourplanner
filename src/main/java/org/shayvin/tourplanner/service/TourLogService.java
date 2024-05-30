package org.shayvin.tourplanner.service;

import javafx.collections.ObservableList;
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
    
    public void addTourLogData(String name, TourLog tour) {
        tourLogRepository.save(name, tour);
    }

    public void removeTourLogFromRepository(String currentSelectedTourName, TourLog tour) {
        tourLogRepository.removeTourLog(currentSelectedTourName, tour);
    }

    public ObservableList<TourLog> getTourLogsByName() {
        currentSelectedTourName = tourService.currentSelectedTourName;
        return tourLogRepository.findByTourName(currentSelectedTourName);
    }

    public void editTourLogData(String currentSelectedTourName, TourLog newTourLog, TourLog oldTourLog) {
        tourLogRepository.editTourLog(currentSelectedTourName, newTourLog, oldTourLog);
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
