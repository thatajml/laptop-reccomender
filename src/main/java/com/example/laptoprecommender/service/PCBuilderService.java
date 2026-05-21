package com.example.laptoprecommender.service;

import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.model.PCBuild;
import org.springframework.stereotype.Service;

@Service
public class PCBuilderService {

    public PCBuild buildEquivalentPC(Laptop laptop) {
        PCBuild pc = new PCBuild();
        double pcCost = 0;
        pc.setApple(false);
        pc.setSimilarMatch(true);

        if (laptop.getChipsetFamily() != null && laptop.getChipsetFamily().equalsIgnoreCase("Apple")) {
            pc.setApple(true);
            pc.setSimilarMatch(false);
            return pc;
        }

        // RAM Mapping
        if (laptop.getRamGB() >= 64) {
            pc.setRam("64GB DDR5 5600MHz");
            pcCost += 250;
        } else if (laptop.getRamGB() >= 32) {
            pc.setRam("32GB DDR5 5200MHz");
            pcCost += 130;
        } else if (laptop.getRamGB() >= 16) {
            pc.setRam("16GB DDR5 4800MHz");
            pcCost += 80;
        } else {
            pc.setRam("8GB DDR4 3200MHz");
            pcCost += 40;
        }

        // CPU Mapping
        if (laptop.getCpu() != null) {
            if (laptop.getCpu().contains("i9") || laptop.getCpu().contains("Ryzen 9")) {
                pc.setCpu("Intel Core i9-14900K");
                pcCost += 550;
            } else if (laptop.getCpu().contains("i7") || laptop.getCpu().contains("Ryzen 7")) {
                pc.setCpu("Intel Core i7-13700K");
                pcCost += 350;
            } else if (laptop.getCpu().contains("i5") || laptop.getCpu().contains("Ryzen 5")) {
                pc.setCpu("AMD Ryzen 5 7600X");
                pcCost += 200;
            } else {
                pc.setCpu("Intel Core i3-12100F");
                pcCost += 90;
            }
        } else {
            pc.setCpu("Intel Core i5-12400F");
            pcCost += 150;
        }

        // GPU Mapping
        if (laptop.getGpu() != null) {
            String gpu = laptop.getGpu();
            if (gpu.contains("4090")) {
                pc.setGpu("NVIDIA RTX 4090 24GB");
                pcCost += 1600;
            } else if (gpu.contains("4080")) {
                pc.setGpu("NVIDIA RTX 4080 16GB");
                pcCost += 1000;
            } else if (gpu.contains("4070") || gpu.contains("3080") || gpu.contains("3070")) {
                pc.setGpu("NVIDIA RTX 4070 12GB");
                pcCost += 600;
            } else if (gpu.contains("4060") || gpu.contains("4050") || gpu.contains("3060")) {
                pc.setGpu("NVIDIA RTX 4060 8GB");
                pcCost += 300;
            } else if (gpu.contains("3050") || gpu.contains("1650") || gpu.contains("RX 6500")) {
                pc.setGpu("AMD Radeon RX 6600 8GB");
                pcCost += 200;
            } else if (gpu.contains("Ada") || gpu.contains("A2000") || gpu.contains("A3000") || gpu.contains("A5000")) {
                pc.setGpu("NVIDIA RTX 4080 16GB (Pro Equivalent)");
                pcCost += 1000;
            } else {
                // Integrated GPUs (UHD, Iris Xe, Radeon Graphics)
                pc.setGpu("Integrated CPU Graphics");
                pcCost += 0;
                pc.setSimilarMatch(false); // Can't build a 1:1 'matching' equivalent custom PC without a dGPU normally
            }
        }

        // Motherboard, Storage, Case, PSU
        if (pcCost > 1500) {
            pc.setMotherboard("ASUS ROG Maximus Z790");
            pc.setStorage("WD Black SN850X 2TB SSD");
            pc.setCaseAndPSU("Corsair 4000D + 1000W 80+ Gold PU");
            pcCost += 400 + 150 + 250;
        } else if (pcCost > 600) {
            pc.setMotherboard("MSI MAG B650 Tomahawk");
            pc.setStorage("Samsung 980 PRO 1TB SSD");
            pc.setCaseAndPSU("NZXT H510 Flow + 750W PSU");
            pcCost += 200 + 90 + 150;
        } else {
            pc.setMotherboard("GIGABYTE A520M K V2");
            pc.setStorage("Crucial P3 500GB SSD");
            pc.setCaseAndPSU("Thermaltake Versa H18 + 500W PSU");
            pcCost += 80 + 40 + 80;
        }

        pcCost = pcCost * 83; // Convert total to INR

        pc.setTotalPrice(pcCost);
        pc.setPriceDifference(laptop.getPrice() - pcCost);

        return pc;
    }
}
