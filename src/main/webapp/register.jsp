<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<div class="form-container">
    <h2 class="form-title">Register</h2>
    
    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${requestScope.username}" required>
            <c:if test="${not empty requestScope.errors.username}">
                <div class="error-message">${requestScope.errors.username}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required>
            <c:if test="${not empty requestScope.errors.password}">
                <div class="error-message">${requestScope.errors.password}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
            <c:if test="${not empty requestScope.errors.confirmPassword}">
                <div class="error-message">${requestScope.errors.confirmPassword}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" class="form-control" value="${requestScope.email}" required>
            <c:if test="${not empty requestScope.errors.email}">
                <div class="error-message">${requestScope.errors.email}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" class="form-control" value="${requestScope.fullName}" required>
            <c:if test="${not empty requestScope.errors.fullName}">
                <div class="error-message">${requestScope.errors.fullName}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="phone">Phone (optional)</label>
            <input type="tel" id="phone" name="phone" class="form-control" value="${requestScope.phone}">
            <c:if test="${not empty requestScope.errors.phone}">
                <div class="error-message">${requestScope.errors.phone}</div>
            </c:if>
        </div>
        
        <div class="form-group">
            <button type="submit" class="btn btn-block">Register</button>
        </div>
    </form>
    
    <div class="text-center">
        <p>Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Login here</a></p>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
