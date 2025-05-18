package com.example.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Models.*;
import java.util.List;
public interface MessOffRequestRepository extends JpaRepository<MessOffRequest, Long> {
    List<MessOffRequest> findByStatus(Request.RequestStatus status);
 
     // Count pending mess-off requests
    @Query("SELECT COUNT(m) FROM MessOffRequest m WHERE m.status = :status")
    long countByStatus(@Param("status") Request.RequestStatus status);
}
