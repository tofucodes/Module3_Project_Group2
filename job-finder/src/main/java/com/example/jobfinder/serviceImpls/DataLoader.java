package com.example.jobfinder.serviceImpls;

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
        jobRepository.save(new Job("Unknown", null, "Operational & Technical", 0, 0, "United Kingdom"));
        jobRepository.save(new Job("Carpenter", null, "Professional", 0, 0, "Singapore"));
        jobRepository.save(new Job("Civil Servant", null, "Supervisors & Managers", 0, 0, "Singapore"));
        jobRepository.save(new Job("Lawyer", null, "Professional", 0, 0, "Singapore"));
        jobRepository.save(new Job("Fashion Designer", null, "Supervisors & Managers", 0, 0, "Singapore"));
        jobRepository.save(new Job("Manager", null, "Supervisors & Managers", 0, 0, "Singapore"));
        jobRepository.save(new Job("Housewife", null, null, 0, 0, "Singapore"));
        jobRepository.save(new Job("Doctor", null, "Professional", 0, 0, "Singapore"));
        jobRepository.save(new Job("Student", null, null, 0, 0, "Singapore"));
        jobRepository.save(new Job("Nurse", null, "Professional", 0, 0, "Singapore"));

        userRepository.save(new User("John", "Doe", "johndoe@gmail.com", "Singapore"));
        userRepository.save(new User("Gary", "Tay", "garytay@gmail.com", "Singapore"));
        userRepository.save(new User("David", "Tay", "davidtay@gmail.com", "Singapore"));
        userRepository.save(new User("Tammy", "Tay", "tammytay@gmail.com", "Singapore"));
        userRepository.save(new User("Vicky", "Tay", "vickytay@gmail.com", "Singapore"));
        userRepository.save(new User("Charlie", "Tay", "charlietay@gmail.com", "Singapore"));
        userRepository.save(new User("Soo Mei", "Tay", "soomeitay@gmail.com", "Singapore"));
        userRepository.save(new User("Teck Ann", "Tan", "teckanntan@gmail.com", "Singapore"));
        userRepository.save(new User("Chee Keong", "Tan", "cheekeongtan@gmail.com", "Singapore"));
        userRepository.save(new User("Shirley", "Ho", "shirleyho@gmail.com", "Singapore"));
    }
}
