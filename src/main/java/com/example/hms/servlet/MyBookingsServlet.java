package com.example.hms.servlet;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.Hotel;
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
import java.util.List;

/**
 * Servlet for viewing user's bookings
 */
@WebServlet(name = "MyBookingsServlet", urlPatterns = {"/my-bookings"})
public class MyBookingsServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
    private final HotelDAO hotelDAO = new HotelDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to view your bookings");
            return;
        }

        // Check if user is an admin (admins should use the admin booking management page)
        if (SessionUtil.isAdmin(request)) {
            // Redirect admins to the admin booking management page
            response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?error=Please use the admin booking management page");
            return;
        }

        // Get user's bookings
        List<Booking> bookings = bookingDAO.findByUserId(user.getUserId());

        // Load additional information for each booking
        for (Booking booking : bookings) {
            Room room = roomDAO.findById(booking.getRoomId());
            booking.setRoom(room);

            if (room != null) {
                RoomType roomType = roomTypeDAO.findById(room.getRoomTypeId());
                room.setRoomType(roomType);

                Hotel hotel = hotelDAO.findById(room.getHotelId());
                room.setHotel(hotel);
                booking.setHotel(hotel);
            }
        }

        // Set bookings as request attribute
        request.setAttribute("bookings", bookings);

        // Forward to the my bookings page
        request.getRequestDispatcher("/my-bookings.jsp").forward(request, response);
    }
}
