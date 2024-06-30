import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shayvin.tourplanner.service.ConfigService;
import org.shayvin.tourplanner.service.OpenRouteService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenRouteServiceTest {

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private ConfigService configService;

    @Mock
    private OpenRouteService openRouteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openRouteService = new OpenRouteService(configService);
        openRouteService.setHttpClient(httpClient);
    }

    @Test
    void testGetCoordinatesSuccess() throws IOException {
        String json = "{\"features\": [{\"geometry\": {\"coordinates\": [16.3731, 48.2083]}}]}";

        // Mock HttpEntity
        HttpEntity httpEntity = mock(HttpEntity.class);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()));

        // Mock CloseableHttpResponse
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine()).thenReturn(mock(StatusLine.class));
        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);

        // Mock HttpClient execute method to return the mocked response
        when(httpClient.execute(any())).thenReturn(httpResponse);

        // Call the method under test
        double[] coordinates = openRouteService.getCoordinates("Wien");

        // Verify the result
        assertNotNull(coordinates);
        assertEquals(16.3731, coordinates[0]);
        assertEquals(48.2083, coordinates[1]);
    }


    @Test
    void testGetCoordinatesFailure() throws IOException {
        when(httpResponse.getStatusLine()).thenReturn(mock(StatusLine.class));
        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(404);
        when(httpClient.execute(any())).thenReturn(httpResponse);

        assertThrows(IOException.class, () -> openRouteService.getCoordinates("WienWienWien"));
    }

    @Test
    void testCalculateDistance() throws Exception {
        String json = "{\"features\": [{\"properties\": {\"summary\": {\"distance\": 1234.56}}}]}";

        // Mock successful response from HTTP client for calculateDistance()
        HttpEntity httpEntity = mock(HttpEntity.class);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        when(httpEntity.getContent()).thenReturn(inputStream);

        // Mock successful response from HTTP client for execute method
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine()).thenReturn(mock(StatusLine.class));
        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);
        when(httpClient.execute(any())).thenReturn(httpResponse);

        // Call calculateDistance() and capture the returned distance
        String distance = openRouteService.calculateDistance(json);

        // Verify the distance calculation
        assertNotNull(distance);
        assertEquals("1234.56", distance);
    }

    @Test
    void testCalculateDuration() throws Exception {
        String json = "{\"features\": [{\"properties\": {\"summary\": {\"duration\": 1234.56}}}]}";

        // Mock successful response from HTTP client for calculateDuration()
        HttpEntity httpEntity = mock(HttpEntity.class);
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        when(httpEntity.getContent()).thenReturn(inputStream);

        // Mock successful response from HTTP client for execute method
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine()).thenReturn(mock(StatusLine.class));
        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);
        when(httpClient.execute(any())).thenReturn(httpResponse);

        // Call calculateDuration() and capture the returned duration
        String duration = openRouteService.calculateDuration(json);

        // Verify the duration calculation
        assertEquals("1234.56", duration);
    }
}
