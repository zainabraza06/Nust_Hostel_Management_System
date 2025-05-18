   document.addEventListener('DOMContentLoaded', function() {
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
            
            // Initialize all modals from the manager dashboard template
            // Approve Mess-Off Modal
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
            
            // Reject Mess-Off Modal
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
            
            // Approve Leave Modal
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
            
            // Reject Leave Modal
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
            
            // Assign Cleaning Modal
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
            
            // Reject Cleaning Modal
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
            
          
        });