package org.shayvin.tourplanner.service;

import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.TourRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourService {
    private final TourRepository tourRepository;

    public static String currentSelectedTour;

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

    public List<String> getTourWithName() {
        Optional<Tour> tour = tourRepository.findByTourName(currentSelectedTour);
        List<String> tourDetails = new ArrayList<>();

        if(tour.isPresent()) {
            Tour presentTour = tour.get();

            tourDetails.add(presentTour.getTourName());
            tourDetails.add(presentTour.getTourDescription());
            tourDetails.add(presentTour.getTourStart());
            tourDetails.add(presentTour.getTourDestination());
            tourDetails.add(presentTour.getTourType());
            tourDetails.add(presentTour.getTourDistance());
            tourDetails.add(presentTour.getTourDuration());
            tourDetails.add(presentTour.getTourInformation());

            return tourDetails;
        }

        return null;
    }

    public String getCurrentSelectedTour() {
        return currentSelectedTour;
    }

    public static void setCurrentSelectedTour(String tourName) {
        System.out.println("in setter");
        currentSelectedTour = tourName;
        System.out.println("TourName: " + currentSelectedTour);
    }
}
