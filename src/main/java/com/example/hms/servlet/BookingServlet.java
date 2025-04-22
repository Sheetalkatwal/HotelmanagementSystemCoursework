package com.example.hms.servlet;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.Room;
import com.example.hms.model.RoomType;
import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servlet for creating bookings
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/booking"})
public class BookingServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to make a booking");
            return;
        }

        // Check if user is an admin (admins should not make bookings)
        if (SessionUtil.isAdmin(request)) {
            // Redirect admins to the admin dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Admins cannot make bookings. Please use a customer account.");
            return;
        }

        // Get parameters
        String hotelIdParam = request.getParameter("hotelId");
        String roomTypeIdParam = request.getParameter("roomTypeId");
        String checkInDateParam = request.getParameter("checkInDate");
        String checkOutDateParam = request.getParameter("checkOutDate");

        // Validate parameters
        if (hotelIdParam == null || roomTypeIdParam == null ||
            checkInDateParam == null || checkOutDateParam == null) {
            // Redirect to hotel search if parameters are missing
            response.sendRedirect(request.getContextPath() + "/search?error=Missing booking parameters");
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdParam);
            int roomTypeId = Integer.parseInt(roomTypeIdParam);
            Date checkInDate = Date.valueOf(checkInDateParam);
            Date checkOutDate = Date.valueOf(checkOutDateParam);

            // Validate dates
            if (checkInDate.after(checkOutDate) || checkInDate.before(Date.valueOf(LocalDate.now()))) {
                // Redirect to hotel details if dates are invalid
                response.sendRedirect(request.getContextPath() + "/hotel?id=" + hotelId +
                                     "&error=Invalid dates. Check-in date must be today or later, and before check-out date.");
                return;
            }

            // Find available rooms
            List<Room> availableRooms = roomDAO.findAvailableRoomsByType(hotelId, roomTypeId, checkInDate, checkOutDate);

            if (availableRooms.isEmpty()) {
                // Redirect to hotel details if no rooms are available
                response.sendRedirect(request.getContextPath() + "/hotel?id=" + hotelId +
                                     "&error=No rooms available for the selected dates and room type.");
                return;
            }

            // Get the first available room
            Room room = availableRooms.get(0);

            // Get the room type
            RoomType roomType = roomTypeDAO.findById(roomTypeId);

            // Calculate number of nights
            LocalDate start = checkInDate.toLocalDate();
            LocalDate end = checkOutDate.toLocalDate();
            long nights = ChronoUnit.DAYS.between(start, end);

            // Calculate total price
            BigDecimal totalPrice = roomType.getPricePerNight().multiply(BigDecimal.valueOf(nights));

            // Set attributes for the booking form
            request.setAttribute("room", room);
            request.setAttribute("roomType", roomType);
            request.setAttribute("checkInDate", checkInDate);
            request.setAttribute("checkOutDate", checkOutDate);
            request.setAttribute("nights", nights);
            request.setAttribute("totalPrice", totalPrice);

            // Forward to the booking form
            request.getRequestDispatcher("/booking-form.jsp").forward(request, response);
        } catch (Exception e) {
            // Redirect to hotel search if there's an error
            response.sendRedirect(request.getContextPath() + "/search?error=Error processing booking request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to make a booking");
            return;
        }

        // Check if user is an admin (admins should not make bookings)
        if (SessionUtil.isAdmin(request)) {
            // Redirect admins to the admin dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Admins cannot make bookings. Please use a customer account.");
            return;
        }

        // Get parameters
        String roomIdParam = request.getParameter("roomId");
        String checkInDateParam = request.getParameter("checkInDate");
        String checkOutDateParam = request.getParameter("checkOutDate");
        String totalPriceParam = request.getParameter("totalPrice");
        String specialRequests = request.getParameter("specialRequests");

        try {
            int roomId = Integer.parseInt(roomIdParam);
            Date checkInDate = Date.valueOf(checkInDateParam);
            Date checkOutDate = Date.valueOf(checkOutDateParam);
            BigDecimal totalPrice = new BigDecimal(totalPriceParam);

            // Create booking
            Booking booking = new Booking();
            booking.setUserId(user.getUserId());
            booking.setRoomId(roomId);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setTotalPrice(totalPrice);
            booking.setStatus(Booking.Status.PENDING);
            booking.setSpecialRequests(specialRequests);

            // Save booking
            Booking savedBooking = bookingDAO.save(booking);

            if (savedBooking != null) {
                // Update room status to OCCUPIED
                roomDAO.updateStatus(roomId, Room.Status.OCCUPIED);

                // Redirect to booking confirmation
                response.sendRedirect(request.getContextPath() + "/my-bookings?success=Booking created successfully");
            } else {
                // Redirect to hotel search if booking failed
                response.sendRedirect(request.getContextPath() + "/search?error=Failed to create booking");
            }
        } catch (Exception e) {
            // Redirect to hotel search if there's an error
            response.sendRedirect(request.getContextPath() + "/search?error=Error processing booking: " + e.getMessage());
        }
    }
}
