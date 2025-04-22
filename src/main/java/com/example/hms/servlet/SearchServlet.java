package com.example.hms.servlet;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.model.Hotel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for searching hotels
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get search parameters
        String keyword = request.getParameter("keyword");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String starRatingParam = request.getParameter("starRating");
        
        List<Hotel> hotels;
        
        // Search based on parameters
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search by keyword
            hotels = hotelDAO.searchHotels(keyword);
            request.setAttribute("keyword", keyword);
        } else if (city != null && !city.trim().isEmpty()) {
            // Search by city
            hotels = hotelDAO.findByCity(city);
            request.setAttribute("city", city);
        } else if (country != null && !country.trim().isEmpty()) {
            // Search by country
            hotels = hotelDAO.findByCountry(country);
            request.setAttribute("country", country);
        } else if (starRatingParam != null && !starRatingParam.trim().isEmpty()) {
            try {
                // Search by star rating
                int starRating = Integer.parseInt(starRatingParam);
                hotels = hotelDAO.findByStarRating(starRating);
                request.setAttribute("starRating", starRating);
            } catch (NumberFormatException e) {
                // If star rating is not a valid number, get all hotels
                hotels = hotelDAO.findAll();
            }
        } else {
            // If no search parameters, get all hotels
            hotels = hotelDAO.findAll();
        }
        
        // Set hotels as request attribute
        request.setAttribute("hotels", hotels);
        
        // Forward to the search results page
        request.getRequestDispatcher("/search-results.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Call doGet to handle POST requests the same way as GET requests
        doGet(request, response);
    }
}
