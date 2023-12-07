package com.example.jobfinder;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

public class DataLoader {
    private JobRepository jobRepository;
    private UserRepository userRepository;

    @Autowired
    public DataLoader(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void loadData() {
        // clear the database first
        jobRepository.deleteAll();
        userRepository.deleteAll();

        // load data here
        jobRepository.save(new Job("Manager", null, null, 0, 0, null));

        userRepository.save(new User("John", "Doe", null, null));
    }
}
