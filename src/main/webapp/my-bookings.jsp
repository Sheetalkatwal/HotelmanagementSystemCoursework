<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="My Bookings" />
</jsp:include>

<h1>My Bookings</h1>

<c:choose>
    <c:when test="${empty requestScope.bookings}">
        <div class="alert">
            <p>You don't have any bookings yet.</p>
        </div>
        <p><a href="${pageContext.request.contextPath}/search" class="btn">Search Hotels</a></p>
    </c:when>
    <c:otherwise>
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Hotel</th>
                        <th>Room</th>
                        <th>Check-in</th>
                        <th>Check-out</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${requestScope.bookings}">
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.hotel.name}</td>
                            <td>${booking.room.roomNumber} (${booking.room.roomType.name})</td>
                            <td><fmt:formatDate value="${booking.checkInDate}" pattern="MM/dd/yyyy" /></td>
                            <td><fmt:formatDate value="${booking.checkOutDate}" pattern="MM/dd/yyyy" /></td>
                            <td>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00" /></td>
                            <td>${booking.status}</td>
                            <td>
                                <c:if test="${booking.status == 'PENDING' || booking.status == 'CONFIRMED'}">
                                    <a href="${pageContext.request.contextPath}/cancel-booking?id=${booking.bookingId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to cancel this booking?')">Cancel</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
