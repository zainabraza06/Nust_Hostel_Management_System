
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Bootstrap modal
    const cancelModal = new bootstrap.Modal('#cancelRequestModal');
    let currentRequestId = null;

    // Handle cancel request button clicks
    document.querySelectorAll('.cancel-request-btn').forEach(button => {
        button.addEventListener('click', function() {
            currentRequestId = this.getAttribute('data-id');
            document.getElementById('cancelRequestId').value = currentRequestId;
            
            // Reset form and button states when modal shows
            document.getElementById('cancelReason').value = '';
            const confirmBtn = document.getElementById('confirmCancel');
            confirmBtn.querySelector('.spinner-border').classList.add('d-none');
            confirmBtn.querySelector('.btn-text').textContent = 'Confirm Cancellation';
            confirmBtn.disabled = false;
            
            cancelModal.show();
        });
    });

    // Handle confirm cancellation button
    document.getElementById('confirmCancel').addEventListener('click', function() {
        const form = document.getElementById('cancelRequestForm');
        const spinner = this.querySelector('.spinner-border');
        const btnText = this.querySelector('.btn-text');
        
        // Show loading state
        spinner.classList.remove('d-none');
        btnText.textContent = 'Processing...';
        this.disabled = true;
        
        // Set the form action with the current request ID
        form.action = `/hostelite/dashboard/requests/${currentRequestId}/cancel`;
        
        // Submit the form
        form.submit();
    });

    // Reset modal when hidden
    document.getElementById('cancelRequestModal').addEventListener('hidden.bs.modal', function() {
        document.getElementById('cancelRequestId').value = '';
        document.getElementById('cancelReason').value = '';
    });

    // Initial render and animations (keep these)
    renderRequests();
    
    gsap.from('.sidebar', {
        duration: 0.8,
        x: -50,
        opacity: 0,
        ease: 'power2.out'
    });
    
    
gsap.from('.main-header', {
        duration: 0.6,
        y: -20,
        opacity: 0,
        delay: 0.3,
        ease: 'power2.out'
    });
    
    gsap.from('.filter-controls', {
        duration: 0.6,
        y: 20,
        opacity: 0,
        delay: 0.5,
        ease: 'power2.out'
    });
    
    gsap.from('.request-card', {
        duration: 0.6,
        y: 20,
        opacity: 0,
        delay: 0.7,
        ease: 'power2.out',
        stagger: 0.1
    });
});