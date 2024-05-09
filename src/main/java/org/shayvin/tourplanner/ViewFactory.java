package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.repository.TourRepository;
import org.shayvin.tourplanner.service.TourService;
import org.shayvin.tourplanner.view.mainView;
import org.shayvin.tourplanner.view.tabView;
import org.shayvin.tourplanner.viewmodel.tabViewModel;
import org.shayvin.tourplanner.view.menuBarView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final TourRepository tourRepository;

    private final TourService tourService;

    private final tabViewModel tabViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        tourRepository = new TourMemoryRepository();
        tourService = new TourService(tourRepository);

        //TODO add tourService to tabViewModel

        tabViewModel = new tabViewModel(publisher);

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

        return null;
    }
}
