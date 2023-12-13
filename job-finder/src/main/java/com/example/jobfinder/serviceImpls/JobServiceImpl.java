package com.example.jobfinder.serviceImpls;

import java.util.Map;
import java.util.Optional;

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
    private JobRepository jobRepository;
    
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    @Override
    public Job createJob(Job job) {
        Job newJob = jobRepository.save(job);
        return newJob;
    }

    @Override
    public Job getJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent() ) {
            Job foundJob = optionalJob.get();
            return foundJob;
        }
        throw new JobNotFoundException(id);
    }


    @Override
    public Job updateJob(Long id, Job job) {
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
        jobRepository.deleteById(id);
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


