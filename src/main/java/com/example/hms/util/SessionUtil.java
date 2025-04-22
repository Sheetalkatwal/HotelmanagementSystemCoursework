package com.example.hms.util;

import com.example.hms.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for session management
 */
public class SessionUtil {
    // Session attribute names
    public static final String USER_ATTRIBUTE = "user";
    public static final String ADMIN_ATTRIBUTE = "isAdmin";
    
    /**
     * Set the user in the session
     * @param request The HTTP request
     * @param user The user to set
     */
    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ATTRIBUTE, user);
        session.setAttribute(ADMIN_ATTRIBUTE, user.getRole() == User.Role.ADMIN);
    }
    
    /**
     * Get the user from the session
     * @param request The HTTP request
     * @return The user or null if not logged in
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (User) session.getAttribute(USER_ATTRIBUTE) : null;
    }
    
    /**
     * Check if the user is logged in
     * @param request The HTTP request
     * @return true if the user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }
    
    /**
     * Check if the user is an admin
     * @param request The HTTP request
     * @return true if the user is an admin, false otherwise
     */
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute(ADMIN_ATTRIBUTE));
    }
    
    /**
     * Invalidate the session
     * @param request The HTTP request
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
