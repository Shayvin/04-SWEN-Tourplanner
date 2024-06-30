import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.service.TourService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TourServiceTest {

    private TourService tourService;
    private TourMemoryRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(TourMemoryRepository.class);
        tourService = new TourService(mockRepository);
    }

    @Test
    void testAddTour() {
        // Mock repository behavior
        when(mockRepository.findByName("Tour1")).thenReturn(Optional.empty());
        when(mockRepository.save(any(Tour.class))).thenAnswer(invocation -> {
            Tour tour = invocation.getArgument(0);
            tour.setId(UUID.randomUUID());
            return tour;
        });

        // Add a tour
        tourService.add("Tour1", "Description", "Start", "Destination", "Type", "Distance", "Duration", "Information");

        // Verify the tour is added
        assertEquals("Tour1", tourService.getTourWithName().get(1));
        assertEquals(1, tourService.updateFullList().size());
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
