package org.shayvin.tourplanner.service;

import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.TourMemoryRepository;

import java.util.*;

public class TourService {
    private final TourMemoryRepository tourMemoryRepository;

    public String currentTourName;
    public Tour currentTour;
    public UUID currentTourId;

    public TourService(TourMemoryRepository tourMemoryRepository) {
        this.tourMemoryRepository = tourMemoryRepository;

    }

    // adds tour into repository if one with the same tourName doesn't exist
    public void add(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation) {
        Optional<Tour> tour = tourMemoryRepository.findByName(tourName);

        if(tour.isPresent()) {
            currentTour = tour.get();
            currentTourId = currentTour.getId();
            // TODO add error handling
            return;
        }

        // set image for current tour
        //TODO rework this: let user upload pictures and add those to the repository
        String imagePath = getRandomPicture();

        Tour addedTour = tourMemoryRepository.save(new Tour(tourName, tourDescription, tourStart, tourDestination, tourType, tourDistance, tourDuration, tourInformation, imagePath));
        currentTour = addedTour;
        currentTourId = addedTour.getId();
    }

    // checks if values are different to current tour and refreshes them, saves it afterwards in db
    public void updateTour(String tourName, String tourDescription, String tourStart, String tourDestination, String tourType, String tourDistance, String tourDuration, String tourInformation){
        // removes current entry ->  TODO will be reworked as soon as there are IDs
        removeTourFromRepository(currentTourName);

        // changes data if edited
        if(!currentTour.getName().equals(tourName)) {
            currentTour.setName(tourName);
        }
        if(!currentTour.getDescription().equals(tourDescription)) {
            currentTour.setDescription(tourDescription);
        }
        if(!currentTour.getStart().equals(tourStart)) {
            currentTour.setStart(tourStart);
        }
        if(!currentTour.getDestination().equals(tourDestination)) {
            currentTour.setDestination(tourDestination);
        }
        if(!currentTour.getType().equals(tourType)) {
            currentTour.setType(tourType);
        }
        if(!currentTour.getDistance().equals(tourDistance)) {
            currentTour.setDistance(tourDistance);
        }
        if(!currentTour.getDuration().equals(tourDuration)) {
            currentTour.setDuration(tourDuration);
        }
        if(!currentTour.getInformation().equals(tourInformation)) {
            currentTour.setInformation(tourInformation);
        }
        //TODO rework this: check id, adapt the entries and save the object with the same id (so that the tourlogs won't go missing
        tourMemoryRepository.update(currentTour);
    }

    // update tourList
    public List<String> updateFullList(){
        return tourMemoryRepository.findAll().stream().map(Tour::getName).toList();
    }

    // get tour from repo with the tourName given
    public List<String> getTourWithName() {
        Optional<Tour> tour = tourMemoryRepository.findByName(currentTourName);
        List<String> tourDetails = new ArrayList<>();

        if(tour.isPresent()) {
            currentTour = tour.get();

            tourDetails.add(currentTour.getName());
            tourDetails.add(currentTour.getDescription());
            tourDetails.add(currentTour.getStart());
            tourDetails.add(currentTour.getDestination());
            tourDetails.add(currentTour.getType());
            tourDetails.add(currentTour.getDistance());
            tourDetails.add(currentTour.getDuration());
            tourDetails.add(currentTour.getInformation());
            tourDetails.add(currentTour.getImage());

            return tourDetails;
        }

        return null;
    }

    public String getCurrentSelectedTour() {
        return currentTourName;
    }

    public void setCurrentTourName(String tourName) {
        Optional <Tour> tour = tourMemoryRepository.findByName(tourName);
        tour.ifPresent(value -> currentTour = value);

        this.currentTourName = tourName;
        this.currentTourId = currentTour.getId();
    }

    public void clearCurrentTour(){
        currentTourName = null;
        currentTour = null;
        currentTourId = null;
    }

    public UUID getCurrentTourId() {
        return currentTourId;
    }

    public void removeTourFromRepository(String tourToRemove) {
        if(tourToRemove.equals(currentTourName)){
            tourMemoryRepository.removeTour(currentTourId);
        }else{
            System.out.println("WARRRRRUUUUUUUUM");
        }
    }

    // current randomizer to set random picture for each route
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
