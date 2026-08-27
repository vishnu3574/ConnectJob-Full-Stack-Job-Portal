package com.connectjob.controller;

import com.connectjob.model.Application;
import com.connectjob.model.Job;
import com.connectjob.model.User;
import com.connectjob.repository.ApplicationRepository;
import com.connectjob.repository.JobRepository;
import com.connectjob.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationController(ApplicationRepository applicationRepository, 
                                 JobRepository jobRepository, 
                                 UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/jobs/{jobId}")
    public ResponseEntity<?> apply(@PathVariable Long jobId, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        if (applicationRepository.existsByJob_IdAndUser_Id(jobId, user.getId())) {
            return ResponseEntity.badRequest().body("Already applied");
        }
        Job job = jobRepository.findById(jobId).orElseThrow();
        Application app = new Application();
        app.setJob(job);
        app.setUser(user);
        return ResponseEntity.ok(applicationRepository.save(app));
    }

    @GetMapping("/my")
    public List<Application> myApps(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return applicationRepository.findByUser_IdOrderByAppliedAtDesc(user.getId());
    }

    @GetMapping("/job/{jobId}")
    public List<Application> jobApps(@PathVariable Long jobId) {
        return applicationRepository.findByJob_IdOrderByAppliedAtDesc(jobId);
    }
}
