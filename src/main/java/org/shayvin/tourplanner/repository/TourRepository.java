package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    List<Tour> findAll();

    Tour save (Tour entity);

    Tour update (Tour entity);

    Optional<Tour> findByName(String tourToFind);

    void removeTour(UUID tourToRemove);

}
