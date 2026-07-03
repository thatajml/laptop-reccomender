package com.example.laptoprecommender.scraper;

import com.example.laptoprecommender.config.DataSeeder;
import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.repository.LaptopRepository;
import com.example.laptoprecommender.scraper.model.RawLaptopData;
import com.example.laptoprecommender.scraper.model.ScrapingResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScrapingService {

    private final ScrapingConfig config;
    private final FlipkartScraperService flipkartScraper;
    private final AmazonScraperService amazonScraper;
    private final SpecNormalizerService normalizer;
    private final LaptopRepository repository;
    private final DataSeeder dataSeeder;

    public ScrapingService(ScrapingConfig config, FlipkartScraperService flipkartScraper, 
                           AmazonScraperService amazonScraper, SpecNormalizerService normalizer, 
                           LaptopRepository repository, DataSeeder dataSeeder) {
        this.config = config;
        this.flipkartScraper = flipkartScraper;
        this.amazonScraper = amazonScraper;
        this.normalizer = normalizer;
        this.repository = repository;
        this.dataSeeder = dataSeeder;
    }

    public ScrapingResult scrape(String source, int limit) {
        long startTime = System.currentTimeMillis();
        ScrapingResult result = new ScrapingResult();
        result.setSource(source);

        if (!config.isEnabled()) {
            result.addError("Scraping is disabled in configuration.");
            return result;
        }

        List<RawLaptopData> rawDataList;
        if ("flipkart".equalsIgnoreCase(source)) {
            rawDataList = flipkartScraper.scrapeSearchResults(limit);
        } else if ("amazon".equalsIgnoreCase(source)) {
            rawDataList = amazonScraper.scrapeSearchResults(limit);
        } else {
            result.addError("Unknown source: " + source);
            return result;
        }

        result.setTotalFound(rawDataList.size());
        
        List<Laptop> existingLaptops = repository.findAll();
        int maxId = existingLaptops.stream().mapToInt(Laptop::getLaptopId).max().orElse(0);

        List<Laptop> toSave = new ArrayList<>();

        for (RawLaptopData raw : rawDataList) {
            try {
                // Determine source prefix
                String dbSource = "flipkart".equalsIgnoreCase(source) ? "SCRAPED_FLIPKART" : "SCRAPED_AMAZON";
                
                Laptop parsed = normalizer.normalize(raw, dbSource);
                parsed.setLastScrapedAt(Instant.now());
                
                // Assign benchmarks
                dataSeeder.assignBenchmarks(Collections.singletonList(parsed));

                // Upsert logic: match by normalized title
                Laptop existing = findByTitle(existingLaptops, parsed.getTitle());
                if (existing != null) {
                    // Update fields
                    existing.setPrice(parsed.getPrice());
                    existing.setRating(parsed.getRating());
                    existing.setThumbnail(parsed.getThumbnail());
                    existing.setProductUrl(parsed.getProductUrl());
                    existing.setLastScrapedAt(parsed.getLastScrapedAt());
                    existing.setSource(parsed.getSource());
                    toSave.add(existing);
                    result.setUpdatedLaptops(result.getUpdatedLaptops() + 1);
                } else {
                    // New insertion
                    maxId++;
                    parsed.setLaptopId(maxId);
                    toSave.add(parsed);
                    existingLaptops.add(parsed); // update local cache for subsequent matches
                    result.setNewLaptops(result.getNewLaptops() + 1);
                }
                
                result.setSuccessfullyParsed(result.getSuccessfullyParsed() + 1);
                
            } catch (Exception e) {
                result.setFailedToParse(result.getFailedToParse() + 1);
                result.addError("Failed to parse laptop: " + raw.getTitle() + " - " + e.getMessage());
            }
        }

        if (!toSave.isEmpty()) {
            repository.saveAll(toSave);
        }

        result.setDurationMs(System.currentTimeMillis() - startTime);
        return result;
    }
    
    private Laptop findByTitle(List<Laptop> laptops, String title) {
        if (title == null) return null;
        for (Laptop l : laptops) {
            if (title.equalsIgnoreCase(l.getTitle())) {
                return l;
            }
        }
        return null;
    }
}
