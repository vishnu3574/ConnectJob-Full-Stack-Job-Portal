package com.connectjob.repository;

import com.connectjob.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJob_IdAndUser_Id(Long jobId, Long userId);
    List<Application> findByUser_IdOrderByAppliedAtDesc(Long userId);
    List<Application> findByJob_IdOrderByAppliedAtDesc(Long jobId);
}
