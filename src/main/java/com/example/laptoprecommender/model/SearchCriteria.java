package com.example.laptoprecommender.model;

public class SearchCriteria {
    // Common
    private double budget;
    private int ram;
    private String purpose;
    private String mode = "basic";

    // Basic + Pro
    private String displaySize = "any";
    private String displayType = "any";
    private String chipset = "any";

    // Pro mode only
    private String cpuTier = "any";
    private String cpuGeneration = "any";
    private String gpuPreference = "any";
    private String storageCapacity = "any";
    private String resolution = "any";
    private int refreshRate = 0;
    private int minUsbA = 0;
    private int minUsbC = 0;
    private boolean hdmiRequired = false;
    private boolean sdCardRequired = false;
    private String speakerQuality = "any";
    private String buildMaterial = "any";
    private String weightPreference = "any";
    private String batteryPreference = "any";

    public SearchCriteria() {}

    // Getters and Setters
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getDisplaySize() { return displaySize; }
    public void setDisplaySize(String displaySize) { this.displaySize = displaySize; }

    public String getDisplayType() { return displayType; }
    public void setDisplayType(String displayType) { this.displayType = displayType; }

    public String getChipset() { return chipset; }
    public void setChipset(String chipset) { this.chipset = chipset; }

    public String getCpuTier() { return cpuTier; }
    public void setCpuTier(String cpuTier) { this.cpuTier = cpuTier; }

    public String getCpuGeneration() { return cpuGeneration; }
    public void setCpuGeneration(String cpuGeneration) { this.cpuGeneration = cpuGeneration; }

    public String getGpuPreference() { return gpuPreference; }
    public void setGpuPreference(String gpuPreference) { this.gpuPreference = gpuPreference; }

    public String getStorageCapacity() { return storageCapacity; }
    public void setStorageCapacity(String storageCapacity) { this.storageCapacity = storageCapacity; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public int getRefreshRate() { return refreshRate; }
    public void setRefreshRate(int refreshRate) { this.refreshRate = refreshRate; }

    public int getMinUsbA() { return minUsbA; }
    public void setMinUsbA(int minUsbA) { this.minUsbA = minUsbA; }

    public int getMinUsbC() { return minUsbC; }
    public void setMinUsbC(int minUsbC) { this.minUsbC = minUsbC; }

    public boolean isHdmiRequired() { return hdmiRequired; }
    public void setHdmiRequired(boolean hdmiRequired) { this.hdmiRequired = hdmiRequired; }

    public boolean isSdCardRequired() { return sdCardRequired; }
    public void setSdCardRequired(boolean sdCardRequired) { this.sdCardRequired = sdCardRequired; }

    public String getSpeakerQuality() { return speakerQuality; }
    public void setSpeakerQuality(String speakerQuality) { this.speakerQuality = speakerQuality; }

    public String getBuildMaterial() { return buildMaterial; }
    public void setBuildMaterial(String buildMaterial) { this.buildMaterial = buildMaterial; }

    public String getWeightPreference() { return weightPreference; }
    public void setWeightPreference(String weightPreference) { this.weightPreference = weightPreference; }

    public String getBatteryPreference() { return batteryPreference; }
    public void setBatteryPreference(String batteryPreference) { this.batteryPreference = batteryPreference; }

    public boolean isProMode() { return "pro".equalsIgnoreCase(mode); }
}
