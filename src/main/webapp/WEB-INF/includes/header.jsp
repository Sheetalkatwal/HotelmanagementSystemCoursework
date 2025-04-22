<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Hotel Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <h1><a href="${pageContext.request.contextPath}/index.jsp">HMS</a></h1>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/search">Hotels</a></li>
                    <li><a href="${pageContext.request.contextPath}/about.jsp">About</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact.jsp">Contact</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li><a href="${pageContext.request.contextPath}/login.jsp">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/register.jsp">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle">${sessionScope.user.username} <span class="dropdown-arrow">▼</span></a>
                                <div class="dropdown-menu">
                                    <a href="${pageContext.request.contextPath}/profile">My Profile</a>
                                    <a href="${pageContext.request.contextPath}/my-bookings">My Bookings</a>
                                    <c:if test="${sessionScope.isAdmin}">
                                        <a href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                                </div>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="container">
            <c:if test="${not empty param.success}">
                <div class="alert success">
                    <p>${param.success}</p>
                </div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert error">
                    <p>${param.error}</p>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.success}">
                <div class="alert success">
                    <p>${requestScope.success}</p>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <div class="alert error">
                    <p>${requestScope.error}</p>
                </div>
            </c:if>
