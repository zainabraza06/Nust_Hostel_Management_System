package com.example.demo.Service;


import com.example.demo.Models.*;

import com.example.demo.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class CleaningStaffService{

    private final CleaningStaffRepository cleaningStaffRepository;

    public CleaningStaffService(CleaningStaffRepository cleaningStaffRepository) {
        this.cleaningStaffRepository = cleaningStaffRepository;
    }

    public CleaningStaff getStaffById(Long id) {
        return cleaningStaffRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cleaning staff not found"));
    }

 public CleaningStaff findByEmail(String email){
    return cleaningStaffRepository.findByEmail(email);
 }

    public List<CleaningStaff> getAllStaff() {
        return cleaningStaffRepository.findAll();
    }
}