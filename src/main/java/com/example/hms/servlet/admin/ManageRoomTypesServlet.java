package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.model.RoomType;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet for managing room types (admin)
 */
@WebServlet(name = "ManageRoomTypesServlet", urlPatterns = {"/admin/manage-room-types"})
public class ManageRoomTypesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ManageRoomTypesServlet.class.getName());
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
        
        // Get filter parameters
        String hotelIdParam = request.getParameter("hotelId");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");
        
        // Get all hotels for the filter dropdown
        List<Hotel> hotels = hotelDAO.findAll();
        request.setAttribute("hotels", hotels);
        
        // Apply filters
        List<RoomType> roomTypes;
        
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                
                if (minPriceParam != null && !minPriceParam.isEmpty() && maxPriceParam != null && !maxPriceParam.isEmpty()) {
                    // Filter by hotel and price range
                    BigDecimal minPrice = new BigDecimal(minPriceParam);
                    BigDecimal maxPrice = new BigDecimal(maxPriceParam);
                    roomTypes = roomTypeDAO.findByHotelAndPriceRange(hotelId, minPrice, maxPrice);
                } else if (minPriceParam != null && !minPriceParam.isEmpty()) {
                    // Filter by hotel and min price
                    BigDecimal minPrice = new BigDecimal(minPriceParam);
                    roomTypes = roomTypeDAO.findByHotelAndMinPrice(hotelId, minPrice);
                } else if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
                    // Filter by hotel and max price
                    BigDecimal maxPrice = new BigDecimal(maxPriceParam);
                    roomTypes = roomTypeDAO.findByHotelAndMaxPrice(hotelId, maxPrice);
                } else {
                    // Filter by hotel only
                    roomTypes = roomTypeDAO.findByHotelId(hotelId);
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid filter parameters: " + e.getMessage());
                roomTypes = roomTypeDAO.findAll();
            }
        } else if (minPriceParam != null && !minPriceParam.isEmpty() && maxPriceParam != null && !maxPriceParam.isEmpty()) {
            try {
                // Filter by price range only
                BigDecimal minPrice = new BigDecimal(minPriceParam);
                BigDecimal maxPrice = new BigDecimal(maxPriceParam);
                roomTypes = roomTypeDAO.findByPriceRange(minPrice, maxPrice);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid price parameters: " + e.getMessage());
                roomTypes = roomTypeDAO.findAll();
            }
        } else if (minPriceParam != null && !minPriceParam.isEmpty()) {
            try {
                // Filter by min price only
                BigDecimal minPrice = new BigDecimal(minPriceParam);
                roomTypes = roomTypeDAO.findByMinPrice(minPrice);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid min price parameter: " + e.getMessage());
                roomTypes = roomTypeDAO.findAll();
            }
        } else if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
            try {
                // Filter by max price only
                BigDecimal maxPrice = new BigDecimal(maxPriceParam);
                roomTypes = roomTypeDAO.findByMaxPrice(maxPrice);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid max price parameter: " + e.getMessage());
                roomTypes = roomTypeDAO.findAll();
            }
        } else {
            // No filters, get all room types
            roomTypes = roomTypeDAO.findAll();
        }
        
        // Load hotel for each room type
        for (RoomType roomType : roomTypes) {
            Hotel hotel = hotelDAO.findById(roomType.getHotelId());
            roomType.setHotel(hotel);
        }
        
        // Set attributes
        request.setAttribute("roomTypes", roomTypes);
        
        // Forward to the manage room types page
        request.getRequestDispatcher("/admin/manage-room-types.jsp").forward(request, response);
    }
}
