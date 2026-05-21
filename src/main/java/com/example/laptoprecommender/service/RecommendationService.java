package com.example.laptoprecommender.service;

import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.model.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    /**
     * HARD CONSTRAINTS first (filter), then SOFT SCORING on the survivors.
     * Returns empty list if nothing matches — controller handles the "quirky popup".
     */
    public List<Laptop> getTopLaptops(List<Laptop> allLaptops, SearchCriteria c) {

        // 1. FILTER — hard constraints (every non-"any" field is mandatory)
        List<Laptop> filtered = new ArrayList<>();
        for (Laptop l : allLaptops) {
            if (passesHardConstraints(l, c)) filtered.add(l);
        }

        if (filtered.isEmpty()) return filtered; // controller shows popup

        // 2. SCORE — soft ranking
        for (Laptop l : filtered) {
            l.getReasons().clear();
            l.setScore(computeScore(l, c));
            computeRadar(l, c);
            generateWhy(l, c);
        }

        int limit = c.isProMode() ? 5 : 3;
        return filtered.stream()
                .sorted(Comparator.comparingDouble(Laptop::getScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /** Public access for compare page radar computation */
    public void computePublicRadar(Laptop l) {
        SearchCriteria dummy = new SearchCriteria();
        dummy.setBudget(l.getPrice() * 1.2);
        dummy.setRam(8);
        dummy.setPurpose("student");
        computeRadar(l, dummy);
    }

    // =========================================================================
    //  HARD CONSTRAINTS — returns false if ANY required filter doesn't match
    // =========================================================================
    private boolean passesHardConstraints(Laptop l, SearchCriteria c) {

        // Budget: always hard
        if (l.getPrice() > c.getBudget()) return false;

        // RAM: always hard (>= minimum)
        if (l.getRamGB() < c.getRam()) return false;

        // Display Size
        if (!isAny(c.getDisplaySize()) && !c.getDisplaySize().equals(l.getDisplaySize())) return false;

        // Display Type
        if (!isAny(c.getDisplayType()) && !c.getDisplayType().equals(l.getDisplayType())) return false;

        // Chipset Family
        if (!isAny(c.getChipset()) && !c.getChipset().equalsIgnoreCase(l.getChipsetFamily())) return false;

        // ---- PRO MODE hard constraints ----
        if (c.isProMode()) {
            // CPU Tier
            if (!isAny(c.getCpuTier()) && !c.getCpuTier().equalsIgnoreCase(l.getCpuTier())) return false;

            // CPU Generation
            if (!isAny(c.getCpuGeneration()) && !c.getCpuGeneration().equalsIgnoreCase(l.getCpuGeneration())) return false;

            // GPU Preference
            if (!isAny(c.getGpuPreference())) {
                String gpuM = l.getGpuModel() != null ? l.getGpuModel().toLowerCase() : "";
                boolean match = false;
                switch (c.getGpuPreference().toLowerCase()) {
                    case "high":       match = gpuM.contains("rtx 40") && !gpuM.contains("4050"); break;
                    case "mid":        match = gpuM.contains("rtx"); break;
                    case "entry":      match = gpuM.contains("gtx") || gpuM.contains("mx") || gpuM.contains("rx"); break;
                    case "integrated": match = gpuM.contains("integrated") || gpuM.contains("iris") || gpuM.contains("uhd") || gpuM.contains("radeon integrated") || gpuM.contains("radeon 780m"); break;
                }
                if (!match) return false;
            }

            // Storage Capacity
            if (!isAny(c.getStorageCapacity()) && !c.getStorageCapacity().equals(l.getStorageCapacity())) return false;

            // Resolution
            if (!isAny(c.getResolution()) && !c.getResolution().equalsIgnoreCase(l.getResolution())) return false;

            // Refresh Rate (minimum)
            if (c.getRefreshRate() > 0 && l.getRefreshRate() < c.getRefreshRate()) return false;

            // Ports
            if (c.getMinUsbA() > 0 && l.getUsbACount() < c.getMinUsbA()) return false;
            if (c.getMinUsbC() > 0 && l.getUsbCCount() < c.getMinUsbC()) return false;
            if (c.isHdmiRequired() && !l.isHdmiPort()) return false;
            if (c.isSdCardRequired() && !l.isSdCardSlot()) return false;

            // Speaker Quality (minimum tier)
            if (!isAny(c.getSpeakerQuality())) {
                if (qLevel(l.getSpeakerQuality()) < qLevel(c.getSpeakerQuality())) return false;
            }

            // Build Material
            if (!isAny(c.getBuildMaterial()) && !c.getBuildMaterial().equalsIgnoreCase(l.getBuildMaterial())) return false;

            // Weight
            if (!isAny(c.getWeightPreference())) {
                switch (c.getWeightPreference().toLowerCase()) {
                    case "ultralight": if (l.getWeight() > 1.5) return false; break;
                    case "medium":     if (l.getWeight() <= 1.5 || l.getWeight() > 2.0) return false; break;
                    case "heavy":      if (l.getWeight() <= 2.0) return false; break;
                }
            }

            // Battery (minimum hours)
            if (!isAny(c.getBatteryPreference())) {
                try {
                    double minBat = Double.parseDouble(c.getBatteryPreference());
                    if (l.getBatteryLife() < minBat) return false;
                } catch (NumberFormatException ignored) {}
            }
        }

        return true; // passed everything
    }

    // =========================================================================
    //  SOFT SCORING — higher is better
    // =========================================================================
    private double computeScore(Laptop l, SearchCriteria c) {
        double score = 0;

        // Value: how much budget headroom
        double budgetUsed = l.getPrice() / c.getBudget();
        if (budgetUsed <= 0.7) { score += 15; l.addReason("Great Value"); }
        else if (budgetUsed <= 0.9) { score += 10; l.addReason("Good Value"); }
        else { score += 5; }

        // RAM surplus
        if (l.getRamGB() > c.getRam()) {
            score += 10;
            l.addReason("Extra RAM (" + l.getRamGB() + "GB)");
        } else {
            score += 5;
        }

        // Purpose fit
        switch (c.getPurpose().toLowerCase()) {
            case "gaming":
                if (l.getGpu() != null && l.getGpu().toLowerCase().contains("rtx")) {
                    score += 25; l.addReason("Strong GPU for Gaming");
                }
                if (l.getRefreshRate() >= 120) {
                    score += 10; l.addReason("High Refresh (" + l.getRefreshRate() + "Hz)");
                }
                if (l.getRamGB() >= 16) score += 5;
                break;
            case "creative":
                if ("OLED/Mini-LED".equals(l.getDisplayType())) {
                    score += 20; l.addReason("Premium Display for Creatives");
                }
                if (l.getRamGB() >= 16) score += 10;
                if (l.getGpu() != null && l.getGpu().toLowerCase().contains("rtx")) {
                    score += 10; l.addReason("GPU-Accelerated Rendering");
                }
                break;
            case "student":
            default:
                if (l.getWeight() < 1.5) {
                    score += 15; l.addReason("Lightweight & Portable");
                }
                if (l.getBatteryLife() >= 10) {
                    score += 10; l.addReason("All-Day Battery");
                }
                break;
        }

        // Rating boost
        score += l.getRating() * 5;
        if (l.getRating() >= 4.5) l.addReason("Highly Rated (" + l.getRating() + " ⭐)");

        // Pro-mode bonus scoring
        if (c.isProMode()) {
            // Storage surplus
            int lStorage = storageToGB(l.getStorageCapacity());
            int cStorage = storageToGB(c.getStorageCapacity());
            if (!isAny(c.getStorageCapacity()) && lStorage > cStorage) {
                score += 5; l.addReason("Extra Storage");
            }

            // Battery surplus
            if (l.getBatteryLife() >= 15) score += 5;

            // Build bonus
            if ("Aluminum".equalsIgnoreCase(l.getBuildMaterial()) || "Magnesium".equalsIgnoreCase(l.getBuildMaterial())) {
                score += 3;
            }
        }

        l.setScore(score);
        return score;
    }

    // =========================================================================
    //  HELPERS
    // =========================================================================
    private boolean isAny(String v) {
        return v == null || v.isEmpty() || "any".equalsIgnoreCase(v);
    }

    private int qLevel(String q) {
        if (q == null) return 0;
        switch (q.toLowerCase()) {
            case "premium": return 3;
            case "good":    return 2;
            case "basic":   return 1;
            default:        return 0;
        }
    }

    private int storageToGB(String s) {
        if (s == null) return 0;
        if (s.contains("8TB")) return 8192;
        if (s.contains("4TB")) return 4096;
        if (s.contains("2TB")) return 2048;
        if (s.contains("1TB")) return 1024;
        if (s.contains("512")) return 512;
        if (s.contains("256")) return 256;
        if (s.contains("128")) return 128;
        return 0;
    }

    // =========================================================================
    //  RADAR CHART — 5 axes, each 0–100
    // =========================================================================
    private void computeRadar(Laptop l, SearchCriteria c) {
        // Performance: CPU benchmark + RAM + GPU
        double perf = Math.min(100, (l.getGeekbenchSingle() / 32.0) + (l.getRamGB() * 1.2) + gpuTier(l.getGpuModel()) * 8);
        l.setPerfScore(Math.round(perf * 10.0) / 10.0);

        // Display: resolution + refresh + panel type
        double disp = 30;
        if ("QHD".equals(l.getResolution())) disp += 25;
        else if ("4K".equals(l.getResolution())) disp += 35;
        else disp += 15; // FHD
        if (l.getRefreshRate() >= 144) disp += 25;
        else if (l.getRefreshRate() >= 120) disp += 20;
        else if (l.getRefreshRate() >= 90) disp += 10;
        if ("OLED/Mini-LED".equals(l.getDisplayType())) disp += 15;
        l.setDisplayScore(Math.min(100, disp));

        // Portability: weight + battery
        double port = 0;
        if (l.getWeight() <= 1.2) port = 50;
        else if (l.getWeight() <= 1.5) port = 40;
        else if (l.getWeight() <= 2.0) port = 25;
        else port = 10;
        port += Math.min(50, l.getBatteryLife() * 3.5);
        l.setPortabilityScore(Math.min(100, port));

        // Build Quality: material + speakers
        double build = 30;
        if ("Aluminum".equalsIgnoreCase(l.getBuildMaterial())) build += 30;
        else if ("Magnesium".equalsIgnoreCase(l.getBuildMaterial())) build += 35;
        else if ("Carbon Fiber".equalsIgnoreCase(l.getBuildMaterial())) build += 40;
        if ("Premium".equalsIgnoreCase(l.getSpeakerQuality())) build += 30;
        else if ("Good".equalsIgnoreCase(l.getSpeakerQuality())) build += 20;
        else build += 5;
        l.setBuildScore(Math.min(100, build));

        // Battery: raw hours mapped to 0-100
        double bat = Math.min(100, l.getBatteryLife() * 5);
        l.setBatteryScore(bat);
    }

    private int gpuTier(String gpu) {
        if (gpu == null) return 1;
        String g = gpu.toLowerCase();
        if (g.contains("4090")) return 10;
        if (g.contains("4080")) return 9;
        if (g.contains("4070")) return 8;
        if (g.contains("4060")) return 7;
        if (g.contains("4050") || g.contains("3060")) return 6;
        if (g.contains("3050")) return 5;
        if (g.contains("1650")) return 4;
        if (g.contains("apple")) return 7;
        if (g.contains("rtx")) return 6;
        return 2; // integrated
    }

    // =========================================================================
    //  "WHY THIS LAPTOP" — one-liner summary
    // =========================================================================
    private void generateWhy(Laptop l, SearchCriteria c) {
        StringBuilder sb = new StringBuilder();
        String purpose = c.getPurpose() != null ? c.getPurpose().toLowerCase() : "student";

        // Lead with strongest trait
        if (l.getPerfScore() >= 80) sb.append("Powerhouse performance");
        else if (l.getPortabilityScore() >= 75) sb.append("Ultra-portable daily driver");
        else if (l.getDisplayScore() >= 80) sb.append("Stunning display experience");
        else if (l.getBuildScore() >= 75) sb.append("Premium build quality");
        else sb.append("Well-rounded option");

        // Purpose match
        switch (purpose) {
            case "gaming":
                sb.append(" for gaming");
                if (l.getRefreshRate() >= 144) sb.append(" with buttery-smooth ");
                else sb.append(" with solid ");
                sb.append(l.getRefreshRate()).append("Hz visuals");
                break;
            case "creative":
                sb.append(" for creative work");
                if ("OLED/Mini-LED".equals(l.getDisplayType())) sb.append(" with color-accurate OLED display");
                else sb.append(" with high-fidelity display");
                break;
            default:
                sb.append(" for everyday use");
                if (l.getBatteryLife() >= 12) sb.append(" with all-day battery (").append((int)l.getBatteryLife()).append("hrs)");
                else if (l.getWeight() < 1.5) sb.append(" \u2014 lightweight at just ").append(l.getWeight()).append("kg");
                break;
        }

        // Value kicker
        double budgetUsed = l.getPrice() / c.getBudget();
        if (budgetUsed <= 0.6) sb.append(". Exceptional value \u2014 saves you ").append((int)((1 - budgetUsed) * 100)).append("% of your budget.");
        else if (budgetUsed <= 0.85) sb.append(". Great bang for the buck.");
        else sb.append(".");

        l.setWhyThisLaptop(sb.toString());
    }
}
