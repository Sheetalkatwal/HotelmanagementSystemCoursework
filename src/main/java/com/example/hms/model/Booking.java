package com.example.hms.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Booking model class representing room bookings in the system
 */
public class Booking {
    private int bookingId;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private BigDecimal totalPrice;
    private Timestamp bookingDate;
    private Status status;
    private String specialRequests;
    
    // Booking status enum
    public enum Status {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }
    
    // Additional fields for displaying booking information
    private User user;
    private Room room;
    private Hotel hotel;
    
    // Default constructor
    public Booking() {
    }
    
    // Constructor with essential fields
    public Booking(int userId, int roomId, Date checkInDate, Date checkOutDate, 
                  BigDecimal totalPrice) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.status = Status.PENDING;
    }
    
    // Constructor with all fields
    public Booking(int bookingId, int userId, int roomId, Date checkInDate, 
                  Date checkOutDate, BigDecimal totalPrice, Timestamp bookingDate, 
                  Status status, String specialRequests) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.status = status;
        this.specialRequests = specialRequests;
    }
    
    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public Date getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Timestamp getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getSpecialRequests() {
        return specialRequests;
    }
    
    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
    
    public Hotel getHotel() {
        return hotel;
    }
    
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
