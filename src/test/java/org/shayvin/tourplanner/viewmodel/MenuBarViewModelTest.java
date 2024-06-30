package org.shayvin.tourplanner.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shayvin.tourplanner.pdf.PdfBox;
import org.shayvin.tourplanner.service.CreatePopupService;

import static org.mockito.Mockito.*;

public class MenuBarViewModelTest {

    @Mock
    private PdfBox mockPdfBox;

    @Mock
    private CreatePopupService createPopupService;

    private MenuBarViewModel menuBarViewModel;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuBarViewModel = new MenuBarViewModel(null, mockPdfBox, createPopupService); // Inject mock PdfBox
    }

    @Test
    void testExportPdf() {
        // Define any necessary mock behavior
        doNothing().when(mockPdfBox).exportToPDF("tour.pdf");

        menuBarViewModel.exportPdf();

        verify(mockPdfBox).exportToPDF("tour.pdf");
    }
}
