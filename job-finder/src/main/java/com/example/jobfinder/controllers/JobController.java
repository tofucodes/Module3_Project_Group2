package com.example.jobfinder.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.services.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService){
        this.jobService= jobService;

    }

    @PostMapping("")
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        Job newJob = jobService.createJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }

    
}
