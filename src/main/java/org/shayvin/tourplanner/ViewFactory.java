package org.shayvin.tourplanner;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;
    private final SearchHistoryViewModel searchHistoryViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        searchViewModel = new SearchViewModel(publisher);
        searchHistoryViewModel = new SearchHistoryViewModel(publisher);
    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    public Object create(Class<?> viewClass) {
        if (SearchView.class == viewClass) {
            return new SearchView(searchViewModel);
        }

        if (SearchHistoryView.class == viewClass) {
            return new SearchHistoryView(searchHistoryViewModel);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
