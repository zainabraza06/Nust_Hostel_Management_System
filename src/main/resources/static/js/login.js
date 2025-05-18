document.addEventListener('DOMContentLoaded', function() {
    // Set current year in footer
    document.getElementById('currentYear').textContent = new Date().getFullYear();

    // Password visibility toggle
    const passwordToggle = document.querySelector('.password-toggle');
    const passwordInput = document.getElementById('password');
    
    if (passwordToggle && passwordInput) {
        passwordToggle.addEventListener('click', function() {
            const icon = this.querySelector('i');
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            icon.classList.toggle('fa-eye');
            icon.classList.toggle('fa-eye-slash');
            
            // Add animation
            icon.style.transform = 'scale(1.2)';
            setTimeout(() => {
                icon.style.transform = 'scale(1)';
            }, 200);
        });
    }

    // Password strength indicator
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            const strengthBar = document.querySelector('.strength-bar');
            const strengthText = document.querySelector('.strength-text');
            const password = this.value;
            let strength = 0;
            
            // Check password length
            if (password.length > 0) strength += 20;
            if (password.length >= 8) strength += 20;
            
            // Check for mixed case
            if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) strength += 20;
            
            // Check for numbers
            if (password.match(/([0-9])/)) strength += 20;
            
            // Check for special chars
            if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) strength += 20;
            
            // Update UI
            strengthBar.style.width = strength + '%';
            
            if (strength < 40) {
                strengthBar.style.backgroundColor = 'var(--danger)';
                strengthText.textContent = 'Weak';
                strengthText.style.color = 'var(--danger)';
            } else if (strength < 70) {
                strengthBar.style.backgroundColor = 'var(--warning)';
                strengthText.textContent = 'Moderate';
                strengthText.style.color = 'var(--warning)';
            } else {
                strengthBar.style.backgroundColor = 'var(--success)';
                strengthText.textContent = 'Strong';
                strengthText.style.color = 'var(--success)';
            }
        });
    }

    // Form validation and submission
    const loginForm = document.getElementById('loginForm');
    const loginBtn = document.querySelector('.login-btn');
    
    if (loginForm && loginBtn) {
        // Client-side validation
        loginForm.addEventListener('input', function() {
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            if (email && password && loginForm.checkValidity()) {
                loginBtn.disabled = false;
            } else {
                loginBtn.disabled = true;
            }
        });
        
        // Form submission
        loginForm.addEventListener('submit', function(e) {
            if (!this.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
            } else {
                const spinner = loginBtn.querySelector('.spinner-border');
                const btnText = loginBtn.querySelector('.btn-text');
                
                // Show loading state
                if (spinner && btnText) {
                    spinner.classList.remove('d-none');
                    btnText.textContent = 'Authenticating...';
                    loginBtn.disabled = true;
                }
            }
            
            this.classList.add('was-validated');
        });
    }

    // Animate elements on load
    gsap.from('.logo-circle', {
        duration: 1,
        y: -50,
        opacity: 0,
        ease: 'back.out(1.7)'
    });
    
    gsap.from('.brand-logo h1', {
        duration: 0.8,
        y: 20,
        opacity: 0,
        delay: 0.3,
        ease: 'power2.out'
    });
    
    gsap.from('.login-header h2', {
        duration: 0.6,
        y: 20,
        opacity: 0,
        delay: 0.5,
        ease: 'power2.out'
    });
    
    gsap.from('.login-form', {
        duration: 0.8,
        y: 30,
        opacity: 0,
        delay: 0.7,
        ease: 'power2.out'
    });

    // Auto-focus email field if empty
    const emailField = document.getElementById('email');
    if (emailField && !emailField.value) {
        setTimeout(() => {
            emailField.focus();
            gsap.to(emailField, {
                duration: 0.5,
                boxShadow: '0 0 0 0.25rem rgba(67, 97, 238, 0.25)',
                yoyo: true,
                repeat: 1
            });
        }, 1000);
    }

    // Forgot password modal handling
    const forgotPasswordModal = document.getElementById('forgotPasswordModal');
    if (forgotPasswordModal) {
        forgotPasswordModal.addEventListener('shown.bs.modal', function () {
            const resetEmail = document.getElementById('resetEmail');
            resetEmail.focus();
        });
    }
});