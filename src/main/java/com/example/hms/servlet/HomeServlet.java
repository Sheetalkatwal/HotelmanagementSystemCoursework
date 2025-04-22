package com.example.hms.servlet;

import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Servlet for handling the home page and role-based redirection
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            // Redirect based on user role
            if (user.getRole() == User.Role.ADMIN) {
                LOGGER.info("Admin user detected, redirecting to admin dashboard");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                LOGGER.info("Customer user detected, redirecting to customer dashboard");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            // Not logged in, redirect to index page
            LOGGER.info("User not logged in, redirecting to index page");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
