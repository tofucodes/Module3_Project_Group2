package com.example.jobfinder.serviceImpls;

import org.springframework.stereotype.Component;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.repositories.JobRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private JobRepository jobRepository;

    public DataLoader(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostConstruct
    public void loadData(){
        jobRepository.deleteAll();

        // jobRepository.save(new Job("Software Engineer","lorem ipsum", "Information Technology", 4500, 5, "Singapore"));

    // clear the database first
    // load data here

    }

}
