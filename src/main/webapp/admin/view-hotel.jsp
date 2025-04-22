<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="${hotel.name} - Admin View" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Hotel Details</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}" class="admin-btn">Edit Hotel</a>
            <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-btn admin-btn-secondary">Manage Room Types</a>
            <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="admin-btn admin-btn-secondary">Manage Rooms</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="hotel-view-container">
            <div class="hotel-view-image">
                <c:choose>
                    <c:when test="${not empty hotel.imageUrl}">
                        <img src="${hotel.imageUrl}" alt="${hotel.name}">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image-container">
                            <p class="no-image-text">No image available</p>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="hotel-rating">
                    <c:forEach begin="1" end="${hotel.starRating}">
                        <span class="star">★</span>
                    </c:forEach>
                </div>
            </div>

            <div class="hotel-view-details">
                <div class="hotel-view-section">
                    <h4 class="section-title">Basic Information</h4>
                    <div class="detail-row">
                        <span class="detail-label">Name:</span>
                        <span class="detail-value">${hotel.name}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Location:</span>
                        <span class="detail-value">${hotel.address}, ${hotel.city}, ${hotel.country}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Star Rating:</span>
                        <span class="detail-value">${hotel.starRating} Stars</span>
                    </div>
                </div>

                <div class="hotel-view-section">
                    <h4 class="section-title">Contact Information</h4>
                    <div class="detail-row">
                        <span class="detail-label">Phone:</span>
                        <span class="detail-value">${not empty hotel.phone ? hotel.phone : 'Not available'}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value">${not empty hotel.email ? hotel.email : 'Not available'}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Postal Code:</span>
                        <span class="detail-value">${not empty hotel.postalCode ? hotel.postalCode : 'Not available'}</span>
                    </div>
                </div>

                <div class="hotel-view-section">
                    <h4 class="section-title">Description</h4>
                    <p class="hotel-description">${not empty hotel.description ? hotel.description : 'No description available.'}</p>
                </div>
            </div>
        </div>

        <div class="hotel-view-section">
            <h4 class="section-title">Room Types</h4>
            <div class="admin-table-container">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Capacity</th>
                            <th>Price per Night</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="roomType" items="${roomTypes}">
                            <tr>
                                <td>${roomType.name}</td>
                                <td>${roomType.capacity} person(s)</td>
                                <td>$<fmt:formatNumber value="${roomType.pricePerNight}" pattern="#,##0.00" /></td>
                                <td>${not empty roomType.description ? roomType.description : 'No description available.'}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/edit-room-type?id=${roomType.roomTypeId}" class="admin-btn admin-btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="hotel-view-section">
            <h4 class="section-title">Rooms</h4>
            <div class="admin-table-container">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Room Number</th>
                            <th>Floor</th>
                            <th>Room Type</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td>${room.roomNumber}</td>
                                <td>${not empty room.floor ? room.floor : 'N/A'}</td>
                                <td>${room.roomType != null ? room.roomType.name : 'Unknown'}</td>
                                <td>
                                    <span class="status-badge status-${room.status.toString().toLowerCase()}">${room.status}</span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/edit-room?id=${room.roomId}" class="admin-btn admin-btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<style>
    .hotel-view-container {
        display: flex;
        flex-wrap: wrap;
        gap: 30px;
        margin-bottom: 30px;
    }

    .hotel-view-image {
        flex: 1;
        min-width: 300px;
        position: relative;
    }

    .hotel-view-image img {
        width: 100%;
        border-radius: 8px;
        box-shadow: var(--admin-box-shadow);
    }

    .no-image-container {
        width: 100%;
        height: 300px;
        background-color: #f5f5f5;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 8px;
        box-shadow: var(--admin-box-shadow);
    }

    .no-image-text {
        font-size: 1.5rem;
        color: #888;
        text-align: center;
    }

    .hotel-rating {
        position: absolute;
        top: 10px;
        right: 10px;
        background: rgba(0, 0, 0, 0.6);
        color: gold;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 0.9rem;
    }

    .hotel-view-details {
        flex: 2;
        min-width: 300px;
    }

    .hotel-view-section {
        margin-bottom: 25px;
    }

    .section-title {
        font-size: 1.2rem;
        color: var(--admin-primary);
        margin-bottom: 15px;
        padding-bottom: 8px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }

    .detail-row {
        display: flex;
        margin-bottom: 10px;
    }

    .detail-label {
        font-weight: 500;
        width: 120px;
        color: var(--admin-dark);
    }

    .detail-value {
        flex: 1;
    }

    .hotel-description {
        line-height: 1.6;
    }

    .status-badge {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 0.85rem;
        font-weight: 500;
    }

    .status-available {
        background-color: rgba(40, 167, 69, 0.1);
        color: #28a745;
        border: 1px solid rgba(40, 167, 69, 0.3);
    }

    .status-occupied {
        background-color: rgba(220, 53, 69, 0.1);
        color: #dc3545;
        border: 1px solid rgba(220, 53, 69, 0.3);
    }

    .status-maintenance {
        background-color: rgba(255, 193, 7, 0.1);
        color: #ffc107;
        border: 1px solid rgba(255, 193, 7, 0.3);
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
