package org.shayvin.tourplanner.viewmodel;

import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.service.TourLogService;
import org.shayvin.tourplanner.service.TourService;

public class TourLogPopupViewModel {

    private final Publisher publisher;
    private final TourLogService tourLogService;
    private final CreatePopupService createPopupService;
    private final TourService tourService;
    private String currentSelectedTourName;

    public TourLogPopupViewModel(Publisher publisher, TourLogService tourLogService, CreatePopupService createPopupService, TourService tourService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;
        this.createPopupService = createPopupService;
        this.tourService = tourService;
        publisher.subscribe(Event.ADD_TOUR_LOG, (event) -> {
            createPopupService.createPopup("tourLog-submit-popup-view.fxml", 800, 500);
        });
        publisher.subscribe(Event.EDIT_TOUR_LOG, (event) -> {
            createPopupService.createPopup("tourLog-edit-popup-view.fxml", 800, 500);
        });
    }

    public void submitTourLog(TourLog tourLog) {
        currentSelectedTourName = tourService.currentTourName;
        tourLogService.addTourLogData(currentSelectedTourName, tourLog);
        publisher.publish(Event.TOURLOG_LIST_UPDATED, "Updated TourLogList");
    }

    public void editTourLog(TourLog tourLog) {
        tourLogService.setNewTourLog(tourLog);
        publisher.publish(Event.SUBMIT_EDIT_TOUR_LOG, "Edit TourLog");
        publisher.publish(Event.TOURLOG_LIST_UPDATED, "Updated TourLogList");
    }
}
