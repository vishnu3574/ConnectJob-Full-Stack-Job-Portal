package com.connectjob.repository;

import com.connectjob.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Search by title or company - for search bar
    List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company);

    // For employer - get his posted jobs
    List<Job> findByRecruiterId(Long recruiterId);

    // Latest jobs first - for home page
    List<Job> findAllByOrderByCreatedAtDesc();
}
