package com.example.hms.dao.impl;

import com.example.hms.dao.RoomDAO;
import com.example.hms.model.Room;
import com.example.hms.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of RoomDAO interface
 */
public class RoomDAOImpl implements RoomDAO {
    private static final Logger LOGGER = Logger.getLogger(RoomDAOImpl.class.getName());

    public RoomDAOImpl() {
        // No need to store connection as a field
    }

    @Override
    public Room save(Room room) {
        String sql = "INSERT INTO rooms (hotel_id, room_type_id, room_number, floor, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, room.getHotelId());
            statement.setInt(2, room.getRoomTypeId());
            statement.setString(3, room.getRoomNumber());
            statement.setString(4, room.getFloor());
            statement.setString(5, room.getStatus().toString());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setRoomId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }

            return room;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving room", e);
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
    public Room update(Room room) {
        String sql = "UPDATE rooms SET hotel_id = ?, room_type_id = ?, room_number = ?, " +
                     "floor = ?, status = ? WHERE room_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, room.getHotelId());
            statement.setInt(2, room.getRoomTypeId());
            statement.setString(3, room.getRoomNumber());
            statement.setString(4, room.getFloor());
            statement.setString(5, room.getStatus().toString());
            statement.setInt(6, room.getRoomId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating room failed, no rows affected.");
            }

            return room;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room", e);
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
    public Room findById(Integer id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToRoom(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding room by ID", e);
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
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all rooms", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting room", e);
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
    public List<Room> findByHotelId(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by hotel ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findByRoomTypeId(int roomTypeId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, roomTypeId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by room type ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findByStatus(Room.Status status) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status.toString());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by status", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findAvailableRooms(int hotelId, Date checkInDate, Date checkOutDate) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.* FROM rooms r " +
                     "WHERE r.hotel_id = ? AND r.status = 'AVAILABLE' " +
                     "AND r.room_id NOT IN (" +
                     "    SELECT b.room_id FROM bookings b " +
                     "    WHERE (b.check_in_date <= ? AND b.check_out_date >= ?) " +
                     "    OR (b.check_in_date <= ? AND b.check_out_date >= ?) " +
                     "    OR (b.check_in_date >= ? AND b.check_out_date <= ?) " +
                     "    AND b.status IN ('PENDING', 'CONFIRMED')" +
                     ")";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setDate(2, checkOutDate);
            statement.setDate(3, checkOutDate);
            statement.setDate(4, checkInDate);
            statement.setDate(5, checkInDate);
            statement.setDate(6, checkInDate);
            statement.setDate(7, checkOutDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding available rooms", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findAvailableRoomsByType(int hotelId, int roomTypeId, Date checkInDate, Date checkOutDate) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.* FROM rooms r " +
                     "WHERE r.hotel_id = ? AND r.room_type_id = ? AND r.status = 'AVAILABLE' " +
                     "AND r.room_id NOT IN (" +
                     "    SELECT b.room_id FROM bookings b " +
                     "    WHERE (b.check_in_date <= ? AND b.check_out_date >= ?) " +
                     "    OR (b.check_in_date <= ? AND b.check_out_date >= ?) " +
                     "    OR (b.check_in_date >= ? AND b.check_out_date <= ?) " +
                     "    AND b.status IN ('PENDING', 'CONFIRMED')" +
                     ")";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setInt(2, roomTypeId);
            statement.setDate(3, checkOutDate);
            statement.setDate(4, checkOutDate);
            statement.setDate(5, checkInDate);
            statement.setDate(6, checkInDate);
            statement.setDate(7, checkInDate);
            statement.setDate(8, checkOutDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding available rooms by type", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public boolean updateStatus(int roomId, Room.Status status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status.toString());
            statement.setInt(2, roomId);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room status", e);
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
    public List<Room> findByRoomType(int roomTypeId) {
        // This is the same as findByRoomTypeId but added for clarity in the manage-rooms page
        return findByRoomTypeId(roomTypeId);
    }

    @Override
    public List<Room> findByHotelAndStatus(int hotelId, Room.Status status) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ? AND status = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setString(2, status.toString());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by hotel and status", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findByHotelAndRoomType(int hotelId, int roomTypeId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ? AND room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setInt(2, roomTypeId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by hotel and room type", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findByHotelStatusAndRoomType(int hotelId, Room.Status status, int roomTypeId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ? AND status = ? AND room_type_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hotelId);
            statement.setString(2, status.toString());
            statement.setInt(3, roomTypeId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding rooms by hotel, status, and room type", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }

        return rooms;
    }

    /**
     * Map a ResultSet to a Room object
     * @param resultSet The ResultSet containing room data
     * @return The mapped Room object
     * @throws SQLException If a database access error occurs
     */
    private Room mapResultSetToRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setRoomId(resultSet.getInt("room_id"));
        room.setHotelId(resultSet.getInt("hotel_id"));
        room.setRoomTypeId(resultSet.getInt("room_type_id"));
        room.setRoomNumber(resultSet.getString("room_number"));
        room.setFloor(resultSet.getString("floor"));

        // Handle status conversion safely
        try {
            String statusStr = resultSet.getString("status");
            if (statusStr != null && !statusStr.isEmpty()) {
                room.setStatus(Room.Status.valueOf(statusStr.toUpperCase()));
            } else {
                // Default to AVAILABLE if status is null or empty
                room.setStatus(Room.Status.AVAILABLE);
                LOGGER.warning("Room status was null or empty for room ID " + room.getRoomId() + ", defaulting to AVAILABLE");
            }
        } catch (IllegalArgumentException e) {
            // If status string doesn't match any enum value, default to AVAILABLE
            room.setStatus(Room.Status.AVAILABLE);
            LOGGER.warning("Invalid room status for room ID " + room.getRoomId() + ", defaulting to AVAILABLE: " + e.getMessage());
        }

        return room;
    }
}
