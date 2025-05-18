package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.Models.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
    
    /**
     * Finds a hostel by the manager's email address
     * @param email The email address of the hostel manager
     * @return Optional containing the Hostel if found, empty otherwise
     */
    Optional<Hostel> findById(Long id);


    
    
    // Other repository methods as needed...
}