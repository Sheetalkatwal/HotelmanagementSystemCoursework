<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - HMS Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body class="admin-body">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <div class="admin-logo">
                <h1><a href="${pageContext.request.contextPath}/admin/dashboard">HMS</a></h1>
            </div>
            <nav class="admin-nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="${pageContext.request.servletPath == '/admin/dashboard.jsp' ? 'active' : ''}">
                        <span class="nav-icon">📊</span> Dashboard
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-hotels" class="${pageContext.request.servletPath == '/admin/manage-hotels.jsp' ? 'active' : ''}">
                        <span class="nav-icon">🏨</span> Hotels
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-rooms" class="${pageContext.request.servletPath == '/admin/manage-rooms.jsp' ? 'active' : ''}">
                        <span class="nav-icon">🛏️</span> Rooms
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-room-types" class="${pageContext.request.servletPath == '/admin/manage-room-types.jsp' ? 'active' : ''}">
                        <span class="nav-icon">🔖</span> Room Types
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-bookings" class="${pageContext.request.servletPath == '/admin/manage-bookings.jsp' ? 'active' : ''}">
                        <span class="nav-icon">📝</span> Bookings
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-users" class="${pageContext.request.servletPath == '/admin/manage-users.jsp' ? 'active' : ''}">
                        <span class="nav-icon">👥</span> Users
                    </a></li>
                </ul>
            </nav>
            <div class="admin-user-info">
                <div class="user-avatar">
                    <span class="avatar-initial">${sessionScope.user.username.charAt(0)}</span>
                </div>
                <div class="user-details">
                    <div class="user-name">${sessionScope.user.fullName}</div>
                    <div class="user-role">Administrator</div>
                </div>
                <div class="user-actions">
                    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                        <span class="logout-icon">🚪</span>
                    </a>
                </div>
            </div>
        </aside>
        <main class="admin-main">
            <header class="admin-header">
                <div class="page-title">
                    <h1>${param.title}</h1>
                </div>
                <div class="header-actions">
                    <a href="${pageContext.request.contextPath}/home" class="view-site-btn" target="_blank">
                        <span class="btn-icon">🌐</span> View Site
                    </a>
                </div>
            </header>
            <div class="admin-content">
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
