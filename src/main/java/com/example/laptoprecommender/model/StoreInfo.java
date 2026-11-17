package com.example.laptoprecommender.model;

public class StoreInfo {
    private String name;
    private String brand;
    private String type;            // "Exclusive Brand Store", "Authorized Retail Partner"
    private String address;
    private String locality;
    private String city;
    private String pincode;
    private String distance;        // e.g. "1.2 km"
    private String phone;
    private String openingHours;    // e.g. "10:30 AM - 9:00 PM"
    private boolean openNow;
    private boolean hasDemoUnits;   // Real-time test-drive availability
    private String googleMapsUrl;
    private String directionsUrl;

    public StoreInfo() {}

    public StoreInfo(String name, String brand, String type, String address, String locality,
                     String city, String pincode, String distance, String phone,
                     String openingHours, boolean openNow, boolean hasDemoUnits,
                     String googleMapsUrl, String directionsUrl) {
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.distance = distance;
        this.phone = phone;
        this.openingHours = openingHours;
        this.openNow = openNow;
        this.hasDemoUnits = hasDemoUnits;
        this.googleMapsUrl = googleMapsUrl;
        this.directionsUrl = directionsUrl;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLocality() { return locality; }
    public void setLocality(String locality) { this.locality = locality; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

    public boolean isOpenNow() { return openNow; }
    public void setOpenNow(boolean openNow) { this.openNow = openNow; }

    public boolean isHasDemoUnits() { return hasDemoUnits; }
    public void setHasDemoUnits(boolean hasDemoUnits) { this.hasDemoUnits = hasDemoUnits; }

    public String getGoogleMapsUrl() { return googleMapsUrl; }
    public void setGoogleMapsUrl(String googleMapsUrl) { this.googleMapsUrl = googleMapsUrl; }

    public String getDirectionsUrl() { return directionsUrl; }
    public void setDirectionsUrl(String directionsUrl) { this.directionsUrl = directionsUrl; }
}
