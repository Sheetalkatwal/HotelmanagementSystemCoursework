package com.example.hms.servlet.admin;

import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.model.Hotel;
import com.example.hms.util.SessionUtil;
import com.example.hms.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Servlet for editing a hotel (admin)
 */
@WebServlet(name = "EditHotelServlet", urlPatterns = {"/admin/edit-hotel"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class EditHotelServlet extends HttpServlet {
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

            // Find hotel by ID
            Hotel hotel = hotelDAO.findById(hotelId);

            if (hotel == null) {
                // If hotel not found, redirect to manage hotels
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Hotel not found");
                return;
            }

            // Set hotel as request attribute
            request.setAttribute("hotel", hotel);

            // Forward to the edit hotel page
            request.getRequestDispatcher("/admin/edit-hotel.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // If hotel ID is not a valid number, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
        }
    }

    // Directory to store uploaded images
    private static final String UPLOAD_DIRECTORY = "hotel-images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }

        // Get form parameters
        String hotelIdParam = request.getParameter("hotelId");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        String postalCode = request.getParameter("postalCode");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String starRatingParam = request.getParameter("starRating");
        String description = request.getParameter("description");
        String currentImageUrl = request.getParameter("currentImageUrl");

        // Process image upload
        String imageUrl = currentImageUrl; // Default to current image URL
        try {
            Part filePart = request.getPart("hotelImage");
            if (filePart != null && filePart.getSize() > 0) {
                imageUrl = processImageUpload(request, filePart);
            }
        } catch (Exception e) {
            // Log the error
            getServletContext().log("Error uploading image", e);
        }

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

            // Validate input
            Map<String, String> errors = validateInput(name, address, city, country, starRatingParam, email, phone);

            if (!errors.isEmpty()) {
                // If there are validation errors, set them as request attributes and forward back to the form
                request.setAttribute("errors", errors);
                request.setAttribute("hotel", createHotelFromParams(hotelId, name, address, city, state, country,
                                                                 postalCode, phone, email, starRatingParam,
                                                                 description, imageUrl));
                request.getRequestDispatcher("/admin/edit-hotel.jsp").forward(request, response);
                return;
            }

            // Update hotel object
            hotel.setName(name);
            hotel.setAddress(address);
            hotel.setCity(city);
            hotel.setState(state);
            hotel.setCountry(country);
            hotel.setPostalCode(postalCode);
            hotel.setPhone(phone);
            hotel.setEmail(email);

            try {
                int starRating = Integer.parseInt(starRatingParam);
                hotel.setStarRating(starRating);
            } catch (NumberFormatException e) {
                hotel.setStarRating(0);
            }

            hotel.setDescription(description);
            hotel.setImageUrl(imageUrl);

            // Update hotel
            Hotel updatedHotel = hotelDAO.update(hotel);

            if (updatedHotel != null) {
                // Redirect to manage hotels with success message
                response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?success=Hotel updated successfully");
            } else {
                // Set error message and forward back to the form
                request.setAttribute("error", "Failed to update hotel. Please try again.");
                request.setAttribute("hotel", hotel);
                request.getRequestDispatcher("/admin/edit-hotel.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // If hotel ID is not a valid number, redirect to manage hotels
            response.sendRedirect(request.getContextPath() + "/admin/manage-hotels?error=Invalid hotel ID");
        }
    }

    /**
     * Create a Hotel object from request parameters
     */
    private Hotel createHotelFromParams(int hotelId, String name, String address, String city, String state,
                                      String country, String postalCode, String phone, String email,
                                      String starRatingParam, String description, String imageUrl) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setCity(city);
        hotel.setState(state);
        hotel.setCountry(country);
        hotel.setPostalCode(postalCode);
        hotel.setPhone(phone);
        hotel.setEmail(email);

        try {
            int starRating = Integer.parseInt(starRatingParam);
            hotel.setStarRating(starRating);
        } catch (NumberFormatException e) {
            hotel.setStarRating(0);
        }

        hotel.setDescription(description);
        hotel.setImageUrl(imageUrl);

        return hotel;
    }

    /**
     * Process image upload and return the URL of the saved image
     */
    private String processImageUpload(HttpServletRequest request, Part filePart) throws IOException {
        // Create upload directory if it doesn't exist
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + "images" + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique filename
        String fileName = UUID.randomUUID().toString() + getFileExtension(filePart);
        String filePath = uploadPath + File.separator + fileName;

        // Save the file
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the URL to the image
        return request.getContextPath() + "/images/" + UPLOAD_DIRECTORY + "/" + fileName;
    }

    /**
     * Get file extension from Part
     */
    private String getFileExtension(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                String fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
                if (fileName.contains(".")) {
                    return fileName.substring(fileName.lastIndexOf("."));
                }
            }
        }
        return ".jpg"; // Default extension
    }

    /**
     * Validate hotel input
     */
    private Map<String, String> validateInput(String name, String address, String city, String country,
                                            String starRatingParam, String email, String phone) {
        Map<String, String> errors = new HashMap<>();

        // Validate required fields
        if (!ValidationUtil.isNotEmpty(name)) {
            errors.put("name", "Hotel name is required");
        }

        if (!ValidationUtil.isNotEmpty(address)) {
            errors.put("address", "Address is required");
        }

        if (!ValidationUtil.isNotEmpty(city)) {
            errors.put("city", "City is required");
        }

        if (!ValidationUtil.isNotEmpty(country)) {
            errors.put("country", "Country is required");
        }

        // Validate star rating
        try {
            int starRating = Integer.parseInt(starRatingParam);
            if (!ValidationUtil.isInRange(starRating, 1, 5)) {
                errors.put("starRating", "Star rating must be between 1 and 5");
            }
        } catch (NumberFormatException e) {
            errors.put("starRating", "Star rating must be a number");
        }

        // Validate email if provided
        if (email != null && !email.isEmpty() && !ValidationUtil.isValidEmail(email)) {
            errors.put("email", "Invalid email format");
        }

        // Validate phone if provided
        if (phone != null && !phone.isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            errors.put("phone", "Invalid phone number format");
        }

        return errors;
    }
}
