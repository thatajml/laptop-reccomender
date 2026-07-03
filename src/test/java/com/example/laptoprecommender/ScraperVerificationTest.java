package com.example.laptoprecommender;

import com.example.laptoprecommender.scraper.ScrapingService;
import com.example.laptoprecommender.scraper.model.ScrapingResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.example.laptoprecommender.repository.LaptopRepository;
import com.example.laptoprecommender.config.DataSeeder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ScraperVerificationTest {

    @Autowired
    private ScrapingService scrapingService;

    @MockitoBean
    private LaptopRepository laptopRepository;

    @MockitoBean
    private DataSeeder dataSeeder;

    @Test
    public void testFlipkartSeleniumScraping() {
        System.out.println("Starting Flipkart Selenium Scrape Test (limit=5)...");
        ScrapingResult result = scrapingService.scrape("flipkart", 5);

        System.out.println("===== FLIPKART SCRAPE RESULT =====");
        System.out.println("Total Found: " + result.getTotalFound());
        System.out.println("Successfully Parsed: " + result.getSuccessfullyParsed());
        System.out.println("Failed to Parse: " + result.getFailedToParse());
        System.out.println("New Laptops: " + result.getNewLaptops());
        System.out.println("Updated Laptops: " + result.getUpdatedLaptops());
        System.out.println("Duration (ms): " + result.getDurationMs());

        if (!result.getErrors().isEmpty()) {
            System.out.println("Errors:");
            result.getErrors().forEach(e -> System.out.println("  - " + e));
        }

        assertTrue(result.getTotalFound() > 0, "Should have found at least one laptop on Flipkart via Selenium");
        assertTrue(result.getSuccessfullyParsed() > 0, "Should have successfully parsed at least one laptop");
    }

    @Test
    public void testAmazonSeleniumScraping() {
        System.out.println("Starting Amazon Selenium Scrape Test (limit=5)...");
        ScrapingResult result = scrapingService.scrape("amazon", 5);

        System.out.println("===== AMAZON SCRAPE RESULT =====");
        System.out.println("Total Found: " + result.getTotalFound());
        System.out.println("Successfully Parsed: " + result.getSuccessfullyParsed());
        System.out.println("Failed to Parse: " + result.getFailedToParse());
        System.out.println("New Laptops: " + result.getNewLaptops());
        System.out.println("Updated Laptops: " + result.getUpdatedLaptops());
        System.out.println("Duration (ms): " + result.getDurationMs());

        if (!result.getErrors().isEmpty()) {
            System.out.println("Errors:");
            result.getErrors().forEach(e -> System.out.println("  - " + e));
        }

        assertTrue(result.getTotalFound() > 0, "Should have found at least one laptop on Amazon via Selenium");
        assertTrue(result.getSuccessfullyParsed() > 0, "Should have successfully parsed at least one laptop");
    }
}
