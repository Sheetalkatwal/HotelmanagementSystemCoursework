<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Manage Room Types" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Room Type Management</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/add-room-type-global" class="admin-btn"><i class="fas fa-plus"></i> Add New Room Type</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-filter-form">
            <form action="${pageContext.request.contextPath}/admin/manage-room-types" method="get">
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
                        <label for="minPrice" class="admin-form-label">Min Price</label>
                        <input type="number" id="minPrice" name="minPrice" class="admin-form-control" value="${param.minPrice}" min="0" step="0.01">
                    </div>
                    
                    <div class="admin-form-group">
                        <label for="maxPrice" class="admin-form-label">Max Price</label>
                        <input type="number" id="maxPrice" name="maxPrice" class="admin-form-control" value="${param.maxPrice}" min="0" step="0.01">
                    </div>
                    
                    <div class="admin-form-group">
                        <button type="submit" class="admin-btn">Apply Filters</button>
                    </div>
                </div>
            </form>
        </div>
        
        <c:choose>
            <c:when test="${empty requestScope.roomTypes}">
                <div class="admin-alert">
                    <p>No room types found matching your criteria.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Hotel</th>
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
                                    <td>${roomType.hotel.name}</td>
                                    <td>${roomType.name}</td>
                                    <td>${roomType.capacity} person(s)</td>
                                    <td>$<fmt:formatNumber value="${roomType.pricePerNight}" pattern="#,##0.00" /></td>
                                    <td>${not empty roomType.description ? roomType.description : 'No description available.'}</td>
                                    <td class="actions-cell">
                                        <a href="${pageContext.request.contextPath}/admin/edit-room-type?id=${roomType.roomTypeId}" class="admin-btn admin-btn-sm">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/delete-room-type?id=${roomType.roomTypeId}&hotelId=${roomType.hotelId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this room type?">Delete</a>
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
