<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Manage Rooms" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Room Management</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/manage-room-types" class="admin-btn admin-btn-secondary">Manage Room Types</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-filter-form">
            <form action="${pageContext.request.contextPath}/admin/manage-rooms" method="get">
                <div class="form-row">
                    <div class="admin-form-group">
                        <label for="hotelId" class="admin-form-label">Filter by Hotel</label>
                        <select id="hotelId" name="hotelId" class="admin-form-select">
                            <option value="">All Hotels</option>
                            <c:forEach var="hotel" items="${requestScope.hotels}">
                                <option value="${hotel.hotelId}" ${param.hotelId == hotel.hotelId ? 'selected' : ''}>${hotel.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="admin-form-group">
                        <label for="status" class="admin-form-label">Filter by Status</label>
                        <select id="status" name="status" class="admin-form-select">
                            <option value="">All Statuses</option>
                            <option value="AVAILABLE" ${param.status == 'AVAILABLE' ? 'selected' : ''}>Available</option>
                            <option value="OCCUPIED" ${param.status == 'OCCUPIED' ? 'selected' : ''}>Occupied</option>
                            <option value="MAINTENANCE" ${param.status == 'MAINTENANCE' ? 'selected' : ''}>Maintenance</option>
                        </select>
                    </div>

                    <div class="admin-form-group">
                        <label for="roomTypeId" class="admin-form-label">Filter by Room Type</label>
                        <select id="roomTypeId" name="roomTypeId" class="admin-form-select">
                            <option value="">All Room Types</option>
                            <c:forEach var="roomType" items="${requestScope.roomTypes}">
                                <option value="${roomType.roomTypeId}" ${param.roomTypeId == roomType.roomTypeId ? 'selected' : ''}>${roomType.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="admin-form-group">
                        <button type="submit" class="admin-btn">Apply Filters</button>
                    </div>
                </div>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.rooms}">
                <div class="admin-alert">
                    <p>No rooms found matching your criteria.</p>
                    <c:if test="${not empty param.hotelId}">
                        <a href="${pageContext.request.contextPath}/admin/add-room?hotelId=${param.hotelId}" class="admin-btn">Add Room</a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Hotel</th>
                                <th>Room Number</th>
                                <th>Floor</th>
                                <th>Room Type</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="room" items="${requestScope.rooms}">
                                <tr>
                                    <td>${room.roomId}</td>
                                    <td>${room.hotel.name}</td>
                                    <td>${room.roomNumber}</td>
                                    <td>${not empty room.floor ? room.floor : 'N/A'}</td>
                                    <td>${room.roomType.name}</td>
                                    <td>$<fmt:formatNumber value="${room.roomType.pricePerNight}" pattern="#,##0.00" /></td>
                                    <td>
                                        <span class="status-badge status-${room.status.toString().toLowerCase()}">${room.status}</span>
                                    </td>
                                    <td class="actions-cell">
                                        <a href="${pageContext.request.contextPath}/admin/edit-room?id=${room.roomId}" class="admin-btn admin-btn-sm">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/delete-room?id=${room.roomId}&hotelId=${room.hotelId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this room?">Delete</a>
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

    .actions-cell {
        display: flex;
        gap: 5px;
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
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
