package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.model.RoomType;
import com.example.hms.util.SessionUtil;
import com.example.hms.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet for managing room types (admin)
 */
@WebServlet(name = "RoomTypeServlet", urlPatterns = {"/admin/room-types", "/admin/add-room-type", "/admin/edit-room-type", "/admin/delete-room-type"})
public class RoomTypeServlet extends HttpServlet {
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
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
        
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/room-types")) {
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
                
                // Set attributes
                request.setAttribute("hotel", hotel);
                request.setAttribute("roomTypes", roomTypes);
                
                // Forward to the room types page
                request.getRequestDispatcher("/admin/room-types.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
            }
        } else if (servletPath.equals("/admin/add-room-type")) {
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
                
                // Set hotel as request attribute
                request.setAttribute("hotel", hotel);
                
                // Forward to the add room type page
                request.getRequestDispatcher("/admin/add-room-type.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
            }
        } else if (servletPath.equals("/admin/edit-room-type")) {
            // Get room type ID from request parameter
            String roomTypeIdParam = request.getParameter("id");
            
            if (roomTypeIdParam == null || roomTypeIdParam.trim().isEmpty()) {
                // If no room type ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No room type specified");
                return;
            }
            
            try {
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                
                // Find room type by ID
                RoomType roomType = roomTypeDAO.findById(roomTypeId);
                
                if (roomType == null) {
                    // If room type not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Room type not found");
                    return;
                }
                
                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(roomType.getHotelId());
                
                // Set attributes
                request.setAttribute("roomType", roomType);
                request.setAttribute("hotel", hotel);
                
                // Forward to the edit room type page
                request.getRequestDispatcher("/admin/edit-room-type.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // If room type ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room type ID");
            }
        } else if (servletPath.equals("/admin/delete-room-type")) {
            // Get room type ID from request parameter
            String roomTypeIdParam = request.getParameter("id");
            String hotelIdParam = request.getParameter("hotelId");
            
            if (roomTypeIdParam == null || roomTypeIdParam.trim().isEmpty()) {
                // If no room type ID is provided, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No room type specified");
                return;
            }
            
            try {
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                int hotelId = Integer.parseInt(hotelIdParam);
                
                // Delete room type
                boolean deleted = roomTypeDAO.deleteById(roomTypeId);
                
                if (deleted) {
                    // Redirect to room types with success message
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&success=Room type deleted successfully");
                } else {
                    // Redirect to room types with error message
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&error=Failed to delete room type");
                }
            } catch (NumberFormatException e) {
                // If room type ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room type ID");
            }
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
        
        if (servletPath.equals("/admin/add-room-type")) {
            // Get form parameters
            String hotelIdParam = request.getParameter("hotelId");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String capacityParam = request.getParameter("capacity");
            String pricePerNightParam = request.getParameter("pricePerNight");
            
            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                
                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(hotelId);
                
                if (hotel == null) {
                    // If hotel not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                    return;
                }
                
                // Validate input
                Map<String, String> errors = validateInput(name, capacityParam, pricePerNightParam);
                
                if (!errors.isEmpty()) {
                    // If there are validation errors, set them as request attributes and forward back to the form
                    request.setAttribute("errors", errors);
                    request.setAttribute("roomType", createRoomTypeFromParams(0, hotelId, name, description, capacityParam, pricePerNightParam));
                    request.setAttribute("hotel", hotel);
                    request.getRequestDispatcher("/admin/add-room-type.jsp").forward(request, response);
                    return;
                }
                
                // Create room type object
                RoomType roomType = createRoomTypeFromParams(0, hotelId, name, description, capacityParam, pricePerNightParam);
                
                // Save room type
                RoomType savedRoomType = roomTypeDAO.save(roomType);
                
                if (savedRoomType != null) {
                    // Redirect to room types with success message
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&success=Room type added successfully");
                } else {
                    // Set error message and forward back to the form
                    request.setAttribute("error", "Failed to add room type. Please try again.");
                    request.setAttribute("roomType", roomType);
                    request.setAttribute("hotel", hotel);
                    request.getRequestDispatcher("/admin/add-room-type.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // If hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
            }
        } else if (servletPath.equals("/admin/edit-room-type")) {
            // Get form parameters
            String roomTypeIdParam = request.getParameter("roomTypeId");
            String hotelIdParam = request.getParameter("hotelId");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String capacityParam = request.getParameter("capacity");
            String pricePerNightParam = request.getParameter("pricePerNight");
            
            try {
                int roomTypeId = Integer.parseInt(roomTypeIdParam);
                int hotelId = Integer.parseInt(hotelIdParam);
                
                // Find room type by ID
                RoomType roomType = roomTypeDAO.findById(roomTypeId);
                
                if (roomType == null) {
                    // If room type not found, redirect to room types
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&error=Room type not found");
                    return;
                }
                
                // Find hotel by ID
                Hotel hotel = hotelDAO.findById(hotelId);
                
                if (hotel == null) {
                    // If hotel not found, redirect to manage hotels
                    response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                    return;
                }
                
                // Validate input
                Map<String, String> errors = validateInput(name, capacityParam, pricePerNightParam);
                
                if (!errors.isEmpty()) {
                    // If there are validation errors, set them as request attributes and forward back to the form
                    request.setAttribute("errors", errors);
                    request.setAttribute("roomType", createRoomTypeFromParams(roomTypeId, hotelId, name, description, capacityParam, pricePerNightParam));
                    request.setAttribute("hotel", hotel);
                    request.getRequestDispatcher("/admin/edit-room-type.jsp").forward(request, response);
                    return;
                }
                
                // Update room type object
                roomType.setName(name);
                roomType.setDescription(description);
                
                try {
                    int capacity = Integer.parseInt(capacityParam);
                    roomType.setCapacity(capacity);
                } catch (NumberFormatException e) {
                    roomType.setCapacity(0);
                }
                
                try {
                    BigDecimal pricePerNight = new BigDecimal(pricePerNightParam);
                    roomType.setPricePerNight(pricePerNight);
                } catch (NumberFormatException e) {
                    roomType.setPricePerNight(BigDecimal.ZERO);
                }
                
                // Update room type
                RoomType updatedRoomType = roomTypeDAO.update(roomType);
                
                if (updatedRoomType != null) {
                    // Redirect to room types with success message
                    response.sendRedirect(request.getContextPath() + "/admin/room-types?hotelId=" + hotelId + "&success=Room type updated successfully");
                } else {
                    // Set error message and forward back to the form
                    request.setAttribute("error", "Failed to update room type. Please try again.");
                    request.setAttribute("roomType", roomType);
                    request.setAttribute("hotel", hotel);
                    request.getRequestDispatcher("/admin/edit-room-type.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // If room type ID or hotel ID is not a valid number, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid room type or hotel ID");
            }
        }
    }
    
    /**
     * Create a RoomType object from request parameters
     */
    private RoomType createRoomTypeFromParams(int roomTypeId, int hotelId, String name, String description, 
                                            String capacityParam, String pricePerNightParam) {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(roomTypeId);
        roomType.setHotelId(hotelId);
        roomType.setName(name);
        roomType.setDescription(description);
        
        try {
            int capacity = Integer.parseInt(capacityParam);
            roomType.setCapacity(capacity);
        } catch (NumberFormatException e) {
            roomType.setCapacity(0);
        }
        
        try {
            BigDecimal pricePerNight = new BigDecimal(pricePerNightParam);
            roomType.setPricePerNight(pricePerNight);
        } catch (NumberFormatException e) {
            roomType.setPricePerNight(BigDecimal.ZERO);
        }
        
        return roomType;
    }
    
    /**
     * Validate room type input
     */
    private Map<String, String> validateInput(String name, String capacityParam, String pricePerNightParam) {
        Map<String, String> errors = new HashMap<>();
        
        // Validate name
        if (!ValidationUtil.isNotEmpty(name)) {
            errors.put("name", "Room type name is required");
        }
        
        // Validate capacity
        try {
            int capacity = Integer.parseInt(capacityParam);
            if (!ValidationUtil.isPositive(capacity)) {
                errors.put("capacity", "Capacity must be a positive number");
            }
        } catch (NumberFormatException e) {
            errors.put("capacity", "Capacity must be a valid number");
        }
        
        // Validate price per night
        try {
            BigDecimal pricePerNight = new BigDecimal(pricePerNightParam);
            if (!ValidationUtil.isPositive(pricePerNight)) {
                errors.put("pricePerNight", "Price per night must be a positive number");
            }
        } catch (NumberFormatException e) {
            errors.put("pricePerNight", "Price per night must be a valid number");
        }
        
        return errors;
    }
}
