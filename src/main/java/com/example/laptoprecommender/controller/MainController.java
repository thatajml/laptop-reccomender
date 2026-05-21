package com.example.laptoprecommender.controller;

import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.model.PCBuild;
import com.example.laptoprecommender.model.SearchCriteria;
import com.example.laptoprecommender.service.MarketDataService;
import com.example.laptoprecommender.service.PCBuilderService;
import com.example.laptoprecommender.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final MarketDataService marketDataService;
    private final RecommendationService recommendationService;
    private final PCBuilderService pcBuilderService;

    @Autowired
    public MainController(MarketDataService marketDataService,
                          RecommendationService recommendationService,
                          PCBuilderService pcBuilderService) {
        this.marketDataService = marketDataService;
        this.recommendationService = recommendationService;
        this.pcBuilderService = pcBuilderService;
    }

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/recommend")
    public String recommendLaptops(SearchCriteria criteria, Model model) {

        List<Laptop> allLaptops = marketDataService.fetchLaptops();
        List<Laptop> topLaptops = recommendationService.getTopLaptops(allLaptops, criteria);

        List<PCBuild> equivalentPCs = new ArrayList<>();
        List<String> laptopTitles = new ArrayList<>();
        List<Double> laptopPrices = new ArrayList<>();
        List<Double> pcPrices = new ArrayList<>();

        for (Laptop laptop : topLaptops) {
            PCBuild pc = pcBuilderService.buildEquivalentPC(laptop);
            equivalentPCs.add(pc);

            if (!pc.isApple() && pc.isSimilarMatch()) {
                laptopTitles.add(laptop.getTitle().replace("'", "\\'"));
                laptopPrices.add(laptop.getPrice());
                pcPrices.add(pc.getTotalPrice());
            }
        }

        model.addAttribute("laptops", topLaptops);
        model.addAttribute("pcBuilds", equivalentPCs);
        
        List<Laptop> affordableLaptops = allLaptops.stream()
                .filter(l -> l.getPrice() <= criteria.getBudget())
                .sorted(java.util.Comparator.comparingDouble(Laptop::getPrice).reversed())
                .collect(java.util.stream.Collectors.toList());
        model.addAttribute("affordableLaptops", affordableLaptops);

        model.addAttribute("titles", laptopTitles);
        model.addAttribute("lPrices", laptopPrices);
        model.addAttribute("pPrices", pcPrices);
        model.addAttribute("mode", criteria.getMode());
        model.addAttribute("noResults", topLaptops.isEmpty());

        return "result";
    }

    @GetMapping("/catalog")
    public String showCatalog(@RequestParam(defaultValue = "basic") String mode, Model model) {
        List<Laptop> allLaptops = marketDataService.fetchLaptops();
        model.addAttribute("laptops", allLaptops);
        model.addAttribute("totalCount", allLaptops.size());
        model.addAttribute("mode", mode);
        return "catalog";
    }

    @GetMapping("/compare")
    public String compareLaptops(@RequestParam List<Integer> ids,
                                 @RequestParam(defaultValue = "basic") String mode,
                                 Model model) {
        List<Laptop> comparables = new ArrayList<>();
        for (int id : ids) {
            Laptop l = marketDataService.findById(id);
            if (l != null) {
                // Compute radar scores for comparison
                SearchCriteria dummyCriteria = new SearchCriteria();
                dummyCriteria.setBudget(l.getPrice() * 1.2);
                dummyCriteria.setRam(8);
                dummyCriteria.setPurpose("student");
                l.getReasons().clear();
                l.setScore(0);
                comparables.add(l);
            }
        }
        // Assign radar scores via recommendation service
        for (Laptop l : comparables) {
            recommendationService.computePublicRadar(l);
        }
        model.addAttribute("laptops", comparables);
        model.addAttribute("mode", mode);
        return "compare";
    }
}
