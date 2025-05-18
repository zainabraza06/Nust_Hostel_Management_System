package com.example.demo.Controllers;

import com.example.demo.Models.CleaningRequest;
import com.example.demo.Models.Hostelite;
import com.example.demo.Service.HosteliteService;
import com.example.demo.Service.RequestService;
import com.example.demo.repository.HosteliteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Page;
import com.example.demo.Models.Request;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;

@Controller
@RequestMapping("/hostelite/dashboard")
public class HosteliteController {
    private static final Logger logger = LoggerFactory.getLogger(HosteliteController.class);

    
    @Autowired
    private HosteliteService hosteliteService;
    
    @Autowired
    private RequestService requestService;

    @Autowired

    private HosteliteRepository hosteliteRepository;
    
   @GetMapping
    public String showDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        Hostelite hostelite = hosteliteService.getHosteliteByEmail(email);
        logger.info("Hostelite ID: {}", hostelite.getId());



        // Add basic hostelite info
        model.addAttribute("hostelite", hostelite);
        model.addAttribute("today", LocalDate.now());

        // Add request statistics
        model.addAttribute("pendingRequestCount", requestService.getPendingRequestsCount(hostelite));
        model.addAttribute("approvedRequestCount", requestService.getApprovedRequestsCount(hostelite));

        // Add recent requests (last 5)
        List<Request> recentRequests = requestService.getRecentRequests(hostelite, 5);
        
        logger.info("printing : "+ recentRequests);
        logger.info("printing:" +requestService.getPendingRequestsCount(hostelite));
         logger.info("printing:" +requestService.getApprovedRequestsCount(hostelite));
        model.addAttribute("recentRequests", recentRequests);
         
        logger.info("printing " + requestService.getMessOffStats(hostelite));
           logger.info("printing " + requestService.getLeaveStats(hostelite));
              logger.info("printing " +  requestService.getCleaningStats(hostelite));

        // Add stats objects
        model.addAttribute("messOffStats", requestService.getMessOffStats(hostelite));
        model.addAttribute("leaveStats", requestService.getLeaveStats(hostelite));
        model.addAttribute("cleaningStats", requestService.getCleaningStats(hostelite));

        return "hostelite/dashboard";
    }
    
    @PostMapping("/mess-off/request")
    public String submitMessOffRequest(@RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam("reason") String reason,
                                      RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
         Hostelite hostelite = hosteliteService.getHosteliteByEmail(email);
        
        try {
            requestService.createMessOffRequest(email, LocalDate.parse(startDate), LocalDate.parse(endDate), reason);
            redirectAttributes.addFlashAttribute("success", "Mess-off request submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit mess-off request: " + e.getMessage());
        }
        
        return "redirect:/hostelite/dashboard";
    }
    
    @PostMapping("/leave/request")
    public String submitLeaveRequest(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("reason") String reason,
                                   @RequestParam("emergencyContact") String emergencyContact,
                                   RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        try {
            requestService.createLeaveRequest(email, LocalDate.parse(startDate), LocalDate.parse(endDate), 
                             reason, emergencyContact);
            redirectAttributes.addFlashAttribute("success", "Leave request submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit leave request: " + e.getMessage());
        }
        
        return "redirect:/hostelite/dashboard";
    }
    
    @PostMapping("/cleaning/request")
    public String submitCleaningRequest(@RequestParam("roomNumber") String roomNumber,
                                      @RequestParam(value="cleaningType", required=false) String type,
                                      @RequestParam(value = "remarks", required = false) String remarks,
                                      RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        try {
            requestService.createCleaningRequest(email, roomNumber, CleaningRequest.CleaningType.valueOf(type), remarks);
            redirectAttributes.addFlashAttribute("success", "Cleaning request submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit cleaning request: " + e.getMessage());
        }
        
        return "redirect:/hostelite/dashboard";
    }



    // @GetMapping("/view/requests")
    @GetMapping("/view/requests")
    public String getRequestHistory(

            Authentication authentication,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {

                 String email = authentication.getName();
                 Hostelite hostelite = hosteliteRepository.findByEmail(email).get();


        
        Page<Request> requests = requestService.getRequestsByHostelite(
            hostelite, 
            page, 
            type, 
            status,
            startDate,
            endDate
        );
        
        model.addAttribute("requests", requests.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", requests.getTotalPages());
        model.addAttribute("hostelite", hostelite);
        
        return "hostelite/recent-history";
    }

    @PostMapping("/{id}/cancel")
    public String cancelRequest(
            @PathVariable Long id,
            @RequestParam(required = false) String reason,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

                   String email = authentication.getName();
                 Hostelite hostelite = hosteliteRepository.findByEmail(email).get();
        
        try {
            requestService.cancelRequest(id, reason, hostelite);
            redirectAttributes.addFlashAttribute("success", "Request cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/requests";
    }
}