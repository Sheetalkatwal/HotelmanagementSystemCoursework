package com.example.hms.servlet;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomTypeDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomTypeDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.model.RoomType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for viewing hotel details
 */
@WebServlet(name = "HotelServlet", urlPatterns = {"/hotel"})
public class HotelServlet extends HttpServlet {
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get hotel ID from request parameter
        String hotelIdParam = request.getParameter("id");
        
        if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
            // If no hotel ID is provided, redirect to the search page
            response.sendRedirect(request.getContextPath() + "/search");
            return;
        }
        
        try {
            int hotelId = Integer.parseInt(hotelIdParam);
            
            // Find hotel by ID
            Hotel hotel = hotelDAO.findById(hotelId);
            
            if (hotel == null) {
                // If hotel not found, redirect to the search page with an error message
                response.sendRedirect(request.getContextPath() + "/search?error=Hotel not found");
                return;
            }
            
            // Find room types for the hotel
            List<RoomType> roomTypes = roomTypeDAO.findByHotelId(hotelId);
            
            // Set hotel and room types as request attributes
            request.setAttribute("hotel", hotel);
            request.setAttribute("roomTypes", roomTypes);
            
            // Forward to the hotel details page
            request.getRequestDispatcher("/hotel-details.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // If hotel ID is not a valid number, redirect to the search page with an error message
            response.sendRedirect(request.getContextPath() + "/search?error=Invalid hotel ID");
        }
    }
}
