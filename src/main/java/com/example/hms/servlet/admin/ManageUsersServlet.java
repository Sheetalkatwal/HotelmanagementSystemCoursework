package com.example.hms.servlet.admin;

import com.example.hms.dao.UserDAO;
import com.example.hms.dao.impl.UserDAOImpl;
import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for managing users (admin)
 */
@WebServlet(name = "ManageUsersServlet", urlPatterns = {"/admin/manage-users"})
public class ManageUsersServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOImpl();
    
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
        String role = request.getParameter("role");
        String search = request.getParameter("search");
        
        // Get users based on filters
        List<User> users;
        if (role != null && !role.isEmpty() && search != null && !search.isEmpty()) {
            // Filter by both role and search
            users = userDAO.findByRoleAndSearch(role, search);
        } else if (role != null && !role.isEmpty()) {
            // Filter by role only
            users = userDAO.findByRole(role);
        } else if (search != null && !search.isEmpty()) {
            // Filter by search only
            users = userDAO.findBySearch(search);
        } else {
            // No filters, get all users
            users = userDAO.findAll();
        }
        
        // Set attributes
        request.setAttribute("users", users);
        
        // Forward to the manage users page
        request.getRequestDispatcher("/admin/manage-users.jsp").forward(request, response);
    }
}
