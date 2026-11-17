package com.example.laptoprecommender;

import com.example.laptoprecommender.controller.StoreLocatorController;
import com.example.laptoprecommender.model.StoreSearchResult;
import com.example.laptoprecommender.service.StoreLocatorService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class StoreLocatorControllerTest {

    @Test
    void testGetNearbyStoresController() {
        StoreLocatorService service = new StoreLocatorService();
        StoreLocatorController controller = new StoreLocatorController(service);

        ResponseEntity<StoreSearchResult> response = controller.getNearbyStores("ASUS TUF A17", "411038");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("ASUS", response.getBody().getBrand());
        assertEquals("411038", response.getBody().getPincode());
        assertFalse(response.getBody().getStores().isEmpty());
    }
}
