package com.example.hms.servlet.admin;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.Room;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for updating booking status (admin)
 */
@WebServlet(name = "UpdateBookingStatusServlet", urlPatterns = {"/admin/update-booking-status"})
public class UpdateBookingStatusServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }
        
        // Get parameters
        String bookingIdParam = request.getParameter("bookingId");
        String statusParam = request.getParameter("status");
        
        if (bookingIdParam == null || statusParam == null) {
            // If parameters are missing, redirect to manage bookings
            response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Missing parameters");
            return;
        }
        
        try {
            int bookingId = Integer.parseInt(bookingIdParam);
            Booking.Status status = Booking.Status.valueOf(statusParam);
            
            // Find booking
            Booking booking = bookingDAO.findById(bookingId);
            
            if (booking == null) {
                // If booking not found, redirect to manage bookings
                response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Booking not found");
                return;
            }
            
            // Update booking status
            boolean updated = bookingDAO.updateStatus(bookingId, status);
            
            if (updated) {
                // Update room status based on booking status
                if (status == Booking.Status.CANCELLED || status == Booking.Status.COMPLETED) {
                    roomDAO.updateStatus(booking.getRoomId(), Room.Status.AVAILABLE);
                } else if (status == Booking.Status.CONFIRMED) {
                    roomDAO.updateStatus(booking.getRoomId(), Room.Status.OCCUPIED);
                }
                
                // Redirect to manage bookings with success message
                response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?success=Booking status updated successfully");
            } else {
                // Redirect to manage bookings with error message
                response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Failed to update booking status");
            }
        } catch (IllegalArgumentException e) {
            // If parameters are invalid, redirect to manage bookings
            response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Invalid parameters");
        }
    }
}
