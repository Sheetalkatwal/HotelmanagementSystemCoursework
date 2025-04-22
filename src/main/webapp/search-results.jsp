<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Search Results" />
</jsp:include>

<div class="search-form">
    <h2>Search Hotels</h2>
    <form action="${pageContext.request.contextPath}/search" method="get">
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="keyword">Keyword</label>
                    <input type="text" id="keyword" name="keyword" class="form-control" value="${requestScope.keyword}">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" id="city" name="city" class="form-control" value="${requestScope.city}">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="country">Country</label>
                    <input type="text" id="country" name="country" class="form-control" value="${requestScope.country}">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="starRating">Star Rating</label>
                    <select id="starRating" name="starRating" class="form-control">
                        <option value="">Any</option>
                        <option value="1" ${requestScope.starRating == 1 ? 'selected' : ''}>1 Star</option>
                        <option value="2" ${requestScope.starRating == 2 ? 'selected' : ''}>2 Stars</option>
                        <option value="3" ${requestScope.starRating == 3 ? 'selected' : ''}>3 Stars</option>
                        <option value="4" ${requestScope.starRating == 4 ? 'selected' : ''}>4 Stars</option>
                        <option value="5" ${requestScope.starRating == 5 ? 'selected' : ''}>5 Stars</option>
                    </select>
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <button type="submit" class="btn btn-block">Search</button>
                </div>
            </div>
        </div>
    </form>
</div>

<h2>Search Results</h2>

<c:choose>
    <c:when test="${empty requestScope.hotels}">
        <div class="alert">
            <p>No hotels found matching your search criteria.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <c:forEach var="hotel" items="${requestScope.hotels}">
                <div class="col-4">
                    <div class="card hotel-card">
                        <div class="hotel-card-image">
                            <c:choose>
                                <c:when test="${not empty hotel.imageUrl}">
                                    <img src="${hotel.imageUrl}" alt="${hotel.name}">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-image">
                                        <p>No image available</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="hotel-card-content">
                            <h3 class="hotel-card-title">${hotel.name}</h3>
                            <p class="hotel-card-location">${hotel.city}, ${hotel.country}</p>
                            <div class="hotel-card-rating">
                                <c:forEach begin="1" end="${hotel.starRating}">
                                    <span class="star">★</span>
                                </c:forEach>
                            </div>
                            <p class="hotel-card-description">
                                ${not empty hotel.description ? (hotel.description.length() > 100 ? hotel.description.substring(0, 100).concat('...') : hotel.description) : 'No description available.'}
                            </p>
                            <a href="${pageContext.request.contextPath}/hotel?id=${hotel.hotelId}" class="btn">View Details</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<style>
    .no-image {
        height: 200px;
        background-color: #f5f5f5;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #888;
        font-size: 1rem;
        text-align: center;
    }
</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
