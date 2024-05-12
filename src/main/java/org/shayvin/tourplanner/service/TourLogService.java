package org.shayvin.tourplanner.service;

import javafx.collections.ObservableList;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourLogRepository;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourService tourService;
    public String currentSelectedTourName;

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
}
