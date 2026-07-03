package com.example.laptoprecommender.scraper;

import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.scraper.model.RawLaptopData;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SpecNormalizerService {

    public Laptop normalize(RawLaptopData raw, String source) {
        Laptop laptop = new Laptop();
        
        laptop.setTitle(raw.getTitle() != null ? raw.getTitle().trim() : "Unknown Laptop");
        laptop.setDescription(laptop.getTitle());
        laptop.setSource(source);
        laptop.setProductUrl(raw.getProductUrl());
        laptop.setThumbnail(raw.getImageUrl());
        
        laptop.setPrice(parsePrice(raw.getPriceText()));
        laptop.setRating(parseRating(raw.getRatingText()));
        
        String ramStr = firstNonNull(raw.getRamText(), raw.getTitle());
        laptop.setRamGB(parseRam(ramStr));
        
        String cpuStr = firstNonNull(raw.getCpuText(), raw.getTitle());
        laptop.setCpu(cpuStr); // Raw string is okay for display
        laptop.setChipsetFamily(inferChipset(cpuStr));
        laptop.setCpuTier(inferCpuTier(cpuStr));
        laptop.setCpuGeneration(inferCpuGen(cpuStr));
        
        String gpuStr = firstNonNull(raw.getGpuText(), raw.getTitle());
        laptop.setGpu(gpuStr); // Raw string for display
        laptop.setGpuModel(inferGpuModel(gpuStr));
        
        String displaySizeStr = firstNonNull(raw.getScreenSizeText(), raw.getTitle());
        laptop.setDisplaySize(inferDisplaySize(displaySizeStr));
        
        String displayTypeStr = firstNonNull(raw.getScreenTypeText(), raw.getTitle());
        laptop.setDisplayType(inferDisplayType(displayTypeStr));
        
        String resStr = firstNonNull(raw.getResolutionText(), raw.getTitle());
        laptop.setResolution(inferResolution(resStr));
        
        String refreshStr = firstNonNull(raw.getRefreshRateText(), raw.getTitle());
        laptop.setRefreshRate(parseRefreshRate(refreshStr));
        
        String storageStr = firstNonNull(raw.getStorageText(), raw.getTitle());
        laptop.setStorageCapacity(inferStorage(storageStr));
        
        String weightStr = firstNonNull(raw.getWeightText(), raw.getTitle());
        laptop.setWeight(parseWeight(weightStr));
        
        // Defaults for things hard to scrape consistently
        laptop.setBatteryLife(8.0); // Safe default
        laptop.setUsbACount(2);
        laptop.setUsbCCount(1);
        laptop.setHdmiPort(true);
        laptop.setSdCardSlot(false);
        laptop.setSpeakerQuality("Basic");
        laptop.setBuildMaterial("Plastic");
        
        if (laptop.getPrice() > 70000) {
            laptop.setBuildMaterial("Aluminum");
            laptop.setSpeakerQuality("Good");
        }
        if (laptop.getPrice() > 150000) {
            laptop.setSpeakerQuality("Premium");
        }
        
        return laptop;
    }
    
    private String firstNonNull(String... strings) {
        for (String s : strings) {
            if (s != null && !s.isEmpty()) return s;
        }
        return "";
    }

    private double parsePrice(String priceText) {
        if (priceText == null) return 0;
        String clean = priceText.replaceAll("[^0-9.]", "");
        if (clean.isEmpty()) return 0;
        try {
            return Double.parseDouble(clean);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double parseRating(String ratingText) {
        if (ratingText == null) return 0;
        Matcher m = Pattern.compile("([0-9]+\\.[0-9]+)").matcher(ratingText);
        if (m.find()) {
            return Double.parseDouble(m.group(1));
        }
        return 0;
    }

    private int parseRam(String text) {
        text = text.toUpperCase();
        if (text.contains("64GB") || text.contains("64 GB")) return 64;
        if (text.contains("32GB") || text.contains("32 GB")) return 32;
        if (text.contains("16GB") || text.contains("16 GB")) return 16;
        if (text.contains("8GB") || text.contains("8 GB")) return 8;
        if (text.contains("4GB") || text.contains("4 GB")) return 4;
        return 8; // default
    }

    private String inferChipset(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("amd") || lower.contains("ryzen")) return "AMD";
        if (lower.contains("apple") || lower.matches(".*\\bm[1-3]\\b.*")) return "Apple";
        return "Intel"; // default
    }

    private String inferCpuTier(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("i9")) return "i9";
        if (lower.contains("i7")) return "i7";
        if (lower.contains("i5")) return "i5";
        if (lower.contains("i3")) return "i3";
        if (lower.contains("ryzen 9")) return "Ryzen 9";
        if (lower.contains("ryzen 7")) return "Ryzen 7";
        if (lower.contains("ryzen 5")) return "Ryzen 5";
        if (lower.contains("ryzen 3")) return "Ryzen 3";
        if (lower.contains("m3 max")) return "M3 Max";
        if (lower.contains("m3 pro")) return "M3 Pro";
        if (lower.contains("m3")) return "M3";
        if (lower.contains("m2 max")) return "M2 Max";
        if (lower.contains("m2 pro")) return "M2 Pro";
        if (lower.contains("m2")) return "M2";
        if (lower.contains("m1 max")) return "M1 Max";
        if (lower.contains("m1 pro")) return "M1 Pro";
        if (lower.contains("m1")) return "M1";
        return "Unknown";
    }

    private String inferCpuGen(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("14th") || lower.matches(".*i[3579]-14.*")) return "14th Gen";
        if (lower.contains("13th") || lower.matches(".*i[3579]-13.*")) return "13th Gen";
        if (lower.contains("12th") || lower.matches(".*i[3579]-12.*")) return "12th Gen";
        if (lower.contains("11th") || lower.matches(".*i[3579]-11.*")) return "11th Gen";
        
        if (lower.contains("ryzen") && (lower.contains(" 8") || lower.contains("-8"))) return "Zen 4";
        if (lower.contains("ryzen") && (lower.contains(" 7") || lower.contains("-7"))) return "Zen 4";
        if (lower.contains("ryzen") && (lower.contains(" 6") || lower.contains("-6"))) return "Zen 3+";
        if (lower.contains("ryzen") && (lower.contains(" 5") || lower.contains("-5"))) return "Zen 3";
        
        if (lower.matches(".*\\bm[1-3]\\b.*")) return "M-Series";
        
        return "Unknown";
    }

    private String inferGpuModel(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("4090")) return "RTX 4090";
        if (lower.contains("4080")) return "RTX 4080";
        if (lower.contains("4070")) return "RTX 4070";
        if (lower.contains("4060")) return "RTX 4060";
        if (lower.contains("4050")) return "RTX 4050";
        if (lower.contains("3080")) return "RTX 3080";
        if (lower.contains("3070")) return "RTX 3070";
        if (lower.contains("3060")) return "RTX 3060";
        if (lower.contains("3050")) return "RTX 3050";
        if (lower.contains("rx 7600")) return "RX 7600S";
        if (lower.contains("rx 6500")) return "RX 6500M";
        if (lower.contains("gtx 1650")) return "GTX 1650";
        
        if (lower.contains("iris")) return "Iris Xe";
        if (lower.contains("uhd")) return "UHD Integrated";
        if (lower.contains("radeon")) return "Radeon Integrated";
        if (lower.contains("apple")) return "Apple Integrated";
        
        return "Integrated";
    }

    private String inferDisplaySize(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("17") || lower.contains("18")) return "17+";
        if (lower.contains("15") || lower.contains("16")) return "15-16";
        if (lower.contains("13") || lower.contains("14")) return "13-14";
        return "15-16"; // default
    }
    
    private String inferDisplayType(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("oled") || lower.contains("mini-led") || lower.contains("mini led")) {
            return "OLED/Mini-LED";
        }
        return "IPS";
    }

    private String inferResolution(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("4k") || lower.contains("3840")) return "4K";
        if (lower.contains("qhd") || lower.contains("2k") || lower.contains("2560") || lower.contains("2880")) return "QHD";
        return "FHD"; // default
    }

    private int parseRefreshRate(String text) {
        Matcher m = Pattern.compile("([0-9]{2,3})\\s*hz").matcher(text.toLowerCase());
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 60; // default
    }

    private String inferStorage(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("4tb") || lower.contains("4 tb")) return "4TB";
        if (lower.contains("2tb") || lower.contains("2 tb")) return "2TB";
        if (lower.contains("1tb") || lower.contains("1 tb")) return "1TB";
        if (lower.contains("512")) return "512GB";
        if (lower.contains("256")) return "256GB";
        return "512GB"; // default
    }

    private double parseWeight(String text) {
        if (text == null) return 1.8;
        Matcher m = Pattern.compile("([0-9]+\\.[0-9]+)\\s*(kg|kilogram|g)").matcher(text.toLowerCase());
        if (m.find()) {
            double val = Double.parseDouble(m.group(1));
            if (m.group(2).equals("g")) val /= 1000;
            return val;
        }
        return 1.8; // default
    }
}
