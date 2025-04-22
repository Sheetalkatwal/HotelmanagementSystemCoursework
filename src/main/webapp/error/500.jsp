<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Server Error" />
</jsp:include>

<div class="error-container">
    <h1>500 - Server Error</h1>
    <p>Sorry, something went wrong on our end. Please try again later.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
