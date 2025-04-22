package com.example.hms.model;

import java.math.BigDecimal;

/**
 * RoomType model class representing different types of rooms in hotels
 */
public class RoomType {
    private int roomTypeId;
    private int hotelId;
    private String name;
    private String description;
    private int capacity;
    private BigDecimal pricePerNight;

    // Reference to the hotel this room type belongs to
    private Hotel hotel;

    // Default constructor
    public RoomType() {
    }

    // Constructor with essential fields
    public RoomType(int hotelId, String name, int capacity, BigDecimal pricePerNight) {
        this.hotelId = hotelId;
        this.name = name;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    // Constructor with all fields
    public RoomType(int roomTypeId, int hotelId, String name, String description,
                   int capacity, BigDecimal pricePerNight) {
        this.roomTypeId = roomTypeId;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    // Getters and Setters
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}
