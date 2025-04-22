package com.example.hms.dao;

import com.example.hms.model.Room;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for Room entity with additional methods specific to Room
 */
public interface RoomDAO extends GenericDAO<Room, Integer> {

    /**
     * Find rooms by hotel ID
     * @param hotelId The hotel ID to search for
     * @return List of rooms for the specified hotel
     */
    List<Room> findByHotelId(int hotelId);

    /**
     * Find rooms by room type ID
     * @param roomTypeId The room type ID to search for
     * @return List of rooms of the specified room type
     */
    List<Room> findByRoomTypeId(int roomTypeId);

    /**
     * Find rooms by status
     * @param status The room status to search for
     * @return List of rooms with the specified status
     */
    List<Room> findByStatus(Room.Status status);

    /**
     * Find rooms by room type (for manage-rooms page)
     * @param roomTypeId The room type ID to search for
     * @return List of rooms of the specified room type
     */
    List<Room> findByRoomType(int roomTypeId);

    /**
     * Find rooms by hotel and status
     * @param hotelId The hotel ID
     * @param status The room status
     * @return List of rooms matching criteria
     */
    List<Room> findByHotelAndStatus(int hotelId, Room.Status status);

    /**
     * Find rooms by hotel and room type
     * @param hotelId The hotel ID
     * @param roomTypeId The room type ID
     * @return List of rooms matching criteria
     */
    List<Room> findByHotelAndRoomType(int hotelId, int roomTypeId);

    /**
     * Find rooms by hotel, status, and room type
     * @param hotelId The hotel ID
     * @param status The room status
     * @param roomTypeId The room type ID
     * @return List of rooms matching criteria
     */
    List<Room> findByHotelStatusAndRoomType(int hotelId, Room.Status status, int roomTypeId);

    /**
     * Find available rooms for a hotel during a specific date range
     * @param hotelId The hotel ID
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @return List of available rooms
     */
    List<Room> findAvailableRooms(int hotelId, Date checkInDate, Date checkOutDate);

    /**
     * Find available rooms for a hotel and room type during a specific date range
     * @param hotelId The hotel ID
     * @param roomTypeId The room type ID
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @return List of available rooms
     */
    List<Room> findAvailableRoomsByType(int hotelId, int roomTypeId, Date checkInDate, Date checkOutDate);

    /**
     * Update room status
     * @param roomId The room ID
     * @param status The new status
     * @return true if updated successfully, false otherwise
     */
    boolean updateStatus(int roomId, Room.Status status);
}
