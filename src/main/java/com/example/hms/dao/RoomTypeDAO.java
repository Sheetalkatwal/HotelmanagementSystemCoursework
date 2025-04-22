package com.example.hms.dao;

import com.example.hms.model.RoomType;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO interface for RoomType entity with additional methods specific to RoomType
 */
public interface RoomTypeDAO extends GenericDAO<RoomType, Integer> {

    /**
     * Find room types by hotel ID
     * @param hotelId The hotel ID to search for
     * @return List of room types for the specified hotel
     */
    List<RoomType> findByHotelId(int hotelId);

    /**
     * Find room types by capacity
     * @param capacity The minimum capacity to search for
     * @return List of room types with at least the specified capacity
     */
    List<RoomType> findByCapacity(int capacity);

    /**
     * Find room types by price range
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return List of room types within the specified price range
     */
    List<RoomType> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find room types by minimum price
     * @param minPrice The minimum price
     * @return List of room types with at least the specified price
     */
    List<RoomType> findByMinPrice(BigDecimal minPrice);

    /**
     * Find room types by maximum price
     * @param maxPrice The maximum price
     * @return List of room types with at most the specified price
     */
    List<RoomType> findByMaxPrice(BigDecimal maxPrice);

    /**
     * Find room types by hotel and price range
     * @param hotelId The hotel ID
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return List of room types for the specified hotel within the price range
     */
    List<RoomType> findByHotelAndPriceRange(int hotelId, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find room types by hotel and minimum price
     * @param hotelId The hotel ID
     * @param minPrice The minimum price
     * @return List of room types for the specified hotel with at least the specified price
     */
    List<RoomType> findByHotelAndMinPrice(int hotelId, BigDecimal minPrice);

    /**
     * Find room types by hotel and maximum price
     * @param hotelId The hotel ID
     * @param maxPrice The maximum price
     * @return List of room types for the specified hotel with at most the specified price
     */
    List<RoomType> findByHotelAndMaxPrice(int hotelId, BigDecimal maxPrice);
}
