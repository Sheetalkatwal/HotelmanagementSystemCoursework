<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Manage Hotels" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Hotel Management</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/add-hotel" class="admin-btn">
                <span class="btn-icon">+</span> Add New Hotel
            </a>
        </div>
    </div>

    <div class="admin-card-body">
        <div class="admin-form-row">
            <form action="${pageContext.request.contextPath}/admin/manage-hotels" method="get" class="admin-search-form">
                <div class="form-row">
                    <div class="admin-form-group">
                        <label for="searchKeyword" class="admin-form-label">Search</label>
                        <input type="text" id="searchKeyword" name="keyword" class="admin-form-control" placeholder="Search by name or location" value="${param.keyword}">
                    </div>
                    <div class="admin-form-group">
                        <label for="filterStarRating" class="admin-form-label">Star Rating</label>
                        <select id="filterStarRating" name="starRating" class="admin-form-select">
                            <option value="">All Ratings</option>
                            <option value="5" ${param.starRating == '5' ? 'selected' : ''}>5 Stars</option>
                            <option value="4" ${param.starRating == '4' ? 'selected' : ''}>4 Stars</option>
                            <option value="3" ${param.starRating == '3' ? 'selected' : ''}>3 Stars</option>
                            <option value="2" ${param.starRating == '2' ? 'selected' : ''}>2 Stars</option>
                            <option value="1" ${param.starRating == '1' ? 'selected' : ''}>1 Star</option>
                        </select>
                    </div>
                    <div class="admin-form-group">
                        <button type="submit" class="admin-btn">Apply Filters</button>
                    </div>
                </div>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.hotels}">
                <div class="admin-alert">
                    <p>No hotels found matching your criteria.</p>
                    <a href="${pageContext.request.contextPath}/admin/add-hotel" class="admin-btn">Add Your First Hotel</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-hotel-grid">
                    <c:forEach var="hotel" items="${requestScope.hotels}">
                        <div class="admin-hotel-card">
                            <div class="hotel-image">
                                <c:choose>
                                    <c:when test="${not empty hotel.imageUrl}">
                                        <img src="${hotel.imageUrl}" alt="${hotel.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">No image available</div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="hotel-rating">
                                    <c:forEach begin="1" end="${hotel.starRating}">
                                        <span class="star">★</span>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="hotel-details">
                                <h4 class="hotel-name">${hotel.name}</h4>
                                <p class="hotel-location">${hotel.city}, ${hotel.country}</p>
                                <p class="hotel-description">
                                    ${not empty hotel.description ? (hotel.description.length() > 100 ? hotel.description.substring(0, 100).concat('...') : hotel.description) : 'No description available.'}
                                </p>
                            </div>
                            <div class="hotel-actions">
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/admin/room-types?hotelId=${hotel.hotelId}" class="admin-btn admin-btn-sm admin-btn-secondary">Room Types</a>
                                    <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.hotelId}" class="admin-btn admin-btn-sm admin-btn-secondary">Rooms</a>
                                </div>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/admin/view-hotel?id=${hotel.hotelId}" class="admin-btn admin-btn-sm">View</a>
                                    <a href="${pageContext.request.contextPath}/admin/edit-hotel?id=${hotel.hotelId}" class="admin-btn admin-btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/delete-hotel?id=${hotel.hotelId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this hotel?">Delete</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<style>
    .form-row {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        align-items: flex-end;
    }

    .admin-form-group {
        flex: 1;
        min-width: 200px;
    }

    .admin-hotel-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
        gap: 25px;
        margin-top: 25px;
    }

    .admin-hotel-card {
        background: white;
        border-radius: var(--admin-border-radius);
        box-shadow: var(--admin-box-shadow);
        overflow: hidden;
        transition: var(--admin-transition);
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .admin-hotel-card:hover {
        transform: translateY(-5px);
    }

    .hotel-image {
        height: 200px;
        overflow: hidden;
        position: relative;
    }

    .hotel-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .no-image {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        background: rgba(0, 0, 0, 0.05);
        color: var(--admin-gray);
        font-size: 1rem;
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

    .hotel-details {
        padding: 15px;
        flex: 1;
    }

    .hotel-name {
        margin: 0 0 10px;
        font-size: 1.2rem;
        color: var(--admin-dark);
    }

    .hotel-location {
        color: var(--admin-gray);
        margin-bottom: 10px;
        font-size: 0.9rem;
    }

    .hotel-description {
        font-size: 0.9rem;
        color: var(--admin-dark);
        margin-bottom: 0;
    }

    .hotel-actions {
        padding: 15px;
        border-top: 1px solid rgba(0, 0, 0, 0.05);
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .action-row {
        display: flex;
        justify-content: space-between;
        gap: 10px;
    }

    .admin-alert {
        background: rgba(255, 154, 0, 0.1);
        border: 1px solid rgba(255, 154, 0, 0.3);
        color: var(--admin-warning);
        padding: 20px;
        border-radius: var(--admin-border-radius);
        text-align: center;
        margin: 20px 0;
    }

    .admin-alert p {
        margin-bottom: 15px;
    }

    @media (max-width: 768px) {
        .admin-hotel-grid {
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        }
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
