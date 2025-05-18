package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.Models.*;
import java.util.List;


@Repository
public interface HosteliteRepository extends JpaRepository<Hostelite, Long> {
    
    Optional<Hostelite> findByEmail(String email);
    
   List<Hostelite> findByHostel(Hostel hostel);
}
