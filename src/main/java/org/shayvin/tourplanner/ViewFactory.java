package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.pdf.PdfBox;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.repository.TourRepository;
import org.shayvin.tourplanner.service.*;
import org.shayvin.tourplanner.view.*;
import org.shayvin.tourplanner.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final TourRepository tourRepository;
    private final TourMemoryRepository tourMemoryRepository;

    private final TourService tourService;
    private final TourLogService tourLogService;
    private final CreatePopupService createPopupService;
    private final OpenRouteService openRouteService;
    private final ValidateInputService validateInputService;
    private final ConfigService configService;

    private final PdfBox pdfBox;

    private final TabViewModel tabViewModel;
    private final RouteButtonsViewModel routeButtonsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogViewModel tourLogViewModel;
    private final TableButtonViewModel tableButtonViewModel;
    private final MenuBarViewModel menuBarViewModel;

    private final TourLogPopupViewModel tourLogPopupViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        tourRepository = new TourMemoryRepository();
        tourMemoryRepository = new TourMemoryRepository();

        tourService = new TourService(tourMemoryRepository);
        tourLogService = new TourLogService(tourMemoryRepository, tourService);
        configService = new ConfigService();
        createPopupService = new CreatePopupService();
        openRouteService = new OpenRouteService(configService);
        validateInputService = new ValidateInputService();

        pdfBox = new PdfBox();

        tabViewModel = new TabViewModel(publisher, tourService, validateInputService, openRouteService);
        routeButtonsViewModel = new RouteButtonsViewModel(publisher);
        tourListViewModel = new TourListViewModel(publisher, tourService);
        tourLogViewModel = new TourLogViewModel(publisher, tourLogService);
        tableButtonViewModel = new TableButtonViewModel(publisher);
        menuBarViewModel = new MenuBarViewModel(publisher, pdfBox, createPopupService);
        tourLogPopupViewModel = new TourLogPopupViewModel(publisher, tourLogService, createPopupService, tourService, validateInputService);


    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    // Create a view based on the class
    public Object create(Class<?> viewClass) {
        if(viewClass == TabView.class) {
            return new TabView(tabViewModel, openRouteService);
        }

        if(viewClass == MenuBarView.class) {
            return new MenuBarView(publisher, pdfBox, menuBarViewModel);
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
            return new TourLogPopupView(publisher, tourLogPopupViewModel);
        }

        if(viewClass == SearchBarView.class){
            return new SearchBarView(publisher);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);

    }
}
