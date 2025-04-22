<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Add Room Type - ${hotel.name}" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <div class="admin-card-header-content">
            <h3 class="admin-card-title">Add Room Type - ${hotel.name}</h3>
            <div class="admin-breadcrumb">
                <a href="${pageContext.request.contextPath}/admin/manage-hotels">Hotels</a> &gt;
                <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}">${hotel.name}</a> &gt;
                <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}">Room Types</a> &gt;
                <span>Add New</span>
            </div>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-tabs">
            <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}" class="admin-tab">Overview</a>
            <a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}" class="admin-tab">Edit Hotel</a>
            <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-tab active">Room Types</a>
            <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="admin-tab">Rooms</a>
        </div>

        <form action="${pageContext.request.contextPath}/admin/add-room-type" method="post" class="admin-form">
            <input type="hidden" name="hotelId" value="${hotel.hotelId}">

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="name" class="admin-form-label">Room Type Name</label>
                    <input type="text" id="name" name="name" class="admin-form-control" value="${roomType.name}" required>
                    <c:if test="${not empty requestScope.errors.name}">
                        <div class="admin-form-error">${requestScope.errors.name}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="capacity" class="admin-form-label">Capacity (persons)</label>
                    <input type="number" id="capacity" name="capacity" class="admin-form-control" value="${roomType.capacity}" min="1" required>
                    <c:if test="${not empty requestScope.errors.capacity}">
                        <div class="admin-form-error">${requestScope.errors.capacity}</div>
                    </c:if>
                </div>

                <div class="admin-form-group">
                    <label for="pricePerNight" class="admin-form-label">Price per Night ($)</label>
                    <input type="number" id="pricePerNight" name="pricePerNight" class="admin-form-control" value="${roomType.pricePerNight}" min="0" step="0.01" required>
                    <c:if test="${not empty requestScope.errors.pricePerNight}">
                        <div class="admin-form-error">${requestScope.errors.pricePerNight}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="description" class="admin-form-label">Description (optional)</label>
                    <textarea id="description" name="description" class="admin-form-textarea" rows="4">${roomType.description}</textarea>
                </div>
            </div>

            <div class="admin-form-actions">
                <button type="submit" class="admin-btn">Add Room Type</button>
                <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-btn admin-btn-secondary">Cancel</a>
            </div>
        </form>
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
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
