<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="My Profile" />
</jsp:include>

<div class="profile-container">
    <div class="row">
        <div class="col-4">
            <div class="card profile-sidebar">
                <div class="profile-avatar">
                    <div class="avatar-circle">
                        <span class="initials">${sessionScope.user.fullName.charAt(0)}</span>
                    </div>
                </div>
                <div class="profile-info">
                    <h2>${sessionScope.user.fullName}</h2>
                    <p class="profile-username">@${sessionScope.user.username}</p>
                    <p class="profile-role">${sessionScope.user.role.toString()}</p>
                </div>
                <div class="profile-nav">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/profile" class="active">Profile Settings</a></li>
                        <li><a href="${pageContext.request.contextPath}/my-bookings">My Bookings</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-8">
            <div class="card">
                <div class="card-header">
                    <h2>Profile Settings</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/profile" method="post">
                        <div class="form-group">
                            <label for="fullName">Full Name</label>
                            <input type="text" id="fullName" name="fullName" class="form-control" value="${sessionScope.user.fullName}" required>
                            <c:if test="${not empty requestScope.errors.fullName}">
                                <div class="error-message">${requestScope.errors.fullName}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" class="form-control" value="${sessionScope.user.email}" required>
                            <c:if test="${not empty requestScope.errors.email}">
                                <div class="error-message">${requestScope.errors.email}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="phone">Phone (optional)</label>
                            <input type="tel" id="phone" name="phone" class="form-control" value="${sessionScope.user.phone}">
                            <c:if test="${not empty requestScope.errors.phone}">
                                <div class="error-message">${requestScope.errors.phone}</div>
                            </c:if>
                        </div>

                        <h3 class="section-title">Change Password</h3>
                        <p class="section-description">Leave these fields empty if you don't want to change your password.</p>

                        <div class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword" class="form-control">
                            <c:if test="${not empty requestScope.errors.currentPassword}">
                                <div class="error-message">${requestScope.errors.currentPassword}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" class="form-control">
                            <c:if test="${not empty requestScope.errors.newPassword}">
                                <div class="error-message">${requestScope.errors.newPassword}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
                            <c:if test="${not empty requestScope.errors.confirmPassword}">
                                <div class="error-message">${requestScope.errors.confirmPassword}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .profile-container {
        max-width: 1000px;
        margin: 0 auto;
    }

    .profile-sidebar {
        text-align: center;
        padding: 30px 20px;
    }

    .profile-avatar {
        margin-bottom: 20px;
    }

    .avatar-circle {
        width: 100px;
        height: 100px;
        background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    }

    .initials {
        font-size: 2.5rem;
        color: white;
        font-weight: bold;
        text-transform: uppercase;
    }

    .profile-info {
        margin-bottom: 25px;
    }

    .profile-info h2 {
        margin-bottom: 5px;
        font-size: 1.5rem;
    }

    .profile-username {
        color: #6c757d;
        margin-bottom: 5px;
    }

    .profile-role {
        display: inline-block;
        padding: 3px 10px;
        background: rgba(67, 97, 238, 0.1);
        color: var(--primary-color);
        border-radius: 20px;
        font-size: 0.9rem;
        font-weight: 500;
    }

    .profile-nav ul {
        border-top: 1px solid rgba(255, 255, 255, 0.2);
        padding-top: 15px;
    }

    .profile-nav ul li {
        margin-bottom: 10px;
    }

    .profile-nav ul li a {
        display: block;
        padding: 10px;
        border-radius: 5px;
        transition: all 0.3s ease;
        color: var(--text-color);
        text-decoration: none;
    }

    .profile-nav ul li a:hover {
        background: rgba(255, 255, 255, 0.2);
    }

    .profile-nav ul li a.active {
        background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
        color: white;
    }

    .section-title {
        margin-top: 30px;
        margin-bottom: 10px;
        font-size: 1.3rem;
        color: var(--text-color);
        position: relative;
        padding-bottom: 10px;
    }

    .section-title:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 40px;
        height: 3px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        border-radius: 3px;
    }

    .section-description {
        color: #6c757d;
        margin-bottom: 20px;
        font-size: 0.9rem;
    }
</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
