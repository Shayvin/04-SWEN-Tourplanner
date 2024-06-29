package org.shayvin.tourplanner.viewmodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.service.TourLogService;
import org.shayvin.tourplanner.service.TourService;

public class TourLogPopupViewModel {

    private static final Logger logger = LogManager.getLogger(TourLogPopupViewModel.class);

    private final Publisher publisher;
    private final TourLogService tourLogService;
    private final CreatePopupService createPopupService;
    private final TourService tourService;
    private String currentSelectedTourName;
    private final TourLog tourLog;

    public TourLogPopupViewModel(Publisher publisher, TourLogService tourLogService, CreatePopupService createPopupService, TourService tourService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;
        this.createPopupService = createPopupService;
        this.tourService = tourService;
        this.tourLog = new TourLog();

        publisher.subscribe(Event.ADD_TOUR_LOG, (event) -> {
            try{
                createPopupService.createPopup("tourLog-submit-popup-view.fxml", 800, 500);
                logger.info("open tourLog-submit-popup");
            }catch(Exception e){
                logger.error("failed to open tourLog-submit-popup: {}", e.getMessage());
            }
        });
        publisher.subscribe(Event.EDIT_TOUR_LOG, (event) -> {
            try{
                createPopupService.createPopup("tourLog-edit-popup-view.fxml", 800, 500);
                logger.info("open tourLog-edit-popup");
            }catch(Exception e){
                logger.error("failed to open tourLog-edit-popup: {}", e.getMessage());
            }
        });
    }

    public void submitTourLog(TourLog tourLog){
        tourLogService.addTourLogData(tourLog);
        publisher.publish(Event.TOURLOG_LIST_UPDATED, "Updated TourLogList");
    }

    public void editTourLog(TourLog tourLog){
        tourLogService.editTourLogData(tourLog);
        publisher.publish(Event.SUBMIT_EDIT_TOUR_LOG, "Edit TourLog");
        publisher.publish(Event.TOURLOG_LIST_UPDATED, "Updated TourLogList");

    }


}
