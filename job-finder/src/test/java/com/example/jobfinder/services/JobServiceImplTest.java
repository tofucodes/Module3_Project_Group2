package com.example.jobfinder.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.exceptions.JobNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.serviceImpls.JobServiceImpl;

@SpringBootTest
public class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    JobServiceImpl jobServiceImpl;

    @Test
    public void testCreateJob() {
        Job job = new Job("Test QA Engineer", "lorem ipsum","on-site",5300, 3, "Singapore");

        when((jobRepository.save(job))).thenReturn(job);

       Job savedJob = jobServiceImpl.createJob(job);

        assertEquals(job, savedJob, "The saved job should be the same as the new job.");

        // also verify that the save method of the customer repository is called once only.
        verify(jobRepository, times(1)).save(job);
    }
    
    @Test
    public void testGetJob() {
        Job job = new Job("Test QA Engineer", "lorem ipsum","on-site",5300, 3, "Singapore");
        // instantiating customer id
        Long jobId = 1L;
        when((jobRepository.findById(jobId))).thenReturn(Optional.of(job));

       Job retrievedJob = jobServiceImpl.getJob(jobId);

        assertEquals(job, retrievedJob, "The saved job should be the same as the new job.");
    }
     @Test
    void testGetJobNotFound() {
        Long jobId = 1L;
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());
        assertThrows(JobNotFoundException.class, () -> jobServiceImpl.getJob(jobId));
    }
    @Test
    public void testUpdateJob() {
         Long jobId = 1L;
         Job existingJob = new Job("Testing QA Engineer", "lorem ipsum","remote",5300, 3, "Singapore");
         Job updatedJob = new Job("Test QA Engineer", "lo ipsum","on-site",5700, 3, "Singapore");

         when(jobRepository.findById(jobId)).thenReturn(Optional.of(existingJob));
        when(jobRepository.save(updatedJob)).thenReturn(updatedJob);

        Job result = jobServiceImpl.updateJob(jobId, updatedJob);
        assertEquals(updatedJob.getTitle(), result.getTitle(), "The updated job title should be returned.");
        assertEquals(updatedJob.getDescription(), result.getDescription(), "The updated job description should be returned.");
        assertEquals(updatedJob.getCategory(), result.getCategory(), "The updated job category should be returned.");
        assertEquals(updatedJob.getSalary(), result.getSalary(), "The updated job salary should be returned.");
        assertEquals(updatedJob.getYearsOfExperience(), result.getYearsOfExperience(), "The updated years of experience for this job should be returned.");
        assertEquals(updatedJob.getCountry(), result.getCountry(), "The updated job country should be returned.");


        verify(jobRepository, times(1)).findById(jobId);
        // verify(jobRepository, times(1)).save(updatedJob);
    }

    @Test 
    public void testDeleteJob() {
        Long jobId = 1L;

        jobServiceImpl.deleteJob(jobId);
        verify(jobRepository, times(1)).deleteById(jobId);
    }
    }