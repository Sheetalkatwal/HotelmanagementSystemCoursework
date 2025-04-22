<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Room Types - ${hotel.name}" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <div class="admin-card-header-content">
            <h3 class="admin-card-title">Room Types - ${hotel.name}</h3>
            <div class="admin-breadcrumb">
                <a href="${pageContext.request.contextPath}/admin/manage-hotels">Hotels</a> &gt;
                <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}">${hotel.name}</a> &gt;
                <span>Room Types</span>
            </div>
        </div>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/add-room-type?hotelId=${hotel.hotelId}" class="admin-btn"><i class="fas fa-plus"></i> Add New Room Type</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-tabs">
            <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}" class="admin-tab">Overview</a>
            <a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}" class="admin-tab">Edit Hotel</a>
            <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-tab active">Room Types</a>
            <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="admin-tab">Rooms</a>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.roomTypes}">
                <div class="admin-alert">
                    <p>No room types found for this hotel.</p>
                    <a href="${pageContext.request.contextPath}/admin/add-room-type?hotelId=${hotel.hotelId}" class="admin-btn">Add Room Type</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Capacity</th>
                                <th>Price per Night</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="roomType" items="${requestScope.roomTypes}">
                                <tr>
                                    <td>${roomType.roomTypeId}</td>
                                    <td>${roomType.name}</td>
                                    <td>${roomType.capacity} person(s)</td>
                                    <td>$<fmt:formatNumber value="${roomType.pricePerNight}" pattern="#,##0.00" /></td>
                                    <td>${not empty roomType.description ? roomType.description : 'No description available.'}</td>
                                    <td class="actions-cell">
                                        <a href="${pageContext.request.contextPath}/admin/edit-room-type?id=${roomType.roomTypeId}" class="admin-btn admin-btn-sm">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/delete-room-type?id=${roomType.roomTypeId}&hotelId=${hotel.hotelId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this room type?">Delete</a>
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
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
