<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<div class="form-container">
    <h2 class="form-title">Login</h2>
    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" value="${requestScope.username}" required>
        </div>
        
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        
        <div class="form-group">
            <button type="submit" class="btn btn-block">Login</button>
        </div>
    </form>
    
    <div class="text-center">
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp">Register here</a></p>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
