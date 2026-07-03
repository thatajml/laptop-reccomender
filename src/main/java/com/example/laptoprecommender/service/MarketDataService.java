package com.example.laptoprecommender.service;

import com.example.laptoprecommender.model.Laptop;
import com.example.laptoprecommender.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketDataService {

    private final LaptopRepository laptopRepository;

    public MarketDataService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public Laptop findByLaptopId(int laptopId) {
        Laptop src = laptopRepository.findByLaptopId(laptopId);
        return src != null ? copy(src) : null;
    }

    public List<Laptop> fetchLaptops() {
        List<Laptop> fresh = new ArrayList<>();
        for (Laptop src : laptopRepository.findAll()) {
            fresh.add(copy(src));
        }
        return fresh;
    }

    /**
     * Deep-copy to prevent mutation of repository-cached objects.
     * Transient fields (score, reasons, radar scores, whyThisLaptop)
     * are not copied — they get computed fresh each request.
     */
    private Laptop copy(Laptop s) {
        Laptop l = new Laptop();
        l.setId(s.getId());
        l.setLaptopId(s.getLaptopId());
        l.setTitle(s.getTitle());
        l.setDescription(s.getDescription());
        l.setPrice(s.getPrice());
        l.setRating(s.getRating());
        l.setThumbnail(s.getThumbnail());
        l.setRamGB(s.getRamGB());
        l.setCpu(s.getCpu());
        l.setGpu(s.getGpu());
        l.setDisplaySize(s.getDisplaySize());
        l.setDisplayType(s.getDisplayType());
        l.setChipsetFamily(s.getChipsetFamily());
        l.setCpuTier(s.getCpuTier());
        l.setCpuGeneration(s.getCpuGeneration());
        l.setGpuModel(s.getGpuModel());
        l.setStorageCapacity(s.getStorageCapacity());
        l.setResolution(s.getResolution());
        l.setRefreshRate(s.getRefreshRate());
        l.setUsbACount(s.getUsbACount());
        l.setUsbCCount(s.getUsbCCount());
        l.setHdmiPort(s.isHdmiPort());
        l.setSdCardSlot(s.isSdCardSlot());
        l.setSpeakerQuality(s.getSpeakerQuality());
        l.setBuildMaterial(s.getBuildMaterial());
        l.setWeight(s.getWeight());
        l.setBatteryLife(s.getBatteryLife());
        l.setSource(s.getSource());
        l.setProductUrl(s.getProductUrl());
        l.setLastScrapedAt(s.getLastScrapedAt());
        l.setGeekbenchSingle(s.getGeekbenchSingle());
        l.setGeekbenchMulti(s.getGeekbenchMulti());
        l.setCinebenchScore(s.getCinebenchScore());
        return l;
    }
}
