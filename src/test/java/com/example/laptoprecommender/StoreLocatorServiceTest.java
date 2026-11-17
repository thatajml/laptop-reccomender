package com.example.laptoprecommender;

import com.example.laptoprecommender.model.StoreInfo;
import com.example.laptoprecommender.model.StoreSearchResult;
import com.example.laptoprecommender.service.StoreLocatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreLocatorServiceTest {

    private StoreLocatorService storeLocatorService;

    @BeforeEach
    void setUp() {
        storeLocatorService = new StoreLocatorService();
    }

    @Test
    void testExtractBrand() {
        assertEquals("ASUS", storeLocatorService.extractBrand("ASUS TUF Gaming A17"));
        assertEquals("ASUS", storeLocatorService.extractBrand("ROG Strix G16"));
        assertEquals("Lenovo", storeLocatorService.extractBrand("Lenovo IdeaPad Slim 3"));
        assertEquals("HP", storeLocatorService.extractBrand("HP Victus 15"));
        assertEquals("Dell", storeLocatorService.extractBrand("Dell Alienware m16"));
        assertEquals("Apple", storeLocatorService.extractBrand("Apple MacBook Air M2"));
        assertEquals("Acer", storeLocatorService.extractBrand("Acer Predator Helios Neo 16"));
        assertEquals("MSI", storeLocatorService.extractBrand("MSI Katana 15"));
        assertEquals("Samsung", storeLocatorService.extractBrand("Samsung Galaxy Book4"));
    }

    @Test
    void testInvalidPincode() {
        StoreSearchResult resShort = storeLocatorService.findNearbyStores("ASUS TUF Gaming A17", "123");
        assertEquals("INVALID_PINCODE", resShort.getStatus());

        StoreSearchResult resAlpha = storeLocatorService.findNearbyStores("ASUS TUF Gaming A17", "ABCDEF");
        assertEquals("INVALID_PINCODE", resAlpha.getStatus());

        StoreSearchResult resNull = storeLocatorService.findNearbyStores("ASUS TUF Gaming A17", null);
        assertEquals("INVALID_PINCODE", resNull.getStatus());
    }

    @Test
    void testNearbyStoresForAsusInPune() {
        StoreSearchResult result = storeLocatorService.findNearbyStores("ASUS TUF Gaming A17", "411038");

        assertNotNull(result);
        assertEquals("411038", result.getPincode());
        assertEquals("ASUS", result.getBrand());
        assertEquals("SUCCESS", result.getStatus());
        assertFalse(result.getStores().isEmpty(), "Stores list should not be empty for 411038");

        // Verify that at least one official brand store and retailer partners are present
        boolean foundAsusExclusive = false;
        boolean foundRetailer = false;

        for (StoreInfo s : result.getStores()) {
            assertNotNull(s.getName());
            assertNotNull(s.getAddress());
            assertNotNull(s.getPhone());
            assertNotNull(s.getGoogleMapsUrl());
            assertNotNull(s.getDirectionsUrl());

            if (s.getName().toLowerCase().contains("asus")) {
                foundAsusExclusive = true;
            }
            if (s.getType().toLowerCase().contains("retailer") || s.getName().toLowerCase().contains("reliance") || s.getName().toLowerCase().contains("croma")) {
                foundRetailer = true;
            }
        }

        assertTrue(foundAsusExclusive, "Should find at least one ASUS Exclusive/ROG store in Pune");
        assertTrue(foundRetailer, "Should find authorized retail partners");
    }

    @Test
    void testStoreNavigationUrls() {
        StoreSearchResult result = storeLocatorService.findNearbyStores("Apple MacBook Air", "560001");

        assertNotNull(result);
        assertEquals("560001", result.getPincode());
        assertEquals("Apple", result.getBrand());

        for (StoreInfo store : result.getStores()) {
            assertTrue(store.getGoogleMapsUrl().startsWith("https://www.google.com/maps/search/?api=1&query="));
            assertTrue(store.getDirectionsUrl().startsWith("https://www.google.com/maps/dir/?api=1&destination="));
        }
    }
}
