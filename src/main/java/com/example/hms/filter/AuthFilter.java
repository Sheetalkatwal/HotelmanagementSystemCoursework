package com.example.hms.filter;

import com.example.hms.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Filter for authentication and authorization
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());

    // Public URLs that don't require authentication
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/index.jsp", "/login.jsp", "/register.jsp", "/about.jsp", "/contact.jsp",
            "/css/", "/js/", "/images/", "/error/", "/favicon.ico",
            "/login", "/register", "/logout", "/home", "/search", "/hotel"
    );

    // Admin URLs that require admin role
    private static final List<String> ADMIN_URLS = Arrays.asList(
            "/admin/"
    );

    // Customer URLs that require customer role (not admin)
    private static final List<String> CUSTOMER_URLS = Arrays.asList(
            "/booking", "/booking-form.jsp", "/my-bookings", "/cancel-booking",
            "/profile", "/update-profile", "/change-password"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String relativePath = requestURI.substring(contextPath.length());

        LOGGER.info("Filtering request: " + relativePath);

        // Check if the requested URL is public
        boolean isPublicURL = isPublicURL(relativePath);

        // Check if the requested URL is admin-only
        boolean isAdminURL = isAdminURL(relativePath);

        // Check if the requested URL is customer-only
        boolean isCustomerURL = isCustomerURL(relativePath);

        // Check if the user is logged in and their role
        boolean isLoggedIn = SessionUtil.isLoggedIn(httpRequest);
        boolean isAdmin = SessionUtil.isAdmin(httpRequest);

        // Allow access to public URLs
        if (isPublicURL) {
            LOGGER.info("Public URL access granted: " + relativePath);
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is logged in
        if (!isLoggedIn) {
            LOGGER.info("Access denied - not logged in: " + relativePath);
            httpResponse.sendRedirect(contextPath + "/login.jsp?error=Please log in to access this page");
            return;
        }

        // Handle admin access
        if (isAdminURL) {
            if (!isAdmin) {
                LOGGER.info("Admin access denied for non-admin user: " + relativePath);
                httpResponse.sendRedirect(contextPath + "/index.jsp?error=You do not have permission to access this page");
                return;
            }
            LOGGER.info("Admin access granted: " + relativePath);
            chain.doFilter(request, response);
            return;
        }

        // Handle customer access
        if (isCustomerURL) {
            if (isAdmin) {
                LOGGER.info("Customer access denied for admin user: " + relativePath);
                httpResponse.sendRedirect(contextPath + "/admin/dashboard?error=This feature is for customers only");
                return;
            }
            LOGGER.info("Customer access granted: " + relativePath);
            chain.doFilter(request, response);
            return;
        }

        // For other URLs, allow access for authenticated users
        LOGGER.info("General access granted for authenticated user: " + relativePath);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }

    /**
     * Check if the URL is public (doesn't require authentication)
     * @param url The URL to check
     * @return true if the URL is public, false otherwise
     */
    private boolean isPublicURL(String url) {
        return PUBLIC_URLS.stream().anyMatch(url::startsWith);
    }

    /**
     * Check if the URL is admin-only
     * @param url The URL to check
     * @return true if the URL is admin-only, false otherwise
     */
    private boolean isAdminURL(String url) {
        return ADMIN_URLS.stream().anyMatch(url::startsWith);
    }

    /**
     * Check if the URL is customer-only
     * @param url The URL to check
     * @return true if the URL is customer-only, false otherwise
     */
    private boolean isCustomerURL(String url) {
        return CUSTOMER_URLS.stream().anyMatch(url::startsWith);
    }
}
