<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Manage Bookings" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Booking Management</h3>
    </div>
    <div class="admin-card-body">

<div class="booking-status-tabs">
    <a href="${pageContext.request.contextPath}/admin/manage-bookings" class="status-tab ${empty selectedStatus ? 'active' : ''}">
        <span class="tab-label">All</span>
        <span class="tab-count">${bookingCount}</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=PENDING" class="status-tab ${selectedStatus == 'PENDING' ? 'active' : ''}">
        <span class="tab-label">Pending</span>
        <span class="tab-count pending-count">${pendingBookings}</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=CONFIRMED" class="status-tab ${selectedStatus == 'CONFIRMED' ? 'active' : ''}">
        <span class="tab-label">Confirmed</span>
        <span class="tab-count confirmed-count">${confirmedBookings}</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=CANCELLED" class="status-tab ${selectedStatus == 'CANCELLED' ? 'active' : ''}">
        <span class="tab-label">Cancelled</span>
        <span class="tab-count cancelled-count">${cancelledBookings}</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/manage-bookings?status=COMPLETED" class="status-tab ${selectedStatus == 'COMPLETED' ? 'active' : ''}">
        <span class="tab-label">Completed</span>
        <span class="tab-count completed-count">${completedBookings}</span>
    </a>
</div>

<div class="admin-filter-form">
    <form action="${pageContext.request.contextPath}/admin/manage-bookings" method="get">
        <div class="form-row">
            <div class="admin-form-group">
                <label for="hotelId" class="admin-form-label">Filter by Hotel</label>
                <select id="hotelId" name="hotelId" class="admin-form-select">
                    <option value="">All Hotels</option>
                    <c:forEach var="hotel" items="${hotels}">
                        <option value="${hotel.hotelId}" ${selectedHotelId == hotel.hotelId ? 'selected' : ''}>${hotel.name}</option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${not empty selectedStatus}">
                <input type="hidden" name="status" value="${selectedStatus}">
            </c:if>

            <div class="admin-form-group">
                <button type="submit" class="admin-btn">Apply Filter</button>
            </div>
        </div>
    </form>
</div>

<c:choose>
    <c:when test="${empty requestScope.bookings}">
        <div class="admin-alert">
            <p>No bookings found matching your criteria.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="admin-table-container">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Hotel</th>
                        <th>Room</th>
                        <th>Dates</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${requestScope.bookings}">
                        <tr>
                            <td>#${booking.bookingId}</td>
                            <td>
                                <div class="user-info">
                                    <div class="user-avatar">
                                        <span class="avatar-initial">${booking.user.fullName.charAt(0)}</span>
                                    </div>
                                    <div class="user-details">
                                        <div class="user-name">${booking.user.fullName}</div>
                                        <div class="user-email">${booking.user.email}</div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="hotel-info">
                                    <div class="hotel-name">${booking.hotel.name}</div>
                                    <div class="hotel-location">${booking.hotel.city}, ${booking.hotel.country}</div>
                                </div>
                            </td>
                            <td>
                                <div class="room-info">
                                    <div class="room-number">Room ${booking.room.roomNumber}</div>
                                    <div class="room-type">${booking.room.roomType.name}</div>
                                </div>
                            </td>
                            <td>
                                <div class="booking-dates">
                                    <div class="check-in">
                                        <span class="date-label">Check-in:</span>
                                        <span class="date-value"><fmt:formatDate value="${booking.checkInDate}" pattern="MM/dd/yyyy" /></span>
                                    </div>
                                    <div class="check-out">
                                        <span class="date-label">Check-out:</span>
                                        <span class="date-value"><fmt:formatDate value="${booking.checkOutDate}" pattern="MM/dd/yyyy" /></span>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="booking-price">$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00" /></div>
                            </td>
                            <td>
                                <span class="status-badge status-${booking.status.toString().toLowerCase()}">${booking.status}</span>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/update-booking-status" method="post" class="status-form">
                                    <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                    <div class="status-select-container">
                                        <select name="status" class="admin-form-select status-select">
                                            <c:forEach var="status" items="${statuses}">
                                                <option value="${status}" ${booking.status == status ? 'selected' : ''}>${status}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit" class="admin-btn admin-btn-sm">Update</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

    </div>
