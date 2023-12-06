package com.example.jobfinder.serviceImpls;

import org.springframework.stereotype.Service;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.services.JobService;

@Service
public class JobServiceImpl implements JobService  {

    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job createJob(Job job){
        Job newJob = jobRepository.save(job);
        return newJob;
    }
    
}
