package com.example.demo.Service;


import com.example.demo.Controllers.HosteliteController;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Models.*;
import com.example.demo.Models.Request.RequestType;
import com.example.demo.dto.*;
import com.example.demo.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.temporal.ChronoUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;

@Service
public class RequestService {




    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private HosteliteService hosteliteService;


    @Autowired 
    private MessOffRequestRepository messOffRequestRepository;


    @Autowired 
    private LeaveRequestRepository leaveRequestRepository;


    @Autowired
    private CleaningRequestRepository cleaningRequestRepository;


    @Autowired
    private CleaningStaffRepository cleaningStaffRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   


     private static final int PAGE_SIZE = 10;



    @Transactional
public MessOffRequest createMessOffRequest(String email, LocalDate startDate, LocalDate endDate, String reason) {
    Hostelite hostelite = hosteliteService.getHosteliteByEmail(email);
    if (hostelite == null) {
        throw new RuntimeException("Hostelite not found");
    }

     // Calculate requested days
    long requestedDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
    
    // Check minimum duration (must be at least 2 days)
    if (requestedDays < 2) {
        throw new RuntimeException("Mess-off request must be for at least 2 days");
    }
    
    // Check if request is for current month
    if (startDate.getMonth() != LocalDate.now().getMonth() || 
        endDate.getMonth() != LocalDate.now().getMonth()) {
        throw new RuntimeException("Mess-off requests must be within the current month");
    }
    
    // Check if start date is before end date
    if (startDate.isAfter(endDate)) {
        throw new RuntimeException("Start date must be before or equal to end date");
    }
    
    // Check if dates are in the future
    if (startDate.isBefore(LocalDate.now())) {
        throw new RuntimeException("Mess-off cannot be requested for past dates");
    }

    // Check monthly limit
    MessOffStats stats = getMessOffStats(hostelite);
    if (stats.getRemainingDays() < requestedDays) {
        throw new RuntimeException(String.format(
            "Only %d mess-off days remaining. Requested %d days would exceed limit.",
            stats.getRemainingDays(), requestedDays));
    }

    // Check for overlapping requests
    List<MessOffRequest> approvedRequests = requestRepository
        .findApprovedMessOffRequestsForMonth(hostelite, LocalDate.now().getMonthValue(), LocalDate.now().getYear(), Request.RequestStatus.APPROVED);
    
    boolean hasOverlap = approvedRequests.stream()
        .anyMatch(req -> !(endDate.isBefore(req.getStartDate()) || startDate.isAfter(req.getEndDate())));
    
    if (hasOverlap) {
        throw new RuntimeException("Your request overlaps with an existing approved mess-off period");
    }

    // Create and save the request
    MessOffRequest request = new MessOffRequest();
    request.setRequestId("MO-" + UUID.randomUUID().toString().substring(0, 8));
    request.setRequestDate(LocalDate.now());
    request.setStatus(Request.RequestStatus.PENDING);
    request.setStartDate(startDate);
    request.setEndDate(endDate);
    request.setReason(reason);
    
      hostelite.addRequest(request);
      request.setHostelite(hostelite);
    
    return requestRepository.save(request);
}



public int getPendingRequestsCount(Hostelite hostelite) {
        return requestRepository.countByHosteliteAndStatus(hostelite, Request.RequestStatus.PENDING);
    }

    public int getApprovedRequestsCount(Hostelite hostelite) {
        return requestRepository.countByHosteliteAndStatus(hostelite, Request.RequestStatus.APPROVED);
    }
    @Transactional
    public LeaveRequest createLeaveRequest(String email, LocalDate startDate, LocalDate endDate, 
                                         String reason, String emergencyContact) {
        Hostelite hostelite = hosteliteService.getHosteliteByEmail(email);
        if (hostelite == null) {
            throw new RuntimeException("Hostelite not found");
        }
        
        LeaveRequest request = new LeaveRequest();
        request.setRequestId("LV-" + UUID.randomUUID().toString().substring(0, 8));
        request.setRequestDate(LocalDate.now());
        request.setStatus(Request.RequestStatus.PENDING);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setReason(reason);
        request.setEmergencyContact(emergencyContact);
          hostelite.addRequest(request);
           request.setHostelite(hostelite);
         request.setManager(hostelite.getHostel().getManager());
        
        return requestRepository.save(request);
    }
    


