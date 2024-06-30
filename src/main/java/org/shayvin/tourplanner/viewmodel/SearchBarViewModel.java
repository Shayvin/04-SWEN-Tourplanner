package org.shayvin.tourplanner.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.service.FullTextService;

public class SearchBarViewModel {

    private final StringProperty searchTerm = new SimpleStringProperty("");

    private final Publisher publisher;
    private final FullTextService fullTextService;


    public SearchBarViewModel(Publisher publisher, FullTextService fullTextService) {
        this.publisher = publisher;
        this.fullTextService = fullTextService;
    }

    public String getSearchTerm() {
        return String.valueOf(searchTerm.get());
    }

    public StringProperty setSearchTermProperty() {
        return searchTerm;
    }

    public void searchForTermInDB(){
        fullTextService.setSearchTerm(getSearchTerm());
        publisher.publish(Event.FIND_SEARCHED_TOURS, "search for Term");
    }

    public void resetView(){
        publisher.publish(Event.RESET_ALL, "Clear view of searchTerm");
    }

}
