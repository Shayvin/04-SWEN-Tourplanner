package org.shayvin.tourplanner.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.FullTextSearchRepository;

import java.util.List;

public class FullTextService {

    private static final Logger logger = LogManager.getLogger(FullTextService.class);

    private String searchTerm;

    private final FullTextSearchRepository fullTextSearchRepository;

    public FullTextService(FullTextSearchRepository fullTextSearchRepository){
        this.fullTextSearchRepository = fullTextSearchRepository;
        this.searchTerm = "";
    }

    public List<String> search(){
        return fullTextSearchRepository.findFitting(searchTerm).stream().map(Tour::getName).toList();
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
