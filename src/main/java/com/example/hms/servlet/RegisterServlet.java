package com.example.hms.servlet;

import com.example.hms.dao.UserDAO;
import com.example.hms.dao.impl.UserDAOImpl;
import com.example.hms.model.User;
import com.example.hms.util.PasswordUtil;
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
 * Servlet for user registration
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the registration page
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        
        // Validate input
        Map<String, String> errors = validateInput(username, password, confirmPassword, email, fullName, phone);
        
        if (!errors.isEmpty()) {
            // If there are validation errors, set them as request attributes and forward back to the form
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check if username or email already exists
        if (userDAO.findByUsername(username) != null) {
            errors.put("username", "Username already exists");
        }
        
        if (userDAO.findByEmail(email) != null) {
            errors.put("email", "Email already exists");
        }
        
        if (!errors.isEmpty()) {
            // If there are duplicate username/email errors, set them as request attributes and forward back to the form
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Create and save the new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRole(User.Role.CUSTOMER);
        
        User savedUser = userDAO.save(user);
        
        if (savedUser != null) {
            // Registration successful, redirect to login page with success message
            response.sendRedirect(request.getContextPath() + "/login.jsp?success=Registration successful. Please log in.");
        } else {
            // Registration failed, set error message and forward back to the form
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    
    /**
     * Validate user input
     * @param username The username
     * @param password The password
     * @param confirmPassword The password confirmation
     * @param email The email
     * @param fullName The full name
     * @param phone The phone number
     * @return Map of field names to error messages
     */
    private Map<String, String> validateInput(String username, String password, String confirmPassword,
                                             String email, String fullName, String phone) {
        Map<String, String> errors = new HashMap<>();
        
        // Validate username
        if (!ValidationUtil.isValidUsername(username)) {
            errors.put("username", "Username must be 3-20 characters and contain only letters, numbers, and underscores");
        }
        
        // Validate password
        if (!ValidationUtil.isValidPassword(password)) {
            errors.put("password", "Password must be at least 6 characters");
        }
        
        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            errors.put("confirmPassword", "Passwords do not match");
        }
        
        // Validate email
        if (!ValidationUtil.isValidEmail(email)) {
            errors.put("email", "Invalid email format");
        }
        
        // Validate full name
        if (!ValidationUtil.isNotEmpty(fullName)) {
            errors.put("fullName", "Full name is required");
        }
        
        // Validate phone (optional)
        if (phone != null && !phone.isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            errors.put("phone", "Invalid phone number format");
        }
        
        return errors;
    }
}
