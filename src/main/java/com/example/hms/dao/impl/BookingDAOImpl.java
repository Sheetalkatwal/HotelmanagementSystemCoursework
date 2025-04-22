package com.example.hms.dao.impl;

import com.example.hms.dao.BookingDAO;
import com.example.hms.model.Booking;
import com.example.hms.util.DBConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of BookingDAO interface
 */
public class BookingDAOImpl implements BookingDAO {
    private static final Logger LOGGER = Logger.getLogger(BookingDAOImpl.class.getName());
    private final Connection connection;

    public BookingDAOImpl() {
        this.connection = DBConnectionUtil.getConnection();
    }

    @Override
    public Booking save(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, " +
                     "total_price, status, special_requests) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getRoomId());
            statement.setDate(3, booking.getCheckInDate());
            statement.setDate(4, booking.getCheckOutDate());
            statement.setBigDecimal(5, booking.getTotalPrice());
            statement.setString(6, booking.getStatus().toString());
            statement.setString(7, booking.getSpecialRequests());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }

            return booking;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving booking", e);
            return null;
        }
    }

    @Override
    public Booking update(Booking booking) {
        String sql = "UPDATE bookings SET user_id = ?, room_id = ?, check_in_date = ?, " +
                     "check_out_date = ?, total_price = ?, status = ?, special_requests = ? " +
                     "WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getRoomId());
            statement.setDate(3, booking.getCheckInDate());
            statement.setDate(4, booking.getCheckOutDate());
            statement.setBigDecimal(5, booking.getTotalPrice());
            statement.setString(6, booking.getStatus().toString());
            statement.setString(7, booking.getSpecialRequests());
            statement.setInt(8, booking.getBookingId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating booking failed, no rows affected.");
            }

            return booking;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating booking", e);
            return null;
        }
    }

    @Override
    public Booking findById(Integer id) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBooking(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding booking by ID", e);
        }

        return null;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all bookings", e);
        }

        return bookings;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting booking", e);
            return false;
        }
    }

    @Override
    public List<Booking> findByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding bookings by user ID", e);
        }

        return bookings;
    }

    @Override
    public List<Booking> findByHotelId(int hotelId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.* FROM bookings b " +
                     "JOIN rooms r ON b.room_id = r.room_id " +
                     "WHERE r.hotel_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding bookings by hotel ID", e);
        }

        return bookings;
    }

    @Override
    public List<Booking> findByRoomId(int roomId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding bookings by room ID", e);
        }

        return bookings;
    }

    @Override
    public List<Booking> findByStatus(Booking.Status status) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding bookings by status", e);
        }

        return bookings;
    }

    @Override
    public List<Booking> findByDateRange(Date startDate, Date endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE " +
                     "(check_in_date BETWEEN ? AND ?) OR " +
                     "(check_out_date BETWEEN ? AND ?) OR " +
                     "(check_in_date <= ? AND check_out_date >= ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            statement.setDate(3, startDate);
            statement.setDate(4, endDate);
            statement.setDate(5, startDate);
            statement.setDate(6, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding bookings by date range", e);
        }

        return bookings;
    }

    @Override
    public boolean updateStatus(int bookingId, Booking.Status status) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.toString());
            statement.setInt(2, bookingId);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating booking status", e);
            return false;
        }
    }

    @Override
    public List<Booking> findRecentBookings(int limit) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC LIMIT ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding recent bookings", e);
        }

        return bookings;
    }

    @Override
    public BigDecimal calculateTotalRevenue() {
        String sql = "SELECT SUM(total_price) AS total_revenue FROM bookings WHERE status IN (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Booking.Status.CONFIRMED.toString());
            statement.setString(2, Booking.Status.COMPLETED.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("total_revenue");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error calculating total revenue", e);
        }

        return BigDecimal.ZERO;
    }

    /**
     * Map a ResultSet to a Booking object
     * @param resultSet The ResultSet containing booking data
     * @return The mapped Booking object
     * @throws SQLException If a database access error occurs
     */
    private Booking mapResultSetToBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(resultSet.getInt("booking_id"));
        booking.setUserId(resultSet.getInt("user_id"));
        booking.setRoomId(resultSet.getInt("room_id"));
        booking.setCheckInDate(resultSet.getDate("check_in_date"));
        booking.setCheckOutDate(resultSet.getDate("check_out_date"));
        booking.setTotalPrice(resultSet.getBigDecimal("total_price"));
        booking.setBookingDate(resultSet.getTimestamp("booking_date"));
        booking.setStatus(Booking.Status.valueOf(resultSet.getString("status")));
        booking.setSpecialRequests(resultSet.getString("special_requests"));
        return booking;
    }
}
