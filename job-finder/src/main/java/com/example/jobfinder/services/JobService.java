package com.example.jobfinder.services;

import com.example.jobfinder.entities.Job;

public interface JobService {
    Job createJob(Job job);

    Job getJob(Long id);

    Job updateJob(Long id, Job job);
    
    void deleteJob(Long id);

    // Job searchByCriteria(String title, String description, String category);


}
