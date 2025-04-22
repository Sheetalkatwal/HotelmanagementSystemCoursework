<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Booking Confirmation" />
</jsp:include>

<div class="booking-container">
    <h1>Booking Confirmation</h1>
    
    <div class="row">
        <div class="col-8">
            <div class="booking-details card">
                <div class="card-header">
                    <h2>Booking Details</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/booking" method="post">
                        <input type="hidden" name="roomId" value="${room.roomId}">
                        <input type="hidden" name="checkInDate" value="${checkInDate}">
                        <input type="hidden" name="checkOutDate" value="${checkOutDate}">
                        <input type="hidden" name="totalPrice" value="${totalPrice}">
                        
                        <div class="form-group">
                            <label for="specialRequests">Special Requests (optional)</label>
                            <textarea id="specialRequests" name="specialRequests" class="form-control" rows="4"></textarea>
                        </div>
                        
                        <div class="form-group">
                            <button type="submit" class="btn btn-block">Confirm Booking</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <div class="col-4">
            <div class="booking-summary card">
                <div class="card-header">
                    <h2>Booking Summary</h2>
                </div>
                <div class="card-body">
                    <p><strong>Hotel:</strong> ${room.hotel.name}</p>
                    <p><strong>Room Type:</strong> ${roomType.name}</p>
                    <p><strong>Room Number:</strong> ${room.roomNumber}</p>
                    <p><strong>Check-in Date:</strong> <fmt:formatDate value="${checkInDate}" pattern="MM/dd/yyyy" /></p>
                    <p><strong>Check-out Date:</strong> <fmt:formatDate value="${checkOutDate}" pattern="MM/dd/yyyy" /></p>
                    <p><strong>Number of Nights:</strong> ${nights}</p>
                    <p><strong>Price per Night:</strong> $<fmt:formatNumber value="${roomType.pricePerNight}" pattern="#,##0.00" /></p>
                    <div class="booking-price">
                        <p><strong>Total Price:</strong> $<fmt:formatNumber value="${totalPrice}" pattern="#,##0.00" /></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
