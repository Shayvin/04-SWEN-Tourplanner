package org.shayvin.tourplanner.viewmodel;

import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.view.SearchBarView;

public class SearchBarViewModel {

    public Publisher publisher;

    public SearchBarViewModel(Publisher publisher) {
        this.publisher = publisher;
    }
}
