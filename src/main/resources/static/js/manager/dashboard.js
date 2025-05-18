// Set up modal data when triggered
    document.addEventListener('DOMContentLoaded', function() {
        // Mess-off modals
        const approveMessModal = document.getElementById('approveMessModal');
        if (approveMessModal) {
            approveMessModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                
                document.getElementById('approveMessRequestId').value = requestId;
                document.getElementById('approveMessStudentName').textContent = studentName;
            });
        }

        const rejectMessModal = document.getElementById('rejectMessModal');
        if (rejectMessModal) {
            rejectMessModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                
                document.getElementById('rejectMessRequestId').value = requestId;
                document.getElementById('rejectMessStudentName').textContent = studentName;
            });
        }

        // Leave modals
        const approveLeaveModal = document.getElementById('approveLeaveModal');
        if (approveLeaveModal) {
            approveLeaveModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                
                document.getElementById('approveLeaveRequestId').value = requestId;
                document.getElementById('approveLeaveStudentName').textContent = studentName;
            });
        }

        const rejectLeaveModal = document.getElementById('rejectLeaveModal');
        if (rejectLeaveModal) {
            rejectLeaveModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                
                document.getElementById('rejectLeaveRequestId').value = requestId;
                document.getElementById('rejectLeaveStudentName').textContent = studentName;
            });
        }

        // Cleaning modals
        const assignCleaningModal = document.getElementById('assignCleaningModal');
        if (assignCleaningModal) {
            assignCleaningModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                const roomNumber = button.getAttribute('data-room-number');
                
                document.getElementById('assignCleaningRequestId').value = requestId;
                document.getElementById('assignCleaningStudentName').textContent = studentName;
                document.getElementById('assignCleaningRoomNumber').textContent = roomNumber;
            });
        }

        const rejectCleaningModal = document.getElementById('rejectCleaningModal');
        if (rejectCleaningModal) {
            rejectCleaningModal.addEventListener('show.bs.modal', function(event) {
                const button = event.relatedTarget;
                const requestId = button.getAttribute('data-request-id');
                const studentName = button.getAttribute('data-student-name');
                
                document.getElementById('rejectCleaningRequestId').value = requestId;
                document.getElementById('rejectCleaningStudentName').textContent = studentName;
            });
        }

        // Form submission handlers
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function(e) {
                const submitButton = this.querySelector('button[type="submit"]');
                if (submitButton) {
                    submitButton.disabled = true;
                    submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Processing...';
                }
            });
        });

        // Initialize tooltips
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });

        // Tab persistence
        const tabEls = document.querySelectorAll('button[data-bs-toggle="tab"]');
        tabEls.forEach(tabEl => {
            tabEl.addEventListener('click', event => {
                localStorage.setItem('lastTab', event.target.getAttribute('id'));
            });
        });

        // Restore last active tab
        const lastTab = localStorage.getItem('lastTab');
        if (lastTab) {
            const tab = new bootstrap.Tab(document.getElementById(lastTab));
            tab.show();
        }
    });


    