</div>

<style>
    .booking-status-tabs {
        display: flex;
        background: rgba(0, 0, 0, 0.03);
        border-radius: 10px;
        margin-bottom: 25px;
        overflow: hidden;
    }

    .status-tab {
        flex: 1;
        padding: 15px 10px;
        text-align: center;
        color: var(--admin-dark);
        text-decoration: none;
        transition: var(--admin-transition);
        position: relative;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .status-tab:hover {
        background: rgba(0, 0, 0, 0.05);
        text-decoration: none;
    }

    .status-tab.active {
        background: rgba(67, 97, 238, 0.1);
        font-weight: 600;
    }

    .status-tab.active::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 40%;
        height: 3px;
        background: var(--admin-primary);
        border-radius: 3px;
    }

    .tab-label {
        margin-bottom: 5px;
    }

    .tab-count {
        font-size: 0.9rem;
        font-weight: 600;
        padding: 2px 8px;
        border-radius: 10px;
        background: rgba(0, 0, 0, 0.05);
    }

    .pending-count {
        background: rgba(255, 154, 0, 0.1);
        color: var(--admin-warning);
    }

    .confirmed-count {
        background: rgba(67, 97, 238, 0.1);
        color: var(--admin-primary);
    }

    .cancelled-count {
        background: rgba(230, 57, 70, 0.1);
        color: var(--admin-danger);
    }

    .completed-count {
        background: rgba(46, 196, 182, 0.1);
        color: var(--admin-success);
    }

    .admin-filter-form {
        margin-bottom: 25px;
    }

    .form-row {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        align-items: flex-end;
    }

    .user-info, .hotel-info, .room-info {
        display: flex;
        align-items: center;
    }

    .user-avatar {
        width: 35px;
        height: 35px;
        border-radius: 50%;
        background: linear-gradient(135deg, var(--admin-primary), var(--admin-secondary));
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 10px;
    }

    .avatar-initial {
        color: white;
        font-weight: bold;
        font-size: 1.2rem;
        text-transform: uppercase;
    }

    .user-name, .hotel-name, .room-number {
        font-weight: 500;
        margin-bottom: 3px;
    }

    .user-email, .hotel-location, .room-type {
        font-size: 0.85rem;
        color: var(--admin-gray);
    }

    .booking-dates {
        font-size: 0.9rem;
    }

    .date-label {
        font-weight: 500;
        color: var(--admin-gray);
    }

    .booking-price {
        font-weight: 600;
        color: var(--admin-primary);
    }

    .status-form {
        display: flex;
        flex-direction: column;
    }

    .status-select-container {
        display: flex;
        gap: 5px;
    }

    .status-select {
        font-size: 0.9rem;
        padding: 5px 10px;
    }

    .status-pending {
        background-color: rgba(255, 154, 0, 0.1);
        color: var(--admin-warning);
        border: 1px solid rgba(255, 154, 0, 0.3);
    }

    .status-confirmed {
        background-color: rgba(67, 97, 238, 0.1);
        color: var(--admin-primary);
        border: 1px solid rgba(67, 97, 238, 0.3);
    }

    .status-cancelled {
        background-color: rgba(230, 57, 70, 0.1);
        color: var(--admin-danger);
        border: 1px solid rgba(230, 57, 70, 0.3);
    }

    .status-completed {
        background-color: rgba(46, 196, 182, 0.1);
        color: var(--admin-success);
        border: 1px solid rgba(46, 196, 182, 0.3);
    }

    @media (max-width: 992px) {
        .booking-status-tabs {
            flex-wrap: wrap;
        }

        .status-tab {
            flex: 1 0 33.333%;
        }
    }

    @media (max-width: 768px) {
        .status-tab {
            flex: 1 0 50%;
        }
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
