<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/admin-header.jsp">
    <jsp:param name="title" value="Add Hotel" />
</jsp:include>

<div class="admin-card">
    <div class="admin-card-header">
        <h3 class="admin-card-title">Add New Hotel</h3>
        <div class="admin-card-actions">
            <a href="${pageContext.request.contextPath}/admin/manage-hotels" class="admin-btn admin-btn-secondary">Back to Hotels</a>
        </div>
    </div>
    <div class="admin-card-body">
        <form action="${pageContext.request.contextPath}/admin/add-hotel" method="post" enctype="multipart/form-data">
            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="name" class="admin-form-label">Hotel Name</label>
                    <input type="text" id="name" name="name" class="admin-form-control" value="${hotel.name}" required>
                    <c:if test="${not empty requestScope.errors.name}">
                        <div class="admin-form-error">${requestScope.errors.name}</div>
                    </c:if>
                </div>

                <div class="admin-form-group">
                    <label for="starRating" class="admin-form-label">Star Rating</label>
                    <select id="starRating" name="starRating" class="admin-form-select" required>
                        <option value="">Select Rating</option>
                        <option value="1" ${hotel.starRating == 1 ? 'selected' : ''}>1 Star</option>
                        <option value="2" ${hotel.starRating == 2 ? 'selected' : ''}>2 Stars</option>
                        <option value="3" ${hotel.starRating == 3 ? 'selected' : ''}>3 Stars</option>
                        <option value="4" ${hotel.starRating == 4 ? 'selected' : ''}>4 Stars</option>
                        <option value="5" ${hotel.starRating == 5 ? 'selected' : ''}>5 Stars</option>
                    </select>
                    <c:if test="${not empty requestScope.errors.starRating}">
                        <div class="admin-form-error">${requestScope.errors.starRating}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="address" class="admin-form-label">Address</label>
                    <input type="text" id="address" name="address" class="admin-form-control" value="${hotel.address}" required>
                    <c:if test="${not empty requestScope.errors.address}">
                        <div class="admin-form-error">${requestScope.errors.address}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="city" class="admin-form-label">City</label>
                    <input type="text" id="city" name="city" class="admin-form-control" value="${hotel.city}" required>
                    <c:if test="${not empty requestScope.errors.city}">
                        <div class="admin-form-error">${requestScope.errors.city}</div>
                    </c:if>
                </div>

                <div class="admin-form-group">
                    <label for="state" class="admin-form-label">State/Province</label>
                    <input type="text" id="state" name="state" class="admin-form-control" value="${hotel.state}">
                </div>

                <div class="admin-form-group">
                    <label for="country" class="admin-form-label">Country</label>
                    <input type="text" id="country" name="country" class="admin-form-control" value="${hotel.country}" required>
                    <c:if test="${not empty requestScope.errors.country}">
                        <div class="admin-form-error">${requestScope.errors.country}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="postalCode" class="admin-form-label">Postal Code</label>
                    <input type="text" id="postalCode" name="postalCode" class="admin-form-control" value="${hotel.postalCode}">
                </div>

                <div class="admin-form-group">
                    <label for="phone" class="admin-form-label">Phone</label>
                    <input type="tel" id="phone" name="phone" class="admin-form-control" value="${hotel.phone}">
                    <c:if test="${not empty requestScope.errors.phone}">
                        <div class="admin-form-error">${requestScope.errors.phone}</div>
                    </c:if>
                </div>

                <div class="admin-form-group">
                    <label for="email" class="admin-form-label">Email</label>
                    <input type="email" id="email" name="email" class="admin-form-control" value="${hotel.email}">
                    <c:if test="${not empty requestScope.errors.email}">
                        <div class="admin-form-error">${requestScope.errors.email}</div>
                    </c:if>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label for="description" class="admin-form-label">Description</label>
                    <textarea id="description" name="description" class="admin-form-textarea" rows="5">${hotel.description}</textarea>
                </div>
            </div>

            <div class="admin-form-row">
                <div class="admin-form-group">
                    <label class="admin-form-label">Hotel Image</label>
                    <div class="image-upload-container">
                        <div class="image-preview">
                            <div class="image-preview-placeholder">No image selected</div>
                        </div>
                        <label for="hotelImage" class="image-upload-btn">Choose Image</label>
                        <input type="file" id="hotelImage" name="hotelImage" class="image-upload-input" accept="image/*">
                    </div>
                </div>
            </div>

            <div class="admin-form-actions">
                <button type="submit" class="admin-btn">Add Hotel</button>
                <a href="${pageContext.request.contextPath}/admin/manage-hotels" class="admin-btn admin-btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<style>
    .admin-form-row {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        margin-bottom: 20px;
    }

    .admin-form-group {
        flex: 1;
        min-width: 200px;
    }

    .admin-form-label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
    }

    .admin-form-error {
        color: var(--admin-danger);
        font-size: 0.85rem;
        margin-top: 5px;
    }

    .admin-form-actions {
        display: flex;
        gap: 10px;
        margin-top: 30px;
    }

    .image-upload-container {
        margin-bottom: 20px;
    }

    .image-preview {
        width: 100%;
        height: 200px;
        border-radius: 5px;
        border: 2px dashed rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 10px;
        overflow: hidden;
        position: relative;
    }

    .image-preview img {
        max-width: 100%;
        max-height: 100%;
        object-fit: contain;
    }

    .image-preview-placeholder {
        color: var(--admin-gray);
        font-size: 0.9rem;
        text-align: center;
    }

    .image-upload-btn {
        display: block;
        width: 100%;
        padding: 10px;
        background: var(--admin-light);
        border: 1px solid rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        cursor: pointer;
        text-align: center;
        transition: var(--admin-transition);
    }

    .image-upload-btn:hover {
        background: #e9ecef;
    }

    .image-upload-input {
        display: none;
    }
</style>

<jsp:include page="/WEB-INF/includes/admin-footer.jsp" />
