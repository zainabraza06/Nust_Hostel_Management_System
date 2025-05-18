    // Set up modal data when triggered
        document.addEventListener('DOMContentLoaded', function() {
            // Complete task modal
            const completeTaskModal = document.getElementById('completeTaskModal');
            if (completeTaskModal) {
                completeTaskModal.addEventListener('show.bs.modal', function(event) {
                    const button = event.relatedTarget;
                    const taskId = button.getAttribute('data-task-id');
                    const roomNumber = button.getAttribute('data-room-number');
                    
                    document.getElementById('completeTaskId').value = taskId;
                    document.getElementById('completeRoomNumber').textContent = 'Room ' + roomNumber;
                });
            }
            
            // Auto-dismiss flash messages after 5 seconds
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
        
        // Function to check for pending tasks at the end of the day
        function checkPendingTasks() {
            const now = new Date();
            const endOfDay = new Date();
            endOfDay.setHours(23, 59, 59, 999);
            
            // Check if it's near end of day (10pm for example)
            if (now.getHours() >= 22 && now.getMinutes() >= 0) {
                const pendingTasks = document.querySelectorAll('.task-card:not(.completed):not(.cancelled)');
                if (pendingTasks.length > 0) {
                    // In a real application, this would trigger an API call to the backend
                    console.log(`You have ${pendingTasks.length} pending tasks that will be marked as cancelled at the end of the day.`);
                    
                    // Show a warning to the user
                    const warning = document.createElement('div');
                    warning.className = 'alert alert-warning alert-dismissible fade show';
                    warning.innerHTML = `
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <strong>Warning!</strong> You have ${pendingTasks.length} pending tasks that will be automatically cancelled at the end of the day.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    `;
                    document.querySelector('.flash-messages-container').appendChild(warning);
                    
                    // Auto-dismiss the warning
                    setTimeout(() => {
                        warning.classList.add('fade');
                        setTimeout(() => {
                            warning.remove();
                        }, 150);
                    }, 10000);
                }
            }
        }
        
        // Check every hour
        setInterval(checkPendingTasks, 60 * 60 * 1000);
        // Initial check
        checkPendingTasks();