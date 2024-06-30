package org.shayvin.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.viewmodel.SearchBarViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarView implements Initializable {

    private static final Logger logger = LogManager.getLogger(SearchBarView.class);

    private final SearchBarViewModel searchBarViewModel;

    @FXML
    private TextField searchField;


    public SearchBarView(Publisher publisher, SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().bindBidirectional(searchBarViewModel.setSearchTermProperty());
    }

    @FXML
    public void searchForTerm() {
        logger.info("Searching for term");
        searchBarViewModel.searchForTermInDB();
    }

    @FXML
    public void reset(){
        logger.info("resetting display");
        searchField.clear();
        searchBarViewModel.resetView();
    }
}
