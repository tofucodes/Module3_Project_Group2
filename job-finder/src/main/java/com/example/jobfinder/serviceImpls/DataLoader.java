package com.example.jobfinder.serviceImpls;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private JobRepository jobRepository;
    private UserRepository userRepository;

    public DataLoader(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // @Autowired
    // public DataLoader(JobRepository jobRepository) {
    //     this.jobRepository = jobRepository;
    // }

    // @Autowired
    // public DataLoader(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @PostConstruct
    public void loadData() {
        // clear the database first
        jobRepository.deleteAll();
        userRepository.deleteAll();
        
        // load data here
        jobRepository.save(new Job("Plumber", "Plumbers install, repair and maintain pipes, fixtures and other plumbing equipment used for water distribution and waste water disposal in residential, commercial and industrial buildings.", "Operational & Technical", 45000, 0, "United Kingdom"));
        jobRepository.save(new Job("Carpenter", "Carpenters construct, install, and repair a variety of residential, commercial, and industrial structures and fixtures.", "Operational & Technical", 50000, 10, "Singapore"));
        jobRepository.save(new Job("Civil Servant", "Civil servants protect, educate, provide for and help their fellow people.", "Supervisors & Managers", 96000, 8, "Singapore"));
        jobRepository.save(new Job("Lawyer", "Advise and represent clients in criminal or civil proceedings and in other legal matters.", "Professional", 200000, 4, "Singapore"));
        jobRepository.save(new Job("Fashion Designer", "Fashion designers sketch designs of clothing, footwear, and accessories.", "Supervisors & Managers", 80000, 6, "Singapore"));
        jobRepository.save(new Job("Manager", "Maintains staff by recruiting, selecting, orienting, and training employees. Ensures a safe, secure, and legal work environment.", "Supervisors & Managers", 100000, 10, "Singapore"));
        jobRepository.save(new Job("Housewife", "A housewife (also known as a homemaker) is a woman whose work is running or managing her family's home", "Executive", 0, 10, "Singapore"));
        jobRepository.save(new Job("Doctor", "Prescribe medications, refer patients to specialized medical professionals and conduct tests to diagnose injuries, illnesses or diseases.", "Professional", 200000, 12, "Singapore"));
        jobRepository.save(new Job("Student", "Study", "Entry-Level", 0, 0, "Singapore"));
        jobRepository.save(new Job("Nurse", "Nurses plan and provide medical and nursing care to patients in hospital, at home or in other settings.", "Professional", 90000, 4, "Singapore"));

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
