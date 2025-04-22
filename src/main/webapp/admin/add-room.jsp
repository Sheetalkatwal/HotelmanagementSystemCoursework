<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Add Room - ${hotel.name}" />
</jsp:include>

<div class="hotel-nav glass">
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}">Edit Hotel</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}">Room Types</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="active">Rooms</a></li>
    </ul>
</div>

<div class="form-container">
    <form action="${pageContext.request.contextPath}/admin/add-room" method="post">
        <input type="hidden" name="hotelId" value="${hotel.hotelId}">

        <div class="form-group">
            <label for="roomTypeId">Room Type</label>
            <select id="roomTypeId" name="roomTypeId" class="form-control" required>
                <option value="">Select Room Type</option>
                <c:forEach var="roomType" items="${roomTypes}">
                    <option value="${roomType.roomTypeId}" ${room.roomTypeId == roomType.roomTypeId ? 'selected' : ''}>${roomType.name} - $${roomType.pricePerNight} per night</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="roomNumber">Room Number</label>
            <input type="text" id="roomNumber" name="roomNumber" class="form-control" value="${room.roomNumber}" required>
            <c:if test="${not empty requestScope.errors.roomNumber}">
                <div class="error-message">${requestScope.errors.roomNumber}</div>
            </c:if>
        </div>

        <div class="form-group">
            <label for="floor">Floor (optional)</label>
            <input type="text" id="floor" name="floor" class="form-control" value="${room.floor}">
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <select id="status" name="status" class="form-control" required>
                <option value="AVAILABLE" ${room.status == 'AVAILABLE' ? 'selected' : ''}>Available</option>
                <option value="OCCUPIED" ${room.status == 'OCCUPIED' ? 'selected' : ''}>Occupied</option>
                <option value="MAINTENANCE" ${room.status == 'MAINTENANCE' ? 'selected' : ''}>Maintenance</option>
            </select>
        </div>

        <div class="form-group">
            <button type="submit" class="btn">Add Room</button>
            <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

<style>
    .hotel-nav {
        margin-bottom: 30px;
    }

    .hotel-nav ul {
        display: flex;
        background: rgba(255, 255, 255, 0.15);
        border-radius: 10px;
        overflow: hidden;
    }

    .hotel-nav ul li {
        flex: 1;
    }

    .hotel-nav ul li a {
        display: block;
        padding: 15px;
        text-align: center;
        color: var(--text-color);
        text-decoration: none;
        transition: all 0.3s ease;
    }

    .hotel-nav ul li a:hover {
        background: rgba(255, 255, 255, 0.2);
    }

    .hotel-nav ul li a.active {
        background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
        color: white;
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
