package com.example.laptoprecommender.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "laptops")
public class Laptop {
    @Id
    private String id;
    private int laptopId;
    private String title;
    private String description;
    private double price;
    private double rating;
    private String thumbnail;

    // Core Hardware
    private int ramGB;
    private String cpu;
    private String gpu;

    // Display & Form
    private String displaySize;     // "13-14", "15-16", "17+"
    private String displayType;     // "IPS", "OLED/Mini-LED"
    private String chipsetFamily;   // "Intel", "AMD", "Apple"

    // Pro-mode detailed specs
    private String cpuTier;         // "i3", "i5", "i7", "i9", "Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9", "M2", "M3", "M3 Pro"
    private String cpuGeneration;   // "11th Gen", "12th Gen", "13th Gen", "14th Gen", "Zen 2", "Zen 3", "Zen 4", "M-Series"
    private String gpuModel;        // Specific model e.g. "RTX 4060", "Iris Xe"
    private String storageCapacity; // "256GB", "512GB", "1TB", "2TB"
    private String resolution;      // "FHD", "QHD", "4K"
    private int refreshRate;        // 60, 90, 120, 144, 165
    private int usbACount;
    private int usbCCount;
    private boolean hdmiPort;
    private boolean sdCardSlot;
    private String speakerQuality;  // "Basic", "Good", "Premium"
    private String buildMaterial;   // "Plastic", "Aluminum", "Magnesium", "Carbon Fiber"
    private double weight;          // kg
    private double batteryLife;     // hours

    // Recommendation (transient — computed at runtime, not stored in DB)
    @Transient
    private double score;
    @Transient
    private List<String> reasons = new ArrayList<>();

    // Benchmark scores (auto-derived from CPU tier)
    private int geekbenchSingle;
    private int geekbenchMulti;
    private int cinebenchScore;

    // Radar chart normalized scores (0–100, transient — computed at runtime)
    @Transient
    private double perfScore;
    @Transient
    private double displayScore;
    @Transient
    private double portabilityScore;
    @Transient
    private double buildScore;
    @Transient
    private double batteryScore;

    // AI-generated summary (transient — computed at runtime)
    @Transient
    private String whyThisLaptop;

    public Laptop() {}

    // --- Getters and Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getLaptopId() { return laptopId; }
    public void setLaptopId(int laptopId) { this.laptopId = laptopId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public int getRamGB() { return ramGB; }
    public void setRamGB(int ramGB) { this.ramGB = ramGB; }

    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }

    public String getGpu() { return gpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }

    public String getDisplaySize() { return displaySize; }
    public void setDisplaySize(String displaySize) { this.displaySize = displaySize; }

    public String getDisplayType() { return displayType; }
    public void setDisplayType(String displayType) { this.displayType = displayType; }

    public String getChipsetFamily() { return chipsetFamily; }
    public void setChipsetFamily(String chipsetFamily) { this.chipsetFamily = chipsetFamily; }

    public String getCpuTier() { return cpuTier; }
    public void setCpuTier(String cpuTier) { this.cpuTier = cpuTier; }

    public String getCpuGeneration() { return cpuGeneration; }
    public void setCpuGeneration(String cpuGeneration) { this.cpuGeneration = cpuGeneration; }

    public String getGpuModel() { return gpuModel; }
    public void setGpuModel(String gpuModel) { this.gpuModel = gpuModel; }

    public String getStorageCapacity() { return storageCapacity; }
    public void setStorageCapacity(String storageCapacity) { this.storageCapacity = storageCapacity; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public int getRefreshRate() { return refreshRate; }
    public void setRefreshRate(int refreshRate) { this.refreshRate = refreshRate; }

    public int getUsbACount() { return usbACount; }
    public void setUsbACount(int usbACount) { this.usbACount = usbACount; }

    public int getUsbCCount() { return usbCCount; }
    public void setUsbCCount(int usbCCount) { this.usbCCount = usbCCount; }

    public boolean isHdmiPort() { return hdmiPort; }
    public void setHdmiPort(boolean hdmiPort) { this.hdmiPort = hdmiPort; }

    public boolean isSdCardSlot() { return sdCardSlot; }
    public void setSdCardSlot(boolean sdCardSlot) { this.sdCardSlot = sdCardSlot; }

    public String getSpeakerQuality() { return speakerQuality; }
    public void setSpeakerQuality(String speakerQuality) { this.speakerQuality = speakerQuality; }

    public String getBuildMaterial() { return buildMaterial; }
    public void setBuildMaterial(String buildMaterial) { this.buildMaterial = buildMaterial; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getBatteryLife() { return batteryLife; }
    public void setBatteryLife(double batteryLife) { this.batteryLife = batteryLife; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
    public void addReason(String reason) { this.reasons.add(reason); }

    public int getGeekbenchSingle() { return geekbenchSingle; }
    public void setGeekbenchSingle(int geekbenchSingle) { this.geekbenchSingle = geekbenchSingle; }

    public int getGeekbenchMulti() { return geekbenchMulti; }
    public void setGeekbenchMulti(int geekbenchMulti) { this.geekbenchMulti = geekbenchMulti; }

    public int getCinebenchScore() { return cinebenchScore; }
    public void setCinebenchScore(int cinebenchScore) { this.cinebenchScore = cinebenchScore; }

    public double getPerfScore() { return perfScore; }
    public void setPerfScore(double perfScore) { this.perfScore = perfScore; }

    public double getDisplayScore() { return displayScore; }
    public void setDisplayScore(double displayScore) { this.displayScore = displayScore; }

    public double getPortabilityScore() { return portabilityScore; }
    public void setPortabilityScore(double portabilityScore) { this.portabilityScore = portabilityScore; }

    public double getBuildScore() { return buildScore; }
    public void setBuildScore(double buildScore) { this.buildScore = buildScore; }

    public double getBatteryScore() { return batteryScore; }
    public void setBatteryScore(double batteryScore) { this.batteryScore = batteryScore; }

    public String getWhyThisLaptop() { return whyThisLaptop; }
    public void setWhyThisLaptop(String whyThisLaptop) { this.whyThisLaptop = whyThisLaptop; }
}
