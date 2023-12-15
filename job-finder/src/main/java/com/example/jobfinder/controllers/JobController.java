package com.example.jobfinder.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.exceptions.JobNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.services.JobService;
import com.example.jobfinder.services.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Locale.Category;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("")
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        Job newJob = jobService.createJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }

    // @GetMapping("")
    // public ResponseEntity<ArrayList<Job>> getallJobs(){
    //     ArrayList<Job> allJobs = jobService.getallJobs();
    //     return new ResponseEntity<>(allJobs, HttpStatus.OK);
    // }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        Job foundJob = jobService.getJob(id);
        return new ResponseEntity<>(foundJob, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = jobService.updateJob(id, job);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);        
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Job> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//     // Search
//     @GetMapping("/search")
//     public ResponseEntity<Page<Job>> searchJobsByQuery(
//         @RequestParam(value = "title", required = false) String title,
//         @RequestParam(value = "description", required = false) String description,
//         @RequestParam(value = "category", required = false) String category, Pageable pageable
//         ) {
//        Page<Job> result = JobRepository.searchByQuery(title, description, category, pageable);
//         return ResponseEntity.ok(result);
// }

    @GetMapping({ "", "/" })
        public ResponseEntity<?> getAllJobs(
                @RequestParam(required = false) String category,
                @RequestParam(required = false) Double minSalary,
                @RequestParam(required = false) Double maxSalary) {
            try {
                ArrayList<Job> results = jobService.findJobsByParam(category, minSalary, maxSalary);
                return ResponseEntity.status(HttpStatus.OK).body(results);
            } catch (JobNotFoundException ex) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

}
