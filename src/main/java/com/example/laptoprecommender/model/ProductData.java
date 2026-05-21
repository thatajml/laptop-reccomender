package com.example.laptoprecommender.model;

import java.util.List;

public class ProductData {
    private List<Product> products;
    private int total;
    private int skip;
    private int limit;

    // Getters and Setters
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
    
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    
    public int getSkip() { return skip; }
    public void setSkip(int skip) { this.skip = skip; }
    
    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }
    
    public static class Product {
        private int id;
        private String title;
        private String description;
        private double price;
        private double rating;
        private String thumbnail;
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
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
    }
}
