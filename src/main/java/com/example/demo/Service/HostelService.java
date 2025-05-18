package com.example.demo.Service;

import com.example.demo.repository.HostelRepository;


import org.springframework.stereotype.Service;
import com.example.demo.Models.Hostel;



@Service
public class HostelService {
    private final HostelRepository hostelRepository;
    
    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }
    
    public Hostel getHostelForManager(Long id) {
        return hostelRepository.findById(id).get();
    }
}