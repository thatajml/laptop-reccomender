package com.example.laptoprecommender.scraper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scraper")
public class ScrapingConfig {
    private boolean enabled = true;
    private String flipkartBaseUrl = "https://www.flipkart.com";
    private String flipkartSearchQuery = "/search?q=laptop";
    private String amazonBaseUrl = "https://www.amazon.in";
    private String amazonSearchQuery = "/s?k=laptops&i=computers";
    private int maxPages = 5;
    private int delayBetweenRequestsMs = 2000;
    private int timeoutMs = 15000;
    private int maxRetries = 3;

    // Selenium-specific
    private int pageLoadTimeoutSec = 15;
    private int elementWaitTimeoutSec = 10;
    private boolean headless = true;

    // Getters and Setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getFlipkartBaseUrl() { return flipkartBaseUrl; }
    public void setFlipkartBaseUrl(String flipkartBaseUrl) { this.flipkartBaseUrl = flipkartBaseUrl; }

    public String getFlipkartSearchQuery() { return flipkartSearchQuery; }
    public void setFlipkartSearchQuery(String flipkartSearchQuery) { this.flipkartSearchQuery = flipkartSearchQuery; }

    public String getAmazonBaseUrl() { return amazonBaseUrl; }
    public void setAmazonBaseUrl(String amazonBaseUrl) { this.amazonBaseUrl = amazonBaseUrl; }

    public String getAmazonSearchQuery() { return amazonSearchQuery; }
    public void setAmazonSearchQuery(String amazonSearchQuery) { this.amazonSearchQuery = amazonSearchQuery; }

    public int getMaxPages() { return maxPages; }
    public void setMaxPages(int maxPages) { this.maxPages = maxPages; }

    public int getDelayBetweenRequestsMs() { return delayBetweenRequestsMs; }
    public void setDelayBetweenRequestsMs(int delayBetweenRequestsMs) { this.delayBetweenRequestsMs = delayBetweenRequestsMs; }

    public int getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(int timeoutMs) { this.timeoutMs = timeoutMs; }

    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }

    public int getPageLoadTimeoutSec() { return pageLoadTimeoutSec; }
    public void setPageLoadTimeoutSec(int pageLoadTimeoutSec) { this.pageLoadTimeoutSec = pageLoadTimeoutSec; }

    public int getElementWaitTimeoutSec() { return elementWaitTimeoutSec; }
    public void setElementWaitTimeoutSec(int elementWaitTimeoutSec) { this.elementWaitTimeoutSec = elementWaitTimeoutSec; }

    public boolean isHeadless() { return headless; }
    public void setHeadless(boolean headless) { this.headless = headless; }
}
