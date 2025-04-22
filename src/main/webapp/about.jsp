<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="About Us" />
</jsp:include>

<div class="about-container">
    <div class="about-header glass">
        <h1>About Hotel Management System</h1>
        <p class="subtitle">Your trusted partner for hotel bookings worldwide</p>
    </div>

    <div class="about-content">
        <div class="row">
            <div class="col-6">
                <div class="card about-card">
                    <div class="card-body">
                        <h2>Our Story</h2>
                        <p>Hotel Management System (HMS) was founded in 2023 with a simple mission: to make hotel booking easy, transparent, and enjoyable for travelers around the world.</p>
                        <p>What started as a small project has grown into a comprehensive platform connecting travelers with the best hotels across the globe. We're passionate about travel and committed to providing exceptional service to our customers.</p>
                        <p>Our team of dedicated professionals works tirelessly to ensure that every booking through HMS is smooth and hassle-free, allowing you to focus on enjoying your journey.</p>
                    </div>
                </div>
            </div>

            <div class="col-6">
                <div class="card about-card">
                    <div class="card-body">
                        <h2>Our Mission</h2>
                        <p>At HMS, our mission is to revolutionize the hotel booking experience by providing a user-friendly platform that connects travelers with their ideal accommodations.</p>
                        <p>We believe that finding and booking the perfect hotel should be a seamless part of your travel planning, not a source of stress or uncertainty.</p>
                        <p>Through innovative technology and exceptional customer service, we aim to make every step of your booking journey smooth, transparent, and tailored to your unique needs.</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="values-section">
            <h2 class="section-title">Our Values</h2>

            <div class="row">
                <div class="col-4">
                    <div class="value-card glass">
                        <div class="value-icon">
                            <i class="value-emoji">🤝</i>
                        </div>
                        <h3>Customer First</h3>
                        <p>We put our customers at the center of everything we do, constantly striving to exceed expectations and deliver exceptional experiences.</p>
                    </div>
                </div>

                <div class="col-4">
                    <div class="value-card glass">
                        <div class="value-icon">
                            <i class="value-emoji">✨</i>
                        </div>
                        <h3>Innovation</h3>
                        <p>We embrace new ideas and technologies to continuously improve our platform and stay ahead of the evolving needs of modern travelers.</p>
                    </div>
                </div>

                <div class="col-4">
                    <div class="value-card glass">
                        <div class="value-icon">
                            <i class="value-emoji">🔍</i>
                        </div>
                        <h3>Transparency</h3>
                        <p>We believe in clear communication, honest pricing, and providing all the information our customers need to make informed decisions.</p>
                    </div>
                </div>
            </div>
        </div>


        <div class="team-section">
            <h2 class="section-title">Our Team</h2>

            <div class="team-container">
                <div class="team-card glass">
                    <div class="team-avatar">
                        <div class="avatar-circle" data-initials="JD">
                            <img src="${pageContext.request.contextPath}/images/team/ceo.jpg" alt="John Doe" class="team-photo" onerror="this.classList.add('error'); this.parentNode.classList.add('show-initials');">
                        </div>
                    </div>
                    <h3>John Doe</h3>
                    <p class="team-role">CEO & Founder</p>
                    <p>With over 15 years of experience in the hospitality industry, John leads our company with passion and vision.</p>
                </div>

                <div class="team-card glass">
                    <div class="team-avatar">
                        <div class="avatar-circle" data-initials="JS">
                            <img src="${pageContext.request.contextPath}/images/team/cto.jpg" alt="Jane Smith" class="team-photo" onerror="this.classList.add('error'); this.parentNode.classList.add('show-initials');">
                        </div>
                    </div>
                    <h3>Jane Smith</h3>
                    <p class="team-role">CTO</p>
                    <p>Jane brings her extensive tech expertise to ensure our platform delivers a seamless booking experience.</p>
                </div>

                <div class="team-card glass">
                    <div class="team-avatar">
                        <div class="avatar-circle" data-initials="RJ">
                            <img src="${pageContext.request.contextPath}/images/team/customer.jpg" alt="Robert Johnson" class="team-photo" onerror="this.classList.add('error'); this.parentNode.classList.add('show-initials');">
                        </div>
                    </div>
                    <h3>Robert Johnson</h3>
                    <p class="team-role">Customer Experience Director</p>
                    <p>Robert ensures that every customer interaction with HMS exceeds expectations.</p>
                </div>

                <div class="team-card glass">
                    <div class="team-avatar">
                        <div class="avatar-circle" data-initials="AM">
                            <img src="${pageContext.request.contextPath}/images/team/marketing.jpg" alt="Alex Morgan" class="team-photo" onerror="this.classList.add('error'); this.parentNode.classList.add('show-initials');">
                        </div>
                    </div>
                    <h3>Alex Morgan</h3>
                    <p class="team-role">Marketing Director</p>
                    <p>Alex develops innovative marketing strategies to connect travelers with their perfect accommodations.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .about-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 20px;
        position: relative;
    }

    /* Background hotel images with parallax effect */
    body::before {
        content: '';
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: url('${pageContext.request.contextPath}/images/hotel-images/luxury-hotel.jpg');
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
        opacity: 0.15;
        z-index: -1;
    }

    .about-header {
        text-align: center;
        padding: 80px 30px;
        margin-bottom: 50px;
        border-radius: 20px;
        background: rgba(255, 255, 255, 0.15);
        position: relative;
        overflow: hidden;
        background-image: url('${pageContext.request.contextPath}/images/hotel-images/luxury-hotel.jpg');
        background-size: cover;
        background-position: center;
        box-shadow: 0 15px 30px rgba(31, 38, 135, 0.2);
        border: 1px solid rgba(255, 255, 255, 0.3);
    }

    .about-header::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(255, 255, 255, 0.5);
        backdrop-filter: blur(5px);
        z-index: 1;
    }

    .about-header::after {
        content: '';
        position: absolute;
        top: -50%;
        left: -50%;
        width: 200%;
        height: 200%;
        background: linear-gradient(to right, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0));
        transform: rotate(30deg);
        animation: shimmer 3s infinite;
        pointer-events: none;
        z-index: 2;
    }

    .about-header h1 {
        font-size: 2.8rem;
        margin-bottom: 15px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        position: relative;
        z-index: 2;
    }

    .subtitle {
        font-size: 1.3rem;
        color: var(--text-color);
        opacity: 0.8;
        position: relative;
        z-index: 2;
    }

    .about-content {
        margin-bottom: 60px;
    }

    .about-card {
        height: 100%;
        transition: transform 0.3s ease;
        background: rgba(255, 255, 255, 0.15);
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        border-radius: 15px;
        border: 1px solid rgba(255, 255, 255, 0.3);
        box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
        overflow: hidden;
        position: relative;
    }

    .about-card::before {
        content: '';
        position: absolute;
        top: -50%;
        left: -50%;
        width: 200%;
        height: 200%;
        background: linear-gradient(to right, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0));
        transform: rotate(30deg);
        animation: shimmerCard 3s infinite;
        pointer-events: none;
        z-index: 0;
    }

    .about-card:hover {
        transform: translateY(-5px);
    }

    .section-title {
        text-align: center;
        margin: 60px 0 40px;
        font-size: 2.2rem;
        position: relative;
        color: var(--text-color);
    }

    .section-title::after {
        content: '';
        position: absolute;
        bottom: -15px;
        left: 50%;
        transform: translateX(-50%);
        width: 80px;
        height: 4px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        border-radius: 4px;
    }

    .team-section {
        margin: 80px 0;
        position: relative;
    }

    /* Hotel image gallery background for team section */
    .team-section::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(255, 255, 255, 0.05);
        backdrop-filter: blur(5px);
        -webkit-backdrop-filter: blur(5px);
        border-radius: 20px;
        z-index: -1;
        border: 1px solid rgba(255, 255, 255, 0.1);
    }

    .team-section .section-title {
        margin-bottom: 50px;
        font-size: 2.4rem;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .team-container {
        display: flex;
        justify-content: space-between;
        gap: 25px;
        margin: 40px 0;
    }

    .value-card {
        text-align: center;
        padding: 30px 20px;
        height: 100%;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        background: rgba(255, 255, 255, 0.15);
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        border-radius: 15px;
        border: 1px solid rgba(255, 255, 255, 0.3);
        box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
        position: relative;
        overflow: hidden;
    }

    .value-card::before {
        content: '';
        position: absolute;
        top: -50%;
        left: -50%;
        width: 200%;
        height: 200%;
        background: linear-gradient(to right, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0));
        transform: rotate(30deg);
        animation: shimmerCard 3s infinite;
        pointer-events: none;
        z-index: 0;
    }

    .team-card {
        text-align: center;
        padding: 35px 25px;
        flex: 1;
        min-width: 0; /* Prevents flex items from overflowing */
        transition: all 0.3s ease;
        background: rgba(255, 255, 255, 0.15);
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        border-radius: 15px;
        border: 1px solid rgba(255, 255, 255, 0.3);
        box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
        position: relative;
        overflow: hidden;
        animation: fadeIn 0.8s ease-out forwards;
    }

    .team-card::before {
        content: '';
        position: absolute;
        top: -50%;
        left: -50%;
        width: 200%;
        height: 200%;
        background: linear-gradient(to right, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0));
        transform: rotate(30deg);
        animation: shimmerCard 3s infinite;
        pointer-events: none;
        z-index: 0;
    }

    @keyframes shimmerCard {
        0% { transform: translateX(-100%) rotate(30deg); }
        100% { transform: translateX(100%) rotate(30deg); }
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .value-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 15px 30px rgba(31, 38, 135, 0.3);
    }

    .team-card:hover {
        transform: translateY(-8px);
        box-shadow: 0 15px 30px rgba(31, 38, 135, 0.4);
    }

    .value-icon {
        margin-bottom: 20px;
    }

    .value-emoji {
        font-size: 3rem;
        display: inline-block;
        background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .value-card h3, .team-card h3 {
        margin-bottom: 15px;
        font-size: 1.4rem;
        color: var(--text-color);
    }

    .team-avatar {
        margin-bottom: 20px;
    }

    .avatar-circle {
        width: 90px;
        height: 90px;
        background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto;
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.25);
        position: relative;
        z-index: 1;
        transition: all 0.3s ease;
        overflow: hidden;
    }

    .team-photo {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.5s ease;
    }

    .team-photo.error {
        display: none;
    }

    .avatar-circle[data-initials]:before {
        content: attr(data-initials);
        font-size: 2rem;
        color: white;
        font-weight: bold;
        display: none;
    }

    .avatar-circle.show-initials:before {
        display: block;
    }

    .team-card:hover .team-photo {
        transform: scale(1.1);
    }

    .team-card:hover .avatar-circle {
        transform: scale(1.1);
        box-shadow: 0 12px 25px rgba(0, 0, 0, 0.3);
    }

    .initials {
        font-size: 2rem;
        color: white;
        font-weight: bold;
    }

    .team-role {
        color: var(--primary-color);
        font-weight: 600;
        margin-bottom: 15px;
        font-size: 1rem;
        position: relative;
        display: inline-block;
    }

    .team-role::after {
        content: '';
        position: absolute;
        bottom: -5px;
        left: 50%;
        transform: translateX(-50%);
        width: 40px;
        height: 2px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        border-radius: 2px;
    }

    .team-card p:not(.team-role) {
        color: #666;
        line-height: 1.6;
        margin-top: 20px;
    }

    @keyframes shimmer {
        0% {
            transform: translateX(-100%) rotate(30deg);
        }
        100% {
            transform: translateX(100%) rotate(30deg);
        }
    }



    /* Responsive styles */
    @media (max-width: 992px) {
        .team-container {
            flex-wrap: wrap;
            gap: 30px;
        }

        .team-card {
            flex-basis: calc(50% - 15px);
            margin-bottom: 20px;
        }

    }

    @media (max-width: 576px) {
        .team-container {
            flex-direction: column;
        }

        .team-card {
            flex-basis: 100%;
        }

    }


</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
