package com.example.laptoprecommender.scraper.model;

public class RawLaptopData {
    private String title;           // Raw product title from listing
    private String priceText;       // "₹45,990" as-is
    private String ratingText;      // "4.3 out of 5 stars"
    private String imageUrl;        // Thumbnail URL
    private String productUrl;      // Full product page URL
    
    // Extracted from specs table or bullet points
    private String ramText;         // "16 GB DDR5"
    private String cpuText;         // "Intel Core i5-13420H"
    private String gpuText;         // "NVIDIA GeForce RTX 4050"
    private String screenSizeText;  // "15.6 Inches"
    private String screenTypeText;  // "IPS"
    private String storageText;     // "512 GB SSD"
    private String weightText;      // "2.2 kg"
    private String batteryText;     // "4-cell, 56 Whr"
    private String brandText;       // "ASUS"
    private String osText;          // "Windows 11 Home"
    private String resolutionText;  // "1920 x 1080 Pixels"
    private String refreshRateText; // "144 Hz"
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getPriceText() { return priceText; }
    public void setPriceText(String priceText) { this.priceText = priceText; }
    
    public String getRatingText() { return ratingText; }
    public void setRatingText(String ratingText) { this.ratingText = ratingText; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getProductUrl() { return productUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }
    
    public String getRamText() { return ramText; }
    public void setRamText(String ramText) { this.ramText = ramText; }
    
    public String getCpuText() { return cpuText; }
    public void setCpuText(String cpuText) { this.cpuText = cpuText; }
    
    public String getGpuText() { return gpuText; }
    public void setGpuText(String gpuText) { this.gpuText = gpuText; }
    
    public String getScreenSizeText() { return screenSizeText; }
    public void setScreenSizeText(String screenSizeText) { this.screenSizeText = screenSizeText; }
    
    public String getScreenTypeText() { return screenTypeText; }
    public void setScreenTypeText(String screenTypeText) { this.screenTypeText = screenTypeText; }
    
    public String getStorageText() { return storageText; }
    public void setStorageText(String storageText) { this.storageText = storageText; }
    
    public String getWeightText() { return weightText; }
    public void setWeightText(String weightText) { this.weightText = weightText; }
    
    public String getBatteryText() { return batteryText; }
    public void setBatteryText(String batteryText) { this.batteryText = batteryText; }
    
    public String getBrandText() { return brandText; }
    public void setBrandText(String brandText) { this.brandText = brandText; }
    
    public String getOsText() { return osText; }
    public void setOsText(String osText) { this.osText = osText; }
    
    public String getResolutionText() { return resolutionText; }
    public void setResolutionText(String resolutionText) { this.resolutionText = resolutionText; }
    
    public String getRefreshRateText() { return refreshRateText; }
    public void setRefreshRateText(String refreshRateText) { this.refreshRateText = refreshRateText; }
}
