   // Mobile menu toggle functionality
        document.addEventListener('DOMContentLoaded', function() {
            const mobileMenuToggle = document.createElement('button');
            mobileMenuToggle.className = 'mobile-menu-toggle';
            mobileMenuToggle.innerHTML = '<i class="fas fa-bars"></i>';
            document.body.appendChild(mobileMenuToggle);
            
            const sidebar = document.querySelector('.sidebar');
            
            mobileMenuToggle.addEventListener('click', function() {
                sidebar.classList.toggle('active');
            });
            
            // Form submission spinners
            const forms = document.querySelectorAll('form');
            forms.forEach(form => {
                form.addEventListener('submit', function() {
                    const submitBtn = this.querySelector('button[type="submit"]');
                    const spinner = submitBtn.querySelector('.spinner-border');
                    spinner.classList.remove('d-none');
                    submitBtn.disabled = true;
                });
            });
            
            // Animation for cards
            gsap.from('.card', {
                duration: 0.5,
                y: 20,
                opacity: 0,
                stagger: 0.1,
                ease: "power2.out"
            });
        });