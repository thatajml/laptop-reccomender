package com.example.laptoprecommender.scraper.model;

import java.util.ArrayList;
import java.util.List;

public class ScrapingResult {
    private int totalFound;
    private int successfullyParsed;
    private int failedToParse;
    private int newLaptops;
    private int updatedLaptops;
    private long durationMs;
    private List<String> errors = new ArrayList<>();
    private String source;

    // Getters and Setters
    public int getTotalFound() { return totalFound; }
    public void setTotalFound(int totalFound) { this.totalFound = totalFound; }

    public int getSuccessfullyParsed() { return successfullyParsed; }
    public void setSuccessfullyParsed(int successfullyParsed) { this.successfullyParsed = successfullyParsed; }

    public int getFailedToParse() { return failedToParse; }
    public void setFailedToParse(int failedToParse) { this.failedToParse = failedToParse; }

    public int getNewLaptops() { return newLaptops; }
    public void setNewLaptops(int newLaptops) { this.newLaptops = newLaptops; }

    public int getUpdatedLaptops() { return updatedLaptops; }
    public void setUpdatedLaptops(int updatedLaptops) { this.updatedLaptops = updatedLaptops; }

    public long getDurationMs() { return durationMs; }
    public void setDurationMs(long durationMs) { this.durationMs = durationMs; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    
    public void addError(String error) { this.errors.add(error); }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
