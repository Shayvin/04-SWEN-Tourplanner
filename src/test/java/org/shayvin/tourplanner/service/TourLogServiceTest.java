package org.shayvin.tourplanner.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourMemoryRepository;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class TourLogServiceTest {

    @Mock
    private TourMemoryRepository tourMemoryRepository;

    @Mock
    private TourService tourService;

    @InjectMocks
    private TourLogService tourLogService;

    private Tour tour;
    private TourLog tourLog;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tour = new Tour();
        tour.setTourLogList(new ArrayList<>());
        tourService.currentTour = tour;

        tourLog = new TourLog();
        tourLog.setId(UUID.randomUUID());
    }

    @Test
    public void testAddTourLogData() {
        tourLogService.addTourLogData(tourLog);

        verify(tourService).addTourLog(tourLog);
        verify(tourMemoryRepository).update(tour);
    }

    @Test
    public void testRemoveTourLogFromRepository() {
        tour.getTourLogList().add(tourLog);

        tourLogService.removeTourLogFromRepository(tourLog);

        assertFalse(tour.getTourLogList().contains(tourLog));
        verify(tourMemoryRepository).update(tour);
    }

    @Test
    public void testSetCurrentTourLog() {
        tourLogService.setCurrentTourLog(tourLog);
        assertEquals(tourLog, tourLogService.getSelectedTourLog());
    }

    @Test
    public void testGetSelectedTourLog() {
        tourLogService.setCurrentTourLog(tourLog);
        assertEquals(tourLog, tourLogService.getSelectedTourLog());
    }

    @Test
    public void testSetSelectedTourLog() {
        tourLogService.setSelectedTourLog(tourLog);
        assertEquals(tourLog, tourLogService.getSelectedTourLog());
    }

    @Test
    public void testGetTourLogsByName() {
        tour.getTourLogList().add(tourLog);
        ObservableList<TourLog> tourLogs = FXCollections.observableArrayList(tour.getTourLogList());

        ObservableList<TourLog> result = tourLogService.getTourLogsByName();
        assertEquals(tourLogs, result);
    }
}