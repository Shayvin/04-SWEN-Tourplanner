import javafx.beans.property.BooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shayvin.tourplanner.event.Event;
import org.shayvin.tourplanner.event.Publisher;
import org.shayvin.tourplanner.event.Subscriber;
import org.shayvin.tourplanner.viewmodel.RouteButtonsViewModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteButtonsViewModelTest {

    @Mock
    private Publisher publisher;

    private RouteButtonsViewModel viewModel;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new RouteButtonsViewModel(publisher);
    }

    @Test
    void testAddButton() {
        viewModel.add();
        verify(publisher).publish(eq(Event.ADD_TOUR), stringCaptor.capture());
        assertEquals("Add tour button clicked", stringCaptor.getValue());
    }

    @Test
    void testRemoveButton() {
        viewModel.remove();
        verify(publisher).publish(eq(Event.REMOVE_TOUR), stringCaptor.capture());
        assertEquals("Remove tour button clicked", stringCaptor.getValue());
    }

    @Test
    void testEditButton() {
        viewModel.edit();
        verify(publisher).publish(eq(Event.EDIT_TOUR), stringCaptor.capture());
        assertEquals("Edit tour button clicked", stringCaptor.getValue());
    }

    @Test
    void testSaveButton() {
        viewModel.save();
        verify(publisher).publish(eq(Event.SAVE_EDITED_TOUR), stringCaptor.capture());
        assertEquals("Save tour button clicked", stringCaptor.getValue());
    }
}
