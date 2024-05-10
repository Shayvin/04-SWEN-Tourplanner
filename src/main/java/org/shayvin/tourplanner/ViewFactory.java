package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.view.*;
import org.shayvin.tourplanner.viewmodel.routeButtonViewModel;
import org.shayvin.tourplanner.viewmodel.tabViewModel;
import org.shayvin.tourplanner.viewmodel.tableButtonViewModel;
import org.shayvin.tourplanner.viewmodel.tourLogViewModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final tabViewModel tabViewModel;
    private final routeButtonViewModel routeViewModel;
    private final tourLogViewModel tourLogViewModel;
    private final tableButtonViewModel tableButtonViewModel;


    private ViewFactory() {
        publisher = new Publisher();

        tabViewModel = new tabViewModel(publisher);
        routeViewModel = new routeButtonViewModel(publisher, tabViewModel);
        tourLogViewModel = new tourLogViewModel(publisher);
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
            return new tabView(tabViewModel, publisher);
        }

        if(viewClass == menuBarView.class) {
            return new menuBarView(publisher);
        }

        if(viewClass == mainView.class) {
            return new mainView(publisher);
        }

        if(viewClass == routeButtonView.class) {
            return new routeButtonView(routeViewModel, publisher);
        }

        if(viewClass == tourLogView.class) {
            return new tourLogView(tourLogViewModel, tableButtonViewModel, publisher);
        }

        if(viewClass == tableButtonView.class) {
            return new tableButtonView(publisher, tableButtonViewModel);
        }

        return null;
    }
}
