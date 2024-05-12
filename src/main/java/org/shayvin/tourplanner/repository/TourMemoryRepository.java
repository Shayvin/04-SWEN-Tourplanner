package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourMemoryRepository implements TourRepository{

    List<Tour> tours;

    public TourMemoryRepository() {
        tours = new ArrayList<>();
    }

    // returns all tours in repository
    @Override
    public List<Tour> findAll() {
        System.out.println("In memory findAll!");
        return tours;
    }

    // adds new tour to repository
    @Override
    public Tour save(Tour entity) {
        System.out.println("In memory save!");
        System.out.println("Saving " + entity);
        tours.add(entity);

        return entity;
    }

    // returns one tour if it exists in the repository
    @Override
    public Optional<Tour> findByTourName(String tourToFind) {
        for (Tour tour : tours) {
            if (!tour.getTourName().equals(tourToFind)){
                continue;
            }
            return Optional.of(tour);
        }
        return Optional.empty();
    }

    // delete tour from repository
    @Override
    public void removeTour(String tourToRemove) {
        System.out.println("In memory removeTour!");
        tours.removeIf(tour -> tour.getTourName().equals(tourToRemove));

    }
}
