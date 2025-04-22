<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Page Not Found" />
</jsp:include>

<div class="error-container">
    <h1>404 - Page Not Found</h1>
    <p>The page you are looking for does not exist.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
