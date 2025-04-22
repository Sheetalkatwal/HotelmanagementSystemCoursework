<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Error Details" />
</jsp:include>

<div class="error-container">
    <h1>Error Details</h1>
    <p>An error occurred while processing your request. Technical details are below:</p>
    
    <div class="error-details">
        <h3>Error Type: ${pageContext.exception.getClass().getName()}</h3>
        <p><strong>Message:</strong> ${pageContext.exception.message}</p>
        
        <h3>Stack Trace:</h3>
        <div class="stack-trace">
            <pre>
<c:forEach var="stackTraceElement" items="${pageContext.exception.stackTrace}">
    ${stackTraceElement}
</c:forEach>
            </pre>
        </div>
        
        <% if (exception.getCause() != null) { %>
        <h3>Caused By:</h3>
        <p><strong>${pageContext.exception.cause.getClass().getName()}</strong>: ${pageContext.exception.cause.message}</p>
        <div class="stack-trace">
            <pre>
<c:forEach var="stackTraceElement" items="${pageContext.exception.cause.stackTrace}">
    ${stackTraceElement}
</c:forEach>
            </pre>
        </div>
        <% } %>
    </div>
    
    <div class="error-actions">
        <a href="${pageContext.request.contextPath}/" class="btn">Go to Home Page</a>
    </div>
</div>

<style>
    .error-container {
        max-width: 800px;
        margin: 2rem auto;
        padding: 2rem;
        background: rgba(255, 255, 255, 0.7);
        backdrop-filter: blur(10px);
        border-radius: 10px;
        box-shadow: 0 8px 32px rgba(31, 38, 135, 0.2);
    }
    
    .error-details {
        margin-top: 2rem;
        padding: 1rem;
        background: rgba(255, 255, 255, 0.5);
        border-radius: 8px;
    }
    
    .stack-trace {
        max-height: 300px;
        overflow-y: auto;
        background: rgba(0, 0, 0, 0.05);
        padding: 1rem;
        border-radius: 4px;
        font-family: monospace;
        font-size: 0.9rem;
    }
    
    .error-actions {
        margin-top: 2rem;
        text-align: center;
    }
</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
