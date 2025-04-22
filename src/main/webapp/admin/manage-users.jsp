<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Manage Users" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">User Management</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/add-user" class="admin-btn"><i class="fas fa-plus"></i> Add New User</a>
        </div>
    </div>
    <div class="admin-card-body">
        <div class="admin-filter-form">
            <form action="${pageContext.request.contextPath}/admin/manage-users" method="get">
                <div class="form-row">
                    <div class="admin-form-group">
                        <label for="role" class="admin-form-label">Filter by Role</label>
                        <select id="role" name="role" class="admin-form-select">
                            <option value="">All Roles</option>
                            <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                            <option value="CUSTOMER" ${param.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
                        </select>
                    </div>

                    <div class="admin-form-group">
                        <label for="search" class="admin-form-label">Search</label>
                        <input type="text" id="search" name="search" class="admin-form-control" placeholder="Search by name, email, or username" value="${param.search}">
                    </div>

                    <div class="admin-form-group">
                        <button type="submit" class="admin-btn">Apply Filters</button>
                    </div>
                </div>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.users}">
                <div class="admin-alert">
                    <p>No users found matching your criteria.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="admin-table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Role</th>
                                <th>Created</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${requestScope.users}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.username}</td>
                                    <td>${user.fullName}</td>
                                    <td>${user.email}</td>
                                    <td>${not empty user.phone ? user.phone : 'N/A'}</td>
                                    <td>
                                        <span class="role-badge role-${user.role.toString().toLowerCase()}">${user.role}</span>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${user.createdAt}" pattern="MM/dd/yyyy" />
                                    </td>
                                    <td class="actions-cell">
                                        <a href="${pageContext.request.contextPath}/admin/edit-user?id=${user.userId}" class="admin-btn admin-btn-sm">Edit</a>
                                        <c:if test="${user.userId != sessionScope.user.userId}">
                                            <a href="${pageContext.request.contextPath}/admin/delete-user?id=${user.userId}" class="admin-btn admin-btn-sm admin-btn-danger" data-confirm="Are you sure you want to delete this user?">Delete</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<style>
    .role-badge {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 0.85rem;
        font-weight: 500;
    }

    .role-admin {
        background-color: rgba(67, 97, 238, 0.1);
        color: var(--admin-primary);
        border: 1px solid rgba(67, 97, 238, 0.3);
    }

    .role-customer {
        background-color: rgba(40, 167, 69, 0.1);
        color: #28a745;
        border: 1px solid rgba(40, 167, 69, 0.3);
    }

    .actions-cell {
        display: flex;
        gap: 5px;
    }

    .admin-filter-form {
        margin-bottom: 25px;
    }

    .form-row {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        align-items: flex-end;
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
