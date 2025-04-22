package com.example.hms.dao;

import com.example.hms.model.Booking;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * DAO interface for Booking entity with additional methods specific to Booking
 */
public interface BookingDAO extends GenericDAO<Booking, Integer> {

    /**
     * Find bookings by user ID
     * @param userId The user ID to search for
     * @return List of bookings for the specified user
     */
    List<Booking> findByUserId(int userId);

    /**
     * Find bookings by hotel ID
     * @param hotelId The hotel ID to search for
     * @return List of bookings for the specified hotel
     */
    List<Booking> findByHotelId(int hotelId);

    /**
     * Find bookings by room ID
     * @param roomId The room ID to search for
     * @return List of bookings for the specified room
     */
    List<Booking> findByRoomId(int roomId);

    /**
     * Find bookings by status
     * @param status The booking status to search for
     * @return List of bookings with the specified status
     */
    List<Booking> findByStatus(Booking.Status status);

    /**
     * Find bookings by date range
     * @param startDate The start date
     * @param endDate The end date
     * @return List of bookings within the specified date range
     */
    List<Booking> findByDateRange(Date startDate, Date endDate);

    /**
     * Update booking status
     * @param bookingId The booking ID
     * @param status The new status
     * @return true if updated successfully, false otherwise
     */
    boolean updateStatus(int bookingId, Booking.Status status);

    /**
     * Find recent bookings
     * @param limit The maximum number of bookings to return
     * @return List of recent bookings
     */
    List<Booking> findRecentBookings(int limit);

    /**
     * Calculate total revenue from all confirmed and completed bookings
     * @return The total revenue
     */
    BigDecimal calculateTotalRevenue();
}
