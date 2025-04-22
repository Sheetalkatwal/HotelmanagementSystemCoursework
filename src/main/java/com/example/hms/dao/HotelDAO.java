package com.example.hms.dao;

import com.example.hms.model.Hotel;

import java.util.List;

/**
 * DAO interface for Hotel entity with additional methods specific to Hotel
 */
public interface HotelDAO extends GenericDAO<Hotel, Integer> {
    
    /**
     * Search hotels by name, city, or country
     * @param keyword The search keyword
     * @return List of hotels matching the search criteria
     */
    List<Hotel> searchHotels(String keyword);
    
    /**
     * Find hotels by city
     * @param city The city to search for
     * @return List of hotels in the specified city
     */
    List<Hotel> findByCity(String city);
    
    /**
     * Find hotels by country
     * @param country The country to search for
     * @return List of hotels in the specified country
     */
    List<Hotel> findByCountry(String country);
    
    /**
     * Find hotels by star rating
     * @param starRating The star rating to search for
     * @return List of hotels with the specified star rating
     */
    List<Hotel> findByStarRating(int starRating);
}
