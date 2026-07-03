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
public class FlipkartScraperService {

    private final ScrapingConfig config;
    private final WebDriverFactory driverFactory;
    private final Random random = new Random();

    public FlipkartScraperService(ScrapingConfig config, WebDriverFactory driverFactory) {
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
                String url = config.getFlipkartBaseUrl() + config.getFlipkartSearchQuery() + "&page=" + page;
                System.out.println("[Flipkart Scraper] Fetching: " + url);

                driver.get(url);
                sleep(2000); // Let page JS execute

                // Wait for product cards to render
                List<WebElement> productCards;
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getElementWaitTimeoutSec()));
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.cPHDOP, div[data-id], div.slAVV4")));

                    productCards = driver.findElements(By.cssSelector("div.cPHDOP, div[data-id], div.slAVV4"));
                } catch (TimeoutException e) {
                    System.out.println("[Flipkart Scraper] Timeout waiting for product cards on page " + page + ". Stopping.");
                    break;
                }

                if (productCards.isEmpty()) {
                    System.out.println("[Flipkart Scraper] No product cards found on page " + page + ". Stopping.");
                    break;
                }

                System.out.println("[Flipkart Scraper] Found " + productCards.size() + " cards on page " + page);

                // Scroll down to trigger lazy loading
                scrollToBottom(driver);
                sleep(1000);

                // Re-fetch after scroll
                productCards = driver.findElements(By.cssSelector("div.cPHDOP, div[data-id], div.slAVV4"));

                for (WebElement card : productCards) {
                    if (scrapedData.size() >= maxItems) break;
                    
                    if (scrapedData.isEmpty() && page == 1) {
                        System.out.println("First card HTML: " + card.getAttribute("outerHTML"));
                    }

                    try {
                        RawLaptopData data = extractFlipkartCard(card);
                        if (data != null && data.getTitle() != null && !data.getTitle().isEmpty()) {
                            scrapedData.add(data);
                        }
                    } catch (StaleElementReferenceException e) {
                        // Card was removed from DOM during iteration — skip it
                        System.out.println("[Flipkart Scraper] Stale element, skipping card.");
                    } catch (Exception e) {
                        System.out.println("[Flipkart Scraper] Error extracting card: " + e.getMessage());
                    }
                }

                page++;
                sleep(config.getDelayBetweenRequestsMs());
            }
        } catch (Exception e) {
            System.err.println("[Flipkart Scraper] Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("[Flipkart Scraper] Driver closed.");
            }
        }

        System.out.println("[Flipkart Scraper] Total scraped: " + scrapedData.size());
        return scrapedData;
    }

    private RawLaptopData extractFlipkartCard(WebElement card) {
        RawLaptopData data = new RawLaptopData();

        // Title
        data.setTitle(safeText(card, "div.KzDlHZ", "a.wjcEIp", "div.RG5Slk"));

        // Price
        data.setPriceText(safeText(card, "div.Nx9bqj._4b5DiR", "div.Nx9bqj", "div.hl05eU div.Nx9bqj", "div.hZ3P6w"));

        // Rating
        data.setRatingText(safeText(card, "div.XQDdHH", "div.MKiFS6"));

        // Image
        String imgSrc = safeAttr(card, "src", "img.DByuf4", "img._53J4C-", "img.UCc1lI");
        data.setImageUrl(imgSrc);

        // Product URL
        String href = safeAttr(card, "href", "a.CGtC98", "a.WKTcLC", "a.VJA3rP", "a.k7wcnx");
        if (href != null && !href.isEmpty()) {
            if (!href.startsWith("http")) {
                href = config.getFlipkartBaseUrl() + href;
            }
            data.setProductUrl(href);
        }

        // Spec bullets (RAM, CPU, Storage, OS, Display)
        List<WebElement> specs = card.findElements(By.cssSelector("ul.G4BRas li, div._6NESgJ, li.J\\+igdf, li.DTBslk"));
        for (WebElement spec : specs) {
            String text = spec.getText();
            if (text == null || text.trim().isEmpty()) {
                text = spec.getAttribute("innerText");
            }
            if (text != null) {
                text = text.toLowerCase();
                if (text.contains("ram") || text.contains("gb ddr")) data.setRamText(text);
                if (text.contains("processor") || text.contains("core") || text.contains("ryzen") || text.contains("apple") || text.contains("intel")) data.setCpuText(text);
                if (text.contains("ssd") || text.contains("hdd") || text.contains("storage")) data.setStorageText(text);
                if (text.contains("display") || text.contains("inch") || text.contains("cm")) data.setScreenSizeText(text);
                if (text.contains("graphic") || text.contains("nvidia") || text.contains("rtx") || text.contains("gtx")) data.setGpuText(text);
            }
        }

        return data;
    }

    /**
     * Try multiple CSS selectors and return the text of the first match.
     */
    private String safeText(WebElement parent, String... selectors) {
        for (String selector : selectors) {
            try {
                List<WebElement> elements = parent.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    String text = elements.get(0).getText();
                    if (text == null || text.trim().isEmpty()) {
                        text = elements.get(0).getAttribute("innerText");
                    }
                    if (text != null && !text.trim().isEmpty()) {
                        return text.trim();
                    }
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    /**
     * Try multiple CSS selectors and return the specified attribute of the first match.
     */
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

    private void scrollToBottom(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception ignored) {}
    }

    private void sleep(long baseMs) {
        try {
            Thread.sleep(baseMs + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
