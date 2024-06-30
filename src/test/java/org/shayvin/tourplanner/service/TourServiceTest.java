package org.shayvin.tourplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.repository.TourMemoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TourServiceTest {

    private TourService tourService;

    @Mock
    private TourMemoryRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        tourService = new TourService(mockRepository);
    }

    @Test
    void testUpdateTour() {
        // Mock repository behavior
        Tour existingTour = new Tour("Tour1", "Description", "Start", "Destination", "Type", "Distance", "Duration", "Information", "image.png");
        existingTour.setId(UUID.randomUUID());
        when(mockRepository.findByName("Tour1")).thenReturn(Optional.of(existingTour));

        // Update tour details
        tourService.setCurrentTourName("Tour1");
        tourService.updateTour("Tour1", "Updated Description", "Start", "Destination", "Type", "Distance", "Duration", "Information");

        // Verify tour details are updated
        verify(mockRepository, times(1)).update(any(Tour.class));
        assertEquals("Updated Description", tourService.getTourWithName().get(1));
    }

    @Test
    void testGetTourWithName() {
        // Mock repository behavior
        Tour existingTour = new Tour("Tour1", "Description", "Start", "Destination", "Type", "Distance", "Duration", "Information", "image.png");
        when(mockRepository.findByName("Tour1")).thenReturn(Optional.of(existingTour));

        // Set current tour
        tourService.setCurrentTourName("Tour1");

        // Get tour details
        List<String> tourDetails = tourService.getTourWithName();
        assertEquals(9, tourDetails.size());
        assertEquals("Tour1", tourDetails.get(0));
    }

    @Test
    void testRemoveTourFromRepository() {
        // Mock repository behavior
        Tour existingTour = new Tour("Tour1", "Description", "Start", "Destination", "Type", "Distance", "Duration", "Information", "image.png");
        existingTour.setId(UUID.randomUUID());
        when(mockRepository.findByName("Tour1")).thenReturn(Optional.of(existingTour));

        // Set current tour
        tourService.setCurrentTourName("Tour1");

        // Remove tour from repository
        tourService.removeTourFromRepository("Tour1");

        // Verify tour is removed
        verify(mockRepository, times(1)).removeTour(existingTour.getId());
    }
}
