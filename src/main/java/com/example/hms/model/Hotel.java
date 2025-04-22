package com.example.hms.model;

import java.sql.Timestamp;

/**
 * Hotel model class representing hotels in the system
 */
public class Hotel {
    private int hotelId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private int starRating;
    private String description;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Hotel() {
    }
    
    // Constructor with essential fields
    public Hotel(String name, String address, String city, String country, int starRating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.starRating = starRating;
    }
    
    // Constructor with all fields
    public Hotel(int hotelId, String name, String address, String city, String state, 
                String country, String postalCode, String phone, String email, 
                int starRating, String description, String imageUrl, 
                Timestamp createdAt, Timestamp updatedAt) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.starRating = starRating;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getHotelId() {
        return hotelId;
    }
    
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getStarRating() {
        return starRating;
    }
    
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", starRating=" + starRating +
                '}';
    }
}
