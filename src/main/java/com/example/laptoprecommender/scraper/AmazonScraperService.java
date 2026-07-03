package com.example.laptoprecommender.scraper;

import com.example.laptoprecommender.scraper.model.RawLaptopData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AmazonScraperService {

    private final ScrapingConfig config;
    private final WebDriverFactory driverFactory;
    private final Random random = new Random();

    public AmazonScraperService(ScrapingConfig config, WebDriverFactory driverFactory) {
        this.config = config;
        this.driverFactory = driverFactory;
    }

    public List<RawLaptopData> scrapeSearchResults(int maxItems) {
        List<RawLaptopData> scrapedData = new ArrayList<>();
        WebDriver driver = null;

        try {
            driver = driverFactory.createHeadlessDriver();
            int page = 1;

            while (scrapedData.size() < maxItems && page <= config.getMaxPages()) {
                String url = config.getAmazonBaseUrl() + config.getAmazonSearchQuery() + "&page=" + page;
                System.out.println("[Amazon Scraper] Fetching: " + url);

                driver.get(url);
                sleep(2000); // Let page JS execute

                // Check for CAPTCHA
                if (isCaptchaPage(driver)) {
                    System.out.println("[Amazon Scraper] CAPTCHA detected! Aborting scrape.");
                    break;
                }

                // Wait for product cards to render
                List<WebElement> productCards;
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getElementWaitTimeoutSec()));
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div[data-component-type='s-search-result']")));

                    productCards = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));
                } catch (TimeoutException e) {
                    System.out.println("[Amazon Scraper] Timeout waiting for product cards on page " + page + ". Stopping.");
                    break;
                }

                if (productCards.isEmpty()) {
                    System.out.println("[Amazon Scraper] No product cards found on page " + page + ". Stopping.");
                    break;
                }

                System.out.println("[Amazon Scraper] Found " + productCards.size() + " cards on page " + page);

                for (WebElement card : productCards) {
                    if (scrapedData.size() >= maxItems) break;

                    try {
                        RawLaptopData data = extractAmazonCard(card);
                        if (data != null && data.getTitle() != null && !data.getTitle().isEmpty()) {
                            scrapedData.add(data);
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("[Amazon Scraper] Stale element, skipping card.");
                    } catch (Exception e) {
                        System.out.println("[Amazon Scraper] Error extracting card: " + e.getMessage());
                    }
                }

                page++;
                sleep(config.getDelayBetweenRequestsMs());
            }
        } catch (Exception e) {
            System.err.println("[Amazon Scraper] Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("[Amazon Scraper] Driver closed.");
            }
        }

        System.out.println("[Amazon Scraper] Total scraped: " + scrapedData.size());
        return scrapedData;
    }

    private RawLaptopData extractAmazonCard(WebElement card) {
        RawLaptopData data = new RawLaptopData();

        // Title
        data.setTitle(safeText(card, "h2 a span", "h2 span"));

        // Price
        data.setPriceText(safeText(card, "span.a-price-whole"));

        // Rating
        data.setRatingText(safeText(card, "span.a-icon-alt"));

        // Image
        String imgSrc = safeAttr(card, "src", "img.s-image");
        data.setImageUrl(imgSrc);

        // Product URL
        String href = safeAttr(card, "href", "h2 a", "a.a-link-normal.s-no-outline");
        if (href != null && !href.isEmpty()) {
            if (!href.startsWith("http")) {
                href = config.getAmazonBaseUrl() + href;
            }
            data.setProductUrl(href);
        }

        return data;
    }

    private boolean isCaptchaPage(WebDriver driver) {
        try {
            List<WebElement> captchaElements = driver.findElements(By.cssSelector("input#captchacharacters, form[action*='validateCaptcha']"));
            return !captchaElements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private String safeText(WebElement parent, String... selectors) {
        for (String selector : selectors) {
            try {
                List<WebElement> elements = parent.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    String text = elements.get(0).getText().trim();
                    if (!text.isEmpty()) return text;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private String safeAttr(WebElement parent, String attr, String... selectors) {
        for (String selector : selectors) {
            try {
                List<WebElement> elements = parent.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    String val = elements.get(0).getAttribute(attr);
                    if (val != null && !val.isEmpty()) return val;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private void sleep(long baseMs) {
        try {
            Thread.sleep(baseMs + random.nextInt(1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
