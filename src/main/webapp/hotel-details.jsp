<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="${hotel.name}" />
</jsp:include>

<div class="hotel-details">
    <div class="row">
        <div class="col-8">
            <h1>${hotel.name}</h1>
            <div class="hotel-rating">
                <c:forEach begin="1" end="${hotel.starRating}">
                    <span class="star">★</span>
                </c:forEach>
            </div>
            <p class="hotel-location">${hotel.address}, ${hotel.city}, ${hotel.country}</p>

            <div class="hotel-image">
                <c:choose>
                    <c:when test="${not empty hotel.imageUrl}">
                        <img src="${hotel.imageUrl}" alt="${hotel.name}" style="max-width: 100%;">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image-container">
                            <p class="no-image-text">No image available</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <style>
                .no-image-container {
                    width: 100%;
                    height: 400px;
                    background-color: #f5f5f5;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    border-radius: 8px;
                    margin-bottom: 20px;
                }

                .no-image-text {
                    font-size: 1.5rem;
                    color: #888;
                    text-align: center;
                }
            </style>

            <div class="hotel-description">
                <h3>About this hotel</h3>
                <p>${not empty hotel.description ? hotel.description : 'No description available.'}</p>
            </div>

            <div class="hotel-contact">
                <h3>Contact Information</h3>
                <p><strong>Phone:</strong> ${not empty hotel.phone ? hotel.phone : 'Not available'}</p>
                <p><strong>Email:</strong> ${not empty hotel.email ? hotel.email : 'Not available'}</p>
            </div>
        </div>

        <div class="col-4">
            <div class="booking-form card">
                <div class="card-header">
                    <h3>Book a Room</h3>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/booking" method="get">
                        <input type="hidden" name="hotelId" value="${hotel.hotelId}">

                        <div class="form-group">
                            <label for="roomTypeId">Room Type</label>
                            <select id="roomTypeId" name="roomTypeId" class="form-control" required>
                                <option value="">Select a room type</option>
                                <c:forEach var="roomType" items="${roomTypes}">
                                    <option value="${roomType.roomTypeId}">${roomType.name} - $${roomType.pricePerNight} per night</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="checkInDate">Check-in Date</label>
                            <input type="date" id="checkInDate" name="checkInDate" class="form-control" required min="${java.time.LocalDate.now()}">
                        </div>

                        <div class="form-group">
                            <label for="checkOutDate">Check-out Date</label>
                            <input type="date" id="checkOutDate" name="checkOutDate" class="form-control" required min="${java.time.LocalDate.now().plusDays(1)}">
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-block">Check Availability</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="room-types">
        <h2>Available Room Types</h2>
        <div class="row">
            <c:forEach var="roomType" items="${roomTypes}">
                <div class="col-4">
                    <div class="card">
                        <div class="card-header">
                            <h3>${roomType.name}</h3>
                        </div>
                        <div class="card-body">
                            <p><strong>Capacity:</strong> ${roomType.capacity} person(s)</p>
                            <p><strong>Price:</strong> $${roomType.pricePerNight} per night</p>
                            <p>${not empty roomType.description ? roomType.description : 'No description available.'}</p>
                        </div>
                        <div class="card-footer">
                            <form action="${pageContext.request.contextPath}/booking" method="get">
                                <input type="hidden" name="hotelId" value="${hotel.hotelId}">
                                <input type="hidden" name="roomTypeId" value="${roomType.roomTypeId}">
                                <div class="form-group">
                                    <label for="checkInDate${roomType.roomTypeId}">Check-in</label>
                                    <input type="date" id="checkInDate${roomType.roomTypeId}" name="checkInDate" class="form-control" required min="${java.time.LocalDate.now()}">
                                </div>
                                <div class="form-group">
                                    <label for="checkOutDate${roomType.roomTypeId}">Check-out</label>
                                    <input type="date" id="checkOutDate${roomType.roomTypeId}" name="checkOutDate" class="form-control" required min="${java.time.LocalDate.now().plusDays(1)}">
                                </div>
                                <button type="submit" class="btn btn-block">Book Now</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
