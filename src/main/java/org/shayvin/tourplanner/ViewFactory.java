package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.repository.TourLogRepository;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.repository.TourRepository;
import org.shayvin.tourplanner.service.CreatePopupService;
import org.shayvin.tourplanner.service.TourLogService;
import org.shayvin.tourplanner.service.TourService;
import org.shayvin.tourplanner.view.*;
import org.shayvin.tourplanner.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;

    private final TourService tourService;
    private final TourLogService tourLogService;
    private final CreatePopupService createPopupService;

    private final TabViewModel tabViewModel;
    private final RouteButtonsViewModel routeButtonsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogViewModel tourLogViewModel;
    private final TableButtonViewModel tableButtonViewModel;
    private final TourLogPopupViewModel TourLogPopupViewModel;

    private ViewFactory() {
        tourLogRepository = new TourLogRepository();
        publisher = new Publisher();

        tourRepository = new TourMemoryRepository();
        tourService = new TourService(tourRepository);
        tourLogService = new TourLogService(tourLogRepository, tourService);
        createPopupService = new CreatePopupService();

        tabViewModel = new TabViewModel(publisher, tourService);
        routeButtonsViewModel = new RouteButtonsViewModel(publisher);
        tourListViewModel = new TourListViewModel(publisher, tourService);
        tourLogViewModel = new TourLogViewModel(publisher, tourLogService);
        tableButtonViewModel = new TableButtonViewModel(publisher);
        TourLogPopupViewModel = new TourLogPopupViewModel(publisher, tourLogService, createPopupService, tourService);

    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    public Object create(Class<?> viewClass) {
        if(viewClass == TabView.class) {
            return new TabView(tabViewModel);
        }

        if(viewClass == MenuBarView.class) {
            return new MenuBarView(publisher);
        }

        if(viewClass == MainView.class) {
            return new MainView(publisher);
        }

        if(viewClass == RouteButtonsView.class) {
            return new RouteButtonsView(routeButtonsViewModel);
        }

        if(viewClass == TourListView.class) {
            return new TourListView(tourListViewModel);
        }

        if(viewClass == TourLogView.class) {
            return new TourLogView(tourLogViewModel, tableButtonViewModel, publisher);
        }

        if(viewClass == TableButtonView.class) {
            return new TableButtonView(publisher, tableButtonViewModel);
        }

        if(viewClass == TourLogPopupView.class) {
            return new TourLogPopupView(publisher, TourLogPopupViewModel);
        }

        //throw new IllegalArgumentException("Unknown view class: " + viewClass);
        return null;
    }
}