    @Transactional
    public CleaningRequest createCleaningRequest(String email, String roomNumber, 
                                          CleaningRequest.CleaningType type, String remarks) {
        Hostelite hostelite = hosteliteService.getHosteliteByEmail(email);
        if (hostelite == null) {
            throw new RuntimeException("Hostelite not found");
        }
        
        CleaningRequest request = new CleaningRequest();
        request.setRequestId("CL-" + UUID.randomUUID().toString().substring(0, 8));
        request.setRequestDate(LocalDate.now());
        request.setStatus(Request.RequestStatus.PENDING);
        request.setRoomNumber(roomNumber);
        request.setType(type);
        request.setRemarks(remarks);
          hostelite.addRequest(request);
        request.setHostelite(hostelite);
         request.setManager(hostelite.getHostel().getManager());
        return requestRepository.save(request);
    }


    
public Page<Request> getRequestsByHostelite(
            Hostelite hostelite, 
            int page, 
            String type, 
            String status,
            String startDate,
            String endDate) {
        
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("createdAt").descending());
        
        if (!"all".equalsIgnoreCase(type)) {
            Request.RequestType requestType = Request.RequestType.valueOf(type.toUpperCase().replace("-", "_"));
            if (!"all".equalsIgnoreCase(status)) {
                Request.RequestStatus requestStatus = Request.RequestStatus.valueOf(status.toUpperCase());
                return requestRepository.findByHosteliteAndTypeAndStatusOrderByCreatedAtDesc(
                    hostelite, 
                    requestType,
                    requestStatus,
                    pageable
                );
            }
            return requestRepository.findByHosteliteAndTypeOrderByCreatedAtDesc(
                hostelite, 
                requestType, 
                pageable
            );
        } else if (!"all".equalsIgnoreCase(status)) {
            Request.RequestStatus requestStatus = Request.RequestStatus.valueOf(status.toUpperCase());
            return requestRepository.findByHosteliteAndStatusOrderByCreatedAtDesc(
                hostelite, 
                requestStatus, 
                pageable
            );
        } else if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
            return requestRepository.findByHosteliteAndCreatedAtBetweenOrderByCreatedAtDesc(
                hostelite,
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay(),
                pageable
            );
        }
        return requestRepository.findByHosteliteOrderByCreatedAtDesc(hostelite, pageable);
    }




    @Transactional
    public void cancelRequest(Long requestId, String reason, Hostelite hostelite) {
        Request request = requestRepository.findByIdAndHostelite(requestId, hostelite);
        
        if (request.getStatus() != Request.RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be cancelled");
        }
        
        request.setStatus(Request.RequestStatus.CANCELLED);
        requestRepository.save(request);
    }

    public Request getRequestDetails(Long requestId, Hostelite hostelite) {
        return requestRepository.findByIdAndHostelite(requestId, hostelite);
       
    }



    public MessOffStats getMessOffStats(Hostelite hostelite) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        
        List<MessOffRequest> requests = requestRepository.findApprovedMessOffRequestsForMonth(
            hostelite, month, year,Request.RequestStatus.APPROVED);
        
        long usedDays = requests.stream()
            .mapToLong(req -> ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1)
            .sum();
        
        return new MessOffStats(usedDays, 12, 12 - usedDays, (int) ((usedDays * 100) / 12));
    }

    public LeaveStats getLeaveStats(Hostelite hostelite) {
        long pendingCount = requestRepository.countByHosteliteAndTypeAndStatus(
            hostelite, Request.RequestType.LEAVE, Request.RequestStatus.PENDING);
        long totalCount = requestRepository.countByHosteliteAndType(hostelite, Request.RequestType.LEAVE);
        
        int percentage = totalCount == 0 ? 0 : (int) ((pendingCount * 100) / totalCount);
        return new LeaveStats(pendingCount, percentage);
    }

    public CleaningStats getCleaningStats(Hostelite hostelite) {
        CleaningRequest lastCleaning = requestRepository.findFirstByHosteliteOrderByRequestDateDesc(hostelite);

        
        long daysSince = lastCleaning == null ? -1 : 
            ChronoUnit.DAYS.between(lastCleaning.getRequestDate(), LocalDate.now());
        
        return new CleaningStats(daysSince, 
            lastCleaning != null ? lastCleaning.getRequestDate() : null, 
            daysSince == -1 ? 0 : (int) Math.min(daysSince * 10, 100));
    }


       public List<Request> getRecentRequests(Hostelite hostelite, int count) {
        List<Request> requests= requestRepository.findTop5ByHosteliteOrderByRequestDateDesc(hostelite);
        System.out.println("Found recent requests: " + requests);
        return requests;

    }

    public List<Request> getAllRequests(Hostelite hostelite) {
        return requestRepository.findByHosteliteOrderByRequestDateDesc(hostelite);
    }





    // Mess-off requests
    public List<MessOffRequest> getPendingMessOffRequests() {
        return messOffRequestRepository.findByStatus(Request.RequestStatus.PENDING);
    }
    
    public MessOffRequest approveMessOffRequest(Long requestId, String remarks) {
        MessOffRequest request = messOffRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Mess-off request not found"));
        
        request.setStatus(Request.RequestStatus.APPROVED);
        request.setManagerRemarks(remarks);
        return messOffRequestRepository.save(request);
    }
    
    public MessOffRequest rejectMessOffRequest(Long requestId, String rejectionReason) {
        MessOffRequest request = messOffRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Mess-off request not found"));
        
        request.setStatus(Request.RequestStatus.REJECTED);
        request.setManagerRemarks(rejectionReason);
        return messOffRequestRepository.save(request);
    }
    
    // Leave requests
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus(Request.RequestStatus.PENDING);
    }
    
    public LeaveRequest approveLeaveRequest(Long requestId, String remarks) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        
        request.setStatus(Request.RequestStatus.APPROVED);
        request.setManagerRemarks(remarks);
        return leaveRequestRepository.save(request);
    }
    
    public LeaveRequest rejectLeaveRequest(Long requestId, String rejectionReason) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        
        request.setStatus(Request.RequestStatus.REJECTED);
        request.setManagerRemarks(rejectionReason);
        return leaveRequestRepository.save(request);
    }
    
    // Cleaning requests
    public List<CleaningRequest> getPendingCleaningRequests() {
        return cleaningRequestRepository.findByStatus(Request.RequestStatus.PENDING);
    }
    
    public List<CleaningStaff> getAvailableCleaningStaff(Hostel hostel) {
        return cleaningStaffRepository.findByStaffTypeAndHostel(CleaningStaff.StaffType.CLEANER, hostel);
    }
    
    public CleaningRequest assignCleaningStaff(Long requestId, Long staffId, String instructions) {
        CleaningRequest request = cleaningRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Cleaning request not found"));
        
        CleaningStaff staff = cleaningStaffRepository.findById(staffId)
            .orElseThrow(() -> new ResourceNotFoundException("Staff member not found"));
        
        request.setStatus(Request.RequestStatus.APPROVED);
        request.setCleaningStaff(staff);
        return cleaningRequestRepository.save(request);
    }
    
    public CleaningRequest rejectCleaningRequest(Long requestId, String rejectionReason) {
        CleaningRequest request = cleaningRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Cleaning request not found"));
        
        request.setStatus(Request.RequestStatus.REJECTED);
        return cleaningRequestRepository.save(request);
    }
    
    public long countPendingRequests() {
        long messOffCount = messOffRequestRepository.countByStatus(Request.RequestStatus.PENDING);
        long leaveCount = leaveRequestRepository.countByStatus(Request.RequestStatus.PENDING);
        long cleaningCount = cleaningRequestRepository.countByStatus(Request.RequestStatus.PENDING);
        
        return messOffCount + leaveCount + cleaningCount;
    }


 
    public Page<Request> getFilteredRequests(Long hostelId, String type, String status, 
                                           LocalDate startDate, LocalDate endDate, 
                                           Long studentId, Pageable pageable) {
        
        RequestType requestType = type != null ? RequestType.valueOf(type) : null;
        Request.RequestStatus requestStatus = status != null ? Request.RequestStatus.valueOf(status) : null;
        
        return requestRepository.findByFilters(
            hostelId, 
            requestType, 
            requestStatus, 
            startDate, 
            endDate, 
            studentId, 
            pageable
        );
    }


    public long countPendingRequests(Long hostelId) {
        return requestRepository.countByHostelite_Hostel_IdAndStatus(hostelId, Request.RequestStatus.PENDING);
    }


    public long countCompletedRequests(Long hostelId) {
        return requestRepository.countByHostelite_Hostel_IdAndStatus(hostelId, Request.RequestStatus.COMPLETED);
    }

 
    public Optional<Request> getRequestById(Long requestId) {
        return requestRepository.findById(requestId);
    }


  public List<Request> getRecentRequests(Long hostelId, int count) {
    Pageable pageable = PageRequest.of(0, count);
    return requestRepository.findTopByHosteliteHostelIdOrderByRequestDateDesc(hostelId, pageable);
}


}