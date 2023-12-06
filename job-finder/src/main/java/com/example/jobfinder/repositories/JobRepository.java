package com.example.jobfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jobfinder.entities.Job;

public interface JobRepository extends JpaRepository <Job, Long> {
    // add custom queries here
} 
