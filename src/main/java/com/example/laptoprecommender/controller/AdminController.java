package com.example.laptoprecommender.controller;

import com.example.laptoprecommender.scraper.ScrapingService;
import com.example.laptoprecommender.scraper.model.ScrapingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ScrapingService scrapingService;

    public AdminController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping("/scrape")
    public String showScrapePage(Model model) {
        return "scrape";
    }

    @PostMapping("/scrape")
    public String triggerScrape(@RequestParam(defaultValue = "flipkart") String source,
                                @RequestParam(defaultValue = "100") int limit,
                                Model model) {
        ScrapingResult result = scrapingService.scrape(source, limit);
        model.addAttribute("result", result);
        model.addAttribute("source", source);
        model.addAttribute("limit", limit);
        return "scrape";
    }
}
