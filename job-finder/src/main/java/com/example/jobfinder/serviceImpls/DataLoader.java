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

    // clear the database first

    // load data here
    jobRepository.save(new Job("Analyst"));
    jobRepository.save(new Job("Project Manager"));
    jobRepository.save(new Job("Scientist"));
    jobRepository.save(new Job("Lab Tech"));

    }

}
