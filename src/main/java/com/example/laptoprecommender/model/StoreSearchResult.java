package com.example.laptoprecommender.model;

import java.util.ArrayList;
import java.util.List;

public class StoreSearchResult {
    private String pincode;
    private String locality;
    private String district;
    private String state;
    private String brand;
    private String laptopTitle;
    private int totalStores;
    private List<StoreInfo> stores = new ArrayList<>();
    private String status;  // "SUCCESS", "NO_STORES_FOUND", "INVALID_PINCODE", "ERROR"
    private String message;

    public StoreSearchResult() {}

    public StoreSearchResult(String pincode, String locality, String district, String state,
                             String brand, String laptopTitle, List<StoreInfo> stores,
                             String status, String message) {
        this.pincode = pincode;
        this.locality = locality;
        this.district = district;
        this.state = state;
        this.brand = brand;
        this.laptopTitle = laptopTitle;
        this.stores = stores != null ? stores : new ArrayList<>();
        this.totalStores = this.stores.size();
        this.status = status;
        this.message = message;
    }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getLocality() { return locality; }
    public void setLocality(String locality) { this.locality = locality; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getLaptopTitle() { return laptopTitle; }
    public void setLaptopTitle(String laptopTitle) { this.laptopTitle = laptopTitle; }

    public int getTotalStores() { return totalStores; }
    public void setTotalStores(int totalStores) { this.totalStores = totalStores; }

    public List<StoreInfo> getStores() { return stores; }
    public void setStores(List<StoreInfo> stores) {
        this.stores = stores != null ? stores : new ArrayList<>();
        this.totalStores = this.stores.size();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
