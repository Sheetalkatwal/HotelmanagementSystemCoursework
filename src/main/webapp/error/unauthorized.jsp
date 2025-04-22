<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Unauthorized Access" />
</jsp:include>

<div class="error-container glass">
    <div class="error-icon">🔒</div>
    <h1>Access Denied</h1>
    <p class="error-message">You do not have permission to access this page.</p>
    
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <p>Please log in to access this feature.</p>
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Log In</a>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline">Go to Homepage</a>
            </div>
        </c:when>
        <c:when test="${sessionScope.isAdmin}">
            <p>This feature is only available to customers.</p>
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn">Go to Admin Dashboard</a>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline">Go to Homepage</a>
            </div>
        </c:when>
        <c:otherwise>
            <p>This feature is only available to administrators.</p>
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/my-bookings" class="btn">My Bookings</a>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline">Go to Homepage</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<style>
    .error-container {
        max-width: 600px;
        margin: 80px auto;
        padding: 40px;
        text-align: center;
        border-radius: 20px;
    }
    
    .error-icon {
        font-size: 5rem;
        margin-bottom: 20px;
        animation: pulse 2s infinite;
    }
    
    .error-container h1 {
        font-size: 2.5rem;
        margin-bottom: 20px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }
    
    .error-message {
        font-size: 1.2rem;
        margin-bottom: 30px;
        color: var(--text-color);
    }
    
    .error-actions {
        display: flex;
        justify-content: center;
        gap: 15px;
        margin-top: 30px;
    }
    
    .btn-outline {
        background: transparent;
        border: 2px solid var(--primary-color);
        color: var(--primary-color);
    }
    
    .btn-outline:hover {
        background: rgba(67, 97, 238, 0.1);
    }
    
    @keyframes pulse {
        0% {
            transform: scale(1);
        }
        50% {
            transform: scale(1.1);
        }
        100% {
            transform: scale(1);
        }
    }
</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
