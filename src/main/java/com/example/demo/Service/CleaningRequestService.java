package com.example.demo.Service;



import com.example.demo.Models.*;

import com.example.demo.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


import java.time.LocalDate;


@Service
public class CleaningRequestService  {

    private final CleaningRequestRepository cleaningTaskRepository;

            private static final Logger logger = LoggerFactory.getLogger(CleaningRequestService.class);

    public CleaningRequestService(CleaningRequestRepository cleaningTaskRepository) {
        this.cleaningTaskRepository = cleaningTaskRepository;
    }

  
    public List<CleaningRequest> getTasksForStaff(Long staffId, LocalDate date) {
    List<CleaningRequest> allTasks = cleaningTaskRepository.findByCleaningStaffIdAndRequestTimeBetween(
        staffId,
        date.atStartOfDay(),
        date.atTime(23, 59, 59)
    );

    return allTasks.stream()
        .filter(task -> "approved".equalsIgnoreCase(task.getStatus().toString()))
        .collect(Collectors.toList());
}



    public long countPendingTasksForStaff(Long staffId, LocalDate date) {
        return cleaningTaskRepository.countByCleaningStaffIdAndStatusAndRequestTimeBetween(
            staffId,
            CleaningRequest.RequestStatus.APPROVED,
            date.atStartOfDay(),
            date.atTime(23, 59, 59)
        );
    }

    public long countCompletedTasksForStaff(Long staffId, LocalDate date) {
        return cleaningTaskRepository.countByCleaningStaffIdAndStatusAndRequestTimeBetween(
            staffId,
            CleaningRequest.RequestStatus.COMPLETED,
            date.atStartOfDay(),
            date.atTime(23, 59, 59)
        );
    }

 
    @Transactional
    public CleaningRequest completeTask(Long taskId, Long staffId) {
        CleaningRequest task = cleaningTaskRepository.findById(taskId).get();
        
        if (task.getStatus() != Request.RequestStatus.APPROVED) {
            throw new IllegalStateException("Task is not in assignable state");
        }
        
        task.setStatus(Request.RequestStatus.COMPLETED);
        logger.info("task"  +task.getStatus().toString());

        
        
         return cleaningTaskRepository.save(task);

    }

    public List<CleaningRequest> getTaskHistory(Long staffId, LocalDate fromDate, LocalDate toDate) {
        return cleaningTaskRepository.findByCleaningStaffIdAndRequestTimeBetween(
            staffId,
            fromDate.atStartOfDay(),
            toDate.atTime(23, 59, 59)
        );
    }
}