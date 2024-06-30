package org.shayvin.tourplanner.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourLogRepository;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.view.TableButtonView;

import java.util.List;
import java.util.UUID;


public class TourLogService {

    private static final Logger logger = LogManager.getLogger(TourLogService.class);

    private final TourMemoryRepository tourMemoryRepository;
    private final TourService tourService;
    private TourLog currentTourLog;


    public TourLogService(TourMemoryRepository tourMemoryRepository, TourService tourService) {
        this.tourMemoryRepository = tourMemoryRepository;
        this.tourService = tourService;

        this.currentTourLog = new TourLog();
    }


    public void addTourLogData(TourLog tourLog) {
        tourService.addTourLog(tourLog);

        tourMemoryRepository.update(tourService.currentTour);
    }

    public void removeTourLogFromRepository(TourLog tourlog) {
        List<TourLog> list = tourService.currentTour.getTourLogList();
        list.remove(tourlog);
        tourService.currentTour.setTourLogList(list);
        tourMemoryRepository.update(tourService.currentTour);
    }

    public void setCurrentTourLog(TourLog tourlog) {
        this.currentTourLog = tourlog;
    }

    public void editTourLogData(TourLog newTourlog) {
        UUID currentId = currentTourLog.getId();
        List<TourLog> list = tourService.currentTour.getTourLogList();

        //eigentlich nicht schön, sollte jedes element vergleichen und adaptieren wenn nötig (maybe TODO rework this <-)
        for(TourLog log : list) {
            if(log.getId().equals(currentId)) {
                list.remove(log);
            }
        }
        newTourlog.setId(currentId);
        tourService.currentTour.setTourLogList(list);
        tourMemoryRepository.update(tourService.currentTour);

    }

    public TourLog getSelectedTourLog() {
        return currentTourLog;
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.currentTourLog = selectedTourLog;
    }

    public ObservableList<TourLog> getTourLogsByName() {
        logger.info("get tourLogs by name of tour");
        return FXCollections.observableArrayList(tourService.currentTour.getTourLogList());
    }

}
