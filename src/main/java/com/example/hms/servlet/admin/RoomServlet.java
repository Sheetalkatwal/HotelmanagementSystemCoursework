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
import com.example.hms.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for managing rooms (admin)
 */
@WebServlet(name = "RoomServlet", urlPatterns = {"/admin/rooms", "/admin/add-room", "/admin/edit-room", "/admin/delete-room"})
public class RoomServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RoomServlet.class.getName());
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();

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

        String servletPath = request.getServletPath();

        if (servletPath.equals("/admin/rooms")) {
            // Get hotel ID from request parameter
            String hotelIdParam = request.getParameter("hotelId");

            if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
                // If no hotel ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Please select a hotel first");
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

                // Find room types for the hotel (for the add room form)
                List<RoomType> roomTypes = roomTypeDAO.findByHotelId(hotelId);

                // Set attributes
                request.setAttribute("hotel", hotel);
                request.setAttribute("rooms", rooms);
                request.setAttribute("roomTypes", roomTypes);

                // Forward to the rooms page
                request.getRequestDispatcher("/admin/rooms.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
            }
        } else if (servletPath.equals("/admin/add-room")) {
            // Get hotel ID from request parameter
            String hotelIdParam = request.getParameter("hotelId");

            if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
                // If no hotel ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Please select a hotel first");
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

                if (roomTypes.isEmpty()) {
                    // If no room types found, redirect to room types
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&error=Please add room types first");
                    return;
                }

                // Set attributes
                request.setAttribute("hotel", hotel);
                request.setAttribute("roomTypes", roomTypes);

                // Forward to the add room page
                request.getRequestDispatcher("/admin/add-room.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
            }
        } else if (servletPath.equals("/admin/edit-room")) {
            // Get room ID from request parameter
            String roomIdParam = request.getParameter("id");

            if (roomIdParam == null || roomIdParam.trim().isEmpty()) {
                // If no room ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No room specified");
                return;
            }

            try {
                int roomId = Integer.parseInt(roomIdParam);

                // Find room by ID
                Room room = roomDAO.findById(roomId);

                if (room == null) {
                    // If room not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Room not found");
                    return;
                }

                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(room.getHotelId());

                // Find room types for the hotel
                List<RoomType> roomTypes = roomTypeDAO.findByHotelId(room.getHotelId());

                // Set attributes
                request.setAttribute("room", room);
                request.setAttribute("hotel", hotel);
                request.setAttribute("roomTypes", roomTypes);

                // Forward to the edit room page
                request.getRequestDispatcher("/admin/edit-room.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If room ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room ID");
            }
        } else if (servletPath.equals("/admin/delete-room")) {
            // Get room ID from request parameter
            String roomIdParam = request.getParameter("id");
            String hotelIdParam = request.getParameter("hotelId");

            if (roomIdParam == null || roomIdParam.trim().isEmpty()) {
                // If no room ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No room specified");
                return;
            }

            try {
                int roomId = Integer.parseInt(roomIdParam);
                int hotelId = Integer.parseInt(hotelIdParam);

                // Delete room
                boolean deleted = roomDAO.deleteById(roomId);

                if (deleted) {
                    // Redirect to rooms with success message
                    response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + hotelId + "&success=Room deleted successfully");
                } else {
                    // Redirect to rooms with error message
                    response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + hotelId + "&error=Failed to delete room");
                }
            } catch (NumberFormatException e) {
                // If room ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room ID");
            }
        }
        } catch (Exception e) {
            // Log the exception
            LOGGER.log(Level.SEVERE, "Unexpected error in RoomServlet.doGet", e);
            // Set error attribute
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.setAttribute("errorDetails", e);
            // Forward to error page
            request.getRequestDispatcher("/error/error-details.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }

        String servletPath = request.getServletPath();

        if (servletPath.equals("/admin/add-room")) {
            // Get form parameters
            String hotelIdParam = request.getParameter("hotelId");
            String roomTypeIdParam = request.getParameter("roomTypeId");
            String roomNumber = request.getParameter("roomNumber");
            String floor = request.getParameter("floor");
            String statusParam = request.getParameter("status");

            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                Room.Status status = Room.Status.valueOf(statusParam);

                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(hotelId);

                if (hotel == null) {
                    // If hotel not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                    return;
                }

                // Find room type by ID
                RoomType roomType = roomTypeDAO.findById(roomTypeId);

                if (roomType == null) {
                    // If room type not found, redirect to room types
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&error=Room type not found");
                    return;
                }

                // Validate input
                Map<String, String> errors = validateInput(roomNumber);

                // Check if room number already exists for this hotel
                List<Room> existingRooms = roomDAO.findByHotelId(hotelId);
                for (Room existingRoom : existingRooms) {
                    if (existingRoom.getRoomNumber().equals(roomNumber)) {
                        errors.put("roomNumber", "Room number already exists for this hotel");
                        break;
                    }
                }

                if (!errors.isEmpty()) {
                    // If there are validation errors, set them as request attributes and forward back to the form
                    request.setAttribute("errors", errors);
                    request.setAttribute("room", createRoomFromParams(0, hotelId, roomTypeId, roomNumber, floor, statusParam));
                    request.setAttribute("hotel", hotel);
                    request.setAttribute("roomTypes", roomTypeDAO.findByHotelId(hotelId));
                    request.getRequestDispatcher("/admin/add-room.jsp").forward(request, response);
                    return;
                }

                // Create room object
                Room room = new Room();
                room.setHotelId(hotelId);
                room.setRoomTypeId(roomTypeId);
                room.setRoomNumber(roomNumber);
                room.setFloor(floor);
                room.setStatus(status);

                // Save room
                Room savedRoom = roomDAO.save(room);

                if (savedRoom != null) {
                    // Redirect to rooms with success message
                    response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + hotelId + "&success=Room added successfully");
                } else {
                    // Set error message and forward back to the form
                    request.setAttribute("error", "Failed to add room. Please try again.");
                    request.setAttribute("room", room);
                    request.setAttribute("hotel", hotel);
                    request.setAttribute("roomTypes", roomTypeDAO.findByHotelId(hotelId));
                    request.getRequestDispatcher("/admin/add-room.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // If hotel ID or room type ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel or room type ID");
            } catch (IllegalArgumentException e) {
                // If status is not valid, redirect to rooms
                response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + request.getParameter("hotelId") + "&error=Invalid room status");
            }
        } else if (servletPath.equals("/admin/edit-room")) {
            // Get form parameters
            String roomIdParam = request.getParameter("roomId");
            String hotelIdParam = request.getParameter("hotelId");
            String roomTypeIdParam = request.getParameter("roomTypeId");
            String roomNumber = request.getParameter("roomNumber");
            String floor = request.getParameter("floor");
            String statusParam = request.getParameter("status");

            try {
                int roomId = Integer.parseInt(roomIdParam);
                int hotelId = Integer.parseInt(hotelIdParam);
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                Room.Status status = Room.Status.valueOf(statusParam);

                // Find room by ID
                Room room = roomDAO.findById(roomId);

                if (room == null) {
                    // If room not found, redirect to rooms
                    response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + hotelId + "&error=Room not found");
                    return;
                }

                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(hotelId);

                if (hotel == null) {
                    // If hotel not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                    return;
                }

                // Find room type by ID
                RoomType roomType = roomTypeDAO.findById(roomTypeId);

                if (roomType == null) {
                    // If room type not found, redirect to room types
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&error=Room type not found");
                    return;
                }

                // Validate input
                Map<String, String> errors = validateInput(roomNumber);

                // Check if room number already exists for this hotel (excluding the current room)
                List<Room> existingRooms = roomDAO.findByHotelId(hotelId);
                for (Room existingRoom : existingRooms) {
                    if (existingRoom.getRoomNumber().equals(roomNumber) && existingRoom.getRoomId() != roomId) {
                        errors.put("roomNumber", "Room number already exists for this hotel");
                        break;
                    }
                }

                if (!errors.isEmpty()) {
                    // If there are validation errors, set them as request attributes and forward back to the form
                    request.setAttribute("errors", errors);
                    request.setAttribute("room", createRoomFromParams(roomId, hotelId, roomTypeId, roomNumber, floor, statusParam));
                    request.setAttribute("hotel", hotel);
                    request.setAttribute("roomTypes", roomTypeDAO.findByHotelId(hotelId));
                    request.getRequestDispatcher("/admin/edit-room.jsp").forward(request, response);
                    return;
                }

                // Update room object
                room.setRoomTypeId(roomTypeId);
                room.setRoomNumber(roomNumber);
                room.setFloor(floor);
                room.setStatus(status);

                // Update room
                Room updatedRoom = roomDAO.update(room);

                if (updatedRoom != null) {
                    // Redirect to rooms with success message
                    response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + hotelId + "&success=Room updated successfully");
                } else {
                    // Set error message and forward back to the form
                    request.setAttribute("error", "Failed to update room. Please try again.");
                    request.setAttribute("room", room);
                    request.setAttribute("hotel", hotel);
                    request.setAttribute("roomTypes", roomTypeDAO.findByHotelId(hotelId));
                    request.getRequestDispatcher("/admin/edit-room.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // If room ID, hotel ID, or room type ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room, hotel, or room type ID");
            } catch (IllegalArgumentException e) {
                // If status is not valid, redirect to rooms
                response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + request.getParameter("hotelId") + "&error=Invalid room status");
            }
        }
    }

    /**
     * Create a Room object from request parameters
     */
    private Room createRoomFromParams(int roomId, int hotelId, int roomTypeId, String roomNumber, String floor, String statusParam) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setHotelId(hotelId);
        room.setRoomTypeId(roomTypeId);
        room.setRoomNumber(roomNumber);
        room.setFloor(floor);

        try {
            Room.Status status = Room.Status.valueOf(statusParam);
            room.setStatus(status);
        } catch (IllegalArgumentException e) {
            room.setStatus(Room.Status.AVAILABLE);
        }

        return room;
    }

    /**
     * Validate room input
     */
    private Map<String, String> validateInput(String roomNumber) {
        Map<String, String> errors = new HashMap<>();

        // Validate room number
        if (!ValidationUtil.isNotEmpty(roomNumber)) {
            errors.put("roomNumber", "Room number is required");
        }

        return errors;
    }
}
