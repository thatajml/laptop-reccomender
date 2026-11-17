package com.example.laptoprecommender.controller;

import com.example.laptoprecommender.model.StoreSearchResult;
import com.example.laptoprecommender.service.StoreLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin
public class StoreLocatorController {

    private final StoreLocatorService storeLocatorService;

    @Autowired
    public StoreLocatorController(StoreLocatorService storeLocatorService) {
        this.storeLocatorService = storeLocatorService;
    }

    @GetMapping("/nearby")
    public ResponseEntity<StoreSearchResult> getNearbyStores(
            @RequestParam(required = false, defaultValue = "") String laptopTitle,
            @RequestParam(required = false, defaultValue = "") String pincode) {

        StoreSearchResult result = storeLocatorService.findNearbyStores(laptopTitle, pincode);
        return ResponseEntity.ok(result);
    }
}
