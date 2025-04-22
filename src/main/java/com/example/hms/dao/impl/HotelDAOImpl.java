package com.example.hms.dao.impl;

import com.example.hms.dao.HotelDAO;
import com.example.hms.model.Hotel;
import com.example.hms.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of HotelDAO interface
 */
public class HotelDAOImpl implements HotelDAO {
    private static final Logger LOGGER = Logger.getLogger(HotelDAOImpl.class.getName());
    private final Connection connection;
    
    public HotelDAOImpl() {
        this.connection = DBConnectionUtil.getConnection();
    }
    
    @Override
    public Hotel save(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, address, city, state, country, postal_code, " +
                     "phone, email, star_rating, description, image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, hotel.getName());
            statement.setString(2, hotel.getAddress());
            statement.setString(3, hotel.getCity());
            statement.setString(4, hotel.getState());
            statement.setString(5, hotel.getCountry());
            statement.setString(6, hotel.getPostalCode());
            statement.setString(7, hotel.getPhone());
            statement.setString(8, hotel.getEmail());
            statement.setInt(9, hotel.getStarRating());
            statement.setString(10, hotel.getDescription());
            statement.setString(11, hotel.getImageUrl());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating hotel failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hotel.setHotelId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating hotel failed, no ID obtained.");
                }
            }
            
            return hotel;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving hotel", e);
            return null;
        }
    }
    
    @Override
    public Hotel update(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, address = ?, city = ?, state = ?, " +
                     "country = ?, postal_code = ?, phone = ?, email = ?, star_rating = ?, " +
                     "description = ?, image_url = ? WHERE hotel_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hotel.getName());
            statement.setString(2, hotel.getAddress());
            statement.setString(3, hotel.getCity());
            statement.setString(4, hotel.getState());
            statement.setString(5, hotel.getCountry());
            statement.setString(6, hotel.getPostalCode());
            statement.setString(7, hotel.getPhone());
            statement.setString(8, hotel.getEmail());
            statement.setInt(9, hotel.getStarRating());
            statement.setString(10, hotel.getDescription());
            statement.setString(11, hotel.getImageUrl());
            statement.setInt(12, hotel.getHotelId());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating hotel failed, no rows affected.");
            }
            
            return hotel;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating hotel", e);
            return null;
        }
    }
    
    @Override
    public Hotel findById(Integer id) {
        String sql = "SELECT * FROM hotels WHERE hotel_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToHotel(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding hotel by ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Hotel> findAll() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                hotels.add(mapResultSetToHotel(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all hotels", e);
        }
        
        return hotels;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM hotels WHERE hotel_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting hotel", e);
            return false;
        }
    }
    
    @Override
    public List<Hotel> searchHotels(String keyword) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE name LIKE ? OR city LIKE ? OR country LIKE ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hotels.add(mapResultSetToHotel(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching hotels", e);
        }
        
        return hotels;
    }
    
    @Override
    public List<Hotel> findByCity(String city) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE city = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hotels.add(mapResultSetToHotel(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding hotels by city", e);
        }
        
        return hotels;
    }
    
    @Override
    public List<Hotel> findByCountry(String country) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE country = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, country);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hotels.add(mapResultSetToHotel(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding hotels by country", e);
        }
        
        return hotels;
    }
    
    @Override
    public List<Hotel> findByStarRating(int starRating) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE star_rating = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, starRating);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hotels.add(mapResultSetToHotel(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding hotels by star rating", e);
        }
        
        return hotels;
    }
    
    /**
     * Map a ResultSet to a Hotel object
     * @param resultSet The ResultSet containing hotel data
     * @return The mapped Hotel object
     * @throws SQLException If a database access error occurs
     */
    private Hotel mapResultSetToHotel(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(resultSet.getInt("hotel_id"));
        hotel.setName(resultSet.getString("name"));
        hotel.setAddress(resultSet.getString("address"));
        hotel.setCity(resultSet.getString("city"));
        hotel.setState(resultSet.getString("state"));
        hotel.setCountry(resultSet.getString("country"));
        hotel.setPostalCode(resultSet.getString("postal_code"));
        hotel.setPhone(resultSet.getString("phone"));
        hotel.setEmail(resultSet.getString("email"));
        hotel.setStarRating(resultSet.getInt("star_rating"));
        hotel.setDescription(resultSet.getString("description"));
        hotel.setImageUrl(resultSet.getString("image_url"));
        hotel.setCreatedAt(resultSet.getTimestamp("created_at"));
        hotel.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return hotel;
    }
}
