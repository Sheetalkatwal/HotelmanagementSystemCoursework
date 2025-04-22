package com.example.hms.servlet;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.Room;
import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for cancelling bookings
 */
@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/cancel-booking"})
public class CancelBookingServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to cancel a booking");
            return;
        }

        // Check if user is an admin (admins should use the admin booking management page)
        if (SessionUtil.isAdmin(request)) {
            // Redirect admins to the admin booking management page
            response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Please use the admin booking management page");
            return;
        }

        // Get booking ID from request parameter
        String bookingIdParam = request.getParameter("id");

        if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {
            // If no booking ID is provided, redirect to my bookings
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=No booking specified");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdParam);

            // Find booking by ID
            Booking booking = bookingDAO.findById(bookingId);

            if (booking == null) {
                // If booking not found, redirect to my bookings
                response.sendRedirect(request.getContextPath() + "/my-bookings?error=Booking not found");
                return;
            }

            // Check if the booking belongs to the user
            if (booking.getUserId() != user.getUserId()) {
                // If not authorized, redirect to my bookings
                response.sendRedirect(request.getContextPath() + "/my-bookings?error=You are not authorized to cancel this booking");
                return;
            }

            // Update booking status to CANCELLED
            boolean updated = bookingDAO.updateStatus(bookingId, Booking.Status.CANCELLED);

            if (updated) {
                // Update room status to AVAILABLE
                roomDAO.updateStatus(booking.getRoomId(), Room.Status.AVAILABLE);

                // Redirect to my bookings with success message
                response.sendRedirect(request.getContextPath() + "/my-bookings?success=Booking cancelled successfully");
            } else {
                // Redirect to my bookings with error message
                response.sendRedirect(request.getContextPath() + "/my-bookings?error=Failed to cancel booking");
            }
        } catch (NumberFormatException e) {
            // If booking ID is not a valid number, redirect to my bookings
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=Invalid booking ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Call doGet to handle POST requests the same way as GET requests
        doGet(request, response);
    }
}
