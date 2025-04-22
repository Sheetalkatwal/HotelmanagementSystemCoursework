package com.example.hms.model;

/**
 * Room model class representing individual rooms in hotels
 */
public class Room {
    private int roomId;
    private int hotelId;
    private int roomTypeId;
    private String roomNumber;
    private String floor;
    private Status status;
    
    // Room status enum
    public enum Status {
        AVAILABLE, OCCUPIED, MAINTENANCE
    }
    
    // Additional fields for displaying room information
    private RoomType roomType;
    private Hotel hotel;
    
    // Default constructor
    public Room() {
    }
    
    // Constructor with essential fields
    public Room(int hotelId, int roomTypeId, String roomNumber, Status status) {
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
        this.status = status;
    }
    
    // Constructor with all fields
    public Room(int roomId, int hotelId, int roomTypeId, String roomNumber, 
               String floor, Status status) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.status = status;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getHotelId() {
        return hotelId;
    }
    
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
    
    public int getRoomTypeId() {
        return roomTypeId;
    }
    
    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getFloor() {
        return floor;
    }
    
    public void setFloor(String floor) {
        this.floor = floor;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }
    
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
    public Hotel getHotel() {
        return hotel;
    }
    
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", hotelId=" + hotelId +
                ", roomTypeId=" + roomTypeId +
                ", roomNumber='" + roomNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
