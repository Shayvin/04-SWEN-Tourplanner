package org.shayvin.tourplanner.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shayvin.tourplanner.entity.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TourMemoryRepositoryTest {

    public TourMemoryRepository tourMemoryRepository;

    @BeforeEach
    void setUp() {
        tourMemoryRepository = new TourMemoryRepository();

        List<Tour> testTours = new ArrayList<>();

        Tour tour1 = new Tour("name1", "description1", "start1", "destination1", "type1", "1", "1", "info1", "pathToImage/pic1.png");
        Tour tour2 = new Tour("name2", "description2", "start2", "destination2", "type2", "2", "2", "info2", "pathToImage/pic2.png");

        testTours.add(tour1);
        testTours.add(tour2);

        tourMemoryRepository.tours = testTours;
    }

    // tests the TourMemoryRepository functions

    @Test
    void findAllSuccess() {
        List<Tour> findAll = tourMemoryRepository.findAll();

        assertEquals(2, findAll.size());
    }

    @Test
    void findAllFailure() {
        List<Tour> findAll = tourMemoryRepository.findAll();

        assertNotEquals(1, findAll.size());
    }

    @Test
    void saveSuccess() {
        Tour tour3 = new Tour("name3", "description3", "start3", "destination3", "type3", "3", "3", "info3", "pathToImage/pic3.png");
        tourMemoryRepository.save(tour3);

        assertTrue(tourMemoryRepository.tours.contains(tour3));
    }

    @Test
    void saveFailure() {
        Tour tour3 = new Tour("name3", "description3", "start3", "destination3", "type3", "3", "3", "info3", "pathToImage/pic3.png");
        tourMemoryRepository.save(tour3);

        assertNotEquals(2, tourMemoryRepository.tours.size());
    }

    @Test
    void findByTourNameSuccess() {
        String tourToFind = "name1";
        Optional<Tour> foundTour = tourMemoryRepository.findByTourName(tourToFind);

        assertTrue(foundTour.isPresent());
    }

    @Test
    void findByTourNameFailure() {
        String tourToFind = "TOURNAME";
        Optional<Tour> foundTour = tourMemoryRepository.findByTourName(tourToFind);

        assertFalse(foundTour.isPresent());
    }

    @Test
    void removeTourSuccess() {

        tourMemoryRepository.removeTour("name1");

        assertEquals(1, tourMemoryRepository.tours.size());
    }

    @Test
    void removeTourFailure() {

        tourMemoryRepository.removeTour("name1");

        assertNotEquals(2, tourMemoryRepository.tours.size());
    }

}