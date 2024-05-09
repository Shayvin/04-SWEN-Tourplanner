package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourMemoryRepository implements TourRepository{

    private final List<Tour> tours;

    public TourMemoryRepository() {
        tours = new ArrayList<>();
    }

    @Override
    public List<Tour> findAll() {
        return tours;
    }

    @Override
    public Tour save(Tour entity) {
        System.out.println("In Tour Save!");
        System.out.println("Saving " + entity);
        tours.add(entity);

        return entity;
    }

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
}
