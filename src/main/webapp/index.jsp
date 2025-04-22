<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>

<section class="hero">
    <div class="hero-content">
        <h1>Welcome to Hotel Management System</h1>
        <p>Find and book the perfect hotel for your next trip</p>
        <a href="${pageContext.request.contextPath}/search" class="btn">Search Hotels</a>
    </div>
</section>

<section class="features">
    <div class="row">
        <div class="col-4">
            <div class="feature-card">
                <h3>Find the Perfect Hotel</h3>
                <p>Search through our extensive collection of hotels to find the perfect match for your needs and budget.</p>
            </div>
        </div>
        <div class="col-4">
            <div class="feature-card">
                <h3>Easy Booking Process</h3>
                <p>Our simple booking process makes it easy to reserve your room in just a few clicks.</p>
            </div>
        </div>
        <div class="col-4">
            <div class="feature-card">
                <h3>Manage Your Bookings</h3>
                <p>View and manage all your bookings in one place, with options to modify or cancel if needed.</p>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/WEB-INF/includes/footer.jsp" />