package com.example.demo.Controllers;

import com.example.demo.Models.Hostel;
import com.example.demo.Models.HostelManager;

import com.example.demo.Service.*;

import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import com.example.demo.Models.Request;

import java.time.LocalDate;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.example.demo.Models.Hostelite;



@Controller
@RequestMapping("/manager/dashboard")
public class ManagerController {
    private final RequestService requestService;
    private final HostelService hostelService;
    private final UserRepository userRepository;
    private final HosteliteService hosteliteService;
    
    public ManagerController(RequestService requestService, HostelService hostelService, UserRepository userRepository,
    HosteliteService hosteliteService) {
        this.requestService = requestService;
        this.hostelService = hostelService;
        this.userRepository=userRepository;
        this.hosteliteService=hosteliteService;
    }
    
    @GetMapping
    public String showRequests(Model model, 
                            Authentication authenticaton) {
        // Get manager's hostel

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        HostelManager hostelManager=(HostelManager)userRepository.findByEmail(email).get();
        
        Hostel hostel = hostelService.getHostelForManager(hostelManager.getHostel().getId());
        
        // Add pending requests to model
        model.addAttribute("messOffRequests", requestService.getPendingMessOffRequests());
        model.addAttribute("leaveRequests", requestService.getPendingLeaveRequests());
        model.addAttribute("cleaningRequests", requestService.getPendingCleaningRequests());
        model.addAttribute("cleaningStaff", requestService.getAvailableCleaningStaff(hostel));
        model.addAttribute("pendingRequestsCount", requestService.countPendingRequests());
        model.addAttribute("manager", hostel.getManager());
        
        return "manager/dashboard";
    }
    
    // Mess-off request handling
    @PostMapping("/approve-mess")
    public String approveMessOffRequest(@RequestParam Long requestId,
                                      @RequestParam(required = false) String remarks,
                                      RedirectAttributes redirectAttributes) {
        requestService.approveMessOffRequest(requestId, remarks);
        redirectAttributes.addFlashAttribute("successs", 
            "Mess-off request approved successfully");
        return "redirect:/manager/dashboard";
    }
    
    @PostMapping("/reject-mess")
    public String rejectMessOffRequest(@RequestParam Long requestId,
                                     @RequestParam String rejectionReason,
                                     RedirectAttributes redirectAttributes) {
        requestService.rejectMessOffRequest(requestId, rejectionReason);
        redirectAttributes.addFlashAttribute("success", "Mess-off request rejected");
        return "redirect:/manager/dashboard";
    }
    
    // Leave request handling
    @PostMapping("/approve-leave")
    public String approveLeaveRequest(@RequestParam Long requestId,
                                    @RequestParam(required = false) String remarks,
                                    RedirectAttributes redirectAttributes) {
        requestService.approveLeaveRequest(requestId, remarks);
        redirectAttributes.addFlashAttribute("success", "Leave approved Successfully");
        return "redirect:/manager/dashboard";
    }
    
    @PostMapping("/reject-leave")
    public String rejectLeaveRequest(@RequestParam Long requestId,
                                   @RequestParam String rejectionReason,
                                   RedirectAttributes redirectAttributes) {
        requestService.rejectLeaveRequest(requestId, rejectionReason);
        redirectAttributes.addFlashAttribute("success", "Leave request Rejected successfully");
        return "redirect:/manager/dashboard";
    }
    
    // Cleaning request handling
    @PostMapping("/assign-cleaning")
    public String assignCleaningStaff(@RequestParam Long requestId,
                                    @RequestParam Long staffId,
                                    @RequestParam(required = false) String instructions,
                                    RedirectAttributes redirectAttributes) {
        requestService.assignCleaningStaff(requestId, staffId, instructions);
        redirectAttributes.addFlashAttribute("success", "Cleaning staff assigned successfully");
        return "redirect:/manager/dashboard";
    }
    
    @PostMapping("/reject-cleaning")
    public String rejectCleaningRequest(@RequestParam Long requestId,
                                      @RequestParam String rejectionReason,
                                      RedirectAttributes redirectAttributes) {
        requestService.rejectCleaningRequest(requestId, rejectionReason);
        redirectAttributes.addFlashAttribute("success", "Cleaning Request Rejected successfully");
        return "redirect:/manager/dashboard";
    }
  

    @GetMapping("/requests")
    public String viewRequests(
            Authentication authentication,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long studentId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            RedirectAttributes redirectAttributes) {
                
 String email = authentication.getName();
        HostelManager hostelManager=(HostelManager)userRepository.findByEmail(email).get();
        // Validate manager access

        // Prepare pagination
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("requestDate").descending());
        
        // Get filtered requests
        Page<Request> requestsPage = requestService.getFilteredRequests(
            hostelManager.getHostel().getId(), 
            type, 
            status, 
            startDate, 
            endDate, 
            studentId, 
            pageable
        );

        // Get all students for filter dropdown
        List<Hostelite> students =hosteliteService.getHostelitesByHostel(hostelManager.getHostel());

        // Add attributes to model
        model.addAttribute("requests", requestsPage.getContent());
        model.addAttribute("currentPage", requestsPage.getNumber() + 1);
        model.addAttribute("totalPages", requestsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        
        model.addAttribute("totalRequests", requestsPage.getTotalElements());
        model.addAttribute("pendingRequests", requestService.countPendingRequests(hostelManager.getHostel().getId()));
        model.addAttribute("completedRequests", requestService.countCompletedRequests(hostelManager.getHostel().getId()));
        
        model.addAttribute("students", students);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("studentId", studentId);
        model.addAttribute("manager", hostelManager);

        return "manager/history";
    }
}