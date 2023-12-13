package com.example.jobfinder.repositories;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.serviceImpls.JobSearchCriteria;

public interface JobRepository extends JpaRepository <Job, Long> {
    // add custom queries here

    
    @Query
    static
 Page<Job> searchByQuery(
        @Param("title") String title,
        @Param("description") String description,
        @Param("category") String category,
        Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByQuery'");
    }
}