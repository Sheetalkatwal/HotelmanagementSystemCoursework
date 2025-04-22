package com.example.hms.servlet;

import com.example.hms.dao.UserDAO;
import com.example.hms.dao.impl.UserDAOImpl;
import com.example.hms.model.User;
import com.example.hms.util.PasswordUtil;
import com.example.hms.util.SessionUtil;
import com.example.hms.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for user profile management
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to view your profile");
            return;
        }
        
        // Forward to the profile page
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = SessionUtil.getUser(request);
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please log in to update your profile");
            return;
        }
        
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate input
        Map<String, String> errors = validateInput(fullName, email, phone, currentPassword, newPassword, confirmPassword, user);
        
        if (!errors.isEmpty()) {
            // If there are validation errors, set them as request attributes and forward back to the form
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }
        
        // Update user object
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        
        // Update password if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(PasswordUtil.hashPassword(newPassword));
        }
        
        // Update user in database
        User updatedUser = userDAO.update(user);
        
        if (updatedUser != null) {
            // Update user in session
            SessionUtil.setUser(request, updatedUser);
            
            // Redirect to profile with success message
            response.sendRedirect(request.getContextPath() + "/profile?success=Profile updated successfully");
        } else {
            // Set error message and forward back to the form
            request.setAttribute("error", "Failed to update profile. Please try again.");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
    }
    
    /**
     * Validate user input
     */
    private Map<String, String> validateInput(String fullName, String email, String phone, 
                                            String currentPassword, String newPassword, 
                                            String confirmPassword, User user) {
        Map<String, String> errors = new HashMap<>();
        
        // Validate full name
        if (!ValidationUtil.isNotEmpty(fullName)) {
            errors.put("fullName", "Full name is required");
        }
        
        // Validate email
        if (!ValidationUtil.isValidEmail(email)) {
            errors.put("email", "Invalid email format");
        } else if (!email.equals(user.getEmail())) {
            // Check if email is already in use by another user
            User existingUser = userDAO.findByEmail(email);
            if (existingUser != null && existingUser.getUserId() != user.getUserId()) {
                errors.put("email", "Email is already in use by another user");
            }
        }
        
        // Validate phone (optional)
        if (phone != null && !phone.isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            errors.put("phone", "Invalid phone number format");
        }
        
        // Validate password if changing
        if (newPassword != null && !newPassword.isEmpty()) {
            // Validate current password
            if (currentPassword == null || currentPassword.isEmpty()) {
                errors.put("currentPassword", "Current password is required to change password");
            } else if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
                errors.put("currentPassword", "Current password is incorrect");
            }
            
            // Validate new password
            if (!ValidationUtil.isValidPassword(newPassword)) {
                errors.put("newPassword", "Password must be at least 6 characters");
            }
            
            // Validate password confirmation
            if (!newPassword.equals(confirmPassword)) {
                errors.put("confirmPassword", "Passwords do not match");
            }
        }
        
        return errors;
    }
}
