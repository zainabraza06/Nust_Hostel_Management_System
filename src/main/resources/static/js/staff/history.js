  document.addEventListener('DOMContentLoaded', function() {
            // Completion notes modal
            const completionNotesModal = document.getElementById('completionNotesModal');
            if (completionNotesModal) {
                completionNotesModal.addEventListener('show.bs.modal', function(event) {
                    const button = event.relatedTarget;
                    const notes = button.getAttribute('data-notes');
                    document.getElementById('completionNotesContent').textContent = notes;
                });
            }
            
            // Set today as default end date if not set
            if (!document.getElementById('endDate').value) {
                const today = new Date().toISOString().split('T')[0];
                document.getElementById('endDate').value = today;
            }
            
            // Auto-dismiss flash messages
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                setTimeout(() => {
                    alert.classList.add('fade');
                    setTimeout(() => {
                        alert.remove();
                    }, 150);
                }, 5000);
            });
        });
    