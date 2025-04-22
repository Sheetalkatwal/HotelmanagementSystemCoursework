package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.model.Room;
import com.example.hms.model.RoomType;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for viewing hotel details (admin)
 */
@WebServlet(name = "AdminViewHotelServlet", urlPatterns = {"/admin/view-hotel"})
public class ViewHotelServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ViewHotelServlet.class.getName());
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }

        // Get hotel ID from request parameter
        String hotelIdParam = request.getParameter("id");

        if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
            // If no hotel ID is provided, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No hotel specified");
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdParam);

            // Find hotel by ID
            Hotel hotel = hotelDAO.findById(hotelId);

            if (hotel == null) {
                // If hotel not found, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                return;
            }

            // Find room types for the hotel
            List<RoomType> roomTypes = roomTypeDAO.findByHotelId(hotelId);

            // Find rooms for the hotel
            List<Room> rooms = roomDAO.findByHotelId(hotelId);

            // Load room types for each room
            for (Room room : rooms) {
                try {
                    RoomType roomType = roomTypeDAO.findById(room.getRoomTypeId());
                    if (roomType != null) {
                        room.setRoomType(roomType);
                    } else {
                        // Log warning if room type not found
                        LOGGER.warning("Room type not found for room ID " + room.getRoomId() + ", type ID " + room.getRoomTypeId());
                    }
                } catch (Exception e) {
                    // Log error and continue with next room
                    LOGGER.log(Level.SEVERE, "Error loading room type for room ID " + room.getRoomId(), e);
                }
            }

            // Set attributes
            request.setAttribute("hotel", hotel);
            request.setAttribute("roomTypes", roomTypes);
            request.setAttribute("rooms", rooms);

            // Forward to the admin view hotel page
            request.getRequestDispatcher("/admin/view-hotel.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // If hotel ID is not a valid number, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
        }
        } catch (Exception e) {
            // Log the exception
            LOGGER.log(Level.SEVERE, "Unexpected error in ViewHotelServlet.doGet", e);
            // Set error attribute
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.setAttribute("errorDetails", e);
            // Forward to error page
            request.getRequestDispatcher("/error/error-details.jsp").forward(request, response);
        }
    }
}
