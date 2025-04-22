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
import java.util.logging.Logger;

/**
 * Servlet for managing rooms (admin)
 */
@WebServlet(name = "ManageRoomsServlet", urlPatterns = {"/admin/manage-rooms"})
public class ManageRoomsServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ManageRoomsServlet.class.getName());
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
    
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
        String hotelIdParam = request.getParameter("hotelId");
        String statusParam = request.getParameter("status");
        String roomTypeIdParam = request.getParameter("roomTypeId");
        
        // Get all hotels for the filter dropdown
        List<Hotel> hotels = hotelDAO.findAll();
        request.setAttribute("hotels", hotels);
        
        // Get all room types for the filter dropdown
        List<RoomType> roomTypes = roomTypeDAO.findAll();
        request.setAttribute("roomTypes", roomTypes);
        
        // Apply filters
        List<Room> rooms;
        
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                
                if (statusParam != null && !statusParam.isEmpty() && roomTypeIdParam != null && !roomTypeIdParam.isEmpty()) {
                    // Filter by hotel, status, and room type
                    int roomTypeId = Integer.parseInt(roomTypeIdParam);
                    Room.Status status = Room.Status.valueOf(statusParam);
                    rooms = roomDAO.findByHotelStatusAndRoomType(hotelId, status, roomTypeId);
                } else if (statusParam != null && !statusParam.isEmpty()) {
                    // Filter by hotel and status
                    Room.Status status = Room.Status.valueOf(statusParam);
                    rooms = roomDAO.findByHotelAndStatus(hotelId, status);
                } else if (roomTypeIdParam != null && !roomTypeIdParam.isEmpty()) {
                    // Filter by hotel and room type
                    int roomTypeId = Integer.parseInt(roomTypeIdParam);
                    rooms = roomDAO.findByHotelAndRoomType(hotelId, roomTypeId);
                } else {
                    // Filter by hotel only
                    rooms = roomDAO.findByHotelId(hotelId);
                }
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Invalid filter parameters: " + e.getMessage());
                rooms = roomDAO.findAll();
            }
        } else if (statusParam != null && !statusParam.isEmpty()) {
            try {
                // Filter by status only
                Room.Status status = Room.Status.valueOf(statusParam);
                rooms = roomDAO.findByStatus(status);
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Invalid status parameter: " + e.getMessage());
                rooms = roomDAO.findAll();
            }
        } else if (roomTypeIdParam != null && !roomTypeIdParam.isEmpty()) {
            try {
                // Filter by room type only
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                rooms = roomDAO.findByRoomType(roomTypeId);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid room type parameter: " + e.getMessage());
                rooms = roomDAO.findAll();
            }
        } else {
            // No filters, get all rooms
            rooms = roomDAO.findAll();
        }
        
        // Load hotel and room type for each room
        for (Room room : rooms) {
            Hotel hotel = hotelDAO.findById(room.getHotelId());
            room.setHotel(hotel);
            
            RoomType roomType = roomTypeDAO.findById(room.getRoomTypeId());
            room.setRoomType(roomType);
        }
        
        // Set attributes
        request.setAttribute("rooms", rooms);
        
        // Forward to the manage rooms page
        request.getRequestDispatcher("/admin/manage-rooms.jsp").forward(request, response);
    }
}
