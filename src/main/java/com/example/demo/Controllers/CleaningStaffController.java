package com.example.demo.Controllers;

import com.example.demo.Models.*;

import com.example.demo.Service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/staff/dashboard")
public class CleaningStaffController {


        private static final Logger logger = LoggerFactory.getLogger(CleaningStaffController.class);
    private final CleaningRequestService cleaningTaskService;
    private final CleaningStaffService cleaningStaffService;

   

    public CleaningStaffController(CleaningRequestService cleaningTaskService,
    CleaningStaffService cleaningStaffService) {
        this.cleaningTaskService = cleaningTaskService;
        this.cleaningStaffService=cleaningStaffService;
    
    }

    @GetMapping
    public String dashboard(Authentication authentication, Model model) {
        LocalDate today = LocalDate.now();
         String email = authentication.getName();
         CleaningStaff staff=cleaningStaffService.findByEmail(email);


        List<CleaningRequest> tasks = cleaningTaskService.getTasksForStaff(staff.getId(), today);
        long pendingTasksCount = cleaningTaskService.countPendingTasksForStaff(staff.getId(), today);
        long completedTasksCount = cleaningTaskService.countCompletedTasksForStaff(staff.getId(), today);
        
        model.addAttribute("staff", staff);
        model.addAttribute("tasks", tasks);
        model.addAttribute("pendingTasksCount", pendingTasksCount);
        model.addAttribute("completedTasksCount", completedTasksCount);
        model.addAttribute("totalTasksCount", tasks.size());
        
        return "staff/dashboard";
    }

 

    @PostMapping("/complete-task")
    public String completeTask(@RequestParam Long taskId,
                            Authentication authentication,
                             RedirectAttributes redirectAttributes) {

                                
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
         CleaningStaff staff=cleaningStaffService.findByEmail(email);
         
            logger.info("printing id"+ staff.getId());
        try {
            cleaningTaskService.completeTask(taskId, staff.getId());
            redirectAttributes.addFlashAttribute("success",
                "Task completed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to complete the task");
        }
        
        return "redirect:/staff/dashboard";
    }
}