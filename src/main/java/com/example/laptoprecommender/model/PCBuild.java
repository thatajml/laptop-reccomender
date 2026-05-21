package com.example.laptoprecommender.model;

public class PCBuild {
    private String cpu;
    private String gpu;
    private String ram;
    private String motherboard;
    private String storage;
    private String caseAndPSU;
    
    private double totalPrice;
    private double priceDifference; // Laptop price - PC price

    private boolean isApple;
    private boolean isSimilarMatch;

    public PCBuild() {}

    // Getters and Setters
    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }

    public String getGpu() { return gpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getMotherboard() { return motherboard; }
    public void setMotherboard(String motherboard) { this.motherboard = motherboard; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    public String getCaseAndPSU() { return caseAndPSU; }
    public void setCaseAndPSU(String caseAndPSU) { this.caseAndPSU = caseAndPSU; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public double getPriceDifference() { return priceDifference; }
    public void setPriceDifference(double priceDifference) { this.priceDifference = priceDifference; }

    public boolean isApple() { return isApple; }
    public void setApple(boolean isApple) { this.isApple = isApple; }

    public boolean isSimilarMatch() { return isSimilarMatch; }
    public void setSimilarMatch(boolean isSimilarMatch) { this.isSimilarMatch = isSimilarMatch; }
}
