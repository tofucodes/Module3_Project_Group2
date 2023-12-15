package com.example.jobfinder.serviceImpls;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.exceptions.JobNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.services.JobService;

@Service
public class JobServiceImpl implements JobService{

    private final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private JobRepository jobRepository;
    
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    @Override
    public Job createJob(Job job) {
        logger.info("createJob method being called");

        Job newJob = jobRepository.save(job);
        return newJob;
    }

    @Override
    public Job getJob(Long id) {
        logger.info("getJob method being called");

        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent() ) {
            Job foundJob = optionalJob.get();
            return foundJob;
        }
        throw new JobNotFoundException(id);
    }


    @Override
    public Job updateJob(Long id, Job job) {
        logger.info("updateJob method being called");

         // retrieve the job from the database
         Job jobToUpdate = jobRepository.findById(id).get();
         // update the job retrieved from the database
         jobToUpdate.setTitle(job.getTitle());
         jobToUpdate.setDescription(job.getDescription());
         jobToUpdate.setCategory(job.getCategory());
         jobToUpdate.setSalary(job.getSalary());
         jobToUpdate.setYearsOfExperience(job.getYearsOfExperience());
         jobToUpdate.setCountry(job.getCountry());
         // save the updated job back to the database
        jobRepository.save(jobToUpdate);
        return jobToUpdate;

    }

    @Override
    public void deleteJob(Long id) {
        logger.info("deleteJob method being called");

        jobRepository.deleteById(id);
    }

    @Override
    public ArrayList<Job> findJobsByParam(String category, Double minSalary, Double maxSalary) {
        logger.info("findJobsByParam method being called");

        List<Job> filteredJobs;
        
        if(category == null && minSalary == null && maxSalary == null) {
            // no param is being passed in, so return all
            filteredJobs = getAllJobs();
        } else if (category == null) {
            // category is null but either minAmount or maxAmount is not null
            if (minSalary == null) {
                // since no minAmount is set, set minAmount to 0 to include all records from 0 onwards
                minSalary = 0.0;
            }
            if (maxSalary == null) {
                // since no maxAmount is set, set maxAmount to biggest value to include all records lesser than biggest value
                maxSalary = Double.MAX_VALUE;
            }
            filteredJobs = jobRepository.findBySalaryBetween(minSalary, maxSalary);
        } else {
            // category is not null, minAmount and maxAmount may be null
            if (minSalary == null) {
                // since no minAmount is set, set minAmount to 0 to include all records from 0 onwards
                minSalary = 0.0;
            }
            if (maxSalary == null) {
                // since no maxAmount is set, set maxAmount to biggest value to include all records lesser than biggest value
                maxSalary = Double.MAX_VALUE;
            }
            filteredJobs = jobRepository.findByCategoryAndSalaryBetween(category, minSalary, maxSalary);
        }
        
        if (filteredJobs.isEmpty()) {
            throw new JobNotFoundException();
        }
        return (ArrayList<Job>) filteredJobs;
    }

    @Override
    public ArrayList<Job> getAllJobs() {

        logger.info("getAllJobs method being called");

        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            throw new JobNotFoundException();
        }
        return (ArrayList<Job>) jobs;
    }
}
    //issue with the .map method below
    // public Page<Job> searchByCriteria(JobSearchCriteria criteria, Pageable pageable) {
    //     return JobRepository.searchByQuery(
    //         criteria.getTitle(),
    //         criteria.getDescription(),
    //         criteria.getCategory(),
    //         pageable
    //     ).map(Page<Job>::Job);


