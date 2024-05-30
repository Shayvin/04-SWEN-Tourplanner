package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.repository.TourLogRepository;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.repository.TourRepository;
import org.shayvin.tourplanner.service.TourLogService;
import org.shayvin.tourplanner.service.TourService;
import org.shayvin.tourplanner.service.ValidateInputService;
import org.shayvin.tourplanner.view.*;
import org.shayvin.tourplanner.viewmodel.routeButtonsViewModel;
import org.shayvin.tourplanner.viewmodel.tabViewModel;
import org.shayvin.tourplanner.viewmodel.tourListViewModel;
import org.shayvin.tourplanner.viewmodel.tourLogViewModel;
import org.shayvin.tourplanner.viewmodel.tableButtonViewModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;

    private final TourService tourService;
    private final TourLogService tourLogService;

    private final ValidateInputService validateInputService;

    private final tabViewModel tabViewModel;
    private final routeButtonsViewModel routeButtonsViewModel;
    private final tourListViewModel tourListViewModel;
    private final tourLogViewModel tourLogViewModel;
    private final tableButtonViewModel tableButtonViewModel;

    private ViewFactory() {
        tourLogRepository = new TourLogRepository();
        publisher = new Publisher();

        tourRepository = new TourMemoryRepository();
        tourService = new TourService(tourRepository);
        tourLogService = new TourLogService(tourLogRepository, tourService);

        validateInputService = new ValidateInputService();

        tabViewModel = new tabViewModel(publisher, tourService, validateInputService);
        routeButtonsViewModel = new routeButtonsViewModel(publisher);
        tourListViewModel = new tourListViewModel(publisher, tourService);
        tourLogViewModel = new tourLogViewModel(publisher, tourLogService);
        tableButtonViewModel = new tableButtonViewModel(publisher);

    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    public Object create(Class<?> viewClass) {
        if(viewClass == tabView.class) {
            return new tabView(tabViewModel);
        }

        if(viewClass == menuBarView.class) {
            return new menuBarView(publisher);
        }

        if(viewClass == mainView.class) {
            return new mainView(publisher);
        }

        if(viewClass == routeButtonsView.class) {
            return new routeButtonsView(routeButtonsViewModel);
        }

        if(viewClass == tourListView.class) {
            return new tourListView(tourListViewModel);
        }

        if(viewClass == tourLogView.class) {
            return new tourLogView(tourLogViewModel, tableButtonViewModel, publisher);
        }

        if(viewClass == tableButtonView.class) {
            return new tableButtonView(publisher, tableButtonViewModel);
        }

        //throw new IllegalArgumentException("Unknown view class: " + viewClass);
        return null;
    }
}
