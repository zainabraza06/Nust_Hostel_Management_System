package com.example.demo.Service;
import com.example.demo.Models.*;
import com.example.demo.Models.Request;
import com.example.demo.repository.HosteliteRepository;
import com.example.demo.repository.RequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.List;

@Service
public class HosteliteService {
    @Autowired
    private HosteliteRepository hosteliteRepository;

    @Autowired
    private RequestRepository requestRepository;
    
    public Hostelite getHosteliteByEmail(String email) {
        return hosteliteRepository.findByEmail(email).get();
    }
    
    public List<Request> getRecentRequests(Hostelite hostelite) {
        // In a real app, you would implement this with pagination
        return requestRepository.findByHosteliteOrderByRequestDateDesc(hostelite).subList(0, 
        Math.min(requestRepository.findByHosteliteOrderByRequestDateDesc(hostelite).size(), 5));
    }
    
    public int getPendingRequestsCount(Hostelite hostelite) {
        return (int) requestRepository.findByHosteliteOrderByRequestDateDesc(hostelite).stream()
                .filter(r -> r.getStatus() == Request.RequestStatus.PENDING)
                .count();
    }
    
    public int getApprovedRequestsCount(Hostelite hostelite) {
        return (int) requestRepository.findByHosteliteOrderByRequestDateDesc(hostelite).stream()
                .filter(r -> r.getStatus() == Request.RequestStatus.APPROVED)
                .count();
    }


     public  List<Hostelite> getHostelitesByHostel(Hostel hostelId) {
        return hosteliteRepository.findByHostel(hostelId);
    }
}