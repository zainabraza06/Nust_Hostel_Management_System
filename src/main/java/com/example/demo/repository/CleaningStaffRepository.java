package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Models.*;
import java.util.List;

public interface CleaningStaffRepository extends JpaRepository<CleaningStaff, Long> {
    List<CleaningStaff> findByStaffTypeAndHostel(CleaningStaff.StaffType staff, Hostel hostel);
    
    public CleaningStaff findByEmail(String email);
}