package com.example.jobfinder.services;

import java.util.ArrayList;
import java.util.Locale.Category;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.exceptions.JobNotFoundException;

public interface JobService {
    Job createJob(Job job);

    Job getJob(Long id);

    Job updateJob(Long id, Job job);
    
    void deleteJob(Long id);

    // Job searchByCriteria(String title, String description, String category);

    // GET FILTERED
    ArrayList<Job> findJobsByParam(String category, Double minSalary, Double maxSalary);

    // GET ALL
    ArrayList<Job> getAllJobs();
}
