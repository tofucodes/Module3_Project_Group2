package com.example.jobfinder.services;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;

public interface UserService {

    User createUser(User user);

    ArrayList<User> getAllUsers();

    User getUser(Long id);

    User updateUser(Long id, User user);
    
    void deleteUser(Long id);

    Job addJobToUser(Long id, Long job_id);


}
