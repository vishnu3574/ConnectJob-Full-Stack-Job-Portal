package com.connectjob.controller;

import com.connectjob.model.Job;
import com.connectjob.model.User;
import com.connectjob.repository.JobRepository;
import com.connectjob.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobController(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Get all jobs - with search
    @GetMapping
    public List<Job> getAllJobs(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) {
            return jobRepository.findAllByOrderByCreatedAtDesc();
        }
        return jobRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(q, q);
    }

    // Get single job
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }

    // Create job - only employer can post
    @PostMapping
    public Job createJob(@RequestBody Job job, Authentication authentication) {
        if (authentication != null) {
            User user = userRepository.findByEmail(authentication.getName()).orElse(null);
            if (user != null) {
                job.setRecruiter(user);
            }
        }
        job.setId(null); // Ensure new job
        return jobRepository.save(job);
    }

    // Delete job
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }

    // Get my posted jobs - for employer dashboard
    @GetMapping("/my-posted")
    public List<Job> myPostedJobs(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return jobRepository.findByRecruiterId(user.getId());
    }
}
