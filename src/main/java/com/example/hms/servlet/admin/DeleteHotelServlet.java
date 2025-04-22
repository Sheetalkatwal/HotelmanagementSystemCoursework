package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for deleting a hotel (admin)
 */
@WebServlet(name = "DeleteHotelServlet", urlPatterns = {"/admin/delete-hotel"})
public class DeleteHotelServlet extends HttpServlet {
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
        
        // Get hotel ID from request parameter
        String hotelIdParam = request.getParameter("id");
        
        if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
            // If no hotel ID is provided, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=No hotel specified");
            return;
        }
        
        try {
            int hotelId = Integer.parseInt(hotelIdParam);
            
            // Delete hotel
            boolean deleted = hotelDAO.deleteById(hotelId);
            
            if (deleted) {
                // Redirect to manage hotels with success message
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?success=Hotel deleted successfully");
            } else {
                // Redirect to manage hotels with error message
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Failed to delete hotel");
            }
        } catch (NumberFormatException e) {
            // If hotel ID is not a valid number, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Call doGet to handle POST requests the same way as GET requests
        doGet(request, response);
    }
}
