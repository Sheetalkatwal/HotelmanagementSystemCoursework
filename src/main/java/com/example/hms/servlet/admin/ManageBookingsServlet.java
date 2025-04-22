package com.example.hms.servlet.admin;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.UserDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.dao.impl.UserDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.Hotel;
import com.example.hms.model.Room;
import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for managing bookings (admin)
 */
@WebServlet(name = "ManageBookingsServlet", urlPatterns = {"/admin/manage-bookings"})
public class ManageBookingsServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }
        
        // Get filter parameters
        String statusParam = request.getParameter("status");
        String hotelIdParam = request.getParameter("hotelId");
        
        List<Booking> bookings;
        
        // Apply filters
        if (statusParam != null && !statusParam.trim().isEmpty()) {
            try {
                Booking.Status status = Booking.Status.valueOf(statusParam);
                bookings = bookingDAO.findByStatus(status);
                request.setAttribute("selectedStatus", status);
            } catch (IllegalArgumentException e) {
                bookings = bookingDAO.findAll();
            }
        } else if (hotelIdParam != null && !hotelIdParam.trim().isEmpty()) {
            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                bookings = bookingDAO.findByHotelId(hotelId);
                request.setAttribute("selectedHotelId", hotelId);
            } catch (NumberFormatException e) {
                bookings = bookingDAO.findAll();
            }
        } else {
            bookings = bookingDAO.findAll();
        }
        
        // Load additional information for each booking
        for (Booking booking : bookings) {
            User user = userDAO.findById(booking.getUserId());
            booking.setUser(user);
            
            Room room = roomDAO.findById(booking.getRoomId());
            booking.setRoom(room);
            
            if (room != null) {
                Hotel hotel = hotelDAO.findById(room.getHotelId());
                room.setHotel(hotel);
                booking.setHotel(hotel);
            }
        }
        
        // Get all hotels for filter dropdown
        List<Hotel> hotels = hotelDAO.findAll();
        
        // Set attributes
        request.setAttribute("bookings", bookings);
        request.setAttribute("hotels", hotels);
        request.setAttribute("statuses", Booking.Status.values());
        
        // Forward to the manage bookings page
        request.getRequestDispatcher("/admin/manage-bookings.jsp").forward(request, response);
    }
}
