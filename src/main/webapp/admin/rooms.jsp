<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Rooms - ${hotel.name}" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <div class="admin-card-header-content">
            <h3 class="admin-card-title">Rooms - ${hotel.name}</h3>
            <div class="admin-breadcrumb">
                <a href="${pageContext.request.contextPath}/admin/manage-hotels">Hotels</a> &gt;
                <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}">${hotel.name}</a> &gt;
                <span>Rooms</span>
            </div>
        </div>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/add-room?hotelId=${hotel.hotelId}" class="admin-btn"><i class="fas fa-plus"></i> Add New Room</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-tabs">
            <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}" class="admin-tab">Overview</a>
            <a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}" class="admin-tab">Edit Hotel</a>
            <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-tab">Room Types</a>
            <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="admin-tab active">Rooms</a>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.rooms}">
                <div class="admin-alert">
                    <p>No rooms found for this hotel.</p>
                    <a href="${pageContext.request.contextPath}/admin/add-room?hotelId=${hotel.hotelId}" class="admin-btn">Add Room</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Room Number</th>
                                <th>Floor</th>
                                <th>Room Type</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="room" items="${requestScope.rooms}">
                                <tr>
                                    <td>${room.roomId}</td>
                                    <td>${room.roomNumber}</td>
                                    <td>${not empty room.floor ? room.floor : 'N/A'}</td>
                                    <td>${room.roomType != null ? room.roomType.name : 'Unknown'}</td>
                                    <td>
                                        <span class="status-badge status-${room.status.toString().toLowerCase()}">${room.status}</span>
                                    </td>
                                    <td class="actions-cell">
                                        <a href="${pageContext.request.contextPath}/admin/edit-room?id=${room.roomId}" class="admin-btn admin-btn-sm">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/delete-room?id=${room.roomId}&hotelId=${hotel.hotelId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this room?">Delete</a>
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
    .admin-breadcrumb {
        font-size: 0.9rem;
        color: var(--admin-gray);
        margin-top: 5px;
    }

    .admin-breadcrumb a {
        color: var(--admin-primary);
        text-decoration: none;
    }

    .admin-breadcrumb a:hover {
        text-decoration: underline;
    }

    .admin-tabs {
        display: flex;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    .admin-tab {
        padding: 10px 20px;
        color: var(--admin-dark);
        text-decoration: none;
        border-bottom: 2px solid transparent;
        transition: var(--admin-transition);
    }

    .admin-tab:hover {
        color: var(--admin-primary);
        border-bottom-color: rgba(67, 97, 238, 0.3);
    }

    .admin-tab.active {
        color: var(--admin-primary);
        border-bottom-color: var(--admin-primary);
        font-weight: 500;
    }

    .admin-card-header-content {
        flex: 1;
    }

    .actions-cell {
        display: flex;
        gap: 5px;
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
