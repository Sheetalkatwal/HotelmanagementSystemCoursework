<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Dashboard" />
</jsp:include>

<div class="admin-welcome">
    <h2>Welcome back, ${sessionScope.user.fullName}!</h2>
    <p>Here's an overview of your hotel management system.</p>
</div>

<div class="stats-grid">
    <div class="stat-card">
        <div class="stat-icon hotels">🏨</div>
        <div class="stat-info">
            <div class="stat-value">${hotelCount}</div>
            <div class="stat-label">Hotels</div>
        </div>
    </div>

    <div class="stat-card">
        <div class="stat-icon bookings">📝</div>
        <div class="stat-info">
            <div class="stat-value">${bookingCount}</div>
            <div class="stat-label">Bookings</div>
        </div>
    </div>

    <div class="stat-card">
        <div class="stat-icon users">👥</div>
        <div class="stat-info">
            <div class="stat-value">${userCount}</div>
            <div class="stat-label">Users</div>
        </div>
    </div>

    <div class="stat-card">
        <div class="stat-icon revenue">💰</div>
        <div class="stat-info">
            <div class="stat-value">$${totalRevenue != null ? totalRevenue : '0.00'}</div>
            <div class="stat-label">Revenue</div>
        </div>
    </div>
</div>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Quick Actions</h3>
    </div>
    <div class="admin-card-body">
        <div class="quick-actions">
            <a href="${pageContext.request.contextPath}/admin/add-hotel" class="admin-btn">
                <span class="btn-icon">🏨</span> Add New Hotel
            </a>
            <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=PENDING" class="admin-btn admin-btn-secondary">
                <span class="btn-icon">📋</span> Pending Bookings
            </a>
            <a href="${pageContext.request.contextPath}/admin/manage-users" class="admin-btn admin-btn-secondary">
                <span class="btn-icon">👥</span> Manage Users
            </a>
            <a href="${pageContext.request.contextPath}/admin/manage-room-types" class="admin-btn admin-btn-secondary">
                <span class="btn-icon">🔖</span> Room Types
            </a>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-6">
        <div class="admin-card">
            <div class="admin-card-header">
                <h3 class="admin-card-title">Booking Statistics</h3>
            </div>
            <div class="admin-card-body">
                <div class="booking-stats">
                    <div class="booking-stat-item">
                        <div class="status-badge status-active">
                            <span class="badge-count">${pendingBookings}</span>
                        </div>
                        <div class="stat-label">Pending</div>
                        <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=PENDING" class="stat-link">View</a>
                    </div>

                    <div class="booking-stat-item">
                        <div class="status-badge status-active">
                            <span class="badge-count">${confirmedBookings}</span>
                        </div>
                        <div class="stat-label">Confirmed</div>
                        <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=CONFIRMED" class="stat-link">View</a>
                    </div>

                    <div class="booking-stat-item">
                        <div class="status-badge status-cancelled">
                            <span class="badge-count">${cancelledBookings}</span>
                        </div>
                        <div class="stat-label">Cancelled</div>
                        <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=CANCELLED" class="stat-link">View</a>
                    </div>

                    <div class="booking-stat-item">
                        <div class="status-badge status-active">
                            <span class="badge-count">${completedBookings}</span>
                        </div>
                        <div class="stat-label">Completed</div>
                        <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=COMPLETED" class="stat-link">View</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-6">
        <div class="admin-card">
            <div class="admin-card-header">
                <h3 class="admin-card-title">Recent Activity</h3>
            </div>
            <div class="admin-card-body">
                <div class="activity-list">
                    <c:choose>
                        <c:when test="${empty recentActivities}">
                            <div class="no-activity">
                                <p>No recent activities to display.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="activity" items="${recentActivities}">
                                <div class="activity-item">
                                    <div class="activity-icon">${activity.icon}</div>
                                    <div class="activity-content">
                                        <div class="activity-title">${activity.title}</div>
                                        <div class="activity-description">${activity.description}</div>
                                        <div class="activity-time">${activity.timeAgo}</div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                    <!-- Fallback if no activities are provided -->
                    <c:if test="${empty recentActivities}">
                        <div class="activity-item">
                            <div class="activity-icon">💳</div>
                            <div class="activity-content">
                                <div class="activity-title">New Booking</div>
                                <div class="activity-description">A new booking was made for Grand Hotel</div>
                                <div class="activity-time">2 hours ago</div>
                            </div>
                        </div>

                        <div class="activity-item">
                            <div class="activity-icon">👤</div>
                            <div class="activity-content">
                                <div class="activity-title">New User</div>
                                <div class="activity-description">A new user registered on the platform</div>
                                <div class="activity-time">5 hours ago</div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .admin-welcome {
        margin-bottom: 30px;
    }

    .admin-welcome h2 {
        font-size: 1.8rem;
        margin-bottom: 10px;
        color: var(--admin-dark);
    }

    .admin-welcome p {
        color: var(--admin-gray);
        font-size: 1.1rem;
    }

    .quick-actions {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
    }

    .booking-stats {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 20px;
    }

    .booking-stat-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
    }

    .badge-count {
        font-size: 1.5rem;
        font-weight: bold;
    }

    .stat-label {
        margin: 8px 0;
        font-weight: 500;
    }

    .stat-link {
        color: var(--admin-primary);
        font-size: 0.9rem;
        text-decoration: none;
    }

    .stat-link:hover {
        text-decoration: underline;
    }

    .activity-list {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    .activity-item {
        display: flex;
        align-items: flex-start;
        padding: 15px;
        background: rgba(0, 0, 0, 0.02);
        border-radius: 8px;
        transition: all 0.3s ease;
    }

    .activity-item:hover {
        background: rgba(0, 0, 0, 0.04);
    }

    .activity-icon {
        font-size: 1.5rem;
        margin-right: 15px;
    }

    .activity-title {
        font-weight: 600;
        margin-bottom: 5px;
    }

    .activity-description {
        font-size: 0.9rem;
        color: var(--admin-gray);
        margin-bottom: 5px;
    }

    .activity-time {
        font-size: 0.8rem;
        color: #999;
    }

    .no-activity {
        text-align: center;
        padding: 20px;
        color: var(--admin-gray);
    }

    @media (max-width: 992px) {
        .row {
            flex-direction: column;
        }

        .col-6 {
            width: 100%;
            margin-bottom: 20px;
        }
    }

    @media (max-width: 768px) {
        .booking-stats {
            grid-template-columns: 1fr;
        }
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
