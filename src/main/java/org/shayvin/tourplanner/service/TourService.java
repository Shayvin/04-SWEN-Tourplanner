package org.shayvin.tourplanner.service;

import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.TourRepository;

import java.util.List;
import java.util.Optional;

public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void add(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation) {
        Optional<Tour> tour = tourRepository.findByTourName(tourName);

        if(tour.isPresent()) {
            return;
        }

        tourRepository.save(new Tour(tourName, tourDescription, tourStart, tourDestination, tourType, tourDistance, tourDuration, tourInformation));
    }

    public List<String> updateFullList(){
        return tourRepository.findAll().stream().map(Tour::getTourName).toList();
    }

    public List<Tour> getAll() {
        return null;
    }
}
