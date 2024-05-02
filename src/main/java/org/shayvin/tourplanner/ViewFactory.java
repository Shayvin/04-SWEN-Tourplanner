package org.shayvin.tourplanner;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.view.mainView;
import org.shayvin.tourplanner.view.tabView;
import org.shayvin.tourplanner.viewmodel.tabViewModel;
import org.shayvin.tourplanner.view.menuBarView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final tabViewModel tabViewModel;

    private ViewFactory() {
        publisher = new Publisher();

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
