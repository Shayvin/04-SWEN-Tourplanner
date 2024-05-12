package org.shayvin.tourplanner.service;

import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.TourRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TourService {
    private final TourRepository tourRepository;

    public String currentSelectedTourName;
    public static Tour currentSelectedTour;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void add(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation) {
        Optional<Tour> tour = tourRepository.findByTourName(tourName);

        if(tour.isPresent()) {
            return;
        }

        //TODO rework this: let user upload pictures and add those to the repository
        String imagePath = getRandomPicture();

        tourRepository.save(new Tour(tourName, tourDescription, tourStart, tourDestination, tourType, tourDistance, tourDuration, tourInformation, imagePath));
    }

    public void updateTour(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation){
        removeTourFromRepository(currentSelectedTourName);

        if(!currentSelectedTour.getTourName().equals(tourName)) {
            currentSelectedTour.setTourName(tourName);
        }
        if(!currentSelectedTour.getTourDescription().equals(tourDescription)) {
            currentSelectedTour.setTourDescription(tourDescription);
        }
        if(!currentSelectedTour.getTourStart().equals(tourStart)) {
            currentSelectedTour.setTourStart(tourStart);
        }
        if(!currentSelectedTour.getTourDestination().equals(tourDestination)) {
            currentSelectedTour.setTourDestination(tourDestination);
        }
        if(!currentSelectedTour.getTourType().equals(tourType)) {
            currentSelectedTour.setTourType(tourType);
        }
        if(!currentSelectedTour.getTourDistance().equals(tourDistance)) {
            currentSelectedTour.setTourDistance(tourDistance);
        }
        if(!currentSelectedTour.getTourDuration().equals(tourDuration)) {
            currentSelectedTour.setTourDuration(tourDuration);
        }
        if(!currentSelectedTour.getTourInformation().equals(tourInformation)) {
            currentSelectedTour.setTourInformation(tourInformation);
        }
        //TODO rework this: check id, adapt the entries and save the object with the same id (so that the tourlogs won't go missing
        tourRepository.save(currentSelectedTour);
    }


    public List<String> updateFullList(){
        return tourRepository.findAll().stream().map(Tour::getTourName).toList();
    }

    public List<String> getTourWithName() {
        Optional<Tour> tour = tourRepository.findByTourName(currentSelectedTourName);
        List<String> tourDetails = new ArrayList<>();

        if(tour.isPresent()) {
            currentSelectedTour = tour.get();

            tourDetails.add(currentSelectedTour.getTourName());
            tourDetails.add(currentSelectedTour.getTourDescription());
            tourDetails.add(currentSelectedTour.getTourStart());
            tourDetails.add(currentSelectedTour.getTourDestination());
            tourDetails.add(currentSelectedTour.getTourType());
            tourDetails.add(currentSelectedTour.getTourDistance());
            tourDetails.add(currentSelectedTour.getTourDuration());
            tourDetails.add(currentSelectedTour.getTourInformation());
            tourDetails.add(currentSelectedTour.getTourImage());

            return tourDetails;
        }

        return null;
    }

    public String getCurrentSelectedTour() {
        return currentSelectedTourName;
    }

    public void setCurrentSelectedTourName(String tourName) {
        System.out.println("in setter");
        currentSelectedTourName = tourName;
        System.out.println("TourName: " + currentSelectedTourName);
    }

    public void removeTourFromRepository(String tourToRemove) {
        tourRepository.removeTour(tourToRemove);
    }

    private String getRandomPicture(){
        List<String> maps = new ArrayList<>();
        maps.add("/org/shayvin/tourplanner/img/map-picture.png");
        maps.add("/org/shayvin/tourplanner/img/location.png");
        maps.add("/org/shayvin/tourplanner/img/gps-tracker.png");
        maps.add("/org/shayvin/tourplanner/img/global-mobility.png");

        Random rand = new Random();
        return maps.get(rand.nextInt(maps.size()));
    }
}
