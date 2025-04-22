<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp">
    <jsp:param name="title" value="Contact Us" />
</jsp:include>

<div class="contact-container">
    <div class="contact-header glass">
        <h1>Contact Us</h1>
        <p class="subtitle">We'd love to hear from you! Get in touch with our team.</p>
    </div>
    
    <div class="row">
        <div class="col-6">
            <div class="card contact-info-card">
                <div class="card-body">
                    <h2>Get In Touch</h2>
                    <p>Have questions, feedback, or need assistance with your booking? Our dedicated support team is here to help you.</p>
                    
                    <div class="contact-methods">
                        <div class="contact-method">
                            <div class="contact-icon">📧</div>
                            <div class="contact-details">
                                <h3>Email Us</h3>
                                <p>support@hms.com</p>
                                <p>For general inquiries: info@hms.com</p>
                            </div>
                        </div>
                        
                        <div class="contact-method">
                            <div class="contact-icon">📞</div>
                            <div class="contact-details">
                                <h3>Call Us</h3>
                                <p>+1 (555) 123-4567</p>
                                <p>Monday to Friday, 9am - 6pm EST</p>
                            </div>
                        </div>
                        
                        <div class="contact-method">
                            <div class="contact-icon">📍</div>
                            <div class="contact-details">
                                <h3>Visit Us</h3>
                                <p>123 Hotel Street, Suite 456</p>
                                <p>New York, NY 10001, USA</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="social-media">
                        <h3>Connect With Us</h3>
                        <div class="social-icons">
                            <a href="#" class="social-icon">
                                <span class="social-emoji">📘</span>
                                <span class="social-name">Facebook</span>
                            </a>
                            <a href="#" class="social-icon">
                                <span class="social-emoji">📷</span>
                                <span class="social-name">Instagram</span>
                            </a>
                            <a href="#" class="social-icon">
                                <span class="social-emoji">🐦</span>
                                <span class="social-name">Twitter</span>
                            </a>
                            <a href="#" class="social-icon">
                                <span class="social-emoji">💼</span>
                                <span class="social-name">LinkedIn</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-6">
            <div class="card contact-form-card">
                <div class="card-body">
                    <h2>Send Us a Message</h2>
                    <form id="contactForm" class="contact-form">
                        <div class="form-group">
                            <label for="name">Your Name</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Your Email</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="subject">Subject</label>
                            <input type="text" id="subject" name="subject" class="form-control" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="message">Message</label>
                            <textarea id="message" name="message" class="form-control" rows="6" required></textarea>
                        </div>
                        
                        <div class="form-group">
                            <button type="submit" class="btn btn-block">Send Message</button>
                        </div>
                    </form>
                    
                    <div id="formSuccess" class="form-success" style="display: none;">
                        <div class="success-icon">✓</div>
                        <h3>Message Sent!</h3>
                        <p>Thank you for contacting us. We'll get back to you as soon as possible.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="map-section glass">
        <h2>Our Location</h2>
        <div class="map-container">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3022.215256349542!2d-73.98784492426285!3d40.75097623440235!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c259a9b3117469%3A0xd134e199a405a163!2sEmpire%20State%20Building!5e0!3m2!1sen!2sus!4v1689321045615!5m2!1sen!2sus" 
                    width="100%" height="450" style="border:0;" allowfullscreen="" loading="lazy" 
                    referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>
    </div>
</div>

<script>
    document.getElementById('contactForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Simulate form submission
        document.getElementById('contactForm').style.display = 'none';
        document.getElementById('formSuccess').style.display = 'block';
        
        // Reset form
        this.reset();
    });
</script>

<style>
    .contact-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 20px;
    }
    
    .contact-header {
        text-align: center;
        padding: 50px 30px;
        margin-bottom: 40px;
        border-radius: 20px;
        background: rgba(255, 255, 255, 0.15);
    }
    
    .contact-header h1 {
        font-size: 2.8rem;
        margin-bottom: 15px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }
    
    .subtitle {
        font-size: 1.3rem;
        color: var(--text-color);
        opacity: 0.8;
    }
    
    .contact-info-card, .contact-form-card {
        height: 100%;
    }
    
    .contact-info-card h2, .contact-form-card h2 {
        margin-bottom: 20px;
        font-size: 1.8rem;
        color: var(--text-color);
        position: relative;
        padding-bottom: 15px;
    }
    
    .contact-info-card h2:after, .contact-form-card h2:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 50px;
        height: 3px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        border-radius: 3px;
    }
    
    .contact-methods {
        margin-top: 30px;
    }
    
    .contact-method {
        display: flex;
        margin-bottom: 25px;
    }
    
    .contact-icon {
        font-size: 2rem;
        margin-right: 15px;
        background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }
    
    .contact-details h3 {
        margin-bottom: 5px;
        font-size: 1.2rem;
        color: var(--text-color);
    }
    
    .contact-details p {
        margin-bottom: 5px;
        color: #6c757d;
    }
    
    .social-media {
        margin-top: 30px;
    }
    
    .social-media h3 {
        margin-bottom: 15px;
        font-size: 1.2rem;
        color: var(--text-color);
    }
    
    .social-icons {
        display: flex;
        flex-wrap: wrap;
    }
    
    .social-icon {
        display: flex;
        align-items: center;
        margin-right: 20px;
        margin-bottom: 10px;
        padding: 8px 15px;
        background: rgba(255, 255, 255, 0.2);
        border-radius: 30px;
        transition: all 0.3s ease;
        text-decoration: none;
        color: var(--text-color);
    }
    
    .social-icon:hover {
        background: rgba(255, 255, 255, 0.3);
        transform: translateY(-3px);
        text-decoration: none;
    }
    
    .social-emoji {
        font-size: 1.2rem;
        margin-right: 8px;
    }
    
    .contact-form {
        margin-top: 10px;
    }
    
    .form-success {
        text-align: center;
        padding: 30px;
    }
    
    .success-icon {
        font-size: 3rem;
        color: #28a745;
        background: rgba(40, 167, 69, 0.1);
        width: 80px;
        height: 80px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 20px;
        border: 2px solid rgba(40, 167, 69, 0.3);
    }
    
    .map-section {
        margin-top: 60px;
        padding: 30px;
        border-radius: 20px;
        margin-bottom: 40px;
    }
    
    .map-section h2 {
        text-align: center;
        margin-bottom: 20px;
        font-size: 1.8rem;
        color: var(--text-color);
    }
    
    .map-container {
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
    }
</style>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
