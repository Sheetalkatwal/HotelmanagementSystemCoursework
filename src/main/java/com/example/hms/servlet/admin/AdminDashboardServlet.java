package com.example.hms.servlet.admin;

import com.example.hms.dao.BookingDAO;
import com.example.hms.dao.HotelDAO;
import com.example.hms.dao.RoomDAO;
import com.example.hms.dao.UserDAO;
import com.example.hms.dao.impl.BookingDAOImpl;
import com.example.hms.dao.impl.HotelDAOImpl;
import com.example.hms.dao.impl.RoomDAOImpl;
import com.example.hms.dao.impl.UserDAOImpl;
import com.example.hms.model.Booking;
import com.example.hms.model.User;
import com.example.hms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet for admin dashboard
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdminDashboardServlet.class.getName());
    private final HotelDAO hotelDAO = new HotelDAOImpl();
    private final RoomDAO roomDAO = new RoomDAOImpl();
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is an admin
        if (!SessionUtil.isAdmin(request)) {
            // Redirect to login page if not admin
            LOGGER.warning("Unauthorized access attempt to admin dashboard");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You must be an admin to access this page");
            return;
        }

        // Get counts for dashboard
        int hotelCount = hotelDAO.findAll().size();
        int roomCount = roomDAO.findAll().size();
        int userCount = userDAO.findAll().size();
        int bookingCount = bookingDAO.findAll().size();

        // Get counts for different booking statuses
        int pendingBookings = bookingDAO.findByStatus(Booking.Status.PENDING).size();
        int confirmedBookings = bookingDAO.findByStatus(Booking.Status.CONFIRMED).size();
        int cancelledBookings = bookingDAO.findByStatus(Booking.Status.CANCELLED).size();
        int completedBookings = bookingDAO.findByStatus(Booking.Status.COMPLETED).size();

        // Calculate total revenue
        BigDecimal totalRevenue = bookingDAO.calculateTotalRevenue();

        // Get recent bookings
        List<Booking> recentBookings = bookingDAO.findRecentBookings(5);

        // Get recent users
        List<User> recentUsers = userDAO.findAll();
        if (recentUsers.size() > 5) {
            recentUsers = recentUsers.subList(0, 5);
        }

        // Count users by role
        int customerCount = 0;
        int adminCount = 0;

        for (User user : recentUsers) {
            if (user.getRole() == User.Role.CUSTOMER) {
                customerCount++;
            } else if (user.getRole() == User.Role.ADMIN) {
                adminCount++;
            }
        }

        // Set attributes for the dashboard
        request.setAttribute("hotelCount", hotelCount);
        request.setAttribute("roomCount", roomCount);
        request.setAttribute("userCount", userCount);
        request.setAttribute("bookingCount", bookingCount);
        request.setAttribute("pendingBookings", pendingBookings);
        request.setAttribute("confirmedBookings", confirmedBookings);
        request.setAttribute("cancelledBookings", cancelledBookings);
        request.setAttribute("completedBookings", completedBookings);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("recentBookings", recentBookings);
        request.setAttribute("recentUsers", recentUsers);
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("adminCount", adminCount);

        LOGGER.info("Admin dashboard loaded successfully");

        // Forward to the admin dashboard page
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
