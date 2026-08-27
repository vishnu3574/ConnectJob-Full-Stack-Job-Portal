package com.connectjob.config;

import com.connectjob.model.Job;
import com.connectjob.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(JobRepository jobRepository) {
        return args -> {
            if (jobRepository.count() == 0) {
                
                Job job1 = new Job();
                job1.setTitle("Java Spring Boot Developer");
                job1.setCompany("TechNova");
                job1.setLocation("Hyderabad");
                job1.setType("Full-time");
                job1.setDescription("Build scalable REST APIs and backend services using Spring Boot.");
                job1.setSkills("Java, Spring Boot, MySQL, REST API");
                job1.setSalary("6-10 LPA");

                Job job2 = new Job();
                job2.setTitle("React Frontend Developer");
                job2.setCompany("CloudWorks");
                job2.setLocation("Bengaluru");
                job2.setType("Full-time");
                job2.setDescription("Create responsive and modern web dashboards.");
                job2.setSkills("React, JavaScript, HTML, CSS");
                job2.setSalary("5-8 LPA");

                Job job3 = new Job();
                job3.setTitle("Full Stack Developer");
                job3.setCompany("InnoSoft");
                job3.setLocation("Chennai");
                job3.setType("Full-time");
                job3.setDescription("Develop complete end-to-end applications.");
                job3.setSkills("React, Spring Boot, MySQL");
                job3.setSalary("7-12 LPA");

                jobRepository.saveAll(List.of(job1, job2, job3));
                
                System.out.println("Dummy Jobs Added Successfully!");
            }
        };
    }
}
