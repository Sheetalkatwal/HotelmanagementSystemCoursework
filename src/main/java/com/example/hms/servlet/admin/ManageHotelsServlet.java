package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for managing hotels (admin)
 */
@WebServlet(name = "ManageHotelsServlet", urlPatterns = {"/admin/manage-hotels"})
public class ManageHotelsServlet extends HttpServlet {
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

        // Get search and filter parameters
        String keyword = request.getParameter("keyword");
        String starRatingParam = request.getParameter("starRating");

        List<Hotel> hotels;

        // Apply filters based on parameters
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search by keyword (name, city, country)
            hotels = hotelDAO.searchHotels(keyword);
        } else if (starRatingParam != null && !starRatingParam.trim().isEmpty()) {
            try {
                // Filter by star rating
                int starRating = Integer.parseInt(starRatingParam);
                hotels = hotelDAO.findByStarRating(starRating);
            } catch (NumberFormatException e) {
                // If star rating is not a valid number, get all hotels
                hotels = hotelDAO.findAll();
            }
        } else {
            // If no filters, get all hotels
            hotels = hotelDAO.findAll();
        }

        // Set hotels as request attribute
        request.setAttribute("hotels", hotels);

        // Forward to the manage hotels page
        request.getRequestDispatcher("/admin/manage-hotels.jsp").forward(request, response);
    }
}
