document.addEventListener('DOMContentLoaded', function() {
    // Image upload preview
    const imageUploadInputs = document.querySelectorAll('.image-upload-input');
    
    imageUploadInputs.forEach(input => {
        input.addEventListener('change', function(e) {
            const file = e.target.files[0];
            const preview = this.closest('.image-upload-container').querySelector('.image-preview');
            const placeholder = preview.querySelector('.image-preview-placeholder');
            
            if (file) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    if (placeholder) {
                        placeholder.style.display = 'none';
                    }
                    
                    let img = preview.querySelector('img');
                    
                    if (!img) {
                        img = document.createElement('img');
                        preview.appendChild(img);
                    }
                    
                    img.src = e.target.result;
                }
                
                reader.readAsDataURL(file);
            }
        });
    });
    
    // Confirm delete actions
    const deleteButtons = document.querySelectorAll('[data-confirm]');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const message = this.getAttribute('data-confirm');
            
            if (!confirm(message || 'Are you sure you want to delete this item?')) {
                e.preventDefault();
            }
        });
    });
    
    // Toggle sidebar on mobile
    const toggleSidebarBtn = document.querySelector('.toggle-sidebar');
    const adminSidebar = document.querySelector('.admin-sidebar');
    
    if (toggleSidebarBtn && adminSidebar) {
        toggleSidebarBtn.addEventListener('click', function() {
            adminSidebar.classList.toggle('active');
        });
    }
    
    // Initialize datepickers if any
    const datepickers = document.querySelectorAll('.datepicker');
    
    if (datepickers.length > 0 && typeof flatpickr !== 'undefined') {
        datepickers.forEach(input => {
            flatpickr(input, {
                dateFormat: 'Y-m-d'
            });
        });
    }
});
