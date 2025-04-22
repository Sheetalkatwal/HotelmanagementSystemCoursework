package com.example.hms.dao.impl;

import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.model.RoomType;
import com.example.hms.util.DBConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of RoomTypeDAO interface
 */
public class RoomTypeDAOImpl implements RoomTypeDAO {
    private static final Logger LOGGER = Logger.getLogger(RoomTypeDAOImpl.class.getName());

    public RoomTypeDAOImpl() {
        // No need to store connection as a field
    }

    @Override
    public RoomType save(RoomType roomType) {
        String sql = "INSERT INTO room_types (hotel_id, name, description, capacity, price_per_night) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, roomType.getHotelId());
            statement.setString(2, roomType.getName());
            statement.setString(3, roomType.getDescription());
            statement.setInt(4, roomType.getCapacity());
            statement.setBigDecimal(5, roomType.getPricePerNight());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating room type failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomType.setRoomTypeId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating room type failed, no ID obtained.");
            }

            return roomType;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving room type", e);
            return null;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }

    @Override
    public RoomType update(RoomType roomType) {
        String sql = "UPDATE room_types SET hotel_id = ?, name = ?, description = ?, " +
                     "capacity = ?, price_per_night = ? WHERE room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, roomType.getHotelId());
            statement.setString(2, roomType.getName());
            statement.setString(3, roomType.getDescription());
            statement.setInt(4, roomType.getCapacity());
            statement.setBigDecimal(5, roomType.getPricePerNight());
            statement.setInt(6, roomType.getRoomTypeId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating room type failed, no rows affected.");
            }

            return roomType;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room type", e);
            return null;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }

    @Override
    public RoomType findById(Integer id) {
        String sql = "SELECT * FROM room_types WHERE room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToRoomType(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room type by ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return null;
    }

    @Override
    public List<RoomType> findAll() {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all room types", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM room_types WHERE room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting room type", e);
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }

    @Override
    public List<RoomType> findByHotelId(int hotelId) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE hotel_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by hotel ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByCapacity(int capacity) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE capacity >= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, capacity);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by capacity", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE price_per_night >= ? AND price_per_night <= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, minPrice);
            statement.setBigDecimal(2, maxPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by price range", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByMinPrice(BigDecimal minPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE price_per_night >= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, minPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by minimum price", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByMaxPrice(BigDecimal maxPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE price_per_night <= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, maxPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by maximum price", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByHotelAndPriceRange(int hotelId, BigDecimal minPrice, BigDecimal maxPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE hotel_id = ? AND price_per_night >= ? AND price_per_night <= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setBigDecimal(2, minPrice);
            statement.setBigDecimal(3, maxPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by hotel and price range", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByHotelAndMinPrice(int hotelId, BigDecimal minPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE hotel_id = ? AND price_per_night >= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setBigDecimal(2, minPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by hotel and minimum price", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    @Override
    public List<RoomType> findByHotelAndMaxPrice(int hotelId, BigDecimal maxPrice) {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE hotel_id = ? AND price_per_night <= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setBigDecimal(2, maxPrice);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roomTypes.add(mapResultSetToRoomType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room types by hotel and maximum price", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return roomTypes;
    }

    /**
     * Map a ResultSet to a RoomType object
     * @param resultSet The ResultSet containing room type data
     * @return The mapped RoomType object
     * @throws SQLException If a database access error occurs
     */
    private RoomType mapResultSetToRoomType(ResultSet resultSet) throws SQLException {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(resultSet.getInt("room_type_id"));
        roomType.setHotelId(resultSet.getInt("hotel_id"));
        roomType.setName(resultSet.getString("name"));
        roomType.setDescription(resultSet.getString("description"));
        roomType.setCapacity(resultSet.getInt("capacity"));
        roomType.setPricePerNight(resultSet.getBigDecimal("price_per_night"));
        return roomType;
    }
}
