package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepository {

    List<Tour> findAll();

    Tour save (Tour entity);

    Optional<Tour> findByTourName(String tourToFind);

    void removeTour(String tourToRemove);

}